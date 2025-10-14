const express = require('express');
const mongoose = require('mongoose');
const { body, validationResult } = require('express-validator');
const { User, Chat, Message } = require('../models');
const { authMiddleware, requireChatParticipant, requireGroupAdmin, asyncHandler, AppError } = require('../middleware');

const router = express.Router();

/**
 * @route   GET /api/chats
 * @desc    Get user's chats
 * @access  Private
 */
router.get('/', authMiddleware, asyncHandler(async (req, res) => {
  const { 
    limit = 20, 
    skip = 0, 
    includeArchived = false,
    isGroup = null 
  } = req.query;

  let query = {
    participants: req.user._id,
    isActive: true,
    isDeleted: false
  };

  // Filter by group chats if specified
  if (isGroup !== null) {
    query.isGroup = isGroup === 'true';
  }

  // Include archived chats if requested
  if (includeArchived !== 'true') {
    query.isArchived = false;
  }

  const chats = await Chat.find(query)
    .populate('participants', 'username firstName lastName avatar status lastSeen')
    .populate('admins', 'username firstName lastName avatar')
    .populate('lastMessage')
    .populate('lastMessageSender', 'username firstName lastName')
    .sort({ lastMessageAt: -1 })
    .limit(parseInt(limit))
    .skip(parseInt(skip));

  // Format response with unread counts and other user-specific data
  const formattedChats = chats.map(chat => {
    const chatObj = chat.toObject();
    
    // Add user-specific data
    chatObj.isMuted = chat.isMuted.get(req.user._id.toString()) || false;
    chatObj.isPinned = chat.isPinned.get(req.user._id.toString()) || false;
    chatObj.unreadCount = chat.unreadCount.get(req.user._id.toString()) || 0;
    
    // For direct chats, get the other participant
    if (!chat.isGroup && chat.participants.length === 2) {
      const otherParticipant = chat.participants.find(p => !p._id.equals(req.user._id));
      chatObj.otherParticipant = otherParticipant;
    }
    
    return chatObj;
  });

  res.json({
    success: true,
    data: {
      chats: formattedChats,
      total: chats.length,
      limit: parseInt(limit),
      skip: parseInt(skip)
    }
  });
}));

/**
 * @route   POST /api/chats
 * @desc    Create a new direct chat
 * @access  Private
 */
router.post('/', authMiddleware, [
  body('participantId')
    .isMongoId()
    .withMessage('Valid participant ID is required')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { participantId } = req.body;

  // Validate participantId format
  if (!mongoose.Types.ObjectId.isValid(participantId)) {
    console.log('❌ Invalid participantId format:', participantId);
    throw new AppError('Invalid participant ID format', 400);
  }

  if (participantId === req.user._id.toString()) {
    throw new AppError('You cannot create a chat with yourself', 400);
  }

  // Check if participant exists and is not blocked
  console.log('🔍 Searching for participant:', participantId, 'Type:', typeof participantId);
  const participant = await User.findById(participantId);
  console.log('✅ Participant found:', participant ? participant._id.toString() : 'NULL');
  
  if (!participant) {
    // List all users for debugging
    const allUsers = await User.find({}, '_id username firstName lastName').limit(10);
    console.log('📋 Available users in DB:', allUsers.map(u => ({ id: u._id.toString(), username: u.username })));
    throw new AppError('Participant not found', 404);
  }

  if (req.user.blockedUsers.includes(participantId) || 
      participant.blockedUsers.includes(req.user._id)) {
    throw new AppError('Cannot create chat with blocked user', 400);
  }

  // Find or create direct chat
  const chat = await Chat.findOrCreateDirectChat(req.user._id, participantId);

  res.status(201).json({
    success: true,
    message: 'Chat created successfully',
    data: { chat }
  });
}));

/**
 * @route   POST /api/chats/group
 * @desc    Create a new group chat
 * @access  Private
 */
router.post('/group', authMiddleware, [
  body('groupName')
    .trim()
    .isLength({ min: 1, max: 50 })
    .withMessage('Group name is required and must be less than 50 characters'),
  body('participants')
    .isArray({ min: 1 })
    .withMessage('At least one participant is required'),
  body('participants.*')
    .isMongoId()
    .withMessage('All participants must have valid IDs'),
  body('description')
    .optional()
    .trim()
    .isLength({ max: 200 })
    .withMessage('Description cannot exceed 200 characters')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { groupName, participants, description } = req.body;

  // Add current user to participants if not already included
  const allParticipants = [...new Set([req.user._id.toString(), ...participants])];

  // Validate participants
  const validParticipants = await User.find({
    _id: { $in: allParticipants },
    isActive: true,
    isDeleted: false
  });

  if (validParticipants.length !== allParticipants.length) {
    throw new AppError('One or more participants not found', 404);
  }

  // Check for blocked users
  const blockedUsers = validParticipants.filter(p => 
    req.user.blockedUsers.includes(p._id) || 
    p.blockedUsers.includes(req.user._id)
  );

  if (blockedUsers.length > 0) {
    throw new AppError('Cannot create group with blocked users', 400);
  }

  // Create group chat
  const chat = new Chat({
    participants: allParticipants,
    isGroup: true,
    groupName,
    groupDescription: description || '',
    createdBy: req.user._id,
    admins: [req.user._id]
  });

  await chat.save();

  // Populate the created chat
  await chat.populate([
    { path: 'participants', select: 'username firstName lastName avatar status lastSeen' },
    { path: 'admins', select: 'username firstName lastName avatar' },
    { path: 'createdBy', select: 'username firstName lastName avatar' }
  ]);

  // Create system message for group creation
  const systemMessage = new Message({
    chatId: chat._id,
    senderId: req.user._id,
    content: `Group "${groupName}" was created`,
    type: 'system',
    isSystemMessage: true,
    systemMessageType: 'group_created'
  });

  await systemMessage.save();

  res.status(201).json({
    success: true,
    message: 'Group chat created successfully',
    data: { chat }
  });
}));

/**
 * @route   GET /api/chats/:chatId
 * @desc    Get chat details
 * @access  Private
 */
router.get('/:chatId', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const chat = await Chat.findById(req.params.chatId)
    .populate('participants', 'username firstName lastName avatar status lastSeen')
    .populate('admins', 'username firstName lastName avatar')
    .populate('createdBy', 'username firstName lastName avatar')
    .populate('lastMessage')
    .populate('lastMessageSender', 'username firstName lastName');

  res.json({
    success: true,
    data: { chat }
  });
}));

/**
 * @route   PUT /api/chats/:chatId
 * @desc    Update chat (for groups)
 * @access  Private
 */
router.put('/:chatId', authMiddleware, requireChatParticipant, [
  body('groupName')
    .optional()
    .trim()
    .isLength({ min: 1, max: 50 })
    .withMessage('Group name must be between 1 and 50 characters'),
  body('description')
    .optional()
    .trim()
    .isLength({ max: 200 })
    .withMessage('Description cannot exceed 200 characters')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { groupName, description } = req.body;
  const chat = req.chat;

  if (!chat.isGroup) {
    throw new AppError('Cannot update direct chat', 400);
  }

  // Check if user is admin or creator
  if (!chat.isAdmin(req.user._id) && !chat.createdBy.equals(req.user._id)) {
    throw new AppError('Only admins can update group information', 403);
  }

  const updates = {};
  if (groupName !== undefined) updates.groupName = groupName;
  if (description !== undefined) updates.groupDescription = description;

  const updatedChat = await Chat.findByIdAndUpdate(
    chat._id,
    updates,
    { new: true, runValidators: true }
  ).populate('participants', 'username firstName lastName avatar status lastSeen')
   .populate('admins', 'username firstName lastName avatar');

  // Create system message for group update
  if (groupName && groupName !== chat.groupName) {
    const systemMessage = new Message({
      chatId: chat._id,
      senderId: req.user._id,
      content: `Group name changed to "${groupName}"`,
      type: 'system',
      isSystemMessage: true,
      systemMessageType: 'group_renamed'
    });
    await systemMessage.save();
  }

  res.json({
    success: true,
    message: 'Chat updated successfully',
    data: { chat: updatedChat }
  });
}));

/**
 * @route   POST /api/chats/:chatId/members
 * @desc    Add members to group chat
 * @access  Private
 */
router.post('/:chatId/members', authMiddleware, requireChatParticipant, [
  body('userIds')
    .isArray({ min: 1 })
    .withMessage('At least one user ID is required'),
  body('userIds.*')
    .isMongoId()
    .withMessage('All user IDs must be valid')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { userIds } = req.body;
  const chat = req.chat;

  if (!chat.isGroup) {
    throw new AppError('Cannot add members to direct chat', 400);
  }

  // Check if user is admin
  if (!chat.isAdmin(req.user._id)) {
    throw new AppError('Only admins can add members', 403);
  }

  // Validate new members
  const newMembers = await User.find({
    _id: { $in: userIds },
    isActive: true,
    isDeleted: false
  });

  if (newMembers.length !== userIds.length) {
    throw new AppError('One or more users not found', 404);
  }

  // Check for blocked users
  const blockedUsers = newMembers.filter(member => 
    req.user.blockedUsers.includes(member._id) || 
    member.blockedUsers.includes(req.user._id)
  );

  if (blockedUsers.length > 0) {
    throw new AppError('Cannot add blocked users to group', 400);
  }

  // Add members to chat
  const addedMembers = [];
  for (const member of newMembers) {
    if (!chat.participants.includes(member._id)) {
      await chat.addParticipant(member._id);
      addedMembers.push(member);
    }
  }

  // Create system messages for each added member
  for (const member of addedMembers) {
    const systemMessage = new Message({
      chatId: chat._id,
      senderId: req.user._id,
      content: `${member.firstName} ${member.lastName} was added`,
      type: 'system',
      isSystemMessage: true,
      systemMessageType: 'user_joined'
    });
    await systemMessage.save();
  }

  res.json({
    success: true,
    message: 'Members added successfully',
    data: { addedMembers: addedMembers.length }
  });
}));

/**
 * @route   DELETE /api/chats/:chatId/members/:userId
 * @desc    Remove member from group chat
 * @access  Private
 */
router.delete('/:chatId/members/:userId', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const { userId } = req.params;
  const chat = req.chat;

  if (!chat.isGroup) {
    throw new AppError('Cannot remove members from direct chat', 400);
  }

  // Check if user is admin or removing themselves
  if (!chat.isAdmin(req.user._id) && userId !== req.user._id.toString()) {
    throw new AppError('Only admins can remove other members', 403);
  }

  // Cannot remove the creator
  if (chat.createdBy.equals(userId)) {
    throw new AppError('Cannot remove the group creator', 400);
  }

  const member = await User.findById(userId);
  if (!member) {
    throw new AppError('Member not found', 404);
  }

  await chat.removeParticipant(userId);

  // Create system message
  const systemMessage = new Message({
    chatId: chat._id,
    senderId: req.user._id,
    content: `${member.firstName} ${member.lastName} was removed`,
    type: 'system',
    isSystemMessage: true,
    systemMessageType: 'user_left'
  });
  await systemMessage.save();

  res.json({
    success: true,
    message: 'Member removed successfully'
  });
}));

/**
 * @route   POST /api/chats/:chatId/admins/:userId
 * @desc    Make user admin of group chat
 * @access  Private
 */
router.post('/:chatId/admins/:userId', authMiddleware, requireGroupAdmin, asyncHandler(async (req, res) => {
  const { userId } = req.params;
  const chat = req.chat;

  const user = await User.findById(userId);
  if (!user) {
    throw new AppError('User not found', 404);
  }

  if (!chat.isParticipant(userId)) {
    throw new AppError('User is not a participant of this chat', 400);
  }

  await chat.makeAdmin(userId);

  // Create system message
  const systemMessage = new Message({
    chatId: chat._id,
    senderId: req.user._id,
    content: `${user.firstName} ${user.lastName} was made an admin`,
    type: 'system',
    isSystemMessage: true,
    systemMessageType: 'admin_added'
  });
  await systemMessage.save();

  res.json({
    success: true,
    message: 'User made admin successfully'
  });
}));

/**
 * @route   DELETE /api/chats/:chatId/admins/:userId
 * @desc    Remove admin from group chat
 * @access  Private
 */
router.delete('/:chatId/admins/:userId', authMiddleware, requireGroupAdmin, asyncHandler(async (req, res) => {
  const { userId } = req.params;
  const chat = req.chat;

  // Cannot remove the creator as admin
  if (chat.createdBy.equals(userId)) {
    throw new AppError('Cannot remove admin privileges from group creator', 400);
  }

  const user = await User.findById(userId);
  if (!user) {
    throw new AppError('User not found', 404);
  }

  await chat.removeAdmin(userId);

  // Create system message
  const systemMessage = new Message({
    chatId: chat._id,
    senderId: req.user._id,
    content: `${user.firstName} ${user.lastName} is no longer an admin`,
    type: 'system',
    isSystemMessage: true,
    systemMessageType: 'admin_removed'
  });
  await systemMessage.save();

  res.json({
    success: true,
    message: 'Admin privileges removed successfully'
  });
}));

/**
 * @route   PUT /api/chats/:chatId/mute
 * @desc    Toggle mute for chat
 * @access  Private
 */
router.put('/:chatId/mute', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const chat = req.chat;
  
  await chat.toggleMute(req.user._id);
  
  const isMuted = chat.isMuted.get(req.user._id.toString()) || false;

  res.json({
    success: true,
    message: `Chat ${isMuted ? 'muted' : 'unmuted'} successfully`,
    data: { isMuted }
  });
}));

/**
 * @route   PUT /api/chats/:chatId/pin
 * @desc    Toggle pin for chat
 * @access  Private
 */
router.put('/:chatId/pin', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const chat = req.chat;
  
  await chat.togglePin(req.user._id);
  
  const isPinned = chat.isPinned.get(req.user._id.toString()) || false;

  res.json({
    success: true,
    message: `Chat ${isPinned ? 'pinned' : 'unpinned'} successfully`,
    data: { isPinned }
  });
}));

/**
 * @route   PUT /api/chats/:chatId/archive
 * @desc    Archive chat
 * @access  Private
 */
router.put('/:chatId/archive', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const chat = req.chat;
  
  await chat.archiveChat();

  res.json({
    success: true,
    message: 'Chat archived successfully'
  });
}));

/**
 * @route   PUT /api/chats/:chatId/unarchive
 * @desc    Unarchive chat
 * @access  Private
 */
router.put('/:chatId/unarchive', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const chat = req.chat;
  
  await chat.unarchiveChat();

  res.json({
    success: true,
    message: 'Chat unarchived successfully'
  });
}));

/**
 * @route   DELETE /api/chats/:chatId
 * @desc    Leave or delete chat
 * @access  Private
 */
router.delete('/:chatId', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const chat = req.chat;

  if (chat.isGroup) {
    // For group chats, just remove the user
    await chat.removeParticipant(req.user._id);

    // Create system message
    const systemMessage = new Message({
      chatId: chat._id,
      senderId: req.user._id,
      content: `${req.user.firstName} ${req.user.lastName} left the group`,
      type: 'system',
      isSystemMessage: true,
      systemMessageType: 'user_left'
    });
    await systemMessage.save();

    res.json({
      success: true,
      message: 'Left group successfully'
    });
  } else {
    // For direct chats, mark as deleted for this user
    chat.isDeleted = true;
    chat.deletedAt = new Date();
    await chat.save();

    res.json({
      success: true,
      message: 'Chat deleted successfully'
    });
  }
}));

/**
 * @route   GET /api/chats/:chatId/messages
 * @desc    Get messages for a specific chat
 * @access  Private
 */
router.get('/:chatId/messages', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const { chatId } = req.params;
  const { limit = 50, skip = 0, before } = req.query;

  const query = { chat: chatId };
  
  if (before) {
    query.createdAt = { $lt: new Date(before) };
  }

  const messages = await Message.find(query)
    .sort({ createdAt: -1 })
    .limit(parseInt(limit))
    .skip(parseInt(skip))
    .populate('sender', 'firstName lastName username avatar')
    .populate('readBy.user', 'firstName lastName username')
    .populate('reactions.user', 'firstName lastName username')
    .lean();

  const totalCount = await Message.countDocuments(query);

  res.json({
    success: true,
    data: {
      messages: messages.reverse(), // Reverse to show oldest first
      total: totalCount,
      limit: parseInt(limit),
      skip: parseInt(skip),
      hasMore: totalCount > (parseInt(skip) + parseInt(limit))
    }
  });
}));

/**
 * @route   PUT /api/chats/:chatId/mute
 * @desc    Mute/unmute chat
 * @access  Private
 */
router.put('/:chatId/mute', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
    const { duration } = req.body; // duration in minutes, 0 to unmute
    
    const chat = await Chat.findById(req.params.chatId);
    
    chat.mutedBy = chat.mutedBy || new Map();
    
    if (duration && duration > 0) {
        const muteUntil = new Date(Date.now() + duration * 60 * 1000);
        chat.mutedBy.set(req.user._id.toString(), muteUntil);
    } else {
        chat.mutedBy.delete(req.user._id.toString());
    }
    
    await chat.save();
    
    res.json({
        success: true,
        message: duration > 0 ? 'Chat muted' : 'Chat unmuted',
        chat: chat.getFormattedChat(req.user._id)
    });
}));

/**
 * @route   DELETE /api/chats/:chatId
 * @desc    Delete chat (soft delete for user)
 * @access  Private
 */
router.delete('/:chatId', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
    const chat = await Chat.findById(req.params.chatId);
    
    if (chat.isGroup) {
        // Remove user from participants
        chat.participants = chat.participants.filter(
            p => p.toString() !== req.user._id.toString()
        );
        
        // If no participants left, mark chat as deleted
        if (chat.participants.length === 0) {
            chat.isDeleted = true;
        }
    } else {
        // For one-on-one chats, add to deletedFor array
        chat.deletedFor = chat.deletedFor || [];
        if (!chat.deletedFor.some(id => id.toString() === req.user._id.toString())) {
            chat.deletedFor.push(req.user._id);
        }
        
        // If both users deleted, mark chat as deleted
        if (chat.deletedFor.length === chat.participants.length) {
            chat.isDeleted = true;
        }
    }
    
    await chat.save();
    
    res.json({
        success: true,
        message: 'Chat deleted'
    });
}));

/**
 * @route   PUT /api/chats/:chatId/pin
 * @desc    Pin/unpin chat
 * @access  Private
 */
router.put('/:chatId/pin', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
    const { pinned } = req.body;
    
    const chat = await Chat.findById(req.params.chatId);
    
    chat.pinnedBy = chat.pinnedBy || [];
    
    if (pinned) {
        if (!chat.pinnedBy.some(id => id.toString() === req.user._id.toString())) {
            chat.pinnedBy.push(req.user._id);
        }
    } else {
        chat.pinnedBy = chat.pinnedBy.filter(
            id => id.toString() !== req.user._id.toString()
        );
    }
    
    await chat.save();
    
    res.json({
        success: true,
        message: pinned ? 'Chat pinned' : 'Chat unpinned',
        chat: chat.getFormattedChat(req.user._id)
    });
}));

module.exports = router;

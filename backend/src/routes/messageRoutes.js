const express = require('express');
const { body, validationResult } = require('express-validator');
const { User, Chat, Message } = require('../models');
const { authMiddleware, requireChatParticipant, asyncHandler, AppError } = require('../middleware');
const multer = require('multer');
const sharp = require('sharp');

const router = express.Router();

// Configure multer for file uploads
const storage = multer.memoryStorage();
const upload = multer({
  storage: storage,
  limits: {
    fileSize: parseInt(process.env.MAX_FILE_SIZE) || 50 * 1024 * 1024, // 50MB
    files: 10 // Maximum 10 files per request
  },
  fileFilter: (req, file, cb) => {
    const allowedTypes = [
      'image/jpeg', 'image/png', 'image/gif', 'image/webp',
      'video/mp4', 'video/avi', 'video/mov', 'video/wmv',
      'audio/mpeg', 'audio/wav', 'audio/ogg', 'audio/mp3',
      'application/pdf', 'application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
      'text/plain', 'application/zip', 'application/x-rar-compressed'
    ];
    
    if (allowedTypes.includes(file.mimetype)) {
      cb(null, true);
    } else {
      cb(new Error(`File type ${file.mimetype} not allowed`), false);
    }
  }
});

/**
 * @route   GET /api/messages/:chatId
 * @desc    Get messages for a chat
 * @access  Private
 */
router.get('/:chatId', authMiddleware, requireChatParticipant, asyncHandler(async (req, res) => {
  const { 
    limit = 50, 
    skip = 0, 
    before = null,
    after = null,
    type = null,
    search = null 
  } = req.query;

  const options = {
    limit: parseInt(limit),
    skip: parseInt(skip),
    before: before ? new Date(before) : null,
    after: after ? new Date(after) : null,
    type: type !== 'all' ? type : null,
    search
  };

  const messages = await Message.findChatMessages(req.params.chatId, options);

  // Mark messages as delivered to current user
  const messageIds = messages.map(msg => msg._id);
  await Message.updateMany(
    { 
      _id: { $in: messageIds },
      'deliveredTo.userId': { $ne: req.user._id }
    },
    { $push: { deliveredTo: { userId: req.user._id, deliveredAt: new Date() } } }
  );

  // Reset unread count for this chat
  await req.chat.resetUnreadCount(req.user._id);

  res.json({
    success: true,
    data: {
      messages: messages.reverse(), // Return in chronological order
      total: messages.length,
      limit: parseInt(limit),
      skip: parseInt(skip),
      chatId: req.params.chatId
    }
  });
}));

/**
 * @route   POST /api/messages/:chatId
 * @desc    Send a text message
 * @access  Private
 */
router.post('/:chatId', authMiddleware, requireChatParticipant, [
  body('content')
    .trim()
    .isLength({ min: 1, max: 4000 })
    .withMessage('Message content must be between 1 and 4000 characters'),
  body('type')
    .optional()
    .isIn(['text', 'reply'])
    .withMessage('Invalid message type'),
  body('replyTo')
    .optional()
    .isMongoId()
    .withMessage('Invalid reply message ID'),
  body('mentions')
    .optional()
    .isArray()
    .withMessage('Mentions must be an array'),
  body('mentions.*')
    .optional()
    .isMongoId()
    .withMessage('All mentions must be valid user IDs')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { content, type = 'text', replyTo, mentions = [], clientMessageId } = req.body;
  const chatId = req.params.chatId;

  // Validate reply message if provided
  if (replyTo) {
    const replyMessage = await Message.findOne({
      _id: replyTo,
      chatId: chatId,
      isDeleted: false
    });

    if (!replyMessage) {
      throw new AppError('Reply message not found', 404);
    }
  }

  // Validate mentions
  if (mentions.length > 0) {
    const validUsers = await User.find({
      _id: { $in: mentions },
      isActive: true,
      isDeleted: false
    });

    if (validUsers.length !== mentions.length) {
      throw new AppError('One or more mentioned users not found', 404);
    }
  }

  // Create message
  const message = new Message({
    chatId,
    senderId: req.user._id,
    content,
    type,
    replyTo: replyTo || null,
    mentions,
    metadata: {
      clientMessageId,
      platform: req.headers['x-platform'] || 'web',
      appVersion: req.headers['x-app-version'] || '1.0.0'
    }
  });

  await message.save();

  // Mark as delivered to sender
  await message.markAsDelivered(req.user._id);

  // Increment unread count for other participants
  const otherParticipants = req.chat.participants.filter(p => !p.equals(req.user._id));
  for (const participantId of otherParticipants) {
    await req.chat.incrementUnreadCount(participantId);
  }

  // Populate the message for response
  await message.populate([
    { path: 'senderId', select: 'username firstName lastName avatar' },
    { path: 'replyTo' },
    { path: 'mentions', select: 'username firstName lastName' }
  ]);

  res.status(201).json({
    success: true,
    message: 'Message sent successfully',
    data: { message }
  });
}));

/**
 * @route   POST /api/messages/:chatId/media
 * @desc    Send a media message (image, video, audio, document)
 * @access  Private
 */
router.post('/:chatId/media', authMiddleware, requireChatParticipant, upload.single('file'), asyncHandler(async (req, res) => {
  if (!req.file) {
    throw new AppError('No file uploaded', 400);
  }

  const { type, caption = '', replyTo, mentions = [] } = req.body;
  const chatId = req.params.chatId;

  // Determine message type from file mimetype
  let messageType = 'document';
  if (req.file.mimetype.startsWith('image/')) {
    messageType = 'image';
  } else if (req.file.mimetype.startsWith('video/')) {
    messageType = 'video';
  } else if (req.file.mimetype.startsWith('audio/')) {
    messageType = 'audio';
  }

  // Validate reply message if provided
  if (replyTo) {
    const replyMessage = await Message.findOne({
      _id: replyTo,
      chatId: chatId,
      isDeleted: false
    });

    if (!replyMessage) {
      throw new AppError('Reply message not found', 404);
    }
  }

  // TODO: Upload file to cloud storage (Cloudinary, AWS S3, etc.)
  // For now, we'll simulate with a placeholder URL
  const mediaUrl = `https://example.com/media/${chatId}/${Date.now()}_${req.file.originalname}`;

  // Generate thumbnail for videos
  let thumbnail = null;
  if (messageType === 'video') {
    // TODO: Generate video thumbnail using ffmpeg
    thumbnail = `https://example.com/thumbnails/${chatId}/${Date.now()}_thumbnail.jpg`;
  }

  // Create media metadata
  const mediaMetadata = {
    filename: req.file.originalname,
    originalName: req.file.originalname,
    mimeType: req.file.mimetype,
    size: req.file.size,
    duration: null, // Will be extracted for audio/video files
    width: null,    // Will be extracted for images/videos
    height: null,   // Will be extracted for images/videos
    thumbnail: thumbnail
  };

  // Extract dimensions for images
  if (messageType === 'image') {
    try {
      const metadata = await sharp(req.file.buffer).metadata();
      mediaMetadata.width = metadata.width;
      mediaMetadata.height = metadata.height;
    } catch (error) {
      console.error('Error extracting image metadata:', error);
    }
  }

  // Create message
  const message = new Message({
    chatId,
    senderId: req.user._id,
    content: caption,
    type: messageType,
    mediaUrl,
    mediaMetadata,
    replyTo: replyTo || null,
    mentions,
    metadata: {
      platform: req.headers['x-platform'] || 'web',
      appVersion: req.headers['x-app-version'] || '1.0.0'
    }
  });

  await message.save();

  // Mark as delivered to sender
  await message.markAsDelivered(req.user._id);

  // Increment unread count for other participants
  const otherParticipants = req.chat.participants.filter(p => !p.equals(req.user._id));
  for (const participantId of otherParticipants) {
    await req.chat.incrementUnreadCount(participantId);
  }

  // Populate the message for response
  await message.populate([
    { path: 'senderId', select: 'username firstName lastName avatar' },
    { path: 'replyTo' },
    { path: 'mentions', select: 'username firstName lastName' }
  ]);

  res.status(201).json({
    success: true,
    message: 'Media message sent successfully',
    data: { message }
  });
}));

/**
 * @route   POST /api/messages/:chatId/location
 * @desc    Send a location message
 * @access  Private
 */
router.post('/:chatId/location', authMiddleware, requireChatParticipant, [
  body('latitude')
    .isFloat({ min: -90, max: 90 })
    .withMessage('Latitude must be between -90 and 90'),
  body('longitude')
    .isFloat({ min: -180, max: 180 })
    .withMessage('Longitude must be between -180 and 180'),
  body('address')
    .optional()
    .trim()
    .isLength({ max: 200 })
    .withMessage('Address cannot exceed 200 characters'),
  body('name')
    .optional()
    .trim()
    .isLength({ max: 100 })
    .withMessage('Location name cannot exceed 100 characters')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { latitude, longitude, address, name } = req.body;
  const chatId = req.params.chatId;

  const message = new Message({
    chatId,
    senderId: req.user._id,
    type: 'location',
    location: {
      latitude: parseFloat(latitude),
      longitude: parseFloat(longitude),
      address: address || '',
      name: name || ''
    },
    metadata: {
      platform: req.headers['x-platform'] || 'web',
      appVersion: req.headers['x-app-version'] || '1.0.0'
    }
  });

  await message.save();

  // Mark as delivered to sender
  await message.markAsDelivered(req.user._id);

  // Increment unread count for other participants
  const otherParticipants = req.chat.participants.filter(p => !p.equals(req.user._id));
  for (const participantId of otherParticipants) {
    await req.chat.incrementUnreadCount(participantId);
  }

  // Populate the message for response
  await message.populate('senderId', 'username firstName lastName avatar');

  res.status(201).json({
    success: true,
    message: 'Location message sent successfully',
    data: { message }
  });
}));

/**
 * @route   POST /api/messages/:chatId/contact
 * @desc    Send a contact message
 * @access  Private
 */
router.post('/:chatId/contact', authMiddleware, requireChatParticipant, [
  body('name')
    .trim()
    .isLength({ min: 1, max: 100 })
    .withMessage('Contact name is required and cannot exceed 100 characters'),
  body('phone')
    .matches(/^\+?[\d\s\-\(\)]+$/)
    .withMessage('Please provide a valid phone number'),
  body('email')
    .optional()
    .isEmail()
    .withMessage('Please provide a valid email address'),
  body('vcard')
    .optional()
    .isString()
    .withMessage('vCard must be a string')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { name, phone, email, vcard } = req.body;
  const chatId = req.params.chatId;

  const message = new Message({
    chatId,
    senderId: req.user._id,
    type: 'contact',
    contact: {
      name,
      phone,
      email: email || '',
      vcard: vcard || ''
    },
    metadata: {
      platform: req.headers['x-platform'] || 'web',
      appVersion: req.headers['x-app-version'] || '1.0.0'
    }
  });

  await message.save();

  // Mark as delivered to sender
  await message.markAsDelivered(req.user._id);

  // Increment unread count for other participants
  const otherParticipants = req.chat.participants.filter(p => !p.equals(req.user._id));
  for (const participantId of otherParticipants) {
    await req.chat.incrementUnreadCount(participantId);
  }

  // Populate the message for response
  await message.populate('senderId', 'username firstName lastName avatar');

  res.json({
    success: true,
    message: 'Contact message sent successfully',
    data: { message }
  });
}));

/**
 * @route   PUT /api/messages/:messageId
 * @desc    Edit a message
 * @access  Private
 */
router.put('/:messageId', authMiddleware, [
  body('content')
    .trim()
    .isLength({ min: 1, max: 4000 })
    .withMessage('Message content must be between 1 and 4000 characters')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { content } = req.body;
  const { messageId } = req.params;

  const message = await Message.findOne({
    _id: messageId,
    senderId: req.user._id,
    isDeleted: false
  });

  if (!message) {
    throw new AppError('Message not found or you cannot edit this message', 404);
  }

  // Check if message is too old to edit (e.g., 15 minutes)
  const editTimeLimit = 15 * 60 * 1000; // 15 minutes
  if (Date.now() - message.createdAt.getTime() > editTimeLimit) {
    throw new AppError('Message is too old to edit', 400);
  }

  await message.editMessage(content);

  // Populate the updated message
  await message.populate([
    { path: 'senderId', select: 'username firstName lastName avatar' },
    { path: 'replyTo' },
    { path: 'mentions', select: 'username firstName lastName' }
  ]);

  res.json({
    success: true,
    message: 'Message edited successfully',
    data: { message }
  });
}));

/**
 * @route   DELETE /api/messages/:messageId
 * @desc    Delete a message
 * @access  Private
 */
router.delete('/:messageId', authMiddleware, asyncHandler(async (req, res) => {
  const { messageId } = req.params;

  const message = await Message.findOne({
    _id: messageId,
    senderId: req.user._id,
    isDeleted: false
  });

  if (!message) {
    throw new AppError('Message not found or you cannot delete this message', 404);
  }

  // Check if message is too old to delete (e.g., 1 hour)
  const deleteTimeLimit = 60 * 60 * 1000; // 1 hour
  if (Date.now() - message.createdAt.getTime() > deleteTimeLimit) {
    throw new AppError('Message is too old to delete', 400);
  }

  await message.softDelete(req.user._id);

  res.json({
    success: true,
    message: 'Message deleted successfully'
  });
}));

/**
 * @route   POST /api/messages/:messageId/reactions
 * @desc    Add reaction to a message
 * @access  Private
 */
router.post('/:messageId/reactions', authMiddleware, [
  body('emoji')
    .trim()
    .isLength({ min: 1, max: 10 })
    .withMessage('Emoji is required and cannot exceed 10 characters')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { emoji } = req.body;
  const { messageId } = req.params;

  const message = await Message.findOne({
    _id: messageId,
    isDeleted: false
  });

  if (!message) {
    throw new AppError('Message not found', 404);
  }

  await message.addReaction(req.user._id, emoji);

  // Populate reactions
  await message.populate('reactions.userId', 'username firstName lastName');

  res.json({
    success: true,
    message: 'Reaction added successfully',
    data: { message }
  });
}));

/**
 * @route   DELETE /api/messages/:messageId/reactions/:emoji
 * @desc    Remove reaction from a message
 * @access  Private
 */
router.delete('/:messageId/reactions/:emoji', authMiddleware, asyncHandler(async (req, res) => {
  const { messageId, emoji } = req.params;

  const message = await Message.findOne({
    _id: messageId,
    isDeleted: false
  });

  if (!message) {
    throw new AppError('Message not found', 404);
  }

  await message.removeReaction(req.user._id, emoji);

  // Populate reactions
  await message.populate('reactions.userId', 'username firstName lastName');

  res.json({
    success: true,
    message: 'Reaction removed successfully',
    data: { message }
  });
}));

/**
 * @route   POST /api/messages/:messageId/read
 * @desc    Mark message as read
 * @access  Private
 */
router.post('/:messageId/read', authMiddleware, asyncHandler(async (req, res) => {
  const { messageId } = req.params;

  const message = await Message.findOne({
    _id: messageId,
    isDeleted: false
  });

  if (!message) {
    throw new AppError('Message not found', 404);
  }

  // Check if user is participant of the chat
  const chat = await Chat.findById(message.chatId);
  if (!chat || !chat.isParticipant(req.user._id)) {
    throw new AppError('You are not a participant of this chat', 403);
  }

  await message.markAsRead(req.user._id);

  res.json({
    success: true,
    message: 'Message marked as read'
  });
}));

/**
 * @route   POST /api/messages/search
 * @desc    Search messages
 * @access  Private
 */
router.post('/search', authMiddleware, [
  body('searchTerm')
    .trim()
    .isLength({ min: 2 })
    .withMessage('Search term must be at least 2 characters long'),
  body('chatId')
    .optional()
    .isMongoId()
    .withMessage('Invalid chat ID'),
  body('type')
    .optional()
    .isIn(['text', 'image', 'video', 'audio', 'document', 'location', 'contact', 'all'])
    .withMessage('Invalid message type')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { searchTerm, chatId, type = 'all', limit = 20, skip = 0 } = req.body;

  const options = {
    limit: parseInt(limit),
    skip: parseInt(skip),
    chatId,
    type: type !== 'all' ? type : null
  };

  const messages = await Message.searchMessages(req.user._id, searchTerm, options);

  res.json({
    success: true,
    data: {
      messages,
      total: messages.length,
      limit: parseInt(limit),
      skip: parseInt(skip),
      searchTerm
    }
  });
}));

/**
 * @route   GET /api/messages/:messageId
 * @desc    Get message details
 * @access  Private
 */
router.get('/:messageId', authMiddleware, asyncHandler(async (req, res) => {
  const { messageId } = req.params;

  const message = await Message.findOne({
    _id: messageId,
    isDeleted: false
  }).populate([
    { path: 'senderId', select: 'username firstName lastName avatar' },
    { path: 'replyTo' },
    { path: 'forwardedFrom' },
    { path: 'readBy.userId', select: 'username firstName lastName' },
    { path: 'deliveredTo.userId', select: 'username firstName lastName' },
    { path: 'reactions.userId', select: 'username firstName lastName' },
    { path: 'mentions', select: 'username firstName lastName' }
  ]);

  if (!message) {
    throw new AppError('Message not found', 404);
  }

  // Check if user is participant of the chat
  const chat = await Chat.findById(message.chatId);
  if (!chat || !chat.isParticipant(req.user._id)) {
    throw new AppError('You are not a participant of this chat', 403);
  }

  res.json({
    success: true,
    data: { message }
  });
}));

module.exports = router;

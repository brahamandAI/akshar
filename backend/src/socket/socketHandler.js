const jwt = require('jsonwebtoken');
const { User, Chat, Message, Status } = require('../models');

// Store connected users
const connectedUsers = new Map();

/**
 * Socket.IO connection handler
 */
const socketHandler = (io) => {
  // Authentication middleware for socket connections
  io.use(async (socket, next) => {
    try {
      const token = socket.handshake.auth.token || socket.handshake.headers.authorization?.replace('Bearer ', '');
      
      console.log('🔐 Socket auth attempt - Token received:', token ? `${token.substring(0, 30)}...` : 'NONE');
      
      if (!token) {
        console.error('❌ Socket auth failed: No token provided');
        return next(new Error('Authentication error: No token provided'));
      }

      const decoded = jwt.verify(token, process.env.JWT_SECRET);
      console.log('✅ Token decoded successfully for user ID:', decoded.id);
      
      const user = await User.findById(decoded.id).select('-password');
      
      if (!user) {
        console.error('❌ Socket auth failed: User not found', decoded.id);
        return next(new Error('Authentication error: User not found'));
      }
      
      if (!user.isActive) {
        console.error('❌ Socket auth failed: User inactive', user.username);
        return next(new Error('Authentication error: User inactive'));
      }
      
      if (user.isDeleted) {
        console.error('❌ Socket auth failed: User deleted', user.username);
        return next(new Error('Authentication error: User deleted'));
      }

      socket.userId = user._id.toString();
      socket.user = user;
      console.log('✅ Socket auth SUCCESS for user:', user.username);
      next();
    } catch (error) {
      console.error('❌ Socket auth error:', error.message);
      next(new Error(`Authentication error: ${error.message}`));
    }
  });

  io.on('connection', (socket) => {
    console.log(`✅ User ${socket.user.username} connected with socket ID: ${socket.id}`);

    // Store user connection
    connectedUsers.set(socket.userId, {
      socketId: socket.id,
      user: socket.user,
      connectedAt: new Date()
    });

    // Update user status to online
    User.findByIdAndUpdate(socket.userId, {
      status: 'online',
      lastSeen: new Date()
    }).catch(error => console.error('Error updating user status:', error));

    // Join user to their chat rooms
    socket.emit('connected', {
      message: 'Connected successfully',
      user: socket.user.getPublicProfile()
    });

    // Handle joining chat rooms
    socket.on('join_chat', async (data) => {
      try {
        const { chatId } = data;
        
        // Verify user is participant of the chat
        const chat = await Chat.findById(chatId);
        if (!chat || !chat.isParticipant(socket.userId)) {
          socket.emit('error', { message: 'You are not a participant of this chat' });
          return;
        }

        socket.join(chatId);
        socket.emit('joined_chat', { chatId });
        
        // Notify other participants
        socket.to(chatId).emit('user_joined_chat', {
          chatId,
          user: socket.user.getPublicProfile()
        });

      } catch (error) {
        console.error('Error joining chat:', error);
        socket.emit('error', { message: 'Error joining chat' });
      }
    });

    // Handle leaving chat rooms
    socket.on('leave_chat', (data) => {
      const { chatId } = data;
      socket.leave(chatId);
      socket.emit('left_chat', { chatId });
      
      socket.to(chatId).emit('user_left_chat', {
        chatId,
        user: socket.user.getPublicProfile()
      });
    });

    // Handle sending messages
    socket.on('send_message', async (data) => {
      try {
        const { chatId, content, type = 'text', replyTo, mentions = [] } = data;

        // Verify user is participant of the chat
        const chat = await Chat.findById(chatId);
        if (!chat || !chat.isParticipant(socket.userId)) {
          socket.emit('error', { message: 'You are not a participant of this chat' });
          return;
        }

        // Validate reply message if provided
        if (replyTo) {
          const replyMessage = await Message.findOne({
            _id: replyTo,
            chatId: chatId,
            isDeleted: false
          });

          if (!replyMessage) {
            socket.emit('error', { message: 'Reply message not found' });
            return;
          }
        }

        // Create message
        const message = new Message({
          chatId,
          senderId: socket.userId,
          content,
          type,
          replyTo: replyTo || null,
          mentions,
          metadata: {
            platform: 'web',
            appVersion: '1.0.0'
          }
        });

        await message.save();

        // Mark as delivered to sender
        await message.markAsDelivered(socket.userId);

        // Increment unread count for other participants
        const otherParticipants = chat.participants.filter(p => !p.equals(socket.userId));
        for (const participantId of otherParticipants) {
          await chat.incrementUnreadCount(participantId);
        }

        // Populate the message
        await message.populate([
          { path: 'senderId', select: 'username firstName lastName avatar' },
          { path: 'replyTo' },
          { path: 'mentions', select: 'username firstName lastName' }
        ]);

        // Emit message to all participants in the chat
        io.to(chatId).emit('new_message', {
          message,
          chatId
        });

        // Send delivery confirmation to sender
        socket.emit('message_sent', {
          messageId: message._id,
          chatId,
          status: 'delivered'
        });

      } catch (error) {
        console.error('Error sending message:', error);
        socket.emit('error', { message: 'Error sending message' });
      }
    });

    // Handle typing indicators
    socket.on('typing_start', (data) => {
      const { chatId } = data;
      socket.to(chatId).emit('user_typing', {
        chatId,
        user: socket.user.getPublicProfile(),
        isTyping: true
      });
    });

    socket.on('typing_stop', (data) => {
      const { chatId } = data;
      socket.to(chatId).emit('user_typing', {
        chatId,
        user: socket.user.getPublicProfile(),
        isTyping: false
      });
    });

    // Handle message reactions
    socket.on('add_reaction', async (data) => {
      try {
        const { messageId, emoji, chatId } = data;

        const message = await Message.findOne({
          _id: messageId,
          isDeleted: false
        });

        if (!message) {
          socket.emit('error', { message: 'Message not found' });
          return;
        }

        // Check if user is participant of the chat
        const chat = await Chat.findById(message.chatId);
        if (!chat || !chat.isParticipant(socket.userId)) {
          socket.emit('error', { message: 'You are not a participant of this chat' });
          return;
        }

        await message.addReaction(socket.userId, emoji);
        await message.populate('reactions.userId', 'username firstName lastName');

        // Emit reaction to all participants
        io.to(chatId).emit('reaction_added', {
          messageId,
          message,
          chatId
        });

      } catch (error) {
        console.error('Error adding reaction:', error);
        socket.emit('error', { message: 'Error adding reaction' });
      }
    });

    // Handle message read receipts
    socket.on('mark_read', async (data) => {
      try {
        const { messageId, chatId } = data;

        const message = await Message.findOne({
          _id: messageId,
          isDeleted: false
        });

        if (!message) {
          socket.emit('error', { message: 'Message not found' });
          return;
        }

        // Check if user is participant of the chat
        const chat = await Chat.findById(message.chatId);
        if (!chat || !chat.isParticipant(socket.userId)) {
          socket.emit('error', { message: 'You are not a participant of this chat' });
          return;
        }

        await message.markAsRead(socket.userId);

        // Reset unread count for this chat
        await chat.resetUnreadCount(socket.userId);

        // Emit read receipt to sender (if different from current user)
        if (!message.senderId.equals(socket.userId)) {
          io.to(chatId).emit('message_read', {
            messageId,
            readBy: socket.user.getPublicProfile(),
            chatId
          });
        }

      } catch (error) {
        console.error('Error marking message as read:', error);
        socket.emit('error', { message: 'Error marking message as read' });
      }
    });

    // Handle online status updates
    socket.on('update_status', async (data) => {
      try {
        const { status } = data;
        
        if (!['online', 'away', 'busy', 'offline'].includes(status)) {
          socket.emit('error', { message: 'Invalid status' });
          return;
        }

        await User.findByIdAndUpdate(socket.userId, {
          status,
          lastSeen: new Date()
        });

        // Notify contacts about status change
        const chats = await Chat.find({
          participants: socket.userId,
          isActive: true,
          isDeleted: false
        });

        for (const chat of chats) {
          io.to(chat._id.toString()).emit('user_status_changed', {
            user: socket.user.getPublicProfile(),
            status,
            chatId: chat._id
          });
        }

      } catch (error) {
        console.error('Error updating status:', error);
        socket.emit('error', { message: 'Error updating status' });
      }
    });

    // Handle presence updates
    socket.on('update_presence', async (data) => {
      try {
        const { isActive } = data;

        await User.findByIdAndUpdate(socket.userId, {
          lastSeen: new Date(),
          status: isActive ? 'online' : 'away'
        });

        // Notify contacts about presence change
        const chats = await Chat.find({
          participants: socket.userId,
          isActive: true,
          isDeleted: false
        });

        for (const chat of chats) {
          io.to(chat._id.toString()).emit('user_presence_changed', {
            user: socket.user.getPublicProfile(),
            isActive,
            chatId: chat._id
          });
        }

      } catch (error) {
        console.error('Error updating presence:', error);
        socket.emit('error', { message: 'Error updating presence' });
      }
    });

    // Handle voice/video call signaling
    socket.on('call_signal', (data) => {
      const { chatId, signal, type, callId } = data; // type: 'offer', 'answer', 'ice-candidate'
      
      socket.to(chatId).emit('call_signal', {
        signal,
        type,
        callId,
        from: socket.user.getPublicProfile()
      });
    });

    // Handle call events
    socket.on('call_start', (data) => {
      const { chatId, callId, callType } = data; // callType: 'voice', 'video'
      
      socket.to(chatId).emit('call_incoming', {
        callId,
        callType,
        from: socket.user.getPublicProfile(),
        chatId
      });
    });

    socket.on('call_end', (data) => {
      const { chatId, callId } = data;
      
      socket.to(chatId).emit('call_ended', {
        callId,
        from: socket.user.getPublicProfile(),
        chatId
      });
    });

    socket.on('call_decline', (data) => {
      const { chatId, callId } = data;
      
      socket.to(chatId).emit('call_declined', {
        callId,
        from: socket.user.getPublicProfile(),
        chatId
      });
    });

    // ==================== STATUS EVENTS ====================
    
    // Handle status creation
    socket.on('status_created', async (data) => {
      try {
        const { statusId } = data;
        
        // Get the status with user info
        const status = await Status.findById(statusId)
          .populate('userId', 'name email profilePicture')
          .lean();
        
        if (!status) {
          socket.emit('error', { message: 'Status not found' });
          return;
        }
        
        // Get user's contacts
        const user = await User.findById(socket.userId).populate('contacts');
        const contactIds = user.contacts.map(contact => contact._id.toString());
        
        // Notify all contacts about new status
        contactIds.forEach(contactId => {
          const contactSocket = Array.from(connectedUsers.values())
            .find(userSocket => userSocket.userId === contactId);
          
          if (contactSocket) {
            contactSocket.emit('new_status', {
              status: {
                id: status._id,
                type: status.type,
                content: status.content,
                backgroundColor: status.backgroundColor,
                fontFamily: status.fontFamily,
                songTitle: status.songTitle,
                artist: status.artist,
                duration: status.duration,
                template: status.template,
                audioPath: status.audioPath,
                audioDuration: status.audioDuration,
                imagePath: status.imagePath,
                viewCount: status.views.length,
                hasUserViewed: false,
                createdAt: status.createdAt,
                expiresAt: status.expiresAt,
                user: {
                  id: status.userId._id,
                  name: status.userId.name,
                  email: status.userId.email,
                  profilePicture: status.userId.profilePicture
                }
              }
            });
          }
        });
        
        socket.emit('status_created_success', { statusId });
      } catch (error) {
        console.error('Error handling status creation:', error);
        socket.emit('error', { message: 'Error creating status' });
      }
    });
    
    // Handle status view
    socket.on('status_viewed', async (data) => {
      try {
        const { statusId } = data;
        
        const status = await Status.findById(statusId);
        if (!status) {
          socket.emit('error', { message: 'Status not found' });
          return;
        }
        
        // Check if user can view this status
        const currentUser = await User.findById(socket.userId).populate('contacts');
        const isContact = currentUser.contacts.some(contact => contact._id.toString() === status.userId.toString());
        const isOwnStatus = socket.userId === status.userId.toString();
        
        if (!isContact && !isOwnStatus) {
          socket.emit('error', { message: 'Access denied' });
          return;
        }
        
        // Add view
        await status.addView(socket.userId);
        
        // Notify status owner about the view
        if (status.userId.toString() !== socket.userId) {
          const ownerSocket = Array.from(connectedUsers.values())
            .find(userSocket => userSocket.userId === status.userId.toString());
          
          if (ownerSocket) {
            ownerSocket.emit('status_viewed', {
              statusId: status._id,
              viewedBy: {
                id: socket.user._id,
                name: socket.user.name,
                profilePicture: socket.user.profilePicture
              },
              viewCount: status.views.length
            });
          }
        }
        
        socket.emit('status_viewed_success', { 
          statusId, 
          viewCount: status.views.length 
        });
      } catch (error) {
        console.error('Error handling status view:', error);
        socket.emit('error', { message: 'Error viewing status' });
      }
    });
    
    // Handle status deletion
    socket.on('status_deleted', async (data) => {
      try {
        const { statusId } = data;
        
        const status = await Status.findOne({
          _id: statusId,
          userId: socket.userId
        });
        
        if (!status) {
          socket.emit('error', { message: 'Status not found or access denied' });
          return;
        }
        
        // Delete the status
        await Status.findByIdAndDelete(statusId);
        
        // Notify all contacts about status deletion
        const user = await User.findById(socket.userId).populate('contacts');
        const contactIds = user.contacts.map(contact => contact._id.toString());
        
        contactIds.forEach(contactId => {
          const contactSocket = Array.from(connectedUsers.values())
            .find(userSocket => userSocket.userId === contactId);
          
          if (contactSocket) {
            contactSocket.emit('status_deleted', { statusId });
          }
        });
        
        socket.emit('status_deleted_success', { statusId });
      } catch (error) {
        console.error('Error handling status deletion:', error);
        socket.emit('error', { message: 'Error deleting status' });
      }
    });
    
    // ==================== END STATUS EVENTS ====================
    
    // Handle disconnect
    socket.on('disconnect', async (reason) => {
      console.log(`⚠️ User ${socket.user.username} disconnected`);
      console.log(`📊 Disconnect reason: ${reason}`);
      console.log(`📊 Socket ID: ${socket.id}`);
      
      // Log different disconnect reasons
      if (reason === 'io server disconnect') {
        console.error('❌ Server forcefully disconnected the client');
      } else if (reason === 'io client disconnect') {
        console.log('✅ Client initiated disconnect');
      } else if (reason === 'ping timeout') {
        console.error('❌ Connection lost - ping timeout');
      } else if (reason === 'transport close') {
        console.error('❌ Transport closed unexpectedly');
      } else if (reason === 'transport error') {
        console.error('❌ Transport error occurred');
      } else {
        console.warn(`⚠️ Unknown disconnect reason: ${reason}`);
      }

      // Remove user from connected users
      connectedUsers.delete(socket.userId);

      // Update user status to offline
      try {
        await User.findByIdAndUpdate(socket.userId, {
          status: 'offline',
          lastSeen: new Date()
        });

        // Notify contacts about status change
        const chats = await Chat.find({
          participants: socket.userId,
          isActive: true,
          isDeleted: false
        });

        for (const chat of chats) {
          socket.to(chat._id.toString()).emit('user_status_changed', {
            user: socket.user.getPublicProfile(),
            status: 'offline',
            chatId: chat._id
          });
        }
      } catch (error) {
        console.error('Error updating user status on disconnect:', error);
      }
    });

    // Handle errors
    socket.on('error', (error) => {
      console.error('Socket error:', error);
    });
  });

  // Handle connection errors
  io.on('connect_error', (error) => {
    console.error('Connection error:', error);
  });
};

/**
 * Get connected users
 */
const getConnectedUsers = () => {
  return Array.from(connectedUsers.values()).map(user => ({
    userId: user.user._id,
    username: user.user.username,
    connectedAt: user.connectedAt
  }));
};

/**
 * Send message to specific user
 */
const sendToUser = (userId, event, data) => {
  const userConnection = connectedUsers.get(userId);
  if (userConnection) {
    io.to(userConnection.socketId).emit(event, data);
    return true;
  }
  return false;
};

/**
 * Send message to chat room
 */
const sendToChat = (chatId, event, data) => {
  io.to(chatId).emit(event, data);
};

module.exports = {
  socketHandler,
  getConnectedUsers,
  sendToUser,
  sendToChat
};

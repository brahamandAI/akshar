const mongoose = require('mongoose');

const messageSchema = new mongoose.Schema({
  chatId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Chat',
    required: true,
    index: true
  },
  senderId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true,
    index: true
  },
  content: {
    type: String,
    required: function() {
      return this.type === 'text' || this.type === 'reply';
    },
    maxlength: [4000, 'Message content cannot exceed 4000 characters'],
    trim: true
  },
  type: {
    type: String,
    enum: [
      'text',
      'image',
      'video',
      'audio',
      'document',
      'location',
      'contact',
      'sticker',
      'reply',
      'forward',
      'system'
    ],
    default: 'text',
    index: true
  },
  mediaUrl: {
    type: String,
    required: function() {
      return ['image', 'video', 'audio', 'document', 'sticker'].includes(this.type);
    }
  },
  mediaMetadata: {
    filename: String,
    originalName: String,
    mimeType: String,
    size: Number,
    duration: Number, // for audio/video
    width: Number, // for images/videos
    height: Number, // for images/videos
    thumbnail: String, // for videos
    waveform: [Number] // for audio waveforms
  },
  location: {
    latitude: {
      type: Number,
      min: -90,
      max: 90
    },
    longitude: {
      type: Number,
      min: -180,
      max: 180
    },
    address: String,
    name: String
  },
  contact: {
    name: String,
    phone: String,
    email: String,
    vcard: String
  },
  replyTo: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Message',
    default: null
  },
  forwardedFrom: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Message',
    default: null
  },
  forwardedCount: {
    type: Number,
    default: 0
  },
  isEdited: {
    type: Boolean,
    default: false
  },
  editedAt: {
    type: Date
  },
  editHistory: [{
    content: String,
    editedAt: {
      type: Date,
      default: Date.now
    }
  }],
  isDeleted: {
    type: Boolean,
    default: false
  },
  deletedAt: {
    type: Date
  },
  deletedBy: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  },
  readBy: [{
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User',
      required: true
    },
    readAt: {
      type: Date,
      default: Date.now
    }
  }],
  deliveredTo: [{
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User',
      required: true
    },
    deliveredAt: {
      type: Date,
      default: Date.now
    }
  }],
  reactions: [{
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User',
      required: true
    },
    emoji: {
      type: String,
      required: true
    },
    createdAt: {
      type: Date,
      default: Date.now
    }
  }],
  mentions: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }],
  hashtags: [String],
  isEncrypted: {
    type: Boolean,
    default: false
  },
  encryptionKey: {
    type: String,
    select: false
  },
  isSystemMessage: {
    type: Boolean,
    default: false
  },
  systemMessageType: {
    type: String,
    enum: [
      'user_joined',
      'user_left',
      'group_created',
      'group_renamed',
      'group_description_changed',
      'admin_added',
      'admin_removed',
      'user_blocked',
      'user_unblocked'
    ]
  },
  metadata: {
    clientMessageId: String,
    appVersion: String,
    platform: {
      type: String,
      enum: ['android', 'ios', 'web']
    },
    deviceInfo: String
  }
}, {
  timestamps: true,
  toJSON: { virtuals: true },
  toObject: { virtuals: true }
});

// Virtual for message status
messageSchema.virtual('status').get(function() {
  if (this.isDeleted) return 'deleted';
  if (this.isEdited) return 'edited';
  return 'sent';
});

// Virtual for formatted content (for display)
messageSchema.virtual('displayContent').get(function() {
  if (this.isDeleted) {
    return 'This message was deleted';
  }
  
  if (this.type === 'image') return '📷 Photo';
  if (this.type === 'video') return '🎥 Video';
  if (this.type === 'audio') return '🎵 Audio';
  if (this.type === 'document') return '📄 Document';
  if (this.type === 'location') return '📍 Location';
  if (this.type === 'contact') return '👤 Contact';
  if (this.type === 'sticker') return '😀 Sticker';
  if (this.type === 'system') return this.content;
  
  return this.content;
});

// Indexes for better query performance
messageSchema.index({ chatId: 1, createdAt: -1 });
messageSchema.index({ senderId: 1, createdAt: -1 });
messageSchema.index({ type: 1, createdAt: -1 });
messageSchema.index({ isDeleted: 1 });
messageSchema.index({ 'readBy.userId': 1 });

// Compound indexes
messageSchema.index({ chatId: 1, isDeleted: 1, createdAt: -1 });
messageSchema.index({ chatId: 1, type: 1, createdAt: -1 });

// Pre-save middleware to update chat's last message
messageSchema.pre('save', async function(next) {
  if (this.isNew && !this.isSystemMessage) {
    try {
      const Chat = mongoose.model('Chat');
      await Chat.findByIdAndUpdate(this.chatId, {
        lastMessage: this._id,
        lastMessageAt: this.createdAt,
        lastMessagePreview: this.displayContent,
        lastMessageSender: this.senderId,
        $inc: { 'metadata.totalMessages': 1 }
      });
    } catch (error) {
      console.error('Error updating chat last message:', error);
    }
  }
  next();
});

// Pre-save middleware to update media count
messageSchema.pre('save', async function(next) {
  if (this.isNew && ['image', 'video', 'audio', 'document'].includes(this.type)) {
    try {
      const Chat = mongoose.model('Chat');
      await Chat.findByIdAndUpdate(this.chatId, {
        $inc: { 'metadata.totalMedia': 1 }
      });
    } catch (error) {
      console.error('Error updating chat media count:', error);
    }
  }
  next();
});

// Instance method to mark as read
messageSchema.methods.markAsRead = function(userId) {
  // Check if already read by this user
  const alreadyRead = this.readBy.some(read => read.userId.equals(userId));
  if (!alreadyRead) {
    this.readBy.push({ userId, readAt: new Date() });
  }
  return this.save();
};

// Instance method to mark as delivered
messageSchema.methods.markAsDelivered = function(userId) {
  // Check if already delivered to this user
  const alreadyDelivered = this.deliveredTo.some(delivered => delivered.userId.equals(userId));
  if (!alreadyDelivered) {
    this.deliveredTo.push({ userId, deliveredAt: new Date() });
  }
  return this.save();
};

// Instance method to add reaction
messageSchema.methods.addReaction = function(userId, emoji) {
  // Remove existing reaction from this user
  this.reactions = this.reactions.filter(reaction => !reaction.userId.equals(userId));
  
  // Add new reaction
  this.reactions.push({
    userId,
    emoji,
    createdAt: new Date()
  });
  
  return this.save();
};

// Instance method to remove reaction
messageSchema.methods.removeReaction = function(userId, emoji) {
  this.reactions = this.reactions.filter(reaction => 
    !(reaction.userId.equals(userId) && reaction.emoji === emoji)
  );
  return this.save();
};

// Instance method to soft delete message
messageSchema.methods.softDelete = function(deletedBy) {
  this.isDeleted = true;
  this.deletedAt = new Date();
  this.deletedBy = deletedBy;
  this.content = '';
  this.mediaUrl = '';
  this.mediaMetadata = {};
  return this.save();
};

// Instance method to edit message
messageSchema.methods.editMessage = function(newContent) {
  // Save old content to edit history
  this.editHistory.push({
    content: this.content,
    editedAt: this.editedAt || this.createdAt
  });
  
  // Update content and edit status
  this.content = newContent;
  this.isEdited = true;
  this.editedAt = new Date();
  
  return this.save();
};

// Instance method to forward message
messageSchema.methods.forwardMessage = function(newChatId, senderId) {
  const Message = mongoose.model('Message');
  
  const forwardedMessage = new Message({
    chatId: newChatId,
    senderId: senderId,
    content: this.content,
    type: this.type,
    mediaUrl: this.mediaUrl,
    mediaMetadata: this.mediaMetadata,
    location: this.location,
    contact: this.contact,
    forwardedFrom: this._id,
    forwardedCount: this.forwardedCount + 1,
    mentions: this.mentions,
    hashtags: this.hashtags
  });
  
  return forwardedMessage.save();
};

// Static method to find chat messages
messageSchema.statics.findChatMessages = function(chatId, options = {}) {
  const {
    limit = 50,
    skip = 0,
    before = null,
    after = null,
    type = null,
    search = null
  } = options;

  const query = {
    chatId: chatId,
    isDeleted: false
  };

  // Filter by message type
  if (type && type !== 'all') {
    query.type = type;
  }

  // Filter by content search
  if (search) {
    query.content = { $regex: search, $options: 'i' };
  }

  // Filter by date range
  if (before) {
    query.createdAt = { ...query.createdAt, $lt: before };
  }
  if (after) {
    query.createdAt = { ...query.createdAt, $gt: after };
  }

  return this.find(query)
    .populate('senderId', 'username firstName lastName avatar')
    .populate('replyTo')
    .populate('forwardedFrom')
    .populate('readBy.userId', 'username firstName lastName')
    .populate('deliveredTo.userId', 'username firstName lastName')
    .populate('reactions.userId', 'username firstName lastName')
    .sort({ createdAt: -1 })
    .limit(limit)
    .skip(skip);
};

// Static method to search messages
messageSchema.statics.searchMessages = function(userId, searchTerm, options = {}) {
  const {
    limit = 20,
    skip = 0,
    chatId = null,
    type = null
  } = options;

  const query = {
    $or: [
      { content: { $regex: searchTerm, $options: 'i' } },
      { 'mediaMetadata.originalName': { $regex: searchTerm, $options: 'i' } }
    ],
    isDeleted: false
  };

  // Filter by chat
  if (chatId) {
    query.chatId = chatId;
  }

  // Filter by type
  if (type && type !== 'all') {
    query.type = type;
  }

  // Filter by user's chats (user must be participant)
  if (!chatId) {
    query['$expr'] = {
      $in: [userId, '$chat.participants']
    };
  }

  return this.find(query)
    .populate('chatId', 'participants isGroup groupName')
    .populate('senderId', 'username firstName lastName avatar')
    .populate('replyTo')
    .sort({ createdAt: -1 })
    .limit(limit)
    .skip(skip);
};

// Static method to get message statistics
messageSchema.statics.getMessageStats = function(chatId, options = {}) {
  const {
    startDate = null,
    endDate = null,
    userId = null
  } = options;

  const matchQuery = {
    chatId: chatId,
    isDeleted: false
  };

  if (startDate || endDate) {
    matchQuery.createdAt = {};
    if (startDate) matchQuery.createdAt.$gte = startDate;
    if (endDate) matchQuery.createdAt.$lte = endDate;
  }

  if (userId) {
    matchQuery.senderId = userId;
  }

  return this.aggregate([
    { $match: matchQuery },
    {
      $group: {
        _id: '$type',
        count: { $sum: 1 },
        totalSize: { $sum: '$mediaMetadata.size' }
      }
    }
  ]);
};

module.exports = mongoose.model('Message', messageSchema);

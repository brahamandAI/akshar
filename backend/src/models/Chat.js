const mongoose = require('mongoose');

const chatSchema = new mongoose.Schema({
  participants: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  }],
  isGroup: {
    type: Boolean,
    default: false
  },
  groupName: {
    type: String,
    trim: true,
    maxlength: [50, 'Group name cannot exceed 50 characters'],
    validate: {
      validator: function(name) {
        if (this.isGroup) {
          return name && name.length > 0;
        }
        return true;
      },
      message: 'Group name is required for group chats'
    }
  },
  groupDescription: {
    type: String,
    maxlength: [200, 'Group description cannot exceed 200 characters'],
    default: ''
  },
  groupAvatar: {
    type: String,
    default: null
  },
  admins: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }],
  createdBy: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  lastMessage: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Message',
    default: null
  },
  lastMessageAt: {
    type: Date,
    default: Date.now
  },
  lastMessagePreview: {
    type: String,
    maxlength: [100, 'Message preview cannot exceed 100 characters'],
    default: ''
  },
  lastMessageSender: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    default: null
  },
  unreadCount: {
    type: Map,
    of: Number,
    default: new Map()
  },
  isActive: {
    type: Boolean,
    default: true
  },
  isArchived: {
    type: Boolean,
    default: false
  },
  isMuted: {
    type: Map,
    of: Boolean,
    default: new Map()
  },
  isPinned: {
    type: Map,
    of: Boolean,
    default: new Map()
  },
  settings: {
    allowInvites: {
      type: Boolean,
      default: true
    },
    onlyAdminsCanSendMessages: {
      type: Boolean,
      default: false
    },
    onlyAdminsCanEditGroupInfo: {
      type: Boolean,
      default: false
    },
    disappearingMessages: {
      enabled: {
        type: Boolean,
        default: false
      },
      duration: {
        type: Number,
        enum: [3600, 86400, 604800, 2592000], // 1 hour, 1 day, 1 week, 1 month
        default: 86400
      }
    }
  },
  metadata: {
    totalMessages: {
      type: Number,
      default: 0
    },
    totalMedia: {
      type: Number,
      default: 0
    },
    totalFiles: {
      type: Number,
      default: 0
    }
  },
  isDeleted: {
    type: Boolean,
    default: false
  },
  deletedAt: {
    type: Date
  }
}, {
  timestamps: true,
  toJSON: { virtuals: true },
  toObject: { virtuals: true }
});

// Virtual for chat display name
chatSchema.virtual('displayName').get(function() {
  if (this.isGroup) {
    return this.groupName;
  }
  return 'Direct Chat';
});

// Virtual for chat avatar
chatSchema.virtual('chatAvatar').get(function() {
  if (this.isGroup && this.groupAvatar) {
    return this.groupAvatar;
  }
  if (this.isGroup) {
    // Generate group avatar with participant initials
    const initials = this.participants.slice(0, 3).map(p => 
      p.firstName ? p.firstName.charAt(0).toUpperCase() : '?'
    ).join('');
    return `https://ui-avatars.com/api/?name=${encodeURIComponent(initials)}&background=random&size=200`;
  }
  return null;
});

// Indexes for better query performance
chatSchema.index({ participants: 1 });
chatSchema.index({ isGroup: 1 });
chatSchema.index({ lastMessageAt: -1 });
chatSchema.index({ createdBy: 1 });
chatSchema.index({ isActive: 1, isDeleted: 1 });

// Compound index for finding chats by participant
chatSchema.index({ 
  participants: 1, 
  isActive: 1, 
  isDeleted: 1 
});

// Pre-save middleware to validate participants
chatSchema.pre('save', function(next) {
  if (this.isGroup) {
    // Group chat must have at least 2 participants
    if (this.participants.length < 2) {
      return next(new Error('Group chat must have at least 2 participants'));
    }
    // Group chat must have a name
    if (!this.groupName || this.groupName.trim().length === 0) {
      return next(new Error('Group chat must have a name'));
    }
  } else {
    // Direct chat must have exactly 2 participants
    if (this.participants.length !== 2) {
      return next(new Error('Direct chat must have exactly 2 participants'));
    }
  }
  next();
});

// Pre-save middleware to update lastMessageAt
chatSchema.pre('save', function(next) {
  if (this.isModified('lastMessage') && this.lastMessage) {
    this.lastMessageAt = new Date();
  }
  next();
});

// Instance method to add participant
chatSchema.methods.addParticipant = function(userId) {
  if (!this.participants.includes(userId)) {
    this.participants.push(userId);
    this.unreadCount.set(userId.toString(), 0);
    this.isMuted.set(userId.toString(), false);
    this.isPinned.set(userId.toString(), false);
  }
  return this.save();
};

// Instance method to remove participant
chatSchema.methods.removeParticipant = function(userId) {
  this.participants = this.participants.filter(id => !id.equals(userId));
  this.unreadCount.delete(userId.toString());
  this.isMuted.delete(userId.toString());
  this.isPinned.delete(userId.toString());
  this.admins = this.admins.filter(id => !id.equals(userId));
  return this.save();
};

// Instance method to check if user is participant
chatSchema.methods.isParticipant = function(userId) {
  return this.participants.some(id => id.equals(userId));
};

// Instance method to check if user is admin
chatSchema.methods.isAdmin = function(userId) {
  return this.admins.some(id => id.equals(userId));
};

// Instance method to make user admin
chatSchema.methods.makeAdmin = function(userId) {
  if (!this.admins.includes(userId)) {
    this.admins.push(userId);
  }
  return this.save();
};

// Instance method to remove admin
chatSchema.methods.removeAdmin = function(userId) {
  this.admins = this.admins.filter(id => !id.equals(userId));
  return this.save();
};

// Instance method to increment unread count
chatSchema.methods.incrementUnreadCount = function(userId) {
  if (!this.isParticipant(userId)) return this;
  
  const currentCount = this.unreadCount.get(userId.toString()) || 0;
  this.unreadCount.set(userId.toString(), currentCount + 1);
  return this.save();
};

// Instance method to reset unread count
chatSchema.methods.resetUnreadCount = function(userId) {
  this.unreadCount.set(userId.toString(), 0);
  return this.save();
};

// Instance method to toggle mute
chatSchema.methods.toggleMute = function(userId) {
  const currentMuteStatus = this.isMuted.get(userId.toString()) || false;
  this.isMuted.set(userId.toString(), !currentMuteStatus);
  return this.save();
};

// Instance method to toggle pin
chatSchema.methods.togglePin = function(userId) {
  const currentPinStatus = this.isPinned.get(userId.toString()) || false;
  this.isPinned.set(userId.toString(), !currentPinStatus);
  return this.save();
};

// Instance method to archive chat
chatSchema.methods.archiveChat = function() {
  this.isArchived = true;
  return this.save();
};

// Instance method to unarchive chat
chatSchema.methods.unarchiveChat = function() {
  this.isArchived = false;
  return this.save();
};

// Static method to find or create direct chat
chatSchema.statics.findOrCreateDirectChat = async function(user1Id, user2Id) {
  // First, try to find existing direct chat
  let chat = await this.findOne({
    participants: { $all: [user1Id, user2Id] },
    isGroup: false,
    isActive: true,
    isDeleted: false
  }).populate('participants', 'username firstName lastName avatar status');

  if (chat) {
    return chat;
  }

  // Create new direct chat
  chat = new this({
    participants: [user1Id, user2Id],
    isGroup: false,
    createdBy: user1Id
  });

  await chat.save();
  
  return await this.findById(chat._id).populate('participants', 'username firstName lastName avatar status');
};

// Static method to find user chats
chatSchema.statics.findUserChats = function(userId, options = {}) {
  const {
    limit = 20,
    skip = 0,
    includeArchived = false
  } = options;

  const query = {
    participants: userId,
    isActive: true,
    isDeleted: false
  };

  if (!includeArchived) {
    query.isArchived = false;
  }

  return this.find(query)
    .populate('participants', 'username firstName lastName avatar status lastSeen')
    .populate('lastMessage')
    .populate('lastMessageSender', 'username firstName lastName')
    .sort({ lastMessageAt: -1 })
    .limit(limit)
    .skip(skip);
};

// Static method to find group chats
chatSchema.statics.findGroupChats = function(userId, options = {}) {
  const {
    limit = 20,
    skip = 0
  } = options;

  return this.find({
    participants: userId,
    isGroup: true,
    isActive: true,
    isDeleted: false
  })
  .populate('participants', 'username firstName lastName avatar status')
  .populate('admins', 'username firstName lastName')
  .populate('lastMessage')
  .populate('lastMessageSender', 'username firstName lastName')
  .sort({ lastMessageAt: -1 })
  .limit(limit)
  .skip(skip);
};

module.exports = mongoose.model('Chat', chatSchema);

const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const userSchema = new mongoose.Schema({
  username: {
    type: String,
    required: [true, 'Username is required'],
    unique: true,
    trim: true,
    minlength: [3, 'Username must be at least 3 characters long'],
    maxlength: [20, 'Username cannot exceed 20 characters'],
    match: [/^[a-zA-Z0-9_]+$/, 'Username can only contain letters, numbers, and underscores']
  },
  email: {
    type: String,
    required: [true, 'Email is required'],
    unique: true,
    lowercase: true,
    trim: true,
    match: [/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/, 'Please enter a valid email address']
  },
  password: {
    type: String,
    required: [true, 'Password is required'],
    minlength: [6, 'Password must be at least 6 characters long'],
    select: false // Don't include password in queries by default
  },
  firstName: {
    type: String,
    required: [true, 'First name is required'],
    trim: true,
    maxlength: [50, 'First name cannot exceed 50 characters']
  },
  lastName: {
    type: String,
    required: [true, 'Last name is required'],
    trim: true,
    maxlength: [50, 'Last name cannot exceed 50 characters']
  },
  avatar: {
    type: String,
    default: null
  },
  profilePicture: {
    type: String,
    default: null
  },
  name: {
    type: String,
    default: function() {
      return `${this.firstName} ${this.lastName}`;
    }
  },
  fcmToken: {
    type: String,
    default: null
  },
  status: {
    type: String,
    enum: ['online', 'offline', 'away', 'busy'],
    default: 'offline'
  },
  lastSeen: {
    type: Date,
    default: Date.now
  },
  bio: {
    type: String,
    maxlength: [150, 'Bio cannot exceed 150 characters'],
    default: ''
  },
  phoneNumber: {
    type: String,
    unique: true,
    sparse: true, // Allow null values but ensure uniqueness when present
    default: null,
    match: [/^\+?[\d\s\-\(\)]+$/, 'Please enter a valid phone number']
  },
  dateOfBirth: {
    type: Date,
    validate: {
      validator: function(date) {
        return date < new Date();
      },
      message: 'Date of birth must be in the past'
    }
  },
  isEmailVerified: {
    type: Boolean,
    default: false
  },
  isPhoneVerified: {
    type: Boolean,
    default: false
  },
  emailVerificationToken: {
    type: String,
    select: false
  },
  phoneVerificationToken: {
    type: String,
    select: false
  },
  passwordResetToken: {
    type: String,
    select: false
  },
  passwordResetExpires: {
    type: Date,
    select: false
  },
  twoFactorEnabled: {
    type: Boolean,
    default: false
  },
  twoFactorSecret: {
    type: String,
    select: false
  },
  blockedUsers: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }],
  appSettings: {
    darkMode: {
      type: Boolean,
      default: false
    },
    enterIsSend: {
      type: Boolean,
      default: false
    },
    mediaVisibility: {
      type: Boolean,
      default: true
    },
    conversationTones: {
      type: Boolean,
      default: true
    },
    vibrate: {
      type: Boolean,
      default: true
    },
    popupNotification: {
      type: Boolean,
      default: false
    },
    autoDownloadMedia: {
      type: Boolean,
      default: true
    },
    downloadOverMobileData: {
      type: Boolean,
      default: false
    }
  },
  privacySettings: {
    showLastSeen: {
      type: String,
      enum: ['everyone', 'contacts', 'nobody'],
      default: 'everyone'
    },
    showProfilePhoto: {
      type: String,
      enum: ['everyone', 'contacts', 'nobody'],
      default: 'everyone'
    },
    showStatus: {
      type: String,
      enum: ['everyone', 'contacts', 'nobody'],
      default: 'everyone'
    },
    showAbout: {
      type: String,
      enum: ['everyone', 'contacts', 'nobody'],
      default: 'everyone'
    }
  },
  deviceTokens: [{
    token: String,
    platform: {
      type: String,
      enum: ['android', 'ios', 'web']
    },
    lastUsed: {
      type: Date,
      default: Date.now
    }
  }],
  isActive: {
    type: Boolean,
    default: true
  },
  isDeleted: {
    type: Boolean,
    default: false
  },
  deletedAt: {
    type: Date
  },
  settings: {
    theme: {
      type: String,
      enum: ['light', 'dark', 'auto'],
      default: 'auto'
    },
    privacy: {
      lastSeen: { type: Boolean, default: true },
      profilePhoto: { type: Boolean, default: true },
      status: { type: Boolean, default: true },
      about: { type: Boolean, default: true }
    },
    notifications: {
      messageNotifications: { type: Boolean, default: true },
      groupNotifications: { type: Boolean, default: true },
      callNotifications: { type: Boolean, default: true },
      vibrate: { type: Boolean, default: true },
      popupNotification: { type: Boolean, default: false }
    },
    chatSettings: {
      enterIsSend: { type: Boolean, default: false },
      mediaVisibility: { type: Boolean, default: true },
      conversationTones: { type: Boolean, default: true },
      chatBackup: { type: Boolean, default: false }
    },
    storageSettings: {
      autoDownloadMedia: { type: Boolean, default: true },
      downloadOverMobileData: { type: Boolean, default: false }
    }
  }
}, {
  timestamps: true,
  toJSON: { virtuals: true },
  toObject: { virtuals: true }
});

// Virtual for full name
userSchema.virtual('fullName').get(function() {
  return `${this.firstName} ${this.lastName}`;
});

// Virtual for profile URL
userSchema.virtual('profileUrl').get(function() {
  return this.avatar ? this.avatar : `https://ui-avatars.com/api/?name=${encodeURIComponent(this.fullName)}&background=random`;
});

// Index for better query performance
userSchema.index({ email: 1 });
userSchema.index({ username: 1 });
userSchema.index({ phoneNumber: 1 });
userSchema.index({ status: 1 });
userSchema.index({ lastSeen: -1 });

// Pre-save middleware to hash password
userSchema.pre('save', async function(next) {
  // Only hash the password if it has been modified (or is new)
  if (!this.isModified('password')) return next();

  try {
    // Hash password with cost of 12
    const salt = await bcrypt.genSalt(12);
    this.password = await bcrypt.hash(this.password, salt);
    next();
  } catch (error) {
    next(error);
  }
});

// Pre-save middleware to update lastSeen when status changes
userSchema.pre('save', function(next) {
  if (this.isModified('status') && this.status === 'online') {
    this.lastSeen = new Date();
  }
  next();
});

// Instance method to check password
userSchema.methods.comparePassword = async function(candidatePassword) {
  return await bcrypt.compare(candidatePassword, this.password);
};

// Instance method to generate JWT token
userSchema.methods.generateAuthToken = function() {
  const payload = {
    id: this._id,
    username: this.username,
    email: this.email
  };

  return jwt.sign(payload, process.env.JWT_SECRET, {
    expiresIn: process.env.JWT_EXPIRES_IN || '24h'
  });
};

// Instance method to generate refresh token
userSchema.methods.generateRefreshToken = function() {
  const payload = {
    id: this._id,
    type: 'refresh'
  };

  return jwt.sign(payload, process.env.JWT_SECRET, {
    expiresIn: process.env.JWT_REFRESH_EXPIRES_IN || '7d'
  });
};

// Instance method to get public profile
userSchema.methods.getPublicProfile = function() {
  const userObject = this.toObject();
  delete userObject.password;
  delete userObject.emailVerificationToken;
  delete userObject.phoneVerificationToken;
  delete userObject.passwordResetToken;
  delete userObject.passwordResetExpires;
  delete userObject.twoFactorSecret;
  delete userObject.deviceTokens;
  delete userObject.blockedUsers;
  delete userObject.fcmToken;
  
  // Ensure name is set
  if (!userObject.name) {
    userObject.name = `${userObject.firstName} ${userObject.lastName}`;
  }
  
  return userObject;
};

// Instance method to update last seen
userSchema.methods.updateLastSeen = function() {
  this.lastSeen = new Date();
  return this.save();
};

// Instance method to add device token
userSchema.methods.addDeviceToken = function(token, platform) {
  // Remove existing token if it exists
  this.deviceTokens = this.deviceTokens.filter(dt => dt.token !== token);
  
  // Add new token
  this.deviceTokens.push({
    token,
    platform,
    lastUsed: new Date()
  });
  
  // Keep only last 5 device tokens
  if (this.deviceTokens.length > 5) {
    this.deviceTokens = this.deviceTokens
      .sort((a, b) => b.lastUsed - a.lastUsed)
      .slice(0, 5);
  }
  
  return this.save();
};

// Static method to find user by email or username
userSchema.statics.findByCredentials = async function(emailOrUsername, password) {
  const user = await this.findOne({
    $or: [
      { email: emailOrUsername.toLowerCase() },
      { username: emailOrUsername }
    ],
    isActive: true,
    isDeleted: false
  }).select('+password');

  if (!user) {
    throw new Error('Invalid credentials');
  }

  const isMatch = await user.comparePassword(password);
  if (!isMatch) {
    throw new Error('Invalid credentials');
  }

  return user;
};

// Static method to find online users
userSchema.statics.findOnlineUsers = function() {
  return this.find({
    status: 'online',
    isActive: true,
    isDeleted: false
  }).select('username firstName lastName avatar status lastSeen');
};

module.exports = mongoose.model('User', userSchema);

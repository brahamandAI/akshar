const mongoose = require('mongoose');

const statusSchema = new mongoose.Schema({
  userId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  type: {
    type: String,
    enum: ['TEXT', 'MUSIC', 'LAYOUT', 'VOICE', 'IMAGE'],
    required: true
  },
  content: {
    type: String,
    required: true
  },
  // For text status
  backgroundColor: {
    type: String,
    default: '#2E7D32'
  },
  fontFamily: {
    type: String,
    default: 'default'
  },
  // For music status
  songTitle: {
    type: String,
    default: ''
  },
  artist: {
    type: String,
    default: ''
  },
  duration: {
    type: String,
    default: ''
  },
  // For layout status
  template: {
    name: String,
    backgroundColor: String,
    tags: [String],
    defaultText: String
  },
  // For voice status
  audioPath: {
    type: String,
    default: ''
  },
  audioDuration: {
    type: Number,
    default: 0
  },
  // For image status
  imagePath: {
    type: String,
    default: ''
  },
  // Metadata
  views: [{
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User'
    },
    viewedAt: {
      type: Date,
      default: Date.now
    }
  }],
  isActive: {
    type: Boolean,
    default: true
  },
  expiresAt: {
    type: Date,
    default: () => new Date(Date.now() + 24 * 60 * 60 * 1000) // 24 hours
  }
}, {
  timestamps: true
});

// Index for efficient queries
statusSchema.index({ userId: 1, createdAt: -1 });
statusSchema.index({ isActive: 1, expiresAt: 1 });

// Virtual for view count
statusSchema.virtual('viewCount').get(function() {
  return this.views.length;
});

// Method to add view
statusSchema.methods.addView = function(userId) {
  // Check if user already viewed
  const existingView = this.views.find(view => view.userId.toString() === userId.toString());
  if (!existingView) {
    this.views.push({ userId, viewedAt: new Date() });
    return this.save();
  }
  return Promise.resolve(this);
};

// Method to check if user has viewed
statusSchema.methods.hasUserViewed = function(userId) {
  return this.views.some(view => view.userId.toString() === userId.toString());
};

module.exports = mongoose.model('Status', statusSchema);

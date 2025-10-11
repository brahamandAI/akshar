const express = require('express');
const { body, validationResult } = require('express-validator');
const { User, Chat } = require('../models');
const { authMiddleware, asyncHandler, AppError } = require('../middleware');
const multer = require('multer');
const path = require('path');
const cloudinaryService = require('../services/cloudinaryService');

const router = express.Router();

// Configure multer for file uploads
const storage = multer.memoryStorage();
const upload = multer({
  storage: storage,
  limits: {
    fileSize: parseInt(process.env.MAX_FILE_SIZE) || 10 * 1024 * 1024, // 10MB
  },
  fileFilter: (req, file, cb) => {
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
    if (allowedTypes.includes(file.mimetype)) {
      cb(null, true);
    } else {
      cb(new Error('Only image files are allowed for avatar upload'), false);
    }
  }
});

/**
 * @route   GET /api/users/profile
 * @desc    Get current user profile
 * @access  Private
 */
router.get('/profile', authMiddleware, asyncHandler(async (req, res) => {
  res.json({
    success: true,
    data: {
      user: req.user.getPublicProfile()
    }
  });
}));

/**
 * @route   PUT /api/users/profile
 * @desc    Update user profile
 * @access  Private
 */
router.put('/profile', authMiddleware, [
  body('firstName')
    .optional()
    .trim()
    .isLength({ min: 1, max: 50 })
    .withMessage('First name must be between 1 and 50 characters'),
  body('lastName')
    .optional()
    .trim()
    .isLength({ min: 1, max: 50 })
    .withMessage('Last name must be between 1 and 50 characters'),
  body('bio')
    .optional()
    .trim()
    .isLength({ max: 150 })
    .withMessage('Bio cannot exceed 150 characters'),
  body('phoneNumber')
    .optional()
    .matches(/^\+?[\d\s\-\(\)]+$/)
    .withMessage('Please provide a valid phone number'),
  body('dateOfBirth')
    .optional()
    .isISO8601()
    .withMessage('Please provide a valid date of birth')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { firstName, lastName, bio, phoneNumber, dateOfBirth } = req.body;
  const allowedUpdates = { firstName, lastName, bio, phoneNumber, dateOfBirth };
  
  // Remove undefined values
  Object.keys(allowedUpdates).forEach(key => 
    allowedUpdates[key] === undefined && delete allowedUpdates[key]
  );

  // Check if phone number is already taken
  if (phoneNumber) {
    const existingUser = await User.findOne({
      phoneNumber,
      _id: { $ne: req.user._id }
    });

    if (existingUser) {
      throw new AppError('Phone number already in use', 400);
    }
  }

  const user = await User.findByIdAndUpdate(
    req.user._id,
    allowedUpdates,
    { new: true, runValidators: true }
  );

  res.json({
    success: true,
    message: 'Profile updated successfully',
    data: {
      user: user.getPublicProfile()
    }
  });
}));

/**
 * @route   POST /api/users/upload-avatar
 * @desc    Upload user avatar
 * @access  Private
 */
router.post('/upload-avatar', authMiddleware, upload.single('avatar'), asyncHandler(async (req, res) => {
  if (!req.file) {
    throw new AppError('No file uploaded', 400);
  }

  // ✅ Upload to Cloudinary
  const uploadResult = await cloudinaryService.uploadImage(req.file.buffer, {
    folder: 'akshar/avatars',
    transformation: [
      { width: 400, height: 400, crop: 'fill', gravity: 'face' },
      { quality: 'auto:good' },
      { fetch_format: 'auto' }
    ]
  });

  // Delete old avatar from Cloudinary if exists
  const user = await User.findById(req.user._id);
  if (user.avatar && user.avatar.includes('cloudinary')) {
    const publicId = user.avatar.split('/').slice(-2).join('/').split('.')[0];
    try {
      await cloudinaryService.deleteFile(`akshar/avatars/${publicId}`);
    } catch (error) {
      console.log('Error deleting old avatar:', error.message);
    }
  }

  // Update user avatar in MongoDB
  const updatedUser = await User.findByIdAndUpdate(
    req.user._id, 
    { avatar: uploadResult.url },
    { new: true }
  );

  res.json({
    success: true,
    message: 'Avatar uploaded successfully',
    data: {
      avatarUrl: uploadResult.url,
      user: updatedUser.getPublicProfile()
    }
  });
}));

/**
 * @route   PUT /api/users/settings
 * @desc    Update user app settings
 * @access  Private
 */
router.put('/settings', authMiddleware, asyncHandler(async (req, res) => {
  const { darkMode, enterIsSend, mediaVisibility, conversationTones, vibrate, popupNotification, autoDownloadMedia, downloadOverMobileData } = req.body;
  
  const settings = {};
  if (darkMode !== undefined) settings['appSettings.darkMode'] = darkMode;
  if (enterIsSend !== undefined) settings['appSettings.enterIsSend'] = enterIsSend;
  if (mediaVisibility !== undefined) settings['appSettings.mediaVisibility'] = mediaVisibility;
  if (conversationTones !== undefined) settings['appSettings.conversationTones'] = conversationTones;
  if (vibrate !== undefined) settings['appSettings.vibrate'] = vibrate;
  if (popupNotification !== undefined) settings['appSettings.popupNotification'] = popupNotification;
  if (autoDownloadMedia !== undefined) settings['appSettings.autoDownloadMedia'] = autoDownloadMedia;
  if (downloadOverMobileData !== undefined) settings['appSettings.downloadOverMobileData'] = downloadOverMobileData;
  
  const user = await User.findByIdAndUpdate(
    req.user._id,
    settings,
    { new: true }
  );
  
  res.json({
    success: true,
    message: 'Settings updated successfully',
    data: {
      user: user.getPublicProfile()
    }
  });
}));

/**
 * @route   PUT /api/users/privacy
 * @desc    Update user privacy settings
 * @access  Private
 */
router.put('/privacy', authMiddleware, asyncHandler(async (req, res) => {
  const { showLastSeen, showProfilePhoto, showStatus, showAbout } = req.body;
  
  const settings = {};
  if (showLastSeen) settings['privacySettings.showLastSeen'] = showLastSeen;
  if (showProfilePhoto) settings['privacySettings.showProfilePhoto'] = showProfilePhoto;
  if (showStatus) settings['privacySettings.showStatus'] = showStatus;
  if (showAbout) settings['privacySettings.showAbout'] = showAbout;
  
  const user = await User.findByIdAndUpdate(
    req.user._id,
    settings,
    { new: true }
  );
  
  res.json({
    success: true,
    message: 'Privacy settings updated successfully',
    data: {
      user: user.getPublicProfile()
    }
  });
}));

/**
 * @route   DELETE /api/users/avatar
 * @desc    Delete user avatar
 * @access  Private
 */
router.delete('/avatar', authMiddleware, asyncHandler(async (req, res) => {
  await User.findByIdAndUpdate(req.user._id, { avatar: null });

  res.json({
    success: true,
    message: 'Avatar deleted successfully'
  });
}));

/**
 * @route   PUT /api/users/status
 * @desc    Update user status
 * @access  Private
 */
router.put('/status', authMiddleware, [
  body('status')
    .isIn(['online', 'offline', 'away', 'busy'])
    .withMessage('Status must be one of: online, offline, away, busy')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { status } = req.body;

  const user = await User.findByIdAndUpdate(
    req.user._id,
    { 
      status,
      lastSeen: new Date()
    },
    { new: true }
  );

  res.json({
    success: true,
    message: 'Status updated successfully',
    data: {
      user: user.getPublicProfile()
    }
  });
}));

/**
 * @route   PUT /api/users/privacy-settings
 * @desc    Update privacy settings
 * @access  Private
 */
router.put('/privacy-settings', authMiddleware, [
  body('showLastSeen')
    .optional()
    .isIn(['everyone', 'contacts', 'nobody'])
    .withMessage('showLastSeen must be one of: everyone, contacts, nobody'),
  body('showProfilePhoto')
    .optional()
    .isIn(['everyone', 'contacts', 'nobody'])
    .withMessage('showProfilePhoto must be one of: everyone, contacts, nobody'),
  body('showStatus')
    .optional()
    .isIn(['everyone', 'contacts', 'nobody'])
    .withMessage('showStatus must be one of: everyone, contacts, nobody'),
  body('showAbout')
    .optional()
    .isIn(['everyone', 'contacts', 'nobody'])
    .withMessage('showAbout must be one of: everyone, contacts, nobody')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { showLastSeen, showProfilePhoto, showStatus, showAbout } = req.body;

  const privacySettings = {};
  if (showLastSeen !== undefined) privacySettings.showLastSeen = showLastSeen;
  if (showProfilePhoto !== undefined) privacySettings.showProfilePhoto = showProfilePhoto;
  if (showStatus !== undefined) privacySettings.showStatus = showStatus;
  if (showAbout !== undefined) privacySettings.showAbout = showAbout;

  const user = await User.findByIdAndUpdate(
    req.user._id,
    { privacySettings: { ...req.user.privacySettings, ...privacySettings } },
    { new: true }
  );

  res.json({
    success: true,
    message: 'Privacy settings updated successfully',
    data: {
      user: user.getPublicProfile()
    }
  });
}));

/**
 * @route   GET /api/users/search
 * @desc    Search users (typeahead, 1-character allowed)
 * @access  Private
 */
router.get('/search', authMiddleware, asyncHandler(async (req, res) => {
  const { q, limit = 20, skip = 0 } = req.query;
  const query = q?.trim() || '';

  // Empty query returns empty list instead of error
  if (!query) {
    return res.status(200).json({
      success: true,
      data: {
        users: [],
        total: 0,
        limit: parseInt(limit),
        skip: parseInt(skip)
      }
    });
  }

  // Regex for partial, case-insensitive match
  const searchRegex = new RegExp(query, 'i');

  const users = await User.find({
    $and: [
      { _id: { $ne: req.user._id } }, // Exclude current user
      { isActive: true },
      { isDeleted: false },
      {
        $or: [
          { username: searchRegex },
          { firstName: searchRegex },
          { lastName: searchRegex },
          { email: searchRegex }
        ]
      }
    ]
  })
  .select('username firstName lastName avatar status lastSeen')
  .limit(parseInt(limit))
  .skip(parseInt(skip))
  .sort({ username: 1 });

  res.json({
    success: true,
    data: {
      users: users.map(user => user.getPublicProfile()),
      total: users.length,
      limit: parseInt(limit),
      skip: parseInt(skip)
    }
  });
}));

/**
 * @route   GET /api/users/contacts
 * @desc    Get user contacts (people they've chatted with)
 * @access  Private
 */
router.get('/contacts', authMiddleware, asyncHandler(async (req, res) => {
  const { limit = 50, skip = 0 } = req.query;

  // Find all chats where user is a participant
  const chats = await Chat.find({
    participants: req.user._id,
    isActive: true,
    isDeleted: false
  }).populate('participants', 'username firstName lastName avatar status lastSeen');

  // Extract unique contacts (excluding current user)
  const contactIds = new Set();
  chats.forEach(chat => {
    chat.participants.forEach(participant => {
      if (!participant._id.equals(req.user._id)) {
        contactIds.add(participant._id.toString());
      }
    });
  });

  // Get contact details
  const contacts = await User.find({
    _id: { $in: Array.from(contactIds) },
    isActive: true,
    isDeleted: false
  })
  .select('username firstName lastName avatar status lastSeen')
  .limit(parseInt(limit))
  .skip(parseInt(skip))
  .sort({ lastSeen: -1 });

  res.json({
    success: true,
    data: {
      contacts: contacts.map(contact => contact.getPublicProfile()),
      total: contacts.length,
      limit: parseInt(limit),
      skip: parseInt(skip)
    }
  });
}));

/**
 * @route   POST /api/users/block
 * @desc    Block a user
 * @access  Private
 */
router.post('/block', authMiddleware, [
  body('userId')
    .isMongoId()
    .withMessage('Valid user ID is required')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { userId } = req.body;

  if (userId === req.user._id.toString()) {
    throw new AppError('You cannot block yourself', 400);
  }

  const userToBlock = await User.findById(userId);
  if (!userToBlock) {
    throw new AppError('User not found', 404);
  }

  // Add to blocked users if not already blocked
  if (!req.user.blockedUsers.includes(userId)) {
    req.user.blockedUsers.push(userId);
    await req.user.save();
  }

  res.json({
    success: true,
    message: 'User blocked successfully'
  });
}));

/**
 * @route   DELETE /api/users/block/:userId
 * @desc    Unblock a user
 * @access  Private
 */
router.delete('/block/:userId', authMiddleware, asyncHandler(async (req, res) => {
  const { userId } = req.params;

  req.user.blockedUsers = req.user.blockedUsers.filter(
    blockedId => !blockedId.equals(userId)
  );
  await req.user.save();

  res.json({
    success: true,
    message: 'User unblocked successfully'
  });
}));

/**
 * @route   GET /api/users/blocked
 * @desc    Get blocked users
 * @access  Private
 */
router.get('/blocked', authMiddleware, asyncHandler(async (req, res) => {
  const blockedUsers = await User.find({
    _id: { $in: req.user.blockedUsers },
    isActive: true
  }).select('username firstName lastName avatar');

  res.json({
    success: true,
    data: {
      blockedUsers: blockedUsers.map(user => user.getPublicProfile())
    }
  });
}));

/**
 * @route   DELETE /api/users/account
 * @desc    Delete user account
 * @access  Private
 */
router.delete('/account', authMiddleware, [
  body('password')
    .notEmpty()
    .withMessage('Password is required to delete account')
], asyncHandler(async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      success: false,
      message: 'Validation failed',
      errors: errors.array()
    });
  }

  const { password } = req.body;

  // Verify password
  const user = await User.findById(req.user._id).select('+password');
  const isMatch = await user.comparePassword(password);
  
  if (!isMatch) {
    throw new AppError('Incorrect password', 400);
  }

  // Soft delete user
  user.isDeleted = true;
  user.deletedAt = new Date();
  user.status = 'offline';
  user.email = `deleted_${Date.now()}_${user.email}`;
  user.username = `deleted_${Date.now()}_${user.username}`;
  user.firstName = 'Deleted';
  user.lastName = 'User';
  user.avatar = null;
  user.bio = '';
  user.phoneNumber = null;
  await user.save();

  res.json({
    success: true,
    message: 'Account deleted successfully'
  });
}));

module.exports = router;

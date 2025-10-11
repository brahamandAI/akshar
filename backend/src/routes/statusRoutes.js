const express = require('express');
const router = express.Router();
const Status = require('../models/Status');
const User = require('../models/User');
const { authMiddleware } = require('../middleware/authMiddleware');
const multer = require('multer');
const cloudinary = require('cloudinary').v2;
const path = require('path');
const { sendStatusNotification, sendStatusViewNotification } = require('../services/pushNotificationService');

// Configure multer for file uploads
const storage = multer.memoryStorage();
const upload = multer({
  storage: storage,
  limits: {
    fileSize: 10 * 1024 * 1024 // 10MB limit
  }
});

// Configure Cloudinary
cloudinary.config({
  cloud_name: process.env.CLOUDINARY_CLOUD_NAME,
  api_key: process.env.CLOUDINARY_API_KEY,
  api_secret: process.env.CLOUDINARY_API_SECRET
});

// Helper function to upload file to Cloudinary
const uploadToCloudinary = async (file, folder) => {
  return new Promise((resolve, reject) => {
    const uploadStream = cloudinary.uploader.upload_stream(
      {
        folder: folder,
        resource_type: 'auto'
      },
      (error, result) => {
        if (error) reject(error);
        else resolve(result);
      }
    );
    uploadStream.end(file.buffer);
  });
};

// @route   POST /api/status
// @desc    Create a new status
// @access  Private
router.post('/', authMiddleware, async (req, res) => {
  try {
    const { type, content, backgroundColor, fontFamily, songTitle, artist, duration, template, audioDuration } = req.body;
    
    const statusData = {
      userId: req.user.id,
      type,
      content
    };

    // Add type-specific data
    if (type === 'TEXT') {
      statusData.backgroundColor = backgroundColor || '#2E7D32';
      statusData.fontFamily = fontFamily || 'default';
    } else if (type === 'MUSIC') {
      statusData.songTitle = songTitle || '';
      statusData.artist = artist || '';
      statusData.duration = duration || '';
    } else if (type === 'LAYOUT') {
      statusData.template = template || {};
    } else if (type === 'VOICE') {
      statusData.audioDuration = audioDuration || 0;
    }

    const status = new Status(statusData);
    await status.save();

    // Populate user data
    await status.populate('userId', 'name email profilePicture');

    // Send push notifications to contacts (skip for now if contacts not set up)
    try {
      // TODO: Implement contacts system and notifications later
      // For now, status is created successfully without notifications
      console.log('Status created successfully - notifications will be added later');
    } catch (notificationError) {
      console.error('Error sending status notifications:', notificationError);
      // Don't fail the request if notifications fail
    }

    res.json({
      success: true,
      message: 'Status created successfully',
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
        viewCount: status.viewCount,
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
  } catch (error) {
    console.error('Error creating status:', error);
    res.status(500).json({
      success: false,
      message: 'Server error',
      error: error.message
    });
  }
});

// @route   POST /api/status/upload-audio
// @desc    Upload audio file for voice status
// @access  Private
router.post('/upload-audio', authMiddleware, upload.single('audio'), async (req, res) => {
  try {
    if (!req.file) {
      return res.status(400).json({
        success: false,
        message: 'No audio file provided'
      });
    }

    const result = await uploadToCloudinary(req.file, 'status-audio');
    
    res.json({
      success: true,
      message: 'Audio uploaded successfully',
      audioPath: result.secure_url,
      publicId: result.public_id
    });
  } catch (error) {
    console.error('Error uploading audio:', error);
    res.status(500).json({
      success: false,
      message: 'Error uploading audio',
      error: error.message
    });
  }
});

// @route   POST /api/status/upload-image
// @desc    Upload image file for image status
// @access  Private
router.post('/upload-image', authMiddleware, upload.single('image'), async (req, res) => {
  try {
    if (!req.file) {
      return res.status(400).json({
        success: false,
        message: 'No image file provided'
      });
    }

    const result = await uploadToCloudinary(req.file, 'status-images');
    
    res.json({
      success: true,
      message: 'Image uploaded successfully',
      imagePath: result.secure_url,
      publicId: result.public_id
    });
  } catch (error) {
    console.error('Error uploading image:', error);
    res.status(500).json({
      success: false,
      message: 'Error uploading image',
      error: error.message
    });
  }
});

// @route   GET /api/status
// @desc    Get all statuses for the user and their contacts
// @access  Private
router.get('/', authMiddleware, async (req, res) => {
  try {
    const { page = 1, limit = 20 } = req.query;
    const skip = (page - 1) * limit;

    // For now, get all active statuses (can filter by contacts later)
    // Get active statuses
    const statuses = await Status.find({
      isActive: true,
      expiresAt: { $gt: new Date() }
    })
    .populate('userId', 'name email profilePicture')
    .sort({ createdAt: -1 })
    .skip(skip)
    .limit(parseInt(limit));

    // Add view information for each status
    const statusesWithViews = statuses.map(status => ({
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
      viewCount: status.viewCount,
      hasUserViewed: status.hasUserViewed(req.user.id),
      createdAt: status.createdAt,
      expiresAt: status.expiresAt,
      user: {
        id: status.userId._id,
        name: status.userId.name,
        email: status.userId.email,
        profilePicture: status.userId.profilePicture
      }
    }));

    res.json({
      success: true,
      message: 'Statuses retrieved successfully',
      statuses: statusesWithViews,
      pagination: {
        page: parseInt(page),
        limit: parseInt(limit),
        total: statusesWithViews.length
      }
    });
  } catch (error) {
    console.error('Error fetching statuses:', error);
    res.status(500).json({
      success: false,
      message: 'Server error',
      error: error.message
    });
  }
});

// @route   GET /api/status/user/:userId
// @desc    Get statuses for a specific user
// @access  Private
router.get('/user/:userId', authMiddleware, async (req, res) => {
  try {
    const { userId } = req.params;
    const { page = 1, limit = 20 } = req.query;
    const skip = (page - 1) * limit;

    // Check if user is in contacts or is the same user
    const currentUser = await User.findById(req.user.id).populate('contacts');
    const isContact = currentUser.contacts.some(contact => contact._id.toString() === userId);
    const isOwnProfile = req.user.id === userId;

    if (!isContact && !isOwnProfile) {
      return res.status(403).json({
        success: false,
        message: 'Access denied. User not in contacts.'
      });
    }

    const statuses = await Status.find({
      userId: userId,
      isActive: true,
      expiresAt: { $gt: new Date() }
    })
    .populate('userId', 'name email profilePicture')
    .sort({ createdAt: -1 })
    .skip(skip)
    .limit(parseInt(limit));

    const statusesWithViews = statuses.map(status => ({
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
      viewCount: status.viewCount,
      hasUserViewed: status.hasUserViewed(req.user.id),
      createdAt: status.createdAt,
      expiresAt: status.expiresAt,
      user: {
        id: status.userId._id,
        name: status.userId.name,
        email: status.userId.email,
        profilePicture: status.userId.profilePicture
      }
    }));

    res.json({
      success: true,
      message: 'User statuses retrieved successfully',
      statuses: statusesWithViews,
      pagination: {
        page: parseInt(page),
        limit: parseInt(limit),
        total: statusesWithViews.length
      }
    });
  } catch (error) {
    console.error('Error fetching user statuses:', error);
    res.status(500).json({
      success: false,
      message: 'Server error',
      error: error.message
    });
  }
});

// @route   POST /api/status/:statusId/view
// @desc    Mark a status as viewed
// @access  Private
router.post('/:statusId/view', authMiddleware, async (req, res) => {
  try {
    const { statusId } = req.params;

    const status = await Status.findById(statusId);
    if (!status) {
      return res.status(404).json({
        success: false,
        message: 'Status not found'
      });
    }

    // Check if user can view this status
    const currentUser = await User.findById(req.user.id).populate('contacts');
    const isContact = currentUser.contacts.some(contact => contact._id.toString() === status.userId.toString());
    const isOwnStatus = req.user.id === status.userId.toString();

    if (!isContact && !isOwnStatus) {
      return res.status(403).json({
        success: false,
        message: 'Access denied. User not in contacts.'
      });
    }

    await status.addView(req.user.id);

    // Send push notification to status owner if it's not their own status
    if (status.userId.toString() !== req.user.id) {
      try {
        const statusOwner = await User.findById(status.userId);
        if (statusOwner && statusOwner.fcmToken) {
          await sendStatusViewNotification(statusOwner, req.user, statusId);
        }
      } catch (notificationError) {
        console.error('Error sending status view notification:', notificationError);
        // Don't fail the request if notifications fail
      }
    }

    res.json({
      success: true,
      message: 'Status marked as viewed',
      viewCount: status.viewCount
    });
  } catch (error) {
    console.error('Error marking status as viewed:', error);
    res.status(500).json({
      success: false,
      message: 'Server error',
      error: error.message
    });
  }
});

// @route   DELETE /api/status/:statusId
// @desc    Delete a status
// @access  Private
router.delete('/:statusId', authMiddleware, async (req, res) => {
  try {
    const { statusId } = req.params;

    const status = await Status.findOne({
      _id: statusId,
      userId: req.user.id
    });

    if (!status) {
      return res.status(404).json({
        success: false,
        message: 'Status not found or access denied'
      });
    }

    // Delete associated files from Cloudinary if they exist
    if (status.audioPath) {
      try {
        const publicId = status.audioPath.split('/').pop().split('.')[0];
        await cloudinary.uploader.destroy(`status-audio/${publicId}`);
      } catch (error) {
        console.error('Error deleting audio from Cloudinary:', error);
      }
    }

    if (status.imagePath) {
      try {
        const publicId = status.imagePath.split('/').pop().split('.')[0];
        await cloudinary.uploader.destroy(`status-images/${publicId}`);
      } catch (error) {
        console.error('Error deleting image from Cloudinary:', error);
      }
    }

    await Status.findByIdAndDelete(statusId);

    res.json({
      success: true,
      message: 'Status deleted successfully'
    });
  } catch (error) {
    console.error('Error deleting status:', error);
    res.status(500).json({
      success: false,
      message: 'Server error',
      error: error.message
    });
  }
});

// @route   GET /api/status/stats
// @desc    Get status statistics for the user
// @access  Private
router.get('/stats', authMiddleware, async (req, res) => {
  try {
    const totalStatuses = await Status.countDocuments({
      userId: req.user.id,
      isActive: true
    });

    const totalViews = await Status.aggregate([
      { $match: { userId: req.user.id, isActive: true } },
      { $project: { viewCount: { $size: '$views' } } },
      { $group: { _id: null, totalViews: { $sum: '$viewCount' } } }
    ]);

    res.json({
      success: true,
      message: 'Status statistics retrieved successfully',
      stats: {
        totalStatuses,
        totalViews: totalViews[0]?.totalViews || 0
      }
    });
  } catch (error) {
    console.error('Error fetching status stats:', error);
    res.status(500).json({
      success: false,
      message: 'Server error',
      error: error.message
    });
  }
});

module.exports = router;

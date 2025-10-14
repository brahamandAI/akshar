const express = require('express');
const router = express.Router();
const { authMiddleware, asyncHandler, AppError } = require('../middleware');
const Community = require('../models/Community');
const Chat = require('../models/Chat');

/**
 * @route   POST /api/communities
 * @desc    Create community
 * @access  Private
 */
router.post('/', authMiddleware, asyncHandler(async (req, res) => {
    const { name, description, isPrivate } = req.body;
    
    if (!name) {
        throw new AppError('Community name is required', 400);
    }
    
    const community = await Community.create({
        name,
        description,
        creator: req.user._id,
        admins: [req.user._id],
        members: [req.user._id],
        isPrivate: isPrivate || false
    });
    
    res.status(201).json({
        success: true,
        community
    });
}));

/**
 * @route   GET /api/communities
 * @desc    Get user's communities
 * @access  Private
 */
router.get('/', authMiddleware, asyncHandler(async (req, res) => {
    const communities = await Community.find({
        members: req.user._id,
        isActive: true
    })
    .populate('creator', 'firstName lastName avatar')
    .populate('admins', 'firstName lastName avatar')
    .sort({ createdAt: -1 });
    
    res.json({
        success: true,
        communities
    });
}));

/**
 * @route   GET /api/communities/:communityId
 * @desc    Get community details
 * @access  Private
 */
router.get('/:communityId', authMiddleware, asyncHandler(async (req, res) => {
    const community = await Community.findById(req.params.communityId)
        .populate('creator', 'firstName lastName avatar')
        .populate('admins', 'firstName lastName avatar')
        .populate('members', 'firstName lastName avatar')
        .populate('groups');
    
    if (!community) {
        throw new AppError('Community not found', 404);
    }
    
    if (!community.isMember(req.user._id) && community.isPrivate) {
        throw new AppError('Not authorized to view this community', 403);
    }
    
    res.json({
        success: true,
        community
    });
}));

/**
 * @route   POST /api/communities/:communityId/groups
 * @desc    Create group in community
 * @access  Private
 */
router.post('/:communityId/groups', authMiddleware, asyncHandler(async (req, res) => {
    const { groupName, groupDescription } = req.body;
    
    const community = await Community.findById(req.params.communityId);
    
    if (!community) {
        throw new AppError('Community not found', 404);
    }
    
    if (!community.isAdmin(req.user._id)) {
        throw new AppError('Only admins can create groups', 403);
    }
    
    // Create group chat
    const group = await Chat.create({
        groupName,
        groupDescription,
        isGroup: true,
        createdBy: req.user._id,
        participants: [req.user._id],
        admins: [req.user._id],
        communityId: community._id
    });
    
    // Add group to community
    community.groups.push(group._id);
    await community.save();
    
    res.status(201).json({
        success: true,
        group
    });
}));

/**
 * @route   POST /api/communities/:communityId/join
 * @desc    Join community
 * @access  Private
 */
router.post('/:communityId/join', authMiddleware, asyncHandler(async (req, res) => {
    const community = await Community.findById(req.params.communityId);
    
    if (!community) {
        throw new AppError('Community not found', 404);
    }
    
    if (community.isMember(req.user._id)) {
        throw new AppError('Already a member', 400);
    }
    
    if (community.settings.requireApprovalToJoin) {
        // Add to pending members (implement approval system)
        throw new AppError('This community requires admin approval', 403);
    }
    
    community.members.push(req.user._id);
    await community.save();
    
    res.json({
        success: true,
        message: 'Joined community successfully'
    });
}));

/**
 * @route   POST /api/communities/:communityId/leave
 * @desc    Leave community
 * @access  Private
 */
router.post('/:communityId/leave', authMiddleware, asyncHandler(async (req, res) => {
    const community = await Community.findById(req.params.communityId);
    
    if (!community) {
        throw new AppError('Community not found', 404);
    }
    
    if (community.creator.toString() === req.user._id.toString()) {
        throw new AppError('Creator cannot leave community. Transfer ownership or delete community.', 400);
    }
    
    community.members = community.members.filter(
        m => m.toString() !== req.user._id.toString()
    );
    community.admins = community.admins.filter(
        a => a.toString() !== req.user._id.toString()
    );
    
    await community.save();
    
    res.json({
        success: true,
        message: 'Left community successfully'
    });
}));

/**
 * @route   DELETE /api/communities/:communityId
 * @desc    Delete community
 * @access  Private
 */
router.delete('/:communityId', authMiddleware, asyncHandler(async (req, res) => {
    const community = await Community.findById(req.params.communityId);
    
    if (!community) {
        throw new AppError('Community not found', 404);
    }
    
    if (community.creator.toString() !== req.user._id.toString()) {
        throw new AppError('Only creator can delete community', 403);
    }
    
    community.isActive = false;
    await community.save();
    
    res.json({
        success: true,
        message: 'Community deleted'
    });
}));

module.exports = router;


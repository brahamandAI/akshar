const express = require('express');
const router = express.Router();
const { authMiddleware, asyncHandler, AppError } = require('../middleware');
const Broadcast = require('../models/Broadcast');
const Message = require('../models/Message');

/**
 * @route   POST /api/broadcasts
 * @desc    Create broadcast list
 * @access  Private
 */
router.post('/', authMiddleware, asyncHandler(async (req, res) => {
    const { name, recipients, description } = req.body;
    
    if (!name || !recipients || recipients.length === 0) {
        throw new AppError('Name and at least one recipient required', 400);
    }
    
    const broadcast = await Broadcast.create({
        name,
        creator: req.user._id,
        recipients,
        description
    });
    
    res.status(201).json({
        success: true,
        broadcast
    });
}));

/**
 * @route   GET /api/broadcasts
 * @desc    Get user's broadcast lists
 * @access  Private
 */
router.get('/', authMiddleware, asyncHandler(async (req, res) => {
    const broadcasts = await Broadcast.find({
        creator: req.user._id,
        isActive: true
    })
    .populate('recipients', 'firstName lastName avatar')
    .populate('lastMessage')
    .sort({ lastMessageAt: -1 });
    
    res.json({
        success: true,
        broadcasts
    });
}));

/**
 * @route   POST /api/broadcasts/:broadcastId/send
 * @desc    Send message to broadcast list
 * @access  Private
 */
router.post('/:broadcastId/send', authMiddleware, asyncHandler(async (req, res) => {
    const { content, type = 'text', mediaUrl } = req.body;
    
    const broadcast = await Broadcast.findById(req.params.broadcastId);
    
    if (!broadcast) {
        throw new AppError('Broadcast not found', 404);
    }
    
    if (broadcast.creator.toString() !== req.user._id.toString()) {
        throw new AppError('Only creator can send broadcast messages', 403);
    }
    
    // Create individual messages for each recipient
    const messages = await Promise.all(
        broadcast.recipients.map(recipientId =>
            Message.create({
                chat: null, // Broadcast messages don't belong to a chat
                sender: req.user._id,
                content,
                type,
                mediaUrl,
                isBroadcast: true,
                broadcastId: broadcast._id
            })
        )
    );
    
    // Update broadcast
    broadcast.lastMessage = messages[0]._id;
    broadcast.lastMessageAt = new Date();
    broadcast.messageCount += 1;
    await broadcast.save();
    
    res.json({
        success: true,
        message: 'Broadcast sent successfully',
        recipientCount: messages.length
    });
}));

/**
 * @route   DELETE /api/broadcasts/:broadcastId
 * @desc    Delete broadcast list
 * @access  Private
 */
router.delete('/:broadcastId', authMiddleware, asyncHandler(async (req, res) => {
    const broadcast = await Broadcast.findById(req.params.broadcastId);
    
    if (!broadcast) {
        throw new AppError('Broadcast not found', 404);
    }
    
    if (broadcast.creator.toString() !== req.user._id.toString()) {
        throw new AppError('Only creator can delete broadcast', 403);
    }
    
    broadcast.isActive = false;
    await broadcast.save();
    
    res.json({
        success: true,
        message: 'Broadcast deleted'
    });
}));

module.exports = router;


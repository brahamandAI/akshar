const express = require('express');
const router = express.Router();
const { authMiddleware } = require('../middleware/authMiddleware');
const Call = require('../models/Call');

/**
 * GET /api/calls
 * Get call history for authenticated user
 */
router.get('/', authMiddleware, async (req, res) => {
    try {
        const { page = 1, limit = 50 } = req.query;
        
        const calls = await Call.getCallHistory(
            req.user._id,
            parseInt(page),
            parseInt(limit)
        );
        
        res.json({
            success: true,
            calls
        });
    } catch (error) {
        console.error('Get call history error:', error);
        res.status(500).json({
            success: false,
            message: 'Failed to fetch call history'
        });
    }
});

/**
 * GET /api/calls/:callId
 * Get specific call details
 */
router.get('/:callId', authMiddleware, async (req, res) => {
    try {
        const call = await Call.findById(req.params.callId)
            .populate('caller', 'firstName lastName profilePicture')
            .populate('receiver', 'firstName lastName profilePicture');
        
        if (!call) {
            return res.status(404).json({
                success: false,
                message: 'Call not found'
            });
        }
        
        // Check if user is part of the call
        if (call.caller._id.toString() !== req.user._id.toString() &&
            call.receiver._id.toString() !== req.user._id.toString()) {
            return res.status(403).json({
                success: false,
                message: 'Not authorized to view this call'
            });
        }
        
        res.json({
            success: true,
            call
        });
    } catch (error) {
        console.error('Get call details error:', error);
        res.status(500).json({
            success: false,
            message: 'Failed to fetch call details'
        });
    }
});

/**
 * DELETE /api/calls/:callId
 * Delete call from history
 */
router.delete('/:callId', authMiddleware, async (req, res) => {
    try {
        const call = await Call.findById(req.params.callId);
        
        if (!call) {
            return res.status(404).json({
                success: false,
                message: 'Call not found'
            });
        }
        
        // Check if user is part of the call
        if (call.caller.toString() !== req.user._id.toString() &&
            call.receiver.toString() !== req.user._id.toString()) {
            return res.status(403).json({
                success: false,
                message: 'Not authorized to delete this call'
            });
        }
        
        await Call.findByIdAndDelete(req.params.callId);
        
        res.json({
            success: true,
            message: 'Call deleted from history'
        });
    } catch (error) {
        console.error('Delete call error:', error);
        res.status(500).json({
            success: false,
            message: 'Failed to delete call'
        });
    }
});

module.exports = router;


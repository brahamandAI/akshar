/**
 * Status Cleanup Job
 * Automatically delete statuses older than 24 hours
 */

const cron = require('node-cron');
const Status = require('../models/Status');
const cloudinaryService = require('../services/cloudinaryService');

/**
 * Delete expired statuses (older than 24 hours)
 */
async function deleteExpiredStatuses() {
    try {
        console.log('[StatusCleanup] Running status cleanup job...');
        
        const twentyFourHoursAgo = new Date(Date.now() - 24 * 60 * 60 * 1000);
        
        // Find expired statuses
        const expiredStatuses = await Status.find({
            createdAt: { $lt: twentyFourHoursAgo },
            isActive: true
        });
        
        console.log(`[StatusCleanup] Found ${expiredStatuses.length} expired statuses`);
        
        for (const status of expiredStatuses) {
            try {
                // Delete media from Cloudinary if exists
                if (status.mediaUrl) {
                    // Extract public_id from URL
                    const publicIdMatch = status.mediaUrl.match(/\/([^\/]+)\.[^.]+$/);
                    if (publicIdMatch) {
                        const publicId = publicIdMatch[1];
                        await cloudinaryService.deleteFile(publicId);
                        console.log(`[StatusCleanup] Deleted media: ${publicId}`);
                    }
                }
                
                // Soft delete status
                status.isActive = false;
                status.expiredAt = new Date();
                await status.save();
                
            } catch (error) {
                console.error(`[StatusCleanup] Error deleting status ${status._id}:`, error);
            }
        }
        
        console.log(`[StatusCleanup] Cleanup complete. Deleted ${expiredStatuses.length} statuses`);
        
    } catch (error) {
        console.error('[StatusCleanup] Job error:', error);
    }
}

/**
 * Initialize status cleanup cron job
 * Runs every hour
 */
function initializeStatusCleanup() {
    // Run every hour
    cron.schedule('0 * * * *', async () => {
        console.log('[StatusCleanup] Starting scheduled cleanup...');
        await deleteExpiredStatuses();
    });
    
    console.log('✅ Status cleanup cron job initialized (runs every hour)');
    
    // Run immediately on startup
    setTimeout(() => {
        deleteExpiredStatuses();
    }, 5000); // 5 second delay after server start
}

module.exports = {
    initializeStatusCleanup,
    deleteExpiredStatuses
};


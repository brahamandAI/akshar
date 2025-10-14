const mongoose = require('mongoose');

const linkedDeviceSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true,
        index: true
    },
    deviceId: {
        type: String,
        required: true,
        unique: true
    },
    deviceName: {
        type: String,
        required: true
    },
    deviceType: {
        type: String,
        enum: ['android', 'ios', 'web', 'windows', 'mac', 'linux'],
        required: true
    },
    platform: {
        type: String
    },
    browserInfo: {
        type: String
    },
    ipAddress: {
        type: String
    },
    linkToken: {
        type: String,
        required: true,
        select: false
    },
    refreshToken: {
        type: String,
        required: true,
        select: false
    },
    lastActive: {
        type: Date,
        default: Date.now,
        index: true
    },
    isActive: {
        type: Boolean,
        default: true
    },
    linkedAt: {
        type: Date,
        default: Date.now
    }
}, {
    timestamps: true
});

// Index for device queries
linkedDeviceSchema.index({ user: 1, isActive: 1, lastActive: -1 });

// Method to update last active
linkedDeviceSchema.methods.updateLastActive = function() {
    this.lastActive = new Date();
    return this.save();
};

// Static method to get user's linked devices
linkedDeviceSchema.statics.getUserDevices = async function(userId) {
    return this.find({
        user: userId,
        isActive: true
    }).sort({ lastActive: -1 });
};

// Static method to verify link token
linkedDeviceSchema.statics.verifyLinkToken = async function(deviceId, linkToken) {
    const device = await this.findOne({ deviceId }).select('+linkToken');
    if (!device) return null;
    
    // Check if token matches
    if (device.linkToken === linkToken) {
        return device;
    }
    return null;
};

const LinkedDevice = mongoose.model('LinkedDevice', linkedDeviceSchema);

module.exports = LinkedDevice;


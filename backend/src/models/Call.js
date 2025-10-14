const mongoose = require('mongoose');

const callSchema = new mongoose.Schema({
    caller: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true,
        index: true
    },
    receiver: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true,
        index: true
    },
    callType: {
        type: String,
        enum: ['audio', 'video'],
        required: true
    },
    status: {
        type: String,
        enum: ['ringing', 'active', 'completed', 'rejected', 'missed', 'failed'],
        default: 'ringing',
        index: true
    },
    duration: {
        type: Number, // Duration in seconds
        default: 0
    },
    startedAt: {
        type: Date,
        default: Date.now,
        index: true
    },
    answeredAt: {
        type: Date
    },
    endedAt: {
        type: Date
    }
}, {
    timestamps: true
});

// Index for call history queries
callSchema.index({ caller: 1, createdAt: -1 });
callSchema.index({ receiver: 1, createdAt: -1 });

// Get formatted duration
callSchema.methods.getFormattedDuration = function() {
    if (this.duration < 60) {
        return `${this.duration}s`;
    }
    const minutes = Math.floor(this.duration / 60);
    const seconds = this.duration % 60;
    return `${minutes}:${seconds.toString().padStart(2, '0')}`;
};

// Get call history for a user
callSchema.statics.getCallHistory = async function(userId, page = 1, limit = 50) {
    const skip = (page - 1) * limit;
    
    const calls = await this.find({
        $or: [
            { caller: userId },
            { receiver: userId }
        ]
    })
    .populate('caller', 'firstName lastName profilePicture')
    .populate('receiver', 'firstName lastName profilePicture')
    .sort({ startedAt: -1 })
    .skip(skip)
    .limit(limit);
    
    return calls;
};

const Call = mongoose.model('Call', callSchema);

module.exports = Call;


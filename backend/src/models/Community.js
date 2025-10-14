const mongoose = require('mongoose');

const communitySchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        trim: true,
        maxlength: [50, 'Community name cannot exceed 50 characters']
    },
    description: {
        type: String,
        maxlength: [500, 'Description cannot exceed 500 characters']
    },
    avatar: {
        type: String
    },
    creator: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true,
        index: true
    },
    admins: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    }],
    members: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    }],
    groups: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Chat'
    }],
    announcements: [{
        message: String,
        createdBy: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'User'
        },
        createdAt: {
            type: Date,
            default: Date.now
        }
    }],
    settings: {
        allowMemberInvites: {
            type: Boolean,
            default: true
        },
        onlyAdminsCanPost: {
            type: Boolean,
            default: false
        },
        requireApprovalToJoin: {
            type: Boolean,
            default: false
        }
    },
    isPrivate: {
        type: Boolean,
        default: false
    },
    isActive: {
        type: Boolean,
        default: true
    }
}, {
    timestamps: true
});

communitySchema.index({ creator: 1, isActive: 1 });
communitySchema.index({ members: 1, isActive: 1 });

// Method to check if user is admin
communitySchema.methods.isAdmin = function(userId) {
    return this.admins.some(admin => admin.toString() === userId.toString()) ||
           this.creator.toString() === userId.toString();
};

// Method to check if user is member
communitySchema.methods.isMember = function(userId) {
    return this.members.some(member => member.toString() === userId.toString());
};

const Community = mongoose.model('Community', communitySchema);

module.exports = Community;


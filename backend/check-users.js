const mongoose = require('mongoose');

// User Schema (same as backend)
const userSchema = new mongoose.Schema({
    username: { type: String, required: true, unique: true, trim: true },
    email: { type: String, required: true, unique: true, lowercase: true },
    password: { type: String, required: true },
    firstName: { type: String, required: true, trim: true },
    lastName: { type: String, required: true, trim: true },
    phoneNumber: { type: String, unique: true, sparse: true },
    avatar: { type: String },
    status: { type: String, default: "Hey there! I'm using Akshar Messaging" },
    lastSeen: { type: Date, default: Date.now },
    isActive: { type: Boolean, default: true },
    isDeleted: { type: Boolean, default: false },
    bio: { type: String, maxlength: 150 },
    dateOfBirth: { type: Date }
}, {
    timestamps: true
});

const User = mongoose.model('User', userSchema);

async function checkUsers() {
    try {
        // Connect to MongoDB (same as backend)
        await mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/akshar_messaging');
        console.log('Connected to MongoDB');

        // Get all users
        const users = await User.find({}).select('username firstName lastName phoneNumber _id');
        
        console.log(`\n📊 Total users in database: ${users.length}`);
        
        if (users.length === 0) {
            console.log('❌ No users found in database');
        } else {
            console.log('\n👥 Users in database:');
            users.forEach((user, i) => {
                console.log(`${i + 1}. ${user.username} (${user.firstName} ${user.lastName}) - ${user.phoneNumber} - ID: ${user._id}`);
            });
        }

    } catch (error) {
        console.error('❌ Error checking users:', error);
    } finally {
        await mongoose.disconnect();
        console.log('\nDisconnected from MongoDB');
    }
}

// Run the script
checkUsers();
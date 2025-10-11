const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

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

// Test users data
const testUsers = [
    {
        username: 'gurav',
        email: 'gurav@test.com',
        password: 'password123',
        firstName: 'Gurav',
        lastName: 'Sharma',
        phoneNumber: '9812237388',
        status: 'Online now',
        bio: 'Test user for messaging app'
    },
    {
        username: 'alice',
        email: 'alice@test.com',
        password: 'password123',
        firstName: 'Alice',
        lastName: 'Johnson',
        phoneNumber: '9876543210',
        status: 'Busy',
        bio: 'Another test user'
    },
    {
        username: 'bob',
        email: 'bob@test.com',
        password: 'password123',
        firstName: 'Bob',
        lastName: 'Smith',
        phoneNumber: '9123456789',
        status: 'Available',
        bio: 'Third test user'
    },
    {
        username: 'charlie',
        email: 'charlie@test.com',
        password: 'password123',
        firstName: 'Charlie',
        lastName: 'Brown',
        phoneNumber: '9234567890',
        status: 'Away',
        bio: 'Fourth test user'
    }
];

async function createTestUsers() {
    try {
        // Connect to MongoDB Atlas (same as backend)
        await mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/akshar_messaging');
        console.log('Connected to MongoDB');

        // Clear existing test users (optional)
        await User.deleteMany({
            email: { $in: testUsers.map(u => u.email) }
        });
        console.log('Cleared existing test users');

        // Create test users
        for (const userData of testUsers) {
            // Hash password
            const hashedPassword = await bcrypt.hash(userData.password, 12);
            
            // Create user
            const user = new User({
                ...userData,
                password: hashedPassword
            });
            
            await user.save();
            console.log(`✅ Created user: ${userData.username} (${userData.phoneNumber}) - ID: ${user._id}`);
        }

        console.log('\n🎉 All test users created successfully!');
        console.log('\n📱 Now you can:');
        console.log('1. Save contacts with these phone numbers in your app');
        console.log('2. Click message icon to start chats');
        console.log('3. Test real-time messaging between dummy users');
        
        // Display user IDs for reference
        console.log('\n👥 User IDs for reference:');
        const users = await User.find({ username: { $in: testUsers.map(u => u.username) } });
        users.forEach(user => {
            console.log(`${user.username}: ${user._id}`);
        });

    } catch (error) {
        console.error('❌ Error creating test users:', error);
    } finally {
        await mongoose.disconnect();
        console.log('\nDisconnected from MongoDB');
    }
}

// Run the script
createTestUsers();

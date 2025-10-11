const mongoose = require('mongoose');
const path = require('path');

// Load environment variables from .env file
require('dotenv').config({ path: path.join(__dirname, '.env') });

async function fixDatabase() {
  try {
    const mongoUri = process.env.MONGO_URI || process.env.MONGODB_URI;
    
    if (!mongoUri) {
      console.log('❌ MongoDB URI not found in .env file');
      console.log('\n--- Quick Fix: Run these commands in MongoDB Atlas ---');
      console.log('1. Go to MongoDB Atlas → Browse Collections');
      console.log('2. Select database: akshar_messaging');
      console.log('3. Select collection: users');
      console.log('4. Click on "Indexes" tab');
      console.log('5. Delete the "phoneNumber_1" index');
      console.log('6. Click on "Documents" tab');
      console.log('7. Delete all documents (optional)');
      console.log('8. Restart your backend server');
      console.log('--- End ---\n');
      return;
    }
    
    // Connect to MongoDB
    console.log('Connecting to MongoDB...');
    await mongoose.connect(mongoUri);
    console.log('✓ Connected to MongoDB');
    
    const db = mongoose.connection.db;
    const usersCollection = db.collection('users');
    
    // Drop the problematic index
    try {
      await usersCollection.dropIndex('phoneNumber_1');
      console.log('✓ Dropped phoneNumber_1 index');
    } catch (error) {
      console.log('⚠ Index already dropped or does not exist');
    }
    
    // Delete all users (removes test data)
    const result = await usersCollection.deleteMany({});
    console.log(`✓ Deleted ${result.deletedCount} users`);
    
    // Recreate the index with sparse option
    await usersCollection.createIndex(
      { phoneNumber: 1 }, 
      { unique: true, sparse: true }
    );
    console.log('✓ Recreated phoneNumber index with sparse option');
    
    console.log('\n✅ Database fixed successfully!');
    console.log('You can now restart your backend server and register new users.');
    
    await mongoose.disconnect();
    process.exit(0);
  } catch (error) {
    console.error('❌ Error:', error.message);
    process.exit(1);
  }
}

fixDatabase();

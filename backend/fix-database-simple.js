const mongoose = require('mongoose');

// Replace with your actual MongoDB connection string
const MONGO_URI = 'mongodb+srv://your-connection-string';

// OR if you know the connection string, paste it here

async function fixDatabase() {
  try {
    console.log('Enter your MongoDB connection string:');
    console.log('You can find it in your .env file as MONGO_URI');
    console.log('\nAlternatively, run this command in MongoDB shell:');
    console.log('\n--- MongoDB Shell Commands ---');
    console.log('use akshar_messaging');
    console.log('db.users.dropIndex("phoneNumber_1")');
    console.log('db.users.deleteMany({})');
    console.log('db.users.createIndex({ phoneNumber: 1 }, { unique: true, sparse: true })');
    console.log('--- End ---\n');
    
    console.log('✅ Copy these commands and run in MongoDB Atlas web interface or MongoDB Compass');
    
  } catch (error) {
    console.error('❌ Error:', error.message);
  }
}

fixDatabase();


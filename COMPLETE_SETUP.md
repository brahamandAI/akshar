# 🚀 Akshar Messaging - Complete Setup Guide

## 📋 **Prerequisites**
- Node.js 18+ installed
- Git installed
- Android Studio (for Android app)
- Xcode (for iOS app)

## 🔧 **Step 1: Run Quick Setup**
```bash
# Run the setup script
quick-setup.bat
```

## 🗄️ **Step 2: Setup MongoDB**
1. Follow: `MONGODB_SETUP.md`
2. Get connection string
3. Update `backend/.env`:
```env
MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/akshar_messaging
```

## ☁️ **Step 3: Setup Cloudinary**
1. Follow: `CLOUDINARY_SETUP.md`
2. Get credentials from dashboard
3. Update `backend/.env`:
```env
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
```

## 🔥 **Step 4: Setup Firebase**
1. Follow: `FIREBASE_SETUP.md`
2. Create project and apps
3. Generate service account key
4. Update `backend/.env`:
```env
FIREBASE_PROJECT_ID=your-project-id
FIREBASE_PRIVATE_KEY="-----BEGIN PRIVATE KEY-----\n..."
FIREBASE_CLIENT_EMAIL=firebase-adminsdk-xxx@project.iam.gserviceaccount.com
```

## 🔑 **Step 5: Setup JWT Secret**
```env
JWT_SECRET=your_super_secret_jwt_key_change_this_in_production_12345
```

## 🚀 **Step 6: Start Backend**
```bash
cd backend
npm run dev
```

## ✅ **Step 7: Test API**
- Open: http://localhost:3000/health
- Should show: `{"status":"OK","timestamp":"..."}`

## 📱 **Step 8: Setup Mobile Apps**

### Android:
1. Open `android/` folder in Android Studio
2. Sync project with Gradle files
3. Add `google-services.json` to `android/app/`
4. Build and run

### iOS:
1. Install CocoaPods: `sudo gem install cocoapods`
2. `cd ios && pod install`
3. Open `AksharMessaging.xcworkspace` in Xcode
4. Add `GoogleService-Info.plist` to project
5. Build and run

## 🧪 **Step 9: Test Features**
1. Register new user via API
2. Login and get JWT token
3. Create chat with another user
4. Send messages
5. Test file uploads
6. Test push notifications

## 📊 **API Endpoints to Test**
```
POST /api/auth/register
POST /api/auth/login
GET  /api/chats
POST /api/chats
POST /api/messages/:chatId
POST /api/messages/:chatId/media
```

## 🎉 **You're Ready!**
Your Akshar messaging app is now fully configured and ready to use!

## 🆘 **Troubleshooting**
- Check MongoDB connection
- Verify Cloudinary credentials
- Test Firebase setup
- Check JWT token generation
- Verify Socket.IO connection

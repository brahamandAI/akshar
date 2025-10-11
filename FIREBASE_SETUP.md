# Firebase Setup Guide

## 🔥 **Push Notifications Setup**

### Step 1: Create Firebase Project
1. Go to: https://console.firebase.google.com/
2. Click "Create a project"
3. Project name: "Akshar Messaging"
4. Enable Google Analytics (optional)
5. Click "Create project"

### Step 2: Add Android App
1. Click "Add app" → Android
2. Package name: `com.akshar.messaging`
3. App nickname: "Akshar Android"
4. Click "Register app"
5. Download `google-services.json`
6. Place in: `android/app/google-services.json`

### Step 3: Add iOS App
1. Click "Add app" → iOS
2. Bundle ID: `com.akshar.messaging`
3. App nickname: "Akshar iOS"
4. Click "Register app"
5. Download `GoogleService-Info.plist`
6. Add to Xcode project

### Step 4: Generate Service Account
1. Go to Project Settings → Service Accounts
2. Click "Generate new private key"
3. Download JSON file
4. Extract these values:

```json
{
  "project_id": "your-project-id",
  "private_key": "-----BEGIN PRIVATE KEY-----\n...",
  "client_email": "firebase-adminsdk-xxx@your-project.iam.gserviceaccount.com"
}
```

### Step 5: Update .env File
```env
FIREBASE_PROJECT_ID=your-project-id
FIREBASE_PRIVATE_KEY="-----BEGIN PRIVATE KEY-----\n..."
FIREBASE_CLIENT_EMAIL=firebase-adminsdk-xxx@your-project.iam.gserviceaccount.com
```

### Step 6: Enable Cloud Messaging
1. Go to "Cloud Messaging" in Firebase Console
2. Enable FCM for your project
3. Configure Android notification channels
4. Test notifications

## ✅ **Firebase Benefits:**
- Free push notifications
- Cross-platform support
- Real-time delivery
- Analytics integration
- Easy setup

## 📱 **Notification Types:**
- New messages
- Incoming calls
- Group updates
- Status changes

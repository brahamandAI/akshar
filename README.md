# Akshar - Modern Messaging App

A WhatsApp-like messaging application built with modern technologies for both Android and iOS platforms.

## 🚀 Features

- **Text Messaging**: Real-time text messaging with delivery and read receipts
- **Voice & Video Calling**: High-quality voice and video calls using WebRTC
- **File Sharing**: Share images, videos, documents, and other files
- **Group Chats**: Create and manage group conversations
- **End-to-End Encryption**: Secure messaging with encryption
- **Emojis & Stickers**: Rich messaging with emojis and stickers
- **Push Notifications**: Real-time notifications for new messages
- **Status Updates**: Share status updates with contacts
- **Contact Management**: Manage contacts and chat history

## 🏗️ Architecture

### Backend
- **Framework**: Node.js with Express.js
- **Database**: MongoDB with Mongoose ODM
- **Authentication**: JWT (JSON Web Tokens)
- **Real-time**: Socket.IO for real-time messaging
- **File Storage**: AWS S3 or Cloudinary for media files
- **Push Notifications**: Firebase Cloud Messaging

### Mobile Apps
- **Android**: Native Android with Kotlin
- **iOS**: Native iOS with SwiftUI
- **State Management**: MVVM architecture pattern
- **Networking**: Retrofit (Android) / URLSession (iOS)

## 📱 Platform Support

- **Android**: API level 21+ (Android 5.0+)
- **iOS**: iOS 14.0+

## 🛠️ Technology Stack

### Backend
```
Node.js + Express.js
MongoDB + Mongoose
Socket.IO
JWT Authentication
Multer (File Upload)
bcryptjs (Password Hashing)
```

### Android
```
Kotlin
Jetpack Compose
Retrofit + OkHttp
Socket.IO Client
Room Database
Material Design 3
```

### iOS
```
SwiftUI
Combine Framework
URLSession
Socket.IO Client
Core Data
Human Interface Guidelines
```

## 📁 Project Structure

```
Akshar_Messaging/
├── backend/                 # Node.js backend
│   ├── src/
│   │   ├── controllers/     # API controllers
│   │   ├── models/          # Mongoose models
│   │   ├── routes/          # API routes
│   │   ├── middleware/      # Custom middleware
│   │   ├── services/        # Business logic
│   │   ├── utils/           # Utility functions
│   │   └── socket/          # Socket.IO handlers
│   ├── uploads/             # File uploads
│   ├── package.json
│   └── server.js
├── android/                 # Android app
│   ├── app/
│   ├── build.gradle
│   └── settings.gradle
├── ios/                     # iOS app
│   ├── AksharMessaging/
│   ├── AksharMessaging.xcodeproj
│   └── Podfile
├── shared/                  # Shared components
│   ├── api/                 # API definitions
│   ├── models/              # Data models
│   └── utils/               # Shared utilities
└── docs/                    # Documentation
```

## 🚀 Getting Started

### Prerequisites
- Node.js 18+
- MongoDB 6+
- Android Studio (for Android development)
- Xcode 14+ (for iOS development)
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Akshar_Messaging
   ```

2. **Backend Setup**
   ```bash
   cd backend
   npm install
   cp .env.example .env
   # Configure your environment variables
   npm run dev
   ```

3. **Android Setup**
   ```bash
   cd android
   # Open in Android Studio and sync project
   ```

4. **iOS Setup**
   ```bash
   cd ios
   pod install
   # Open AksharMessaging.xcworkspace in Xcode
   ```

## 🔧 Configuration

### Environment Variables
Create a `.env` file in the backend directory:

```env
PORT=3000
MONGODB_URI=mongodb://localhost:27017/akshar_messaging
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRES_IN=24h
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
FIREBASE_SERVER_KEY=your_firebase_server_key
```

## 📝 API Documentation

### Authentication Endpoints
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh JWT token
- `POST /api/auth/logout` - User logout

### User Endpoints
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile
- `POST /api/users/upload-avatar` - Upload profile picture
- `GET /api/users/contacts` - Get user contacts

### Chat Endpoints
- `GET /api/chats` - Get user chats
- `POST /api/chats` - Create new chat
- `GET /api/chats/:chatId/messages` - Get chat messages
- `POST /api/chats/:chatId/messages` - Send message
- `PUT /api/chats/:chatId/messages/:messageId` - Edit message
- `DELETE /api/chats/:chatId/messages/:messageId` - Delete message

### Group Chat Endpoints
- `POST /api/chats/group` - Create group chat
- `PUT /api/chats/:chatId/group` - Update group info
- `POST /api/chats/:chatId/group/members` - Add group members
- `DELETE /api/chats/:chatId/group/members/:userId` - Remove group member

## 🔒 Security Features

- JWT-based authentication with 24-hour token expiration
- Password hashing with bcryptjs
- Input validation and sanitization
- Rate limiting for API endpoints
- CORS configuration
- File upload validation
- End-to-end encryption for messages

## 📊 Database Schema

### User Model
```javascript
{
  _id: ObjectId,
  username: String (unique),
  email: String (unique),
  password: String (hashed),
  avatar: String (URL),
  status: String,
  lastSeen: Date,
  createdAt: Date,
  updatedAt: Date
}
```

### Chat Model
```javascript
{
  _id: ObjectId,
  participants: [ObjectId], // User IDs
  isGroup: Boolean,
  groupName: String (if group),
  groupAvatar: String (URL),
  lastMessage: ObjectId, // Message ID
  lastMessageAt: Date,
  createdAt: Date,
  updatedAt: Date
}
```

### Message Model
```javascript
{
  _id: ObjectId,
  chatId: ObjectId,
  senderId: ObjectId,
  content: String,
  type: String (text, image, video, audio, document),
  mediaUrl: String (if media message),
  isEdited: Boolean,
  editedAt: Date,
  isDeleted: Boolean,
  deletedAt: Date,
  readBy: [ObjectId], // User IDs who read the message
  createdAt: Date,
  updatedAt: Date
}
```

## 🧪 Testing

### Backend Testing
```bash
cd backend
npm test
```

### Android Testing
```bash
cd android
./gradlew test
```

### iOS Testing
```bash
cd ios
xcodebuild test -workspace AksharMessaging.xcworkspace -scheme AksharMessaging
```

## 📦 Deployment

### Backend Deployment
- Deploy to AWS, Google Cloud, or Heroku
- Configure MongoDB Atlas for production database
- Set up CI/CD pipeline with GitHub Actions

### Mobile App Deployment
- Android: Google Play Store
- iOS: Apple App Store

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Team

- Backend Development: Node.js + MongoDB
- Android Development: Kotlin + Jetpack Compose
- iOS Development: SwiftUI + Combine

## 📞 Support

For support and questions, please open an issue in the repository or contact the development team.

---

**Akshar** - Connecting people through modern messaging technology.

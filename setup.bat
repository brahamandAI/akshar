@echo off
echo 🚀 Setting up Akshar Messaging App...
echo.

REM Check if Node.js is installed
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Node.js is not installed. Please install Node.js 18+ first.
    echo Download from: https://nodejs.org/
    pause
    exit /b 1
)

echo [SUCCESS] Node.js version:
node --version

REM Check if MongoDB is installed
mongod --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [WARNING] MongoDB is not installed. Please install MongoDB 6+ or use MongoDB Atlas.
    echo Download from: https://www.mongodb.com/try/download/community
    echo.
)

REM Setup Backend
echo [INFO] Setting up backend...
cd backend

REM Install dependencies
echo [INFO] Installing backend dependencies...
call npm install

if %errorlevel% neq 0 (
    echo [ERROR] Failed to install backend dependencies
    pause
    exit /b 1
)

echo [SUCCESS] Backend dependencies installed successfully

REM Create .env file if it doesn't exist
if not exist .env (
    echo [INFO] Creating .env file...
    copy env.example .env
    echo [WARNING] Please update the .env file with your configuration
    echo [WARNING] Required configurations:
    echo [WARNING]   - MONGODB_URI
    echo [WARNING]   - JWT_SECRET
    echo [WARNING]   - CLOUDINARY_* (for file uploads)
    echo [WARNING]   - FIREBASE_* (for push notifications)
) else (
    echo [SUCCESS] .env file already exists
)

cd ..

REM Check for Android development
where adb >nul 2>&1
if %errorlevel% equ 0 (
    echo [INFO] Android development environment detected
    echo [INFO] To build Android app:
    echo [INFO]   1. Open android/ folder in Android Studio
    echo [INFO]   2. Sync project with Gradle files
    echo [INFO]   3. Build and run the app
) else (
    echo [WARNING] Android development environment not detected
    echo [WARNING] To set up Android development:
    echo [WARNING]   1. Install Android Studio
    echo [WARNING]   2. Install Android SDK
    echo [WARNING]   3. Open android/ folder in Android Studio
)

echo.
echo [SUCCESS] 🎉 Akshar Messaging App setup completed!
echo.
echo [INFO] Next steps:
echo   1. Configure your .env file in the backend directory
echo   2. Start MongoDB (local or use MongoDB Atlas)
echo   3. Start the backend server: cd backend ^&^& npm run dev
echo   4. Set up Firebase for push notifications
echo   5. Configure Cloudinary for file uploads
echo   6. Build and run mobile apps
echo.
echo [INFO] Backend API will be available at: http://localhost:3000
echo [INFO] Health check endpoint: http://localhost:3000/health
echo.
echo [INFO] Features included:
echo   ✅ Real-time messaging with Socket.IO
echo   ✅ JWT authentication
echo   ✅ File upload and sharing
echo   ✅ Group chats
echo   ✅ Push notifications
echo   ✅ Message reactions and replies
echo   ✅ User status and presence
echo   ✅ Voice and video calling (WebRTC ready)
echo   ✅ End-to-end encryption ready
echo.
echo [INFO] Happy coding! 🚀
pause

@echo off
echo 🚀 Akshar Messaging - Quick Setup
echo ================================
echo.

echo [INFO] Setting up development environment...
echo.

REM Check Node.js
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Node.js not found! Please install Node.js 18+
    echo Download from: https://nodejs.org/
    pause
    exit /b 1
)

echo [SUCCESS] Node.js found: 
node --version
echo.

REM Setup backend
echo [INFO] Setting up backend...
cd backend

REM Install dependencies
echo [INFO] Installing dependencies...
call npm install

REM Create .env file
echo [INFO] Creating .env file...
if not exist .env (
    copy env.example .env
    echo [SUCCESS] .env file created!
    echo.
    echo [IMPORTANT] Please update .env file with your credentials:
    echo.
    echo 1. MongoDB:
    echo    - Local: MONGODB_URI=mongodb://localhost:27017/akshar_messaging
    echo    - Atlas: MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/akshar_messaging
    echo.
    echo 2. JWT Secret:
    echo    JWT_SECRET=your_super_secret_key_here
    echo.
    echo 3. Cloudinary (for file uploads):
    echo    - Sign up at: https://cloudinary.com/
    echo    - Get credentials from dashboard
    echo    CLOUDINARY_CLOUD_NAME=your_cloud_name
    echo    CLOUDINARY_API_KEY=your_api_key
    echo    CLOUDINARY_API_SECRET=your_api_secret
    echo.
    echo 4. Firebase (for push notifications):
    echo    - Create project at: https://console.firebase.google.com/
    echo    - Generate service account key
    echo    FIREBASE_PROJECT_ID=your_project_id
    echo    FIREBASE_PRIVATE_KEY=your_private_key
    echo    FIREBASE_CLIENT_EMAIL=your_client_email
    echo.
    echo [NEXT] After updating .env file, run: npm run dev
) else (
    echo [SUCCESS] .env file already exists
)

cd ..

echo.
echo [SUCCESS] 🎉 Setup completed!
echo.
echo [INFO] Next steps:
echo 1. Update backend/.env file with your credentials
echo 2. Start backend: cd backend ^&^& npm run dev
echo 3. Backend will run at: http://localhost:3000
echo 4. Test API: http://localhost:3000/health
echo.
echo [INFO] For mobile apps:
echo - Android: Open android/ folder in Android Studio
echo - iOS: Open ios/ folder in Xcode
echo.
pause

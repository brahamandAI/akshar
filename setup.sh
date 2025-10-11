#!/bin/bash

echo "🚀 Setting up Akshar Messaging App..."

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    print_error "Node.js is not installed. Please install Node.js 18+ first."
    exit 1
fi

# Check Node.js version
NODE_VERSION=$(node -v | cut -d'v' -f2 | cut -d'.' -f1)
if [ "$NODE_VERSION" -lt 18 ]; then
    print_error "Node.js version 18+ is required. Current version: $(node -v)"
    exit 1
fi

print_success "Node.js version: $(node -v)"

# Check if MongoDB is installed
if ! command -v mongod &> /dev/null; then
    print_warning "MongoDB is not installed. Please install MongoDB 6+ or use MongoDB Atlas."
    print_warning "You can install MongoDB from: https://www.mongodb.com/try/download/community"
fi

# Setup Backend
print_status "Setting up backend..."

cd backend

# Install dependencies
print_status "Installing backend dependencies..."
npm install

if [ $? -eq 0 ]; then
    print_success "Backend dependencies installed successfully"
else
    print_error "Failed to install backend dependencies"
    exit 1
fi

# Create .env file if it doesn't exist
if [ ! -f .env ]; then
    print_status "Creating .env file..."
    cp env.example .env
    print_warning "Please update the .env file with your configuration"
    print_warning "Required configurations:"
    print_warning "  - MONGODB_URI"
    print_warning "  - JWT_SECRET"
    print_warning "  - CLOUDINARY_* (for file uploads)"
    print_warning "  - FIREBASE_* (for push notifications)"
else
    print_success ".env file already exists"
fi

cd ..

# Setup Android (if Android Studio is available)
if command -v adb &> /dev/null; then
    print_status "Android development environment detected"
    print_status "To build Android app:"
    print_status "  1. Open android/ folder in Android Studio"
    print_status "  2. Sync project with Gradle files"
    print_status "  3. Build and run the app"
else
    print_warning "Android development environment not detected"
    print_warning "To set up Android development:"
    print_warning "  1. Install Android Studio"
    print_warning "  2. Install Android SDK"
    print_warning "  3. Open android/ folder in Android Studio"
fi

# Setup iOS (if Xcode is available)
if command -v xcodebuild &> /dev/null; then
    print_status "iOS development environment detected"
    print_status "To build iOS app:"
    print_status "  1. Open ios/AksharMessaging.xcodeproj in Xcode"
    print_status "  2. Install CocoaPods: sudo gem install cocoapods"
    print_status "  3. Run: cd ios && pod install"
    print_status "  4. Open AksharMessaging.xcworkspace in Xcode"
    print_status "  5. Build and run the app"
else
    print_warning "iOS development environment not detected"
    print_warning "To set up iOS development:"
    print_warning "  1. Install Xcode from Mac App Store"
    print_warning "  2. Install CocoaPods: sudo gem install cocoapods"
    print_warning "  3. Open ios/AksharMessaging.xcodeproj in Xcode"
fi

echo ""
print_success "🎉 Akshar Messaging App setup completed!"
echo ""
print_status "Next steps:"
echo "  1. Configure your .env file in the backend directory"
echo "  2. Start MongoDB (local or use MongoDB Atlas)"
echo "  3. Start the backend server: cd backend && npm run dev"
echo "  4. Set up Firebase for push notifications"
echo "  5. Configure Cloudinary for file uploads"
echo "  6. Build and run mobile apps"
echo ""
print_status "Backend API will be available at: http://localhost:3000"
print_status "Health check endpoint: http://localhost:3000/health"
echo ""
print_status "Features included:"
print_status "  ✅ Real-time messaging with Socket.IO"
print_status "  ✅ JWT authentication"
print_status "  ✅ File upload and sharing"
print_status "  ✅ Group chats"
print_status "  ✅ Push notifications"
print_status "  ✅ Message reactions and replies"
print_status "  ✅ User status and presence"
print_status "  ✅ Voice and video calling (WebRTC ready)"
print_status "  ✅ End-to-end encryption ready"
echo ""
print_status "Happy coding! 🚀"

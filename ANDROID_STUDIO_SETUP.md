# Android Studio Setup Guide

## 📱 **Android Studio Download & Installation**

### Step 1: Download Android Studio
1. Go to: https://developer.android.com/studio
2. Click "Download Android Studio"
3. Download the installer (Windows/Mac/Linux)

### Step 2: Install Android Studio
1. Run the downloaded installer
2. Follow installation wizard
3. Make sure to install:
   - ✅ Android Studio
   - ✅ Android SDK
   - ✅ Android Virtual Device (AVD)
   - ✅ Intel HAXM (for emulator performance)

### Step 3: First Time Setup
1. Open Android Studio
2. Choose "Standard" installation
3. Accept license agreements
4. Wait for SDK components to download
5. Click "Finish"

### Step 4: Install Required SDK Components
1. Go to: Tools → SDK Manager
2. Install these components:
   - ✅ Android 14 (API 34)
   - ✅ Android 13 (API 33)
   - ✅ Android 12 (API 31)
   - ✅ Android 11 (API 30)
   - ✅ Build Tools (latest)
   - ✅ Google Play services
   - ✅ Android Emulator

## 🚀 **Open Akshar Project in Android Studio**

### Step 1: Import Project
1. Open Android Studio
2. Click "Open an existing project"
3. Navigate to: `C:\Users\Dell\Downloads\Akshar_Messaging\android`
4. Select the `android` folder
5. Click "OK"

### Step 2: Gradle Sync
1. Android Studio will automatically sync Gradle
2. Wait for "Gradle sync finished" message
3. If errors appear, click "Try Again"

### Step 3: Add Firebase Files
1. Copy `google-services.json` to `android/app/` folder
2. Android Studio will detect it automatically
3. Sync project again

## 📱 **Create Virtual Device (Emulator)**

### Step 1: AVD Manager
1. Go to: Tools → AVD Manager
2. Click "Create Virtual Device"

### Step 2: Choose Device
1. Select "Phone" category
2. Choose "Pixel 7" or "Pixel 6"
3. Click "Next"

### Step 3: Choose System Image
1. Select "Android 14 (API 34)" or latest
2. Click "Download" if not already downloaded
3. Click "Next"

### Step 4: Configure AVD
1. AVD Name: "Pixel_7_API_34"
2. Click "Finish"

## 🏃‍♂️ **Run the App**

### Step 1: Select Device
1. Click device dropdown (top toolbar)
2. Select your virtual device or connected phone

### Step 2: Run App
1. Click green "Run" button (▶️)
2. Or press Shift + F10
3. Wait for app to build and install

### Step 3: Test App
1. App will open on emulator/device
2. You should see Akshar messaging app
3. Test registration/login functionality

## 🔧 **Common Issues & Solutions**

### Issue: Gradle Sync Failed
**Solution:**
1. Check internet connection
2. Go to File → Invalidate Caches
3. Restart Android Studio

### Issue: SDK Not Found
**Solution:**
1. Go to File → Project Structure
2. Set Android SDK location
3. Usually: `C:\Users\YourName\AppData\Local\Android\Sdk`

### Issue: Build Failed
**Solution:**
1. Check if all dependencies are downloaded
2. Clean project: Build → Clean Project
3. Rebuild project: Build → Rebuild Project

## ✅ **Verification Checklist**
- [ ] Android Studio installed
- [ ] SDK components downloaded
- [ ] Project opened successfully
- [ ] Gradle sync completed
- [ ] Virtual device created
- [ ] App runs without errors

## 🎯 **Alternative: Use Physical Device**
If you have an Android phone:
1. Enable Developer Options
2. Enable USB Debugging
3. Connect phone via USB
4. Select phone in device dropdown
5. Run app on phone

## 💡 **Tips:**
- Keep Android Studio updated
- Use SSD for better performance
- Allocate at least 8GB RAM to Android Studio
- Close other heavy applications while building

# Akshar Messaging - Icon Setup Guide 📱

## Overview
This guide explains how to setup the Akshar Messaging app icon across all Android densities and use cases.

---

## 🎯 Where the App Icon Appears

1. **Home Screen Launcher** - Main app icon users tap
2. **App Drawer** - List of all apps
3. **Recent Apps** - Task switcher
4. **Notifications** - Status bar and notification panel
5. **Settings** - App info and permissions
6. **Play Store** - App listing (requires separate 512x512 icon)
7. **Splash Screen** - App launch screen

---

## 📏 Icon Sizes Required

### Launcher Icons (Color, PNG)
```
mipmap-mdpi/     → 48x48 px
mipmap-hdpi/     → 72x72 px
mipmap-xhdpi/    → 96x96 px
mipmap-xxhdpi/   → 144x144 px
mipmap-xxxhdpi/  → 192x192 px
```

### Notification Icon (Monochrome, XML)
```
drawable/ic_stat_notification.xml → 24dp (white/transparent only)
drawable/ic_notification.xml      → 24dp (can have colors)
```

### Play Store Icon
```
512x512 px, 32-bit PNG, sRGB color space
```

---

## 🚀 Quick Setup Methods

### Method 1: Using Android Studio (Recommended)
1. Right-click on `app/src/main/res` folder
2. Select **New → Image Asset**
3. Choose **Launcher Icons (Adaptive and Legacy)**
4. Select `akshar_messaging_icon.PNG` as source
5. Adjust padding if needed
6. Click **Next** → **Finish**
7. Build and run the app

### Method 2: Using ImageMagick (Automated)
```bash
cd android
./setup-icon.bat  # Windows
# or
./setup-icon.sh   # Linux/Mac (create shell version if needed)
```

### Method 3: Manual Copy (Quick Test)
```bash
cd android
./copy-icon-manual.bat
```
**Note:** This copies the same size to all folders. Not recommended for production!

---

## 📱 What We've Implemented

### ✅ Launcher Icons
- **Location:** `app/src/main/res/mipmap-*/`
- **Files:** `ic_launcher.png`, `ic_launcher_round.png`
- **Status:** Ready (needs actual icon placement)

### ✅ Adaptive Icons (Android 8.0+)
- **Location:** `app/src/main/res/mipmap-anydpi-v26/`
- **Files:** `ic_launcher.xml`, `ic_launcher_round.xml`
- **Background Color:** `#1A1A1A` (Dark theme)
- **Foreground:** App icon with transparency

### ✅ Notification Icons
- **Location:** `app/src/main/res/drawable/`
- **Files:** 
  - `ic_stat_notification.xml` - Status bar (monochrome)
  - `ic_notification.xml` - Notification content (colored)
- **Usage:** Already integrated in `FirebaseMessagingService.kt`

### ✅ Splash Screen (Android 12+)
- **Theme:** `Theme.AksharMessaging.Splash`
- **Location:** `app/src/main/res/values/themes.xml`
- **Background:** `#121212` (Dark)
- **Icon:** Launcher icon (adaptive)
- **Duration:** 1000ms animation
- **Status:** Implemented in `MainActivity.kt`

---

## 🎨 Brand Colors

```xml
Akshar Primary:     #00BCD4 (Cyan)
Primary Dark:       #0097A7 (Dark Cyan)
Accent:             #00E5FF (Light Cyan)
Background:         #121212 (Near Black)
Icon Background:    #1A1A1A (Slightly lighter)
```

---

## 📝 Icon Design Guidelines

### ✅ DO:
- Use simple, recognizable design
- Ensure good contrast on dark backgrounds
- Test on different launcher themes
- Use proper sizes for each density
- Keep consistent branding
- Use transparency wisely

### ❌ DON'T:
- Use too much text in icons
- Make icons too complex
- Forget about notification icons
- Use low-resolution images
- Ignore adaptive icon safe zone

---

## 🔍 Current Icon

**File:** `akshar_messaging_icon.PNG`
**Theme:** Modern messaging with network/connection visualization
**Colors:** Cyan gradient with metallic spheres
**Style:** 3D, professional, tech-forward

---

## 📦 Files Created

### Scripts
- `setup-icon.bat` - Automated icon generation with ImageMagick
- `copy-icon-manual.bat` - Quick manual copy (testing only)

### XML Resources
- `mipmap-anydpi-v26/ic_launcher.xml` - Adaptive launcher icon
- `mipmap-anydpi-v26/ic_launcher_round.xml` - Adaptive round icon
- `drawable/ic_stat_notification.xml` - Status bar notification icon
- `drawable/ic_notification.xml` - Notification content icon
- `values/themes.xml` - App themes with splash screen

### Modified Files
- `values/colors.xml` - Added Akshar brand colors
- `AndroidManifest.xml` - Set splash screen theme
- `MainActivity.kt` - Added splash screen initialization
- `FirebaseMessagingService.kt` - Updated notification icon
- `build.gradle` - Added splash screen dependency

---

## 🎯 Next Steps

### 1. Generate Icons
Use one of the methods above to generate all icon sizes from `akshar_messaging_icon.PNG`

### 2. Test on Device
```bash
cd android
./gradlew clean
./gradlew assembleDebug
# Or use Android Studio: Build → Rebuild Project
```

### 3. Verify Icon Appears:
- [ ] Home screen launcher
- [ ] App drawer
- [ ] Recent apps
- [ ] Notification icon
- [ ] Splash screen
- [ ] Settings → Apps

### 4. Create Play Store Icon
If publishing to Play Store:
1. Open `akshar_messaging_icon.PNG` in image editor
2. Resize to 512x512 px
3. Export as 32-bit PNG
4. Use in Play Console

---

## 🐛 Troubleshooting

### Icon not showing on launcher
- Clean and rebuild project
- Clear app data
- Uninstall and reinstall app
- Check if icons exist in all mipmap folders

### Notification icon appears as white square
- Make sure using `ic_stat_notification.xml` (monochrome)
- Check if icon is truly white/transparent only
- Verify `setSmallIcon()` in notification builder

### Splash screen not appearing
- Check if `installSplashScreen()` is called before `super.onCreate()`
- Verify theme in AndroidManifest.xml
- Test on Android 12+ device

### Icons look blurry
- Regenerate with correct sizes for each density
- Don't scale up small images
- Use vector drawable if possible

---

## 📚 Resources

- [Android Icon Design Guide](https://developer.android.com/guide/practices/ui_guidelines/icon_design_launcher)
- [Adaptive Icons](https://developer.android.com/guide/practices/ui_guidelines/icon_design_adaptive)
- [Splash Screen API](https://developer.android.com/guide/topics/ui/splash-screen)
- [Play Store Icon Requirements](https://developer.android.com/distribute/google-play/resources/icon-design-specifications)

---

## ✅ Checklist

- [x] Adaptive icon XML created
- [x] Notification icons created
- [x] Splash screen implemented
- [x] Brand colors defined
- [x] Setup scripts created
- [ ] Icons generated in all sizes
- [ ] Tested on real device
- [ ] Verified on different Android versions
- [ ] Play Store icon created (512x512)

---

**Status:** Setup complete, ready for icon generation! 🚀

Run one of the setup methods above to generate and place icons in all required sizes.


# 🎨 Android Studio Image Asset Studio - Complete Guide

## Step-by-Step Instructions for Akshar Messaging Icon Setup

---

## 📋 Prerequisites

✅ Android Studio installed and open  
✅ Akshar_Messaging project loaded  
✅ Icon file available: `android/akshar_messaging_icon.PNG` (614 KB)

---

## 🚀 STEP-BY-STEP GUIDE

### **STEP 1: Open Project View** 📁

```
Make sure you're in "Project" view (not "Android" view)

Top-left dropdown should show: Project
```

---

### **STEP 2: Navigate to res folder** 📂

```
Expand the folders:
Akshar_Messaging/
  └─ android/
      └─ app/
          └─ src/
              └─ main/
                  └─ res/  ← FIND THIS FOLDER
```

---

### **STEP 3: Right-Click on 'res' folder** 🖱️

```
Right-click on 'res' folder

A context menu will appear with options:
  ├─ New
  ├─ Cut
  ├─ Copy
  ├─ Paste
  ├─ Delete
  └─ ...
```

---

### **STEP 4: Select 'New → Image Asset'** ✨

```
From context menu:
  Click: New →
    A submenu opens:
      ├─ Android Resource Directory
      ├─ Android Resource File
      ├─ Vector Asset
      ├─ Image Asset          ← CLICK THIS ONE!
      └─ ...
```

---

### **STEP 5: Asset Studio Window Opens** 🪟

```
┌──────────────────────────────────────────────────────────┐
│  Asset Studio                                             │
├──────────────────────────────────────────────────────────┤
│                                                           │
│  Icon Type:  [Launcher Icons (Adaptive and Legacy) ▼]    │
│              ← Keep this selected                         │
│                                                           │
│  Name:       ic_launcher                                  │
│              ← Don't change this                          │
│                                                           │
│  ┌─── Foreground Layer ───────────────────────────────┐  │
│  │                                                     │  │
│  │  Source Asset:                                      │  │
│  │    ○ Clip Art                                       │  │
│  │    ● Image    📁 [Path: (None)]                     │  │
│  │              ↑                                       │  │
│  │              CLICK THIS FOLDER ICON →               │  │
│  │                                                     │  │
│  │  Path: ________________________________  📁         │  │
│  │                                                     │  │
│  │  ☑ Trim                                             │  │
│  │  ☐ Resize (0% - 100%)  [────────●──────] 75%       │  │
│  │                                                     │  │
│  └─────────────────────────────────────────────────────┘  │
│                                                           │
│  ┌─── Background Layer ────────────────────────────────┐  │
│  │                                                     │  │
│  │  Source Asset:                                      │  │
│  │    ● Color                                          │  │
│  │    ○ Image                                          │  │
│  │                                                     │  │
│  │  Color: [#1A1A1A]  🎨                               │  │
│  │         ↑ Use this dark color                       │  │
│  │                                                     │  │
│  └─────────────────────────────────────────────────────┘  │
│                                                           │
│  ┌─── Legacy ───────────────────────────────────────────┐ │
│  │  ☑ Generate Legacy Icon                            │  │
│  │  ☑ Generate Round Icon                             │  │
│  └─────────────────────────────────────────────────────┘  │
│                                                           │
│  ┌─── Preview ──────────────────────────────────────────┐ │
│  │                                                     │  │
│  │   Circle    Square    Squircle    Round            │  │
│  │    [🎨]      [🎨]      [🎨]        [🎨]            │  │
│  │                                                     │  │
│  └─────────────────────────────────────────────────────┘  │
│                                                           │
│              [Previous]  [Next]  [Finish]                 │
│                                                           │
└──────────────────────────────────────────────────────────┘
```

---

### **STEP 6: Select Icon File** 📂

```
1. Click the folder icon (📁) next to "Path:"

2. File browser opens

3. Navigate to:
   C:\Users\Dell\Downloads\Akshar_Messaging\android\
   
4. Find and select:
   akshar_messaging_icon.PNG
   
5. Click "Open"

6. Icon will appear in preview immediately!
```

---

### **STEP 7: Adjust Settings** ⚙️

```
┌─── Recommended Settings ─────────────────────────────────┐
│                                                           │
│  Foreground Layer:                                        │
│    • Image: ✅ akshar_messaging_icon.PNG selected         │
│    • Trim: ✅ YES (removes extra whitespace)              │
│    • Resize: 75% - 85% (adjust to see what looks best)   │
│                                                           │
│  Background Layer:                                        │
│    • Color: ✅ #1A1A1A (dark background)                  │
│    • OR use: #121212 (slightly darker)                   │
│                                                           │
│  Legacy:                                                  │
│    • Generate Legacy Icon: ✅ CHECKED                     │
│    • Generate Round Icon: ✅ CHECKED                      │
│                                                           │
└──────────────────────────────────────────────────────────┘

💡 TIP: Look at the preview! If icon looks too big/small,
        adjust the Resize slider until it looks perfect.
```

---

### **STEP 8: Preview Check** 👀

```
In the Preview section, you'll see your icon in different shapes:

  ○ Circle    - For circular launchers
  □ Square    - For square launchers  
  ◯ Squircle  - For rounded square launchers
  ⬭ Round     - For round icon (Android 7.1+)

Make sure icon looks good in ALL shapes!

If icon is cut off: Decrease resize percentage
If icon is too small: Increase resize percentage
```

---

### **STEP 9: Click "Next"** ➡️

```
Click the "Next" button at bottom-right

A confirmation screen appears showing:

┌─── Confirm Icon Path ────────────────────────────────────┐
│                                                           │
│  The following files will be created:                    │
│                                                           │
│  ✓ res/mipmap-anydpi-v26/ic_launcher.xml                │
│  ✓ res/mipmap-anydpi-v26/ic_launcher_round.xml          │
│  ✓ res/mipmap-hdpi/ic_launcher.png                      │
│  ✓ res/mipmap-hdpi/ic_launcher_round.png                │
│  ✓ res/mipmap-mdpi/ic_launcher.png                      │
│  ✓ res/mipmap-mdpi/ic_launcher_round.png                │
│  ✓ res/mipmap-xhdpi/ic_launcher.png                     │
│  ✓ res/mipmap-xhdpi/ic_launcher_round.png               │
│  ✓ res/mipmap-xxhdpi/ic_launcher.png                    │
│  ✓ res/mipmap-xxhdpi/ic_launcher_round.png              │
│  ✓ res/mipmap-xxxhdpi/ic_launcher.png                   │
│  ✓ res/mipmap-xxxhdpi/ic_launcher_round.png             │
│  ✓ res/mipmap-anydpi-v26/ic_launcher_foreground.xml     │
│                                                           │
│  Existing files will be backed up with .bak extension    │
│                                                           │
│              [Previous]  [Finish]                         │
│                                                           │
└──────────────────────────────────────────────────────────┘
```

---

### **STEP 10: Click "Finish"** ✅

```
Click "Finish" button

Android Studio will:
  ⏳ Generating icons...
  ✅ Creating mipmap files...
  ✅ Creating adaptive icon XMLs...
  ✅ Backing up old files...
  ✅ Done!

You'll see: "Asset Studio: Icons generated successfully"
```

---

### **STEP 11: Verify Icons Created** 🔍

```
In Project view, check these folders:

app/src/main/res/
  ├─ mipmap-mdpi/
  │   ├─ ic_launcher.png ✅
  │   └─ ic_launcher_round.png ✅
  ├─ mipmap-hdpi/
  │   ├─ ic_launcher.png ✅
  │   └─ ic_launcher_round.png ✅
  ├─ mipmap-xhdpi/
  │   ├─ ic_launcher.png ✅
  │   └─ ic_launcher_round.png ✅
  ├─ mipmap-xxhdpi/
  │   ├─ ic_launcher.png ✅
  │   └─ ic_launcher_round.png ✅
  ├─ mipmap-xxxhdpi/
  │   ├─ ic_launcher.png ✅
  │   └─ ic_launcher_round.png ✅
  └─ mipmap-anydpi-v26/
      ├─ ic_launcher.xml ✅
      ├─ ic_launcher_round.xml ✅
      └─ ic_launcher_foreground.xml ✅

All green checkmarks = SUCCESS! 🎉
```

---

## 🏗️ STEP 12: Build the App

### **Method 1: Android Studio**

```
Top menu bar:
Build → Clean Project        (wait for it to finish)
Build → Rebuild Project      (wait 30-60 seconds)

✅ Build should succeed!
```

### **Method 2: Command Line**

```bash
cd android
.\gradlew.bat clean assembleDebug
```

---

## 📱 STEP 13: Install & Test

### **Install on Device/Emulator:**

```
1. Connect Android device OR start emulator

2. In Android Studio:
   Run → Run 'app'
   
   OR press: Shift + F10

3. App will install with your NEW ICON! 🎉
```

### **Where to Check Icon:**

```
✅ Home Screen        - Long press empty space, add app
✅ App Drawer         - Open all apps list
✅ Recent Apps        - Press square/overview button
✅ Notifications      - Receive a notification
✅ Settings → Apps    - Go to Android Settings
✅ Splash Screen      - Close & reopen app
```

---

## 🎉 SUCCESS CHECKLIST

After installation, verify:

- [ ] **Home Screen Icon** - Shows cyan gradient messaging icon
- [ ] **App Drawer Icon** - Same beautiful icon
- [ ] **Recent Apps** - Icon appears in task switcher
- [ ] **Notification** - Icon in status bar (when notification comes)
- [ ] **Splash Screen** - Shows icon with dark background when opening app
- [ ] **Settings** - Icon in Android Settings → Apps → Akshar Messaging
- [ ] **Different Shapes** - Test on different launchers (Nova, etc.)

---

## 🐛 Troubleshooting

### **Icon Not Showing?**

```
1. Uninstall old app completely
2. Clean project: Build → Clean Project
3. Rebuild: Build → Rebuild Project
4. Reinstall app
```

### **Icon Looks Blurry?**

```
1. Go back to Image Asset Studio
2. Increase Resize percentage (try 85% or 90%)
3. Regenerate icons
```

### **Icon Too Big/Small?**

```
1. Go back to Image Asset Studio
2. Adjust Resize slider
3. Check preview before finishing
```

### **Adaptive Icon Not Working?**

```
1. Check device Android version (need 8.0+)
2. Verify files exist in mipmap-anydpi-v26/
3. Ensure ic_launcher.xml and ic_launcher_round.xml present
```

---

## 📚 Additional Resources

- **Android Icon Design:** https://developer.android.com/guide/practices/ui_guidelines/icon_design_adaptive
- **Material Design Icons:** https://material.io/design/iconography
- **Adaptive Icons Guide:** https://medium.com/google-design/designing-adaptive-icons-515af294c783

---

## ✨ Summary

**What Image Asset Studio Does:**

1. ✅ Resizes icon to 5 different densities (mdpi to xxxhdpi)
2. ✅ Creates both square and round versions
3. ✅ Generates adaptive icon with foreground layer
4. ✅ Creates XML files for Android 8.0+
5. ✅ Backs up old icons automatically
6. ✅ Shows preview before creating
7. ✅ Ensures proper file naming and structure

**Time Required:** 5-10 minutes  
**Difficulty:** ⭐ Very Easy  
**Result:** 🎨 Professional icons everywhere!

---

## 🎯 FINAL RESULT

After completing these steps, your Akshar Messaging app will have:

🎨 **Beautiful cyan gradient icon** with 3D metallic spheres  
📱 **Professional appearance** on all Android devices  
🔔 **Custom notification icon** with brand colors  
🚀 **Modern splash screen** with animated icon  
✨ **Adaptive icon support** for different launcher shapes  
💪 **All densities covered** from low-end to flagship devices  

**Your app will look PROFESSIONAL! 🏆**

---

*Last Updated: October 11, 2025*  
*Project: Akshar Messaging*  
*Icon: akshar_messaging_icon.PNG*


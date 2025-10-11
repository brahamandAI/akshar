# Firebase iOS SDK Setup - Step by Step

## 🔥 **Firebase iOS SDK Installation**

### Step 1: Add GoogleService-Info.plist to Xcode
1. Download `GoogleService-Info.plist` from Firebase Console
2. Open your iOS project in Xcode
3. Drag `GoogleService-Info.plist` into Xcode project
4. Make sure:
   - ✅ "Copy items if needed" is checked
   - ✅ "Add to target: AksharMessaging" is selected
   - ✅ File is in the project root (same level as Info.plist)

### Step 2: Add Firebase SDK via Swift Package Manager

#### Method 1: Using Xcode (Recommended)
1. In Xcode, go to: **File → Add Package Dependencies**
2. Enter this URL: `https://github.com/firebase/firebase-ios-sdk`
3. Click "Add Package"
4. Select version: **"Up to Next Major Version"** (latest)
5. Choose these Firebase libraries:
   - ✅ **FirebaseAnalytics**
   - ✅ **FirebaseMessaging** 
   - ✅ **FirebaseAuth**
   - ✅ **FirebaseFirestore**
6. Click "Add Package"

#### Method 2: Using Package.swift (Alternative)
Add to your Package.swift dependencies:
```swift
dependencies: [
    .package(url: "https://github.com/firebase/firebase-ios-sdk", from: "10.0.0")
]
```

### Step 3: Update Podfile (If using CocoaPods)
```ruby
# ios/Podfile
platform :ios, '14.0'

target 'AksharMessaging' do
  use_frameworks!
  
  # Firebase pods
  pod 'Firebase/Core'
  pod 'Firebase/Messaging'
  pod 'Firebase/Analytics'
  pod 'Firebase/Auth'
  pod 'Firebase/Firestore'
end
```

Then run:
```bash
cd ios
pod install
```

### Step 4: Initialize Firebase in App
Update `AksharMessagingApp.swift`:
```swift
import SwiftUI
import FirebaseCore
import FirebaseMessaging

@main
struct AksharMessagingApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init() {
        // Initialize Firebase
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
```

### Step 5: Configure App Delegate
Create `AppDelegate.swift`:
```swift
import UIKit
import FirebaseCore
import FirebaseMessaging

class AppDelegate: NSObject, UIApplicationDelegate, MessagingDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        // Configure Firebase
        FirebaseApp.configure()
        
        // Set messaging delegate
        Messaging.messaging().delegate = self
        
        // Request notification permissions
        UNUserNotificationCenter.current().delegate = self
        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        UNUserNotificationCenter.current().requestAuthorization(options: authOptions) { granted, error in
            if granted {
                DispatchQueue.main.async {
                    application.registerForRemoteNotifications()
                }
            }
        }
        
        return true
    }
    
    // Handle APNs token
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
    }
    
    // Firebase messaging delegate
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        print("Firebase registration token: \(String(describing: fcmToken))")
    }
}

// UNUserNotificationCenterDelegate
extension AppDelegate: UNUserNotificationCenterDelegate {
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        completionHandler([.banner, .sound, .badge])
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        completionHandler()
    }
}
```

### Step 6: Add Capabilities
1. Select project in Xcode
2. Go to "Signing & Capabilities"
3. Click "+ Capability"
4. Add these capabilities:
   - ✅ **Push Notifications**
   - ✅ **Background Modes**
     - Background processing
     - Background fetch

### Step 7: Test Firebase Connection
Add this to test Firebase:
```swift
import FirebaseAnalytics

// In your view
.onAppear {
    Analytics.logEvent("app_opened", parameters: nil)
    print("🔥 Firebase initialized successfully!")
}
```

## ✅ **Verification Checklist**
- [ ] GoogleService-Info.plist added to Xcode project
- [ ] Firebase SDK added via Swift Package Manager
- [ ] FirebaseApp.configure() called in app init
- [ ] AppDelegate configured for notifications
- [ ] Push Notifications capability added
- [ ] Background Modes capability added
- [ ] App builds without errors
- [ ] Console shows "Firebase initialized"

## 🚨 **Common Issues & Solutions**

### Issue: "No such module 'FirebaseCore'"
**Solution:**
1. Clean build folder: Cmd+Shift+K
2. Rebuild project: Cmd+B
3. Check if Firebase SDK is properly added

### Issue: "GoogleService-Info.plist not found"
**Solution:**
1. Make sure file is in project root
2. Check "Add to target" is selected
3. Verify file is in Xcode project navigator

### Issue: Push notifications not working
**Solution:**
1. Check Push Notifications capability
2. Verify APNs certificate in Firebase Console
3. Test on real device (not simulator)

## 🎯 **Next Steps**
After setup:
1. Build and run the app
2. Check Xcode console for Firebase logs
3. Test push notifications
4. Verify analytics tracking

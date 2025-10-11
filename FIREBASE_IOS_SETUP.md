# Firebase iOS SDK Setup

## 🔥 **After adding GoogleService-Info.plist**

### Step 1: Add GoogleService-Info.plist to Xcode
1. Drag GoogleService-Info.plist into Xcode project
2. Make sure "Copy items if needed" is checked
3. Add to target: AksharMessaging

### Step 2: Update Podfile
```ruby
# ios/Podfile
platform :ios, '14.0'

target 'AksharMessaging' do
  use_frameworks!
  
  # Firebase pods
  pod 'Firebase/Core'
  pod 'Firebase/Messaging'
  pod 'Firebase/Analytics'
end
```

### Step 3: Install Pods
```bash
cd ios
pod install
```

### Step 4: Open Workspace (Not Project)
- Always open `AksharMessaging.xcworkspace` (not .xcodeproj)

### Step 5: Initialize Firebase in App
In AksharMessagingApp.swift:
```swift
import FirebaseCore

@main
struct AksharMessagingApp: App {
    init() {
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
```

### Step 6: Add Capabilities
1. Select project in Xcode
2. Go to "Signing & Capabilities"
3. Add capabilities:
   - Push Notifications
   - Background Modes
     - Background processing
     - Background fetch

### Step 7: Test Firebase
Add this to test connection:
```swift
import FirebaseAnalytics

// In your view
.onAppear {
    Analytics.logEvent("app_opened", parameters: nil)
    print("Firebase initialized successfully")
}
```

## ✅ **Verification:**
- No build errors
- Console shows "Firebase initialized"
- Pod installation successful

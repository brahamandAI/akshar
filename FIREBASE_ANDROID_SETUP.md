# Firebase Android SDK Setup (Updated 2024)

## 🔥 **After adding google-services.json**

### Step 1: Update Root build.gradle (android/build.gradle):
```gradle
buildscript {
    dependencies {
        classpath "com.google.dagger:hilt-android-gradle-plugin:$hilt_version"
        // Add Google services plugin - Latest version
        classpath 'com.google.gms:google-services:4.4.3'
    }
}
```

### Step 2: Update App build.gradle (android/app/build.gradle):
```gradle
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
    id 'kotlin-parcelize'
    id 'org.jetbrains.kotlin.plugin.serialization'
    // Add Google services plugin
    id 'com.google.gms.google-services'
}

dependencies {
    // Firebase BOM (Bill of Materials) - Latest version
    implementation platform('com.google.firebase:firebase-bom:34.3.0')
    
    // Firebase services (versions managed by BOM - don't specify versions)
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-messaging'
    implementation 'com.google.firebase:firebase-auth'
    implementation 'com.google.firebase:firebase-firestore'
}
```

### Step 3: Sync Project
1. In Android Studio, click "Sync Now"
2. Wait for Gradle sync to complete
3. Should show "Gradle sync finished" without errors

### Step 4: Add Firebase Initialization
In your MainActivity.kt or Application class:
```kotlin
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        setContent {
            // Your app content
        }
    }
}
```

### Step 4: Test Firebase Connection
Add this code to test:
```kotlin
import com.google.firebase.analytics.FirebaseAnalytics

// In your activity
val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
Log.d("Firebase", "Firebase initialized successfully")
```

## ✅ **Verification:**
- Check Android Studio logs for "Firebase initialized"
- No red errors in code
- Gradle sync successful

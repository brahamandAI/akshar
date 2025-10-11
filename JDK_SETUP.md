# JDK 17 Setup Guide for Akshar Messaging

## ☕ **JDK 17 Installation & Configuration**

### Step 1: Extract JDK 17
1. Extract the downloaded `jdk-17.0.12_windows-x64_bin.zip` file
2. Extract to: `C:\Program Files\Java\jdk-17.0.12\`
3. Make sure folder structure is:
   ```
   C:\Program Files\Java\jdk-17.0.12\
   ├── bin\
   ├── lib\
   ├── include\
   └── other files...
   ```

### Step 2: Set JAVA_HOME Environment Variable

#### Method 1: Using System Properties
1. Press `Win + R`, type `sysdm.cpl`, press Enter
2. Click "Advanced" tab
3. Click "Environment Variables"
4. Under "System Variables", click "New"
5. Variable name: `JAVA_HOME`
6. Variable value: `C:\Program Files\Java\jdk-17.0.12`
7. Click "OK"

#### Method 2: Using Command Line (Run as Administrator)
```cmd
setx JAVA_HOME "C:\Program Files\Java\jdk-17.0.12" /M
```

### Step 3: Update PATH Environment Variable
1. In Environment Variables window
2. Find "Path" variable under System Variables
3. Click "Edit"
4. Click "New"
5. Add: `%JAVA_HOME%\bin`
6. Move it to the top of the list
7. Click "OK" on all windows

### Step 4: Verify Installation
Open Command Prompt and run:
```cmd
java -version
javac -version
echo %JAVA_HOME%
```

Expected output:
```
java version "17.0.12" 2024-07-16 LTS
Java(TM) SE Runtime Environment (build 17.0.12+9-LTS-252)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.12+9-LTS-252, mixed mode, sharing)
```

### Step 5: Configure Android Studio
1. Open Android Studio
2. Go to: File → Settings (or Android Studio → Preferences on Mac)
3. Go to: Build, Execution, Deployment → Build Tools → Gradle
4. Set "Gradle JDK" to: "17" or browse to your JDK 17 path
5. Click "Apply" and "OK"

### Step 6: Alternative JDK Locations
If you installed JDK in different location:
- `C:\Program Files\Eclipse Adoptium\jdk-17.0.12-hotspot\`
- `C:\Users\YourName\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.12-hotspot\`

## ✅ **Verification Checklist**
- [ ] JDK 17 extracted to correct location
- [ ] JAVA_HOME environment variable set
- [ ] PATH updated with JDK bin directory
- [ ] Command line shows Java 17 version
- [ ] Android Studio configured to use JDK 17
- [ ] Gradle sync works without errors

## 🚨 **Common Issues & Solutions**

### Issue: "JDK 17 or higher is required"
**Solution:**
1. Restart Android Studio after setting JAVA_HOME
2. Check if JAVA_HOME points to JDK (not JRE)
3. Verify PATH includes %JAVA_HOME%\bin

### Issue: "java.jdt.ls.java.home" error
**Solution:**
1. In VS Code: Ctrl+Shift+P → "Java: Configure Java Runtime"
2. Set "JDK 17" as default
3. Or add to settings.json:
   ```json
   "java.jdt.ls.java.home": "C:\\Program Files\\Java\\jdk-17.0.12"
   ```

### Issue: Gradle still shows JDK 11
**Solution:**
1. In Android Studio: File → Settings → Build Tools → Gradle
2. Change "Gradle JDK" to "17"
3. Clean and rebuild project

## 🎯 **Quick Test**
After setup, run this in Command Prompt:
```cmd
java -version
echo %JAVA_HOME%
```

Should show JDK 17 and correct path!

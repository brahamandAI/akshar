@echo off
echo ☕ JDK 17 Setup for Akshar Messaging
echo ====================================
echo.

echo [INFO] Checking Java installation...
java -version >nul 2>&1
if %errorlevel% equ 0 (
    echo [SUCCESS] Java is installed
    java -version
) else (
    echo [ERROR] Java not found in PATH
    echo Please follow JDK_SETUP.md to configure Java
    pause
    exit /b 1
)

echo.
echo [INFO] Checking JAVA_HOME...
if defined JAVA_HOME (
    echo [SUCCESS] JAVA_HOME is set to: %JAVA_HOME%
) else (
    echo [WARNING] JAVA_HOME not set
    echo Please set JAVA_HOME environment variable
    echo Example: setx JAVA_HOME "C:\Program Files\Java\jdk-17.0.12" /M
)

echo.
echo [INFO] Testing Gradle...
cd android
call gradlew --version >nul 2>&1
if %errorlevel% equ 0 (
    echo [SUCCESS] Gradle wrapper found
    echo [INFO] Running gradle --version:
    call gradlew --version
) else (
    echo [ERROR] Gradle wrapper not found or failed
    echo Please run this script from the project root directory
)

echo.
echo [INFO] Next steps:
echo 1. Open Android Studio
echo 2. Open android/ folder as project
echo 3. Sync project with Gradle files
echo 4. Build and run the app
echo.
pause

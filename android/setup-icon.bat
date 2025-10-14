@echo off
echo ========================================
echo Akshar Messaging - Icon Setup
echo ========================================
echo.

REM Check if source icon exists
if not exist "akshar_messaging_icon.PNG" (
    echo Error: akshar_messaging_icon.PNG not found!
    pause
    exit /b 1
)

echo Source icon found: akshar_messaging_icon.PNG
echo.

REM Check if ImageMagick or similar tool is available
where magick >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo ImageMagick found! Using it to resize icons...
    echo.
    
    REM Create mipmap directories if they don't exist
    if not exist "app\src\main\res\mipmap-mdpi" mkdir "app\src\main\res\mipmap-mdpi"
    if not exist "app\src\main\res\mipmap-hdpi" mkdir "app\src\main\res\mipmap-hdpi"
    if not exist "app\src\main\res\mipmap-xhdpi" mkdir "app\src\main\res\mipmap-xhdpi"
    if not exist "app\src\main\res\mipmap-xxhdpi" mkdir "app\src\main\res\mipmap-xxhdpi"
    if not exist "app\src\main\res\mipmap-xxxhdpi" mkdir "app\src\main\res\mipmap-xxxhdpi"
    
    REM Generate icons for all densities
    echo Generating mdpi (48x48)...
    magick "akshar_messaging_icon.PNG" -resize 48x48 "app\src\main\res\mipmap-mdpi\ic_launcher.png"
    magick "akshar_messaging_icon.PNG" -resize 48x48 "app\src\main\res\mipmap-mdpi\ic_launcher_round.png"
    
    echo Generating hdpi (72x72)...
    magick "akshar_messaging_icon.PNG" -resize 72x72 "app\src\main\res\mipmap-hdpi\ic_launcher.png"
    magick "akshar_messaging_icon.PNG" -resize 72x72 "app\src\main\res\mipmap-hdpi\ic_launcher_round.png"
    
    echo Generating xhdpi (96x96)...
    magick "akshar_messaging_icon.PNG" -resize 96x96 "app\src\main\res\mipmap-xhdpi\ic_launcher.png"
    magick "akshar_messaging_icon.PNG" -resize 96x96 "app\src\main\res\mipmap-xhdpi\ic_launcher_round.png"
    
    echo Generating xxhdpi (144x144)...
    magick "akshar_messaging_icon.PNG" -resize 144x144 "app\src\main\res\mipmap-xxhdpi\ic_launcher.png"
    magick "akshar_messaging_icon.PNG" -resize 144x144 "app\src\main\res\mipmap-xxhdpi\ic_launcher_round.png"
    
    echo Generating xxxhdpi (192x192)...
    magick "akshar_messaging_icon.PNG" -resize 192x192 "app\src\main\res\mipmap-xxxhdpi\ic_launcher.png"
    magick "akshar_messaging_icon.PNG" -resize 192x192 "app\src\main\res\mipmap-xxxhdpi\ic_launcher_round.png"
    
    echo.
    echo ✓ Icons generated successfully!
    echo.
) else (
    echo WARNING: ImageMagick not found!
    echo.
    echo Please install ImageMagick from: https://imagemagick.org/script/download.php
    echo Or use Android Studio's Image Asset Studio:
    echo   1. Right-click on 'res' folder in Android Studio
    echo   2. Select New ^> Image Asset
    echo   3. Choose 'akshar_messaging_icon.PNG' as source
    echo   4. Generate icons
    echo.
)

echo.
echo ========================================
echo Instructions:
echo ========================================
echo.
echo 1. If icons were generated, rebuild the app in Android Studio
echo 2. If not, use Android Studio's Image Asset Studio:
echo    - Right-click 'res' folder
echo    - New ^> Image Asset
echo    - Select akshar_messaging_icon.PNG
echo    - Generate icons
echo.
echo 3. For Play Store, create 512x512 icon separately
echo.

pause


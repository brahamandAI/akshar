@echo off
echo ========================================
echo Akshar Messaging - Manual Icon Copy
echo ========================================
echo.

REM Copy icon to all mipmap folders (as fallback)
echo Copying icon to all mipmap folders...
echo.

copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-mdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-mdpi\ic_launcher_round.png"

copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-hdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-hdpi\ic_launcher_round.png"

copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xhdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xhdpi\ic_launcher_round.png"

copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xxhdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xxhdpi\ic_launcher_round.png"

copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xxxhdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xxxhdpi\ic_launcher_round.png"

echo.
echo ✓ Icons copied to all folders!
echo.
echo NOTE: Icons are not resized. For best results:
echo 1. Use Android Studio's Image Asset Studio
echo 2. Or install ImageMagick and run setup-icon.bat
echo.

pause


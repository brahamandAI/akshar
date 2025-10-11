@echo off
echo Copying launcher icon to all mipmap folders...

copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-mdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-hdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xhdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xxhdpi\ic_launcher.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xxxhdpi\ic_launcher.png"

echo.
echo Round icons:
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-mdpi\ic_launcher_round.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-hdpi\ic_launcher_round.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xhdpi\ic_launcher_round.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xxhdpi\ic_launcher_round.png"
copy /Y "akshar_messaging_icon.PNG" "app\src\main\res\mipmap-xxxhdpi\ic_launcher_round.png"

echo.
echo ✅ Icon copied to all mipmap folders!
echo Now delete old XML files...

del /Q "app\src\main\res\mipmap-mdpi\ic_launcher.xml" 2>nul
del /Q "app\src\main\res\mipmap-hdpi\ic_launcher.xml" 2>nul
del /Q "app\src\main\res\mipmap-xhdpi\ic_launcher.xml" 2>nul
del /Q "app\src\main\res\mipmap-xxhdpi\ic_launcher.xml" 2>nul
del /Q "app\src\main\res\mipmap-xxxhdpi\ic_launcher.xml" 2>nul

del /Q "app\src\main\res\mipmap-mdpi\ic_launcher_round.xml" 2>nul
del /Q "app\src\main\res\mipmap-hdpi\ic_launcher_round.xml" 2>nul
del /Q "app\src\main\res\mipmap-xhdpi\ic_launcher_round.xml" 2>nul
del /Q "app\src\main\res\mipmap-xxhdpi\ic_launcher_round.xml" 2>nul
del /Q "app\src\main\res\mipmap-xxxhdpi\ic_launcher_round.xml" 2>nul

del /Q "app\src\main\res\mipmap-mdpi\ic_launcher_foreground.xml" 2>nul
del /Q "app\src\main\res\mipmap-hdpi\ic_launcher_foreground.xml" 2>nul
del /Q "app\src\main\res\mipmap-xhdpi\ic_launcher_foreground.xml" 2>nul
del /Q "app\src\main\res\mipmap-xxhdpi\ic_launcher_foreground.xml" 2>nul
del /Q "app\src\main\res\mipmap-xxxhdpi\ic_launcher_foreground.xml" 2>nul

echo ✅ Old XML icons deleted!
echo.
echo 🎉 Done! Your PNG icon is now ready!
pause


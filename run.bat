@echo off
start "" /d v2c-dispatcher java -jar build\libs\v2c-dispatcher.jar
ping -n 3 127.0.0.1>nul
start "" /d v2c-desktop-controller-linux java -jar build\libs\v2c-desktop-controller-linux.jar -u
start "" /d v2c-recognizer\Recognizer cmd /c "python speech.py"
start "" /d v2c-recognizer\Recognizer cmd /c "python widget.py"
start "" /d v2c-dashboard cmd /c "npm start"

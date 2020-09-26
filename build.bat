@echo off

rem build the java projects
for %%m in (v2c-desktop-controller-linux v2c-dispatcher) do (
  echo Building %%m
  cd %%m
  call .\gradlew clean shadowJar --refresh-dependencies
  cd ..
)

rem build the python modules
for %%p in (pyaudio playsound pocketsphinx SpeechRecognition pydub websockets) do (
  echo Checking for %%p
  pip list | find "%%p">nul
  if errorlevel 1 pip install "%%p"
)

rem install node project
for %%n in (v2c-dashboard) do (
  echo Installing %%n
  cd %%n
  npm install
  cd ..
)

echo Done.
@echo on

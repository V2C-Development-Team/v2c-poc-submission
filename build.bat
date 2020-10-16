@echo off

rem pull the repos
git submodule update --init --recursive

rem build this project
call .\gradlew clean shadowJar --refresh-dependencies

rem build the java projects
for %%m in (v2c-desktop-controller-linux v2c-dispatcher v2c-dashboard-backend) do (
  echo Building %%m
  cd %%m
  call .\gradlew clean shadowJar --refresh-dependencies
  cd ..
)

rem build the python modules
for %%p in (pyaudio playsound pocketsphinx SpeechRecognition pydub websocket-client websocket keyboard) do (
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

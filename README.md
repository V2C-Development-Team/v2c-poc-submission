# V2C Proof-of-Concept Submission

*UCO Fall 2020 Capstone Submission Repository*

## Prerequisites

Please make sure the following are installed **before** building the project.

- [Java JDK 11 LTS](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Node 12 LTS](https://nodejs.org/en/download/)
- [Python 3.6 64-bit](https://www.python.org/downloads/windows/) (make sure that pip is on the PATH)
- [Microsoft Visual C++ 14.0](https://aka.ms/vs/16/release/vs_buildtools.exe)
- [PyAudio](https://people.csail.mit.edu/hubert/pyaudio/packages/pyaudio-0.2.8.py33.exe) - if on Windows
- [swig](http://prdownloads.sourceforge.net/swig/swigwin-4.0.2.zip) - if on Windows, add to PATH

## Automatic Build

**It's important that you've installed the appropriate SSH keys in the remote repo service.**

You can exit the build script (`build.bat` on Windows) to build the program.

## Manual Build

These steps can be done out of order.

1. Enter `v2c-dashboard` and execute `npm install`
2. Enter `v2c-desktop-controller-linux` and execute `.\gradlew clean shadowJar`
3. Enter `v2c-dispatcher` and execute `.\gradlew clean shadowJar`
4. Install the `v2c-recognizer` dependencies denoted in `v2c-recognizer\README.md`

## Automated Execution

Run the `run.bat` (on Windows) to run the program.

## Manual Execution

These steps must be run in order.

1. Enter `v2c-dispatcher` and execute `java -jar build\libs\v2c-dispatcher.jar`
2. Enter `v2c-desktop-controller-linux` and execute `java -jar build\libs\v2c-desktop-controller-linux -u`
3. Enter `v2c-dashboard` and execute `npm start`
4. Enter `v2c-recognizer\Recognizer` and execute `python speech.py`
5. Enter `v2c-recognizer\Recognizer` and execute `python widget.py`

## Notes

- Additional instructions are in each of the `README.md` files in the various directories.
- If you run into issues building `pyaudio`, please attempt to install it manually with `pipwin`.


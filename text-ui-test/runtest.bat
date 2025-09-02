@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java

IF ERRORLEVEL 1 (
  echo ********** BUILD FAILURE **********
  exit /b 1
)

REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin sumtingwong.ui.SumTingWong < input.txt > ACTUAL.txt

REM Normalize ACTUAL.TXT by trimming trailing spaces and rewriting with consistent encoding
powershell -NoProfile -Command "(Get-Content 'ACTUAL.TXT') |%% { $_.TrimEnd() } | Set-Content -Encoding Unicode 'ACTUAL.TXT'"

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT

set EXITCODE=%ERRORLEVEL%
IF "%EXITCODE%"=="0" (
  echo TEST PASSED: yippie!!
  exit /b 0
) ELSE IF "%EXITCODE%"=="1" (
  echo TEST FAILED: differences found
  exit /b 1
) ELSE (
  echo TEST ERROR: fc returned code %EXITCODE%
  exit /b %EXITCODE%
)
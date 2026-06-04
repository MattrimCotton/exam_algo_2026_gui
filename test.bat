@echo off
set LIB=lib\junit-platform-console-standalone-1.11.4.jar
set SRC=src\App.java test\AppTest.java

javac -cp %LIB% -d bin %SRC%
if errorlevel 1 (
    echo Echec de la compilation.
    exit /b 1
)

java -jar %LIB% --class-path bin --scan-class-path

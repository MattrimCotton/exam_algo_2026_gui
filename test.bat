@echo off
set LIB=lib\junit-platform-console-standalone-1.11.4.jar

if not exist bin mkdir bin

dir /s /b src\*.java > sources.txt
echo test\AppTest.java >> sources.txt

javac -encoding UTF-8 -cp "%LIB%;bin" -d bin @sources.txt
if errorlevel 1 (
    echo Echec de la compilation.
    del sources.txt
    exit /b 1
)
del sources.txt

java -jar %LIB% execute --class-path bin --scan-class-path

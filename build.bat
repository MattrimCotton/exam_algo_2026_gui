@echo off
javac -d bin src\App.java
if errorlevel 1 (
    echo Echec de la compilation.
    exit /b 1
)
java -cp bin App

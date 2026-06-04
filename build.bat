@echo off
if not exist bin mkdir bin

:: Lister tous les fichiers Java
dir /s /b src\*.java > sources.txt

javac -encoding UTF-8 -d bin -cp "lib\*" @sources.txt
if errorlevel 1 (
    echo Echec de la compilation.
    del sources.txt
    exit /b 1
)
del sources.txt

:: Copier les fichiers de ressources i18n
xcopy /s /q /y src\*.properties bin\ >nul 2>&1

java -cp bin App

# Lanceur de dés

Application de bureau en Java (Swing) permettant de lancer des dés de jeu de rôle avec bonus/malus et retour audio.

## Fonctionnalités

- Dés disponibles : d4, d6, d8, d10, d12, d20, d100
- Nombre de dés configurable (1 à 100)
- Bonus ou malus appliqué au total (−999 à +999)
- Validation : le total ne peut pas être négatif
- Retour audio à chaque action (lancer, erreur, démarrage, fermeture)
- Historique des lancers avec numérotation
- Accessibilité clavier complète (mnémoniques, lecteurs d'écran)

## Prérequis

- JDK 17 ou supérieur

## Lancer l'application

```bat
build.bat
```

Ou manuellement :

```bat
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d bin -cp "lib\*" @sources.txt
del sources.txt
xcopy /s /q /y src\*.properties bin\ >nul
xcopy /q /y src\audio\*.ogg bin\audio\ >nul
java -cp "bin;lib\*" App
```

**VS Code :** touche `F5` (configuration `Launch App`).

## Raccourcis clavier

| Raccourci | Action          |
|-----------|-----------------|
| Alt+T     | Type de dé      |
| Alt+N     | Nombre de dés   |
| Alt+B     | Bonus / Malus   |
| Alt+L     | Lancer          |
| Alt+E     | Effacer         |
| Alt+Q     | Quitter         |
| Entrée    | Lancer (global) |

## Structure

```
src/
  App.java                  # Point d'entrée
  audio/
    AudioService.java       # Lecture des fichiers OGG
  gui/
    MainWindow.java         # Interface graphique (JFrame)
  model/
    DiceRoller.java         # Logique de lancer
    ResultFormatter.java    # Formatage de l'affichage
    RollResult.java         # Résultat d'un lancer (record)
lib/                        # Dépendances (décodeur OGG)
```

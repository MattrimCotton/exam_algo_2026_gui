# CLAUDE.md — exam_algo_2026_gui

> _Mis à jour le 2026-06-04._

## Build & Run

```bat
build.bat          # compile + lance
```

Ou manuellement :
```bat
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d bin -cp "lib\*" @sources.txt
del sources.txt
xcopy /s /q /y src\*.properties bin\ >nul
java -cp bin App
```

VS Code : **F5** (`Launch App` dans `.vscode/launch.json`).  
Prérequis : JDK 17+, extension *Extension Pack for Java*.

## Structure

```
src/
  App.java                  # Point d'entrée — lance MainWindow sur l'EDT
  gui/
    MainWindow.java         # JFrame principal (Swing)
  model/
    DiceRoller.java         # Logique de lancer (stateless)
    RollResult.java         # Record : faces, rolls, bonus, subtotal(), total()
  i18n/
    Messages.java           # Accès ResourceBundle
  messages.properties       # Chaînes FR (défaut)
bin/                        # Bytecode compilé
lib/                        # JARs externes (JUnit…)
```

## Accessibilité (Swing)

- `AccessibleContext.setAccessibleName/Description` sur tous les contrôles
- `JLabel.setLabelFor()` pour associer labels aux champs
- Mnémoniques clavier : **Alt+T** dé, **Alt+N** nombre, **Alt+B** bonus, **Alt+L** lancer, **Alt+E** effacer
- `getRootPane().setDefaultButton(rollButton)` → Entrée = lancer depuis n'importe quel champ

## i18n

Ajouter une langue : créer `src/messages_en.properties`, la JVM choisit selon `Locale`.  
Forcer : `java -Duser.language=en -cp bin App`

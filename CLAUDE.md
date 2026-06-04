# CLAUDE.md — exam_algo_2026

> _Mis à jour automatiquement le 2026-06-04 11:37 — ne pas éditer à la main._

## Build & Run

```bat
# Depuis la racine du projet
javac -d bin src\App.java
java -cp bin App
```

VS Code : **F5** (`Launch App` dans `.vscode/launch.json`).
Prérequis : JDK 17+, extension *Extension Pack for Java*.
Pas de tests automatisés — validation manuelle.

## Structure

```
exam_algo_2026/
├── src/    # Sources Java
├── bin/    # Bytecode compile (.class)
└── lib/    # Dependances JAR
```

## Classes publiques

### `App` — `App.java`
- main (point d'entree)
- `int lancerDe(int nombreFaces)`


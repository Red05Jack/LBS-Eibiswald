# Hangman

Dieses Projekt implementiert das klassische "Hangman"-Spiel als Java-Anwendung. Es bietet eine grafische Benutzeroberfläche (GUI) mit Swing. Das Ziel des Spiels ist es, ein verstecktes Wort zu erraten, indem Buchstaben nacheinander erraten werden, bis das Wort vollständig gelöst ist oder der Spieler verliert.

<p align="right"><a href="#readme-top">back to top</a></p>

## Inhaltsverzeichnis

- [Über das Projekt](#über-das-projekt)
- [Features](#features)
- [Verwendung](#verwendung)
- [Voraussetzungen](#voraussetzungen)
- [Installation](#installation)
- [Dateien im Projekt](#dateien-im-projekt)
- [Beispiele für Wortliste](#beispiele-für-wortliste)
- [Technologien](#technologien)
- [Autor](#autor)
- [Lizenz](#lizenz)

<p align="right"><a href="#readme-top">back to top</a></p>

## Über das Projekt

Dieses Projekt ist eine Implementierung des Hangman-Spiels, das als Unterrichtsprojekt für die LBS Eibiswald erstellt wurde. Das Spiel wird über eine grafische Benutzeroberfläche gespielt, die mit Java Swing implementiert wurde, um eine benutzerfreundliche Erfahrung zu bieten.

<p align="right"><a href="#readme-top">back to top</a></p>

## Features

- **Spielmodus**: GUI (Swing).
- **Vielzahl an Wörtern**: Das Spiel bietet eine umfangreiche Auswahl an Wörtern, die erraten werden müssen.
- **Benutzerfreundliche Oberfläche**: Die GUI-Version ist intuitiv und einfach zu bedienen.
- **Echtzeit-Feedback**: Die Spieler erhalten direktes Feedback zu ihren Eingaben.

<p align="right"><a href="#readme-top">back to top</a></p>

## Verwendung

Das Spiel wird über die grafische Benutzeroberfläche gespielt. Die GUI bietet eine interaktive Oberfläche, in der mit Maus und Tastatur interagiert werden kann.

- Starten Sie das Spiel, indem Sie die Klasse `HangmanGUI` ausführen.

<p align="right"><a href="#readme-top">back to top</a></p>

## Voraussetzungen

- **Java Development Kit (JDK) 11 oder höher**: Dieses Projekt ist mit Java geschrieben und benötigt JDK Version 11 oder höher.

<p align="right"><a href="#readme-top">back to top</a></p>

## Installation

1. **Klonen Sie das Repository**

   ```sh
   git clone https://github.com/Red05Jack/LBS-Eibiswald/tree/A7_Hangman/A7_Hangman.git
   ```

2. **Kompilieren Sie die Java-Dateien**

   Navigieren Sie in das Verzeichnis des Projekts und kompilieren Sie die Dateien:

   ```sh
   javac Main.java Hangman.java HangmanGUI.java
   ```

3. **Ausführen**

   Um die GUI-Version zu starten:

   ```sh
   java HangmanGUI
   ```

<p align="right"><a href="#readme-top">back to top</a></p>

## Dateien im Projekt

- **Main.java**: Übergibt das Spiel an die GUI und startet die grafische Benutzeroberfläche.
- **Hangman.java**: Enthält die Logik des Hangman-Spiels, einschließlich der Verwaltung des Worterratens und der Fehlerzählung.
- **HangmanGUI.java**: Implementiert die grafische Benutzeroberfläche des Hangman-Spiels mit Java Swing.
- **words.txt**: Enthält eine Liste von möglichen Wörtern, die im Spiel verwendet werden.

<p align="right"><a href="#readme-top">back to top</a></p>

## Beispiele für Wortliste

Das Projekt verwendet eine externe Datei (`words.txt`), die eine Liste von Wörtern enthält, die im Hangman-Spiel erraten werden sollen. Einige Beispiele aus der Datei sind:

- `rindfleischetikettierungsüberwachungsaufgabenübertragungsgesetz`
- `Umweltschutzorganisation`
- `Haftpflichtversicherung`
- `Kuddelmuddel`
- `Voodoopuppe`
- `Zwiebelsuppe`
- `Verbalkloake`

Die Datei enthält eine Mischung aus kurzen und langen Wörtern, um die Schwierigkeit des Spiels dynamisch zu halten.

<p align="right"><a href="#readme-top">back to top</a></p>

## Technologien

- **Programmiersprache**: Java
- **Grafische Benutzeroberfläche**: Swing

<p align="right"><a href="#readme-top">back to top</a></p>

## Autor

Dieses Projekt wurde von [Red05Jack](https://github.com/Red05Jack) erstellt. Das Projekt ist Teil einer Aufgabenstellung an der LBS Eibiswald.

<p align="right"><a href="#readme-top">back to top</a></p>

## Lizenz

Dieses Projekt steht unter der MIT-Lizenz. Weitere Informationen finden Sie in der [LICENSE](LICENSE)-Datei.

<p align="right"><a href="#readme-top">back to top</a></p>

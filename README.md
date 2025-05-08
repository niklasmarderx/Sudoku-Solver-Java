# Sudoku Solver

Ein Java-Programm zum Lösen von Sudoku-Rätseln mit einer Konsolenschnittstelle.

## Funktionen

- Lösen von 9x9 Sudoku-Rätseln mit dem Backtracking-Algorithmus
- Konsolenschnittstelle mit farbiger Ausgabe
- Möglichkeit, eigene Rätsel einzugeben
- Vordefiniertes Beispiel-Rätsel
- Farbliche Unterscheidung zwischen ursprünglichen und gelösten Zahlen

## Verwendung

1. Kompilieren Sie die Java-Datei:
   ```bash
   javac SudokuPuzzle.java
   ```

2. Führen Sie das Programm aus:
   ```bash
   java SudokuPuzzle
   ```

3. Wählen Sie eine Option:
   - Eigenes Rätsel eingeben
   - Beispiel-Rätsel lösen
   - Programm beenden

## Eingabeformat

Bei der Eingabe eines eigenen Rätsels:
- Geben Sie die Zahlen zeilenweise ein
- Verwenden Sie Leerzeichen als Trennzeichen
- Verwenden Sie 0 für leere Felder
- Beispiel: `7 0 0 0 0 0 2 0 0`

## Technische Details

- Implementierung des Backtracking-Algorithmus
- Validierung der Benutzereingaben
- Farbige Konsolenausgabe mit ANSI-Escape-Sequenzen
- Fehlerbehandlung für ungültige Eingaben


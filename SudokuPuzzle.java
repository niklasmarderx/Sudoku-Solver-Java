import java.util.Scanner;

/**
 * Sudoku Solver - Ein Programm zum Lösen von Sudoku-Rätseln
 * 
 * Dieses Programm verwendet den Backtracking-Algorithmus, um Sudoku-Rätsel zu lösen.
 * Es bietet eine benutzerfreundliche Konsolenschnittstelle mit farbiger Ausgabe
 * und der Möglichkeit, eigene Rätsel einzugeben.
 */
public class SudokuPuzzle {
    // ANSI-Farbcodes für die Konsolenausgabe
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * Überprüft, ob es sicher ist, die Zahl 'n' in der Zelle an Position [r][c] zu platzieren
     * @param board Das Sudoku-Spielfeld
     * @param r Zeilenindex
     * @param c Spaltenindex
     * @param n Die zu platzierende Zahl
     * @return true, wenn die Platzierung sicher ist, sonst false
     */
    public boolean isSafe(int[][] board, int r, int c, int n) {
        // Überprüfe Konflikte in der Zeile
        for (int d = 0; d < board.length; d++) {
            if (board[r][d] == n) {
                return false;
            }
        }

        // Überprüfe Konflikte in der Spalte
        for (int r1 = 0; r1 < board.length; r1++) {
            if (board[r1][c] == n) {
                return false;
            }
        }

        // Überprüfe Konflikte im 3x3 Block
        int sqrt = (int) Math.sqrt(board.length);
        int boxRowStart = r - r % sqrt;
        int boxColStart = c - c % sqrt;

        for (int r1 = boxRowStart; r1 < boxRowStart + sqrt; r1++) {
            for (int d = boxColStart; d < boxColStart + sqrt; d++) {
                if (board[r1][d] == n) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Löst das Sudoku-Rätsel mit dem Backtracking-Algorithmus
     * @param board Das Sudoku-Spielfeld
     * @param num Die Größe des Spielfelds (normalerweise 9)
     * @return true, wenn eine Lösung gefunden wurde, sonst false
     */
    public boolean solveSudoku(int[][] board, int num) {
        int r = -1;
        int c = -1;
        boolean isVacant = true;

        // Finde ein leeres Feld
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (board[i][j] == 0) {
                    r = i;
                    c = j;
                    isVacant = false;
                    break;
                }
            }
            if (!isVacant) {
                break;
            }
        }

        // Wenn kein leeres Feld gefunden wurde, ist das Rätsel gelöst
        if (isVacant) {
            return true;
        }

        // Versuche Zahlen von 1 bis num in der Zelle zu platzieren
        for (int no = 1; no <= num; no++) {
            if (isSafe(board, r, c, no)) {
                board[r][c] = no;
                if (solveSudoku(board, num)) {
                    return true;
                } else {
                    board[r][c] = 0; // Backtracking
                }
            }
        }
        return false; // Keine Lösung für die aktuelle Konfiguration gefunden
    }

    /**
     * Zeigt das Sudoku-Spielfeld mit verbesserter Formatierung an
     * @param board Das aktuelle Spielfeld
     * @param n Die Größe des Spielfelds
     * @param originalBoard Das ursprüngliche Spielfeld (für farbliche Hervorhebung)
     */
    public void display(int[][] board, int n, int[][] originalBoard) {
        int sqrt = (int) Math.sqrt(n);
        System.out.println("\n" + ANSI_BLUE + "╔═══╤═══╤═══╦═══╤═══╤═══╦═══╤═══╤═══╗" + ANSI_RESET);
        
        for (int i = 0; i < n; i++) {
            System.out.print(ANSI_BLUE + "║" + ANSI_RESET);
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    System.out.print("   ");
                } else {
                    // Farbe basierend darauf, ob es eine ursprüngliche Zahl ist
                    String color = (board[i][j] == originalBoard[i][j]) ? ANSI_RED : ANSI_GREEN;
                    System.out.print(color + " " + board[i][j] + " " + ANSI_RESET);
                }
                
                if ((j + 1) % sqrt == 0 && j < n - 1) {
                    System.out.print(ANSI_BLUE + "║" + ANSI_RESET);
                } else if (j < n - 1) {
                    System.out.print("│");
                }
            }
            System.out.println(ANSI_BLUE + "║" + ANSI_RESET);
            
            if ((i + 1) % sqrt == 0 && i < n - 1) {
                System.out.println(ANSI_BLUE + "╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣" + ANSI_RESET);
            } else if (i < n - 1) {
                System.out.println(ANSI_BLUE + "╟───┼───┼───╫───┼───┼───╫───┼───┼───╢" + ANSI_RESET);
            }
        }
        System.out.println(ANSI_BLUE + "╚═══╧═══╧═══╩═══╧═══╧═══╩═══╧═══╧═══╝" + ANSI_RESET);
    }

    /**
     * Liest ein Sudoku-Rätsel von der Benutzereingabe ein
     * @return Das eingegebene Sudoku-Spielfeld
     */
    public int[][] readSudokuFromUser() {
        Scanner scanner = new Scanner(System.in);
        int[][] board = new int[9][9];
        
        System.out.println(ANSI_YELLOW + "\nBitte geben Sie das Sudoku-Rätsel ein (0 für leere Felder):" + ANSI_RESET);
        System.out.println("Geben Sie die Zahlen zeilenweise ein, getrennt durch Leerzeichen.");
        System.out.println("Beispiel: 7 0 0 0 0 0 2 0 0");
        
        for (int i = 0; i < 9; i++) {
            System.out.print(ANSI_YELLOW + "Zeile " + (i + 1) + ": " + ANSI_RESET);
            String[] input = scanner.nextLine().trim().split("\\s+");
            
            if (input.length != 9) {
                System.out.println(ANSI_RED + "Fehler: Bitte geben Sie genau 9 Zahlen ein!" + ANSI_RESET);
                i--; // Wiederhole diese Zeile
                continue;
            }
            
            try {
                for (int j = 0; j < 9; j++) {
                    int num = Integer.parseInt(input[j]);
                    if (num < 0 || num > 9) {
                        throw new NumberFormatException();
                    }
                    board[i][j] = num;
                }
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Fehler: Bitte geben Sie nur Zahlen zwischen 0 und 9 ein!" + ANSI_RESET);
                i--; // Wiederhole diese Zeile
            }
        }
        
        return board;
    }

    public static void main(String[] args) {
        SudokuPuzzle solver = new SudokuPuzzle();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println(ANSI_YELLOW + "\n=== Sudoku Solver ===" + ANSI_RESET);
            System.out.println("1. Eigenes Rätsel eingeben");
            System.out.println("2. Beispiel-Rätsel lösen");
            System.out.println("3. Beenden");
            System.out.print(ANSI_YELLOW + "Ihre Auswahl (1-3): " + ANSI_RESET);
            
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Bitte geben Sie eine gültige Zahl ein!" + ANSI_RESET);
                continue;
            }
            
            int[][] board;
            switch (choice) {
                case 1:
                    board = solver.readSudokuFromUser();
                    break;
                case 2:
                    board = new int[][] {
                        {7, 0, 0, 0, 0, 0, 2, 0, 0},
                        {4, 0, 2, 0, 0, 0, 0, 0, 3},
                        {0, 0, 0, 2, 0, 1, 0, 0, 0},
                        {3, 0, 0, 1, 8, 0, 0, 9, 7},
                        {0, 0, 9, 0, 7, 0, 6, 0, 0},
                        {6, 5, 0, 0, 3, 2, 0, 0, 1},
                        {0, 0, 0, 4, 0, 9, 0, 0, 0},
                        {5, 0, 0, 0, 0, 0, 1, 0, 6},
                        {0, 0, 6, 0, 0, 0, 0, 0, 8}
                    };
                    break;
                case 3:
                    System.out.println(ANSI_GREEN + "\nAuf Wiedersehen!" + ANSI_RESET);
                    return;
                default:
                    System.out.println(ANSI_RED + "Ungültige Auswahl!" + ANSI_RESET);
                    continue;
            }
            
            // Kopie des Original-Rätsels erstellen
            int[][] originalBoard = new int[9][9];
            for (int i = 0; i < 9; i++) {
                System.arraycopy(board[i], 0, originalBoard[i], 0, 9);
            }
            
            System.out.println(ANSI_YELLOW + "\nDas eingegebene Rätsel:" + ANSI_RESET);
            solver.display(board, 9, originalBoard);
            
            if (solver.solveSudoku(board, 9)) {
                System.out.println(ANSI_GREEN + "\nDie Lösung:" + ANSI_RESET);
                solver.display(board, 9, originalBoard);
            } else {
                System.out.println(ANSI_RED + "\nKeine Lösung gefunden!" + ANSI_RESET);
            }
        }
    }
}

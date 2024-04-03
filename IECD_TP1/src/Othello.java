import java.util.Scanner;

public class Othello {
    private static final int BOARD_SIZE = 8;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'W';
    private static final char PLAYER2 = 'B';

    private char[][] board;
    private char currentPlayer;

    private String player1_name;
    private String player2_name;


    public Othello() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
        currentPlayer = PLAYER1;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        // Starting pieces
        board[3][3] = board[4][4] = PLAYER1;
        board[3][4] = board[4][3] = PLAYER2;
    }

    private void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean isValidMove(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE || board[row][col] != EMPTY) {
            return false;
        }
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int r = row + dr;
                int c = col + dc;
                while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == currentPlayer) {
                    r += dr;
                    c += dc;
                }
                if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == getOpponent()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void makeMove(int row, int col) {
        if (!isValidMove(row, col)) {
            System.out.println("Invalid move. Try again.");
            return;
        }
        board[row][col] = currentPlayer;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int r = row + dr;
                int c = col + dc;
                while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == getOpponent()) {
                    r += dr;
                    c += dc;
                }
                if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == currentPlayer) {
                    r -= dr;
                    c -= dc;
                    while (r != row || c != col) {
                        board[r][c] = currentPlayer;
                        r -= dr;
                        c -= dc;
                    }
                }
            }
        }
        currentPlayer = getOpponent();
    }

    private char getOpponent() {
        return currentPlayer == PLAYER1 ? PLAYER2 : PLAYER1;
    }

    private boolean isGameOver() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isValidMove(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printWinner() {
        int player1Count = 0;
        int player2Count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == PLAYER1) {
                    player1Count++;
                } else if (board[i][j] == PLAYER2) {
                    player2Count++;
                }
            }
        }
        if (player1Count > player2Count) {
            System.out.println("Player 1 wins!");
        } else if (player1Count < player2Count) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insira o nome do jogador 1: ");
        player1_name = scanner.next();
        System.out.print("Insira o nome do jogador 2: ");
        player2_name = scanner.next();
        while (!isGameOver()) {
            printBoard();
            if (currentPlayer == 'W') {
                System.out.println("Turno de " + player1_name + " W");
            }
            else if (currentPlayer == 'B') {
                System.out.println("Turno de " + player2_name + " B");
            }
            System.out.print("Insira linha e coluna (e.g., 2 3): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            makeMove(row, col);
        }
        printBoard();
        printWinner();
        scanner.close();
    }

    public static void main(String[] args) {
        Othello game = new Othello();
        game.play();
    }
}

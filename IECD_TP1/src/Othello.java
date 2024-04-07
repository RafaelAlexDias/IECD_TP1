
import java.util.Scanner;

public class Othello {
    private static final int BOARD_SIZE = 8;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'B';
    private static final char PLAYER2 = 'W';

    private final char[][] board;
    private char currentPlayer;

    private Player player1;
    private Player player2;


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
        board[3][3] = board[4][4] = PLAYER2;
        board[3][4] = board[4][3] = PLAYER1;
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
        // Check in all eight directions
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = col + dc;
                boolean hasOpponentBetween = false;

                // Check if there is at least one opponent's disc in this direction
                while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == getOpponent()) {
                    r += dr;
                    c += dc;
                    hasOpponentBetween = true;
                }

                // If we encountered at least one opponent's disc and reached our disc again, it's a valid move
                if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == currentPlayer && hasOpponentBetween) {
                    return true;
                }
            }
        }
        return false;
    }

    private void makeMove(int row, int col) {
        if (!isValidMove(row, col)) {
            System.out.println("Movimento inválido. Tente novamente.");
            return;
        }
        board[row][col] = currentPlayer;

        // Flip discs in all eight directions
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = col + dc;
                boolean hasOpponentBetween = false;

                // Check if there is at least one opponent's disc in this direction
                while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == getOpponent()) {
                    r += dr;
                    c += dc;
                    hasOpponentBetween = true;
                }

                // If we encountered at least one opponent's disc and reached our disc again, flip discs in between
                if (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == currentPlayer && hasOpponentBetween) {
                    r -= dr;
                    c -= dc;
                    while (board[r][c] == getOpponent()) {
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
            player1.setWinCount(player1.getWinCount(player1) + 1);
        } else if (player1Count < player2Count) {
            System.out.println("Player 2 wins!");
            player2.setWinCount(player2.getWinCount(player2) + 1);
        } else {
            System.out.println("It's a tie!");
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Faça o login do primeiro jogador ");
        player1 = LoginPlayer.loginPlayer();
        System.out.println("Faça o login do segundo jogador ");
        player2 = LoginPlayer.loginPlayer();

        while (!isGameOver()) {
            printBoard();
            if (currentPlayer == PLAYER1) {
                System.out.println("Turno de " + player1.getName() + " (B)");
                player1.setWinCount(player1.getWinCount(player1) + 1);
                Database.updateWins(player1, player1.getWinCount(player1));
            } else {
                System.out.println("Turno de " + player2.getName() + " (W)");
                player2.setWinCount(player2.getWinCount(player2) + 1);
                Database.updateWins(player2, player2.getWinCount(player2));
            }
            System.out.print("Insira linha e coluna (e.g., 2 3): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if (isValidMove(row, col)) {
                makeMove(row, col);
            } else {
                System.out.println("Movimento inválido. Tente novamente.");
            }
        }
        printBoard();
        printWinner();
    }

}

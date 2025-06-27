package game;

import java.util.Scanner;

public class TicTacToeGame {
    private GameSettings settings;
    private Scanner scanner;
    private StatisticsManager statisticsManager;

    public TicTacToeGame(GameSettings settings, Scanner scanner, StatisticsManager statisticsManager) {
        this.settings = settings;
        this.scanner = scanner;
        this.statisticsManager = statisticsManager;
    }

    public void playGame() {
        int boardSize = settings.getBoardSize();
        int rows = boardSize * 2 + 1;
        int cols = boardSize * 4 - 1;
        char[][] board = createGameBoard(rows, cols);
        char currentPlayer = 'X';
        boolean isGameOver = false;
        String winner = null;
        String currentPlayerName = settings.getPlayer1Name();

        while (!isGameOver) {
            System.out.println("\nГрають: " + currentPlayerName + " (" + currentPlayer + ")");
            displayBoard(board);

            int[] move = getPlayerMove(board);
            if (move[0] == 0) {
                break;
            }

            int displayRow = (move[0] - 1) * 2 + 2;
            int displayCol = (move[1] - 1) * 4 + 2;
            board[displayRow][displayCol] = currentPlayer;

            if (checkWin(board, currentPlayer, rows, cols)) {
                System.out.println("Переміг " + currentPlayerName + " (" + currentPlayer + ") !!!");
                winner = currentPlayerName;
                isGameOver = true;
            } else if (checkDraw(board, rows, cols)) {
                System.out.println("Нічия");
                winner = "Draw";
                isGameOver = true;
            }

            if (currentPlayer == 'X') {
                currentPlayer = 'O';
                currentPlayerName = settings.getPlayer2Name();
            } else {
                currentPlayer = 'X';
                currentPlayerName = settings.getPlayer1Name();
            }
        }

        System.out.println("\nДошка зараз така:");
        displayBoard(board);

        if (winner != null) {
            statisticsManager.saveGameStatistics(settings, winner);
        }
    }

    private char[][] createGameBoard(int rows, int cols) {
        char[][] displayBoard = new char[rows][cols];
        int boardSize = settings.getBoardSize();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                displayBoard[i][j] = ' ';
            }
        }

        for (int i = 0; i < boardSize; i++) {
            displayBoard[0][i * 4 + 2] = (char) ('1' + i);
            displayBoard[i * 2 + 2][0] = (char) ('1' + i);
        }

        for (int i = 1; i < rows; i += 2) {
            for (int j = 1; j < cols; j++) {
                displayBoard[i][j] = '-';
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 4; j < cols; j += 4) {
                displayBoard[i][j - 1] = '|';
            }
        }

        return displayBoard;
    }

    private void displayBoard(char[][] board) {
        for (char[] rowArray : board) {
            System.out.println(rowArray);
        }
    }

    private boolean isValidMove(int row, int col, char[][] board) {
        int boardSize = settings.getBoardSize();
        return row >= 1 && row <= boardSize &&
                col >= 1 && col <= boardSize &&
                board[(row - 1) * 2 + 2][(col - 1) * 4 + 2] == ' ';
    }

    private boolean checkWin(char[][] board, char igrok, int rows, int cols) {
        int boardSize = settings.getBoardSize();

        // Перевірка рядків
        for (int i = 2; i < rows; i += 2) {
            int count = 0;
            for (int j = 2; j < cols; j += 4) {
                if (board[i][j] == igrok) {
                    count++;
                    if (count == boardSize) return true;
                } else {
                    count = 0;
                }
            }
        }

        // Перевірка стовпців
        for (int j = 2; j < cols; j += 4) {
            int count = 0;
            for (int i = 2; i < rows; i += 2) {
                if (board[i][j] == igrok) {
                    count++;
                    if (count == boardSize) return true;
                } else {
                    count = 0;
                }
            }
        }

        // Перевірка головної діагоналі
        int count = 0;
        for (int i = 0; i < boardSize; i++) {
            if (board[i * 2 + 2][i * 4 + 2] == igrok) {
                count++;
                if (count == boardSize) return true;
            } else {
                count = 0;
            }
        }

        // Перевірка побічної діагоналі
        count = 0;
        for (int i = 0; i < boardSize; i++) {
            if (board[i * 2 + 2][(boardSize - 1 - i) * 4 + 2] == igrok) {
                count++;
                if (count == boardSize) return true;
            } else {
                count = 0;
            }
        }

        return false;
    }

    private boolean checkDraw(char[][] board, int rows, int cols) {
        for (int i = 2; i < rows; i += 2) {
            for (int j = 2; j < cols; j += 4) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    private int[] getPlayerMove(char[][] board) {
        int boardSize = settings.getBoardSize();

        while (true) {
            System.out.println("Ряд (1-" + boardSize + ", або 0 щоб вийти):");
            String input = scanner.nextLine();
            if (input.isEmpty()) continue;
            int row = input.charAt(0) - '0';

            if (row == 0) return new int[]{0, 0};

            System.out.println("Колонка (1-" + boardSize + "):");
            input = scanner.nextLine();
            if (input.isEmpty()) continue;
            int col = input.charAt(0) - '0';

            if (isValidMove(row, col, board)) {
                return new int[]{row, col};
            }
            System.out.println("Спробуйте ще раз.");
        }
    }
}
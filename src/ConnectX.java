import java.util.Scanner;
import java.util.Arrays;

public class ConnectX {
    private char[][] board;
    private String[] players;
    private char currentPlayer;
    private int winLength;
    private int rows;
    private int cols;
    private Scanner scanner;

    public ConnectX() {
        scanner = new Scanner(System.in);
        gameSetup();
    }

    // Called from constructor or from reset to initialize a new game
    private void gameSetup() {

        // Prompt the user until a valid number of rows is entered
        while (true) {
            System.out.print("Enter number of rows: ");
            try {
                rows = scanner.nextInt();
                if (rows > 0) {
                    break;
                } else {
                    System.out.println("Number of rows must be positive.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        // Prompt the user until a valid number of columns is entered
        while (true) {
            System.out.print("Enter number of columns: ");
            try {
                cols = scanner.nextInt();
                if (cols > 0) {
                    break;
                } else {
                    System.out.println("Number of columns must be positive.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        // Prompt the user until a valid win length is entered
        while (true) {
            System.out.print("Enter win length: ");
            try {
                winLength = scanner.nextInt();
                if (winLength < 2) {
                    System.out.println("Win length must be at least 2.");
                } else if (winLength > rows || winLength > cols) {
                    System.out.println("Win length cannot exceed the number of rows or columns.");

                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        // Prompt the user until a valid number of players is entered
        while (true) {
            System.out.print("Enter number of players: ");
            try {
                int numPlayers = scanner.nextInt();
                if (numPlayers < 2) {
                    System.out.println("Number of players must be at least 2.");
                } else {
                    scanner.nextLine();
                    players = new String[numPlayers];
                    //  Prompts users for each player's name
                    for (int i = 0; i < numPlayers; i++) {
                        while (true) {
                            System.out.print("Enter name of player " + (i + 1) + ": ");
                            String name = scanner.nextLine().trim();
                            if (!name.isEmpty()) {
                                // Ensure the player name is unique among the already entered names
                                if (Arrays.asList(players).contains(name)) {
                                    System.out.println("Player name must be unique.");
                                    continue;
                                } else {
                                    players[i] = name;
                                    break;
                                }
                            } else {
                                System.out.println("Player name cannot be empty.");
                            }                     
                        }
                    }
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        board = new char[rows][cols];
        // Initialize the board with empty spaces
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = '.';
            }
        }
        System.out.println("Game setup complete. Starting game...");
        gameLoop();
    }

    private void gameLoop() {
    }

    // Method to print the game board whenever needed, including row and column numbers
    private void printBoard() {
        for (int i = 0; i < rows; i++) {
            System.out.print("| ");
            for (int j = 0; j < cols; j++) {
                System.out.print(String.valueOf(board[i][j]) + " ");
            }
            System.out.println("|");
        }
    }

    // Test space
    public static void main(String[] args) {
        ConnectX game = new ConnectX();
        game.printBoard();
    }
}

import java.util.Scanner;
import java.util.Arrays;

public class ConnectX {
    private String[][] board;
    private String[] players;
    private int winLength;
    private int rows;
    private int cols;
    private Scanner scanner;

    public ConnectX() {
        scanner = new Scanner(System.in);
        gameSetup();
        gameControl();
    }

    // Main game loop and control logic
    private void gameControl() {
        while (true) {
            int result = gameLoop();
            if (result == -2) {
                continue;
            } else {
                boolean reset = winnerCircle(result);
                if (reset) {
                    continue;
                } else {
                    break;
                }
            }
        }
    }



    // Called from constructor or from reset to initialize a new game
    private void gameSetup() {

        // Prompt the user until a valid number of rows is entered (greater than 1)
        while (true) {
            System.out.print("Enter number of rows: ");
            try {
                rows = scanner.nextInt();
                if (rows > 1) {
                    break;
                } else {
                    System.out.println("Number of rows must be greater than 1.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        // Prompt the user until a valid number of columns is entered (greater than 1)
        while (true) {
            System.out.print("Enter number of columns: ");
            try {
                cols = scanner.nextInt();
                if (cols > 1) {
                    break;
                } else {
                    System.out.println("Number of columns must be greater than 1.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        // Prompt the user until a valid win length is entered (at least 2 and not exceeding rows or columns)
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
        // Prompt the user until a valid number of players is entered (at least 2)
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
                                } else if (name.length() > 1) {
                                    System.out.println("Player name cannot exceed 1 character.");
                                    continue;
                                } else if (name.equals(".")){
                                    System.out.println("Player name cannot be a period.");
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
        System.out.println("Game setup complete. Starting game...");
        return;
    }

    private int gameLoop() {
        board = new String[rows][cols];
        // Initialize the board with empty spaces
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ".";
            }
        }
        while (true) {
            for (int i = 0; i < players.length; i++) {
                printBoard();
                System.out.println(String.valueOf(players[i]) + "'s turn");
                while (true) {
                    System.out.print("Enter column number for your move: ");
                    String input = scanner.nextLine().trim();
                    //returns to setup if the user enters "restart"
                    if (input.equalsIgnoreCase("restart")) {
                        System.out.println("Restarting game...");
                        gameSetup();
                        return -2;
                    // exits the game if the user enters "exit"
                    } else if (input.equalsIgnoreCase("reset")) {
                        System.out.println("Resetting game...");
                        gameSetup();
                        return -2;
                    } else if (input.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting game...");
                        System.exit(0);
                    }
                    try {
                        int col = Integer.parseInt(input);
                        if (checkLegalMove(col)) {
                            // Make the move on the board
                            // for loop finds lowest empty row to place token
                            for (int row = rows - 1; row >= 0; row--) {
                                if (board[row][col].equals(".")) {
                                    board[row][col] = players[i];
                                    int result = checkEnd(i);
                                    if (result == -2) {
                                        break; // When valid move is made, break out of locator for loop.
                                    } else {
                                        return result; // When win or draw, return result to GameControl
                                    }
                                }
                            }
                            break; // Exit the inner while loop after a valid move
                        } else {
                            System.out.println("Invalid move. Please try again.");
                        }
                        
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                
                }
            }
        }
    }

    // Method to print the game board whenever needed, including row and column numbers
    private void printBoard() {
        for (int i = 0; i < rows; i++) {
            System.out.print("| ");
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
    }

    // Method to check if a move is legal
    // illegal moves: full column or out of bounds
    private boolean checkLegalMove(int col) {
        if (col < 0 || col >= cols) {
            return false;
        }
        for (int row = 0; row < rows; row++) {
            if (board[row][col].equals(".")) {
                return true;
            }
        }
        return false;
    }

    // Method to check if the game has ended in a win or draw, called after each move
    // Returns true if the game has ended in a win or if the board is full, false otherwise
    // When game ends, calls winnerCircle, sends int currentPlayer as the winner, -1 if draw
    private int checkEnd(int currentPlayer) {
        // Checks for wins horizontally
        for (int row = 0; row < rows; row++) {
            // For each row, checks each possible horizontal sequence of winLength cells
            for (int col = 0; col <= (cols - winLength); col++) {
                // If any cell in the current sequence does not belong to the current player, 
                // the current player has not won, continues to next sequence
                boolean win = true;
                for (int k = 0; k < winLength; k++) {
                    if (!board[row][col + k].equals(players[currentPlayer])) {
                        win = false;
                        break;
                    }
                }
                // If all cells belong to the current player in the sequence, the current player has won
                // calls winnerCircle with the current player as the winner to end the game
                if (win) {
                    return currentPlayer;
                }
            }
        }

        // Checks for wins vertically
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row <= (rows - winLength); row++) {
                boolean win = true;
                for (int k = 0; k < winLength; k++) {
                    if (!board[row + k][col].equals(players[currentPlayer])) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return currentPlayer;
                }
            }
        }

        // Checks for wins diagonally, down-right
        for (int row = 0; row <= (rows - winLength); row++) {
            for (int col = 0; col <= (cols - winLength); col++) {
                boolean win = true;
                for (int k = 0; k < winLength; k++) {
                    if (!board[row + k][col + k].equals(players[currentPlayer])) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return currentPlayer;
                }
            }
        }

        // Checks for wins diagonally, down-left
        for (int row = 0; row <= (rows - winLength); row++) {
            for (int col = winLength - 1; col < cols; col++) {
                boolean win = true;
                for (int k = 0; k < winLength; k++) {
                    if (!board[row + k][col - k].equals(players[currentPlayer])) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return currentPlayer;
                }
            }
        }

        // If no wins found, checks if the board is full for a draw
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col].equals(".")) {
                    return -2;
                }
            }
        }
        return -1;
    }

    // Handles the game outcome and offers to play again
    public boolean winnerCircle(int winner) {
        if (winner == -1) {
            System.out.println("The game ended in a draw!");
            System.out.println("Final board:");
            printBoard();
        } else {
            System.out.println("Player " + players[winner] + " has won!");
            System.out.println("Final board:");
            printBoard();
        }
        while (true) {
            System.out.println("Play again? Y/N");
            String response = scanner.nextLine().trim();
            if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
                // Reset the game state for a new game
                System.out.println("Return to setup?");
                String response2 = scanner.nextLine().trim();
                if (response2.equalsIgnoreCase("yes") || response2.equalsIgnoreCase("y")) {
                    // Return to setup
                    System.out.println("Returning to setup...");
                    gameSetup();
                    return true;
                } else if (response2.equalsIgnoreCase("no") || response2.equalsIgnoreCase("n")) {
                    System.out.println("Starting new game...");
                    return true;
                } else {
                    System.out.println("Invalid response. Please enter yes/no.");
                }
            } else if (response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")) {
                System.out.println("Thanks for playing!");
                return false;
            } else {
                System.out.println("Invalid response. Please enter yes/no.");
            }
        }
    }

    // Test space
    public static void main(String[] args) {
        ConnectX game = new ConnectX();
    }
}

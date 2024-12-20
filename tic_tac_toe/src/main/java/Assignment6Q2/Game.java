package Assignment6Q2;

import java.util.Random;
import java.util.Scanner;

abstract class Player {
    protected Board board;
    protected Counter counter;

    public Player(Board board) {
        this.board = board;
    }

    public Counter getCounter() {
        return this.counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public abstract Move getMove();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.counter + "]";
    }
}

class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer(Board board) {
        super(board);
        this.scanner = new Scanner(System.in);
    }

    public int[] getUserInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            String[] parts = input.split(",");
            if (parts.length == 2) {
                try {
                    int row = Integer.parseInt(parts[0].trim()) - 1;
                    int column = Integer.parseInt(parts[1].trim()) - 1;

                    if (row >= 0 && row < board.getRows() && column >= 0 && column < board.getColumns()) {
                        return new int[]{row, column};
                    } else {
                        System.out.println("Please enter values within the within the row and column dimensions");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter two numbers separated by a comma.");
                }
            } else {
                System.out.println("Invalid format. Please enter row and column separated by a comma.");
            }
        }
    }

    @Override
    public Move getMove() {
        while (true) {
            int[] position = getUserInput("Please enter the row and column: ");
            int row = position[0];
            int column = position[1];
            if (board.isEmpty(row, column)) {
                return new Move(counter, row, column);
            } else {
                System.out.println("This position is already filled up. Please choose a different one");
            }
        }
    }
}

class ComputerPlayer extends Player {
    private Random random;

    public ComputerPlayer(Board board) {
        super(board);
        this.random = new Random();
    }

    @Override
    public Move getMove() {
        while (true) {
            int row = random.nextInt(board.getRows());
            int column = random.nextInt(board.getColumns());
            if (board.isEmpty(row, column)) {
                return new Move(counter, row, column);
            }
        }
    }
}

class Counter {
    private String label;

    public Counter(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}

class Move {
    public Counter counter;
    public int x, y;

    public Move(Counter counter, int x, int y) {
        this.counter = counter;
        this.x = x;
        this.y = y;
    }
}

class Board {
    private String[][] board;
    private String separator;
    private int rows;
    private int columns;
    private int winLength;

    public Board(int rows, int columns, int winLength) {
        this.rows = rows;
        this.columns = columns;
        this.winLength = winLength;
        board = new String[rows][columns];
        separator = "\n-----\n";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = " ";
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isEmpty(int row, int column) {
        return board[row][column].equals(" ");
    }

    public boolean isFull() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (isEmpty(row, column)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addMove(Move move) {
        board[move.x][move.y] = move.counter.toString();
    }

    public boolean checkForWinner(Player player) {
        String c = player.getCounter().toString();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (board[row][column].equals(c)) {
                    if (checkDirection(row, column, 0, 1, c)) return true; // Horizontal
                    if (checkDirection(row, column, 1, 0, c)) return true; // Vertical
                    if (checkDirection(row, column, 1, 1, c)) return true; // Diagonal \
                    if (checkDirection(row, column, 1, -1, c)) return true; // Diagonal /
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int column, int deltaRow, int deltaColumn, String c) {
        int count = 0;
        for (int i = 0; i < winLength; i++) {
            int r = row + i * deltaRow;
            int col = column + i * deltaColumn;
            if (r >= 0 && r < rows && col >= 0 && col < columns && board[r][col].equals(c)) {
                count++;
            } else {
                break;
            }
        }
        return count == winLength;
    }

    @Override
    public String toString() {
        StringBuilder modifiable_string = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                modifiable_string.append(board[i][j]);
                if (j < columns - 1) modifiable_string.append("|");
            }
            if (i < rows - 1) modifiable_string.append("\n-----\n");
        }
        return modifiable_string.toString();
    }
}

class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player nextPlayer;
    private Player winner;
    private boolean isTwoPlayer;
    private boolean isComputerVsComputer;

    public Game(int gridSize, int winLength, int gameMode) {
        board = new Board(gridSize, gridSize, winLength);
        // Set players based on the selected game mode
        if (gameMode == 1) {  // Player vs Player
            isTwoPlayer = true;
            player1 = new HumanPlayer(board);
            player2 = new HumanPlayer(board);
        } else if (gameMode == 2) {  // Player vs Computer
            isTwoPlayer = false;
            player1 = new HumanPlayer(board);
            player2 = new ComputerPlayer(board);
        } else {  // Computer vs Computer
            isTwoPlayer = false;
            player1 = new ComputerPlayer(board);
            player2 = new ComputerPlayer(board);
        }
    }

    public void selectPlayerCounter() {
        Scanner scanner = new Scanner(System.in);
        String counter;
        while (true) {
            System.out.println("Please select either 'X' or 'O' for Player 1: ");
            counter = scanner.nextLine().toUpperCase();
            if (counter.equals("X") || counter.equals("O")) {
                break;
            } else {
                System.out.println("Please select 'X' or 'O' only.");
            }
        }
        if (counter.equals("X")) {
            player1.setCounter(new Counter("X"));
            player2.setCounter(new Counter("O"));
        } else {
            player1.setCounter(new Counter("O"));
            player2.setCounter(new Counter("X"));
        }
    }

    public void playerToGoFirst() {
        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            nextPlayer = player1;
        } else {
            nextPlayer = player2;
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic Tac Toe!");

        do {
            board = new Board(board.getRows(), board.getColumns(), board.getRows()); // Reset the board
            selectPlayerCounter();
            playerToGoFirst();
            winner = null; // Reset winner
            System.out.println("Player " + nextPlayer + " will go first.");

            while (winner == null) {
                System.out.println(nextPlayer + "'s turn:");
                Move move = nextPlayer.getMove();
                board.addMove(move);
                System.out.println(board);
                if (board.checkForWinner(nextPlayer)) {
                    winner = nextPlayer;
                } else if (board.isFull()) {
                    System.out.println("The game is a draw!");
                    break;
                } else {
                    nextPlayer = (nextPlayer == player1) ? player2 : player1;
                }
            }

            if (winner != null) {
                System.out.println("The winner is " + winner + "!");
            }

            // Ask the player if they want to play again
            System.out.print("Do you want to play again? (yes/no): ");
            String playAgain = scanner.nextLine();
            if (!playAgain.equalsIgnoreCase("yes")) {
                break; // Exit the loop and end the game
            }

        } while (true); // Repeat the game loop if the player chooses to play again
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int gridSize;
        while (true) {
            System.out.print("Enter grid size (between 3 and 20): ");
            gridSize = scanner.nextInt();
            if (gridSize >= 3 && gridSize <= 20) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a grid size between 3 and 20");
            }
        }

        int winLength;
        System.out.print("Enter win length: ");
        winLength = scanner.nextInt();

        System.out.println("Select game mode:");
        System.out.println("1: Player vs Player");
        System.out.println("2: Player vs Computer");
        System.out.println("3: Computer vs Computer");
        int gameMode = scanner.nextInt();

        Game game = new Game(gridSize, winLength, gameMode);
        game.play();
    }
}


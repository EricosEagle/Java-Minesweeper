// <IMPORT>

import java.util.*; //Imports Java's I/O and utility class

// </IMPORT>


/**
 * Main.class
 * <p>
 * MineSweeper Game
 * <p>
 * 
 * @author Eric G.D
 * @version 1.1
 * @since 1.0	
 */
public class Main // Start of Main class

// <PUBLIC CLASS>

{

	public static Scanner scan = new Scanner(System.in);

	// <FUNCTIONS>

	/**
	 * The main method of the class
	 * <p>
	 *
	 * @param args	not used
	 */
	public static void main(String[] args) {
		final int[] d = dimensionInput();
		Cell[][] board = new Cell[d[0]][d[1]];
		realBoardMaker(board, d[2]);
		while(!allFound(board)) 
			turn(board);
		endGame(board, "Win");
	}
	
	
	/**
	 * Input function for user's requested dimensions
	 * @return	An array, 0 = ROWS, 1 = COLUMNS, 2 = MINES
	 */
	public static int[] dimensionInput() {
		int[] arr = new int[3];
		System.out.println("Enter the number of rows in the board: ");
		arr[0] = scan.nextInt();
		while(arr[0] <= 1 || arr[0] > 100) {
			System.out.println("Invalid value, please enter again. ");
			arr[0] = scan.nextInt();
		}
		System.out.println("Enter the number of columns in the board: ");
		arr[1] = scan.nextInt();
		while(arr[1] <= 1 || arr[1] > 100) {
			System.out.println("Invalid value, please enter again. ");
			arr[1] = scan.nextInt();
		}
		System.out.println("Enter the number of bombs: ");
		arr[2] = scan.nextInt();
		while(arr[2] <= 1 || arr[2] >= arr[0] * arr[1]) {
			System.out.println("Invalid value, please enter again. ");
			arr[2] = scan.nextInt();
		}
		return arr;
	}
	
	/**
	 * Fills board with default cell objects
	 * @param b	The board
	 */
	public static void boardInit(Cell[][] b) {
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b[0].length; j++) {
				b[i][j] = new Cell();
			}
		}
	}
	
	/**
	 * Initializes the real board based on user input
	 * @param board	The game board
	 * @param mines	Number of mines to put in board
	 */
	public static void realBoardMaker(Cell[][] board, int mines) {
		boardInit(board);
		mineSetter(board, mines);
		numberSetter(board);
	}
	
	/**
	 * Places mines on the board
	 * @param board	The game board
	 * @param mines	The number of requested mines
	 */
	public static void mineSetter(Cell[][] board, int mines) {
		Random rnd = new Random();
		while(mines > 0) {
			int i = rnd.nextInt(board.length), j = rnd.nextInt(board[0].length);
			/*if(!bombsNear(board, i, j)) {} */ //Used so there won't be a cluster of bombs on the board
			if(board[i][j].getReal() == 9) continue;
			board[i][j].setReal(9);
			mines--;
		}
	}
	
	/**
	 * Sets numbers on the board
	 * @param board	The game board
	 */
	public static void numberSetter(Cell[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j].getReal() == 9) continue;
				board[i][j].setReal(bombCounter(board, i, j));
			}
		}
	}
	
	/**
	 * @param board	The game board
	 * @param i	Row index
	 * @param j	Column index
	 * @return	Are there any neighboring bombs near board[i][j]?
	 */
	@Deprecated
	public static boolean bombsNear(Cell[][] board, int i, int j) {
		for(int k = -1; k <= 1; k++) {
			if(i + k < 0 || i + k > board.length - 1) continue;
			for(int l = -1; l <= 1; l++) {
				if(k == 0 && l == 0) continue;
				if(j + l < 0 || j + l > board[0].length - 1) continue;
				if(board[i + k][j + l].getReal() == 9) return true;
			}
		}
		return false;
	}
	
	/**
	 * @param board	The game board
	 * @param i	Row index
	 * @param j	Column index
	 * @return	The number of bombs around board[i][j], in range [0,8]
	 */
	public static int bombCounter(Cell[][] board, int i, int j) {
		int counter = 0;
		for(int k = -1; k <= 1; k++) {
			if(i + k < 0 || i + k > board.length - 1) continue;
			for(int l = -1; l <= 1; l++) {
				if(k == 0 && l == 0) continue;
				if(j + l < 0 || j + l > board[0].length - 1) continue;
				if(board[i + k][j + l].getReal() == 9) counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Prints the player's board
	 * @param board	The game board
	 */
	public static void printPlayerBoard(Cell[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				System.out.print(board[i][j] + " | ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints the real board
	 * @param board	The game board
	 */
	public static void printRealBoard(Cell[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				System.out.print(board[i][j].getReal() + " | ");
			}
			System.out.println();
		}
	}
	
	/**
	 * @param board	The game board
	 * @return	Have all the non-mines been pressed?
	 */
	public static boolean allFound(Cell[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j].getReal() == 9) continue;
				if(!board[i][j].isOpened())
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Main game
	 * @param board	The game board
	 */
	public static void turn(Cell[][] board) {
		int row, column;
		printPlayerBoard(board);
		System.out.println("Enter row: ");
		row = scan.nextInt();
		System.out.println("Enter column: ");
		column = scan.nextInt();
		while(row < 0 || row >= board.length || column < 0 || column >= board[0].length || board[row][column].isOpened()) {
			System.out.println("Invalid indexes entered. Please enter again.");
			System.out.println("Enter row: ");
			row = scan.nextInt();
			System.out.println("Enter column: ");
			column = scan.nextInt();
		}
		if(board[row][column].getReal() == 0)
			recursiveOpen(board, row, column);
		if(board[row][column].getReal() == 9)
			endGame(board, "Lose");
		board[row][column].open();
	}
	
	/**
	 * Recursively opens the board until a number is found (Starting cell = 0)
	 * @param board	The game board
	 * @param i	Row index
	 * @param j	Column index
	 */
	public static void recursiveOpen(Cell[][] board, int i, int j) {
		if(board[i][j].isOpened()) return;
		board[i][j].open();
		if(board[i][j].getReal() != 0) return;
		for(int k = -1; k <= 1; k++) {
			if(i + k < 0 || i + k >= board.length) continue;
			for(int l = -1; l <= 1; l++) {
				if(k == l) continue;
				if(j + l < 0 || j + l >= board[0].length) continue;
				if(board[i + k][j + l].getPlayer() == -1) continue;
				recursiveOpen(board, i + k, j + l);
			}
		}
	}
	
	/**
	 * Ends game and displays appropriate message
	 * @param board	The game board
	 * @param flag	End type
	 * @throws IllegalArgumentException	Thrown when flag is invalid
	 */
	public static void endGame(Cell[][] board, String flag) throws IllegalArgumentException {
		String message;
		switch(flag) {
		case "Lose": message = "Game over! You triggered a mine!"; break;
		case "Win": message = "Congratulations, you win!"; break;
		default: throw new IllegalArgumentException("Invalid Flag!");
		}
		System.out.println(message);
		printRealBoard(board);
		System.exit(0);
	}
	
	// </FUNCTIONS>

}

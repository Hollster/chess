package chess;

public class Board {
	private static final int numberOfRows = 8;
	private static final int numberOfColumns = 8;
	private String[][] board = new String[8][8];
	
	public void makeBoard() {
		for (int row = 0; row < numberOfRows; row = row + 2) {
			for (int column = 0; column < numberOfColumns; column = column + 2) {
				board[row][column] = "[]";
			}
			for (int column = 1; column < numberOfColumns; column = column + 2) {
				board[row][column] = "{}";
			}
		}
		for (int row = 1; row < numberOfRows; row = row + 2) {
			for (int column = 1; column < numberOfColumns; column = column + 2) {
				board[row][column] = "[]";
			}
			for (int column = 0; column < numberOfColumns; column = column + 2) {
				board[row][column] = "{}";
			}
		}

	}
	
	public void initializeBoard() {
		
	}
	
	public void printBoard() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				System.out.print(board[row][column]);
			}
			System.out.print("\n");
		}
	}

}

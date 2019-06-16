package chess;

public class Board {
	private static final int numberOfRows = 8;
	private static final int numberOfColumns = 8;
	protected String[][] board = new String[9][9];
	
	public void makeBoard() {
		board[0][0] = "    ";
		for (char letters = 'A'; letters <= 'H'; letters++) {
			board[0][(int)letters - 64] = "  " + String.valueOf(letters) + "  ";
		}
		for (int row = 1; row <= numberOfRows; row = row + 2) {
			for (int column = 1; column <= numberOfColumns; column = column + 2) {
				board[row][column] = "[   ]";
			}
			for (int column = 2; column <= numberOfColumns; column = column + 2) {
				board[row][column] = "{   }";
			}
			board[row][0] = "  " + String.valueOf(row) + "  ";
		}
		for (int row = 2; row <= numberOfRows; row = row + 2) {
			for (int column = 2; column <= numberOfColumns; column = column + 2) {
				board[row][column] = "[   ]";
			}
			for (int column = 1; column <= numberOfColumns; column = column + 2) {
				board[row][column] = "{   }";
			}
			board[row][0] = "  " + String.valueOf(row) + "  ";
		}

	}
	
	public void initializeBoard() {
		for(int column = 1; column <= numberOfColumns; column++) {
			board[1][column] = "{}";
			board[2][column] = "()";
			board[1][column][0] = "b";
			board[2][column][0] = "b";
		}
		
	}
	
	public void printBoard() {
		for (int row = 0; row <= numberOfRows; row++) {
			for (int column = 0; column <= numberOfColumns; column++) {
				System.out.print(board[row][column]);
			}
			System.out.print("\n");
		}
	}

}

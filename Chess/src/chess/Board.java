package chess;

public class Board {
	static final int NUMBER_OF_ROWS = 8;
	static final int NUMBER_OF_COLUMNS = 8;
	static String[][] board = new String[NUMBER_OF_COLUMNS][NUMBER_OF_ROWS];
	
	static void populate() {
		eraseBoard();
		placePieces(MainChess.getPlayer(true));
		placePieces(MainChess.getPlayer(false));
		fillEmptyTiles();
	}
	
	private static void eraseBoard() {
		for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
			for (int row = 0; row < NUMBER_OF_ROWS; row++) {
				board[column][row] = "";
			}
		}
	}
	
	private static void placePieces(Player player) {
		String color = player.color;
		for(Piece piece: player.getPiecesOnBoard()) {
			board[piece.x][piece.y] = " " + color.substring(0, 1) + piece.getClass().getName().substring(6, 7) + " ";
		}
	}

	private static void fillEmptyTiles() {
		for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
			for (int row = 0; row < NUMBER_OF_ROWS; row++) {
				if(board[column][row] == "") {
					board[column][row] = "    ";
				}
			}
		}
	}

	static void print() {
		System.out.println();
			for(int row = 0; row < NUMBER_OF_ROWS; row++) {
				System.out.print("  " + (NUMBER_OF_ROWS - row) + "  ");
				for(int column = 0; column < NUMBER_OF_COLUMNS; column++) {
					if((row % 2 == 0 && column % 2 == 0) || (row % 2 == 1 && column % 2 == 1)) {
						System.out.print("[" + board[column][row] + "]");
					} else {
						System.out.print("{" + board[column][row] + "}");
					}
				}
				System.out.println();
			}
			System.out.print("    ");
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				System.out.print("   " + (char)(column + MainChess.LETTER_TO_NUMBER_DIFFERENCE) + "  ");
			} 
			System.out.print("\n");
		}
	
	static boolean entryIsOnBoard(String playerInput) {
		if(entryHasCorrectLength(playerInput)) {
			char letter = playerInput.charAt(0);
			char number = playerInput.charAt(1);
			if(letter >= 'A' && letter <= 'H' && number >='1' && number <='8') {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private static boolean entryHasCorrectLength(String playerInput) {
		if (playerInput.length() == 2) {
			return true;
		} else {
			return false;
		}
	}
}
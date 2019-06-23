package chess;

public class Board {
	public static int numberOfRows = 8;
	public static int numberOfColumns = 8;
	public static String[][] board = new String[numberOfColumns][numberOfRows];
	
	public static void populateBoard() {
		eraseBoard();
		placePieces(MainChess.getPlayer(true));
		placePieces(MainChess.getPlayer(false));
		fillEmptyTiles();
	}
	
	private static void eraseBoard() {
		for (int column = 0; column < numberOfColumns; column++) {
			for (int row = 0; row < numberOfRows; row++) {
				board[column][row] = "";
			}
		}
	}
	
	private static void placePieces(Player player) {
		String color = player.color;
		for(Piece piece: player.getPiecesOnBoard()) {
			board[piece.position[0]][piece.position[1]] = " " + color.substring(0, 1) + piece.getClass().getName().substring(6, 7) + " ";
		}
	}

	private static void fillEmptyTiles() {
		for (int column = 0; column < numberOfColumns; column++) {
			for (int row = 0; row < numberOfRows; row++) {
				if(board[column][row] == "") {
					board[column][row] = "    ";
				}
			}
		}
	}

	public static void printBoard() {

		System.out.println();
			for(int row = 0; row < numberOfRows; row++) {
				System.out.print("  " + (numberOfRows - row) + "  ");
				for(int column = 0; column < numberOfColumns; column++) {
					if((row % 2 == 0 && column % 2 == 0) || (row % 2 == 1 && column % 2 == 1)) {
						System.out.print("[" + board[column][row] + "]");
					} else {
						System.out.print("{" + board[column][row] + "}");
					}
				}
				System.out.println();
			}
			System.out.print("    ");
			for (int column = 0; column < numberOfColumns; column++) {
				System.out.print("   " + (char)(column + MainChess.letterToNumberDifference) + "  ");
				//System.out.print("   " + row + "  ");
			} 
			System.out.print("\n");
		}
}
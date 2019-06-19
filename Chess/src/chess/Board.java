package chess;

public class Board {
	private int numberOfRows;
	private int numberOfColumns;
	protected static Tile[][] board;
	private Piece[] whitePieces;
	private Piece[] blackPieces;
	
	Board(){
		numberOfRows = 8;
		numberOfColumns = 8;
		board = new Tile[numberOfRows][numberOfColumns];
		whitePieces = makePieces("white");
		blackPieces = makePieces("black");
	}
	
		private Piece[] makePieces(String color) {
			Piece[] pieces = new Piece[2 * numberOfColumns];
			pieces[0] = new Rook(color);
			pieces[1] = new Knight(color);
			pieces[2] = new Bishop(color);
			pieces[3] = new Queen(color);
			pieces[4] = new King(color);
			pieces[5] = new Bishop(color);
			pieces[6] = new Knight(color);
			pieces[7] = new Rook(color);
			for (int column = 0; column < numberOfColumns; column++) {
				pieces[numberOfColumns + column] = new Pawn(color);
			}
			return pieces;
		}
		
	public void makeBoard() {		
		for (int row = 0; row < numberOfRows; row = row + 2) {
			for (int column = 0; column < numberOfColumns; column = column + 2) {
				board[row][column] = new Tile(new int[] {row, column}, "white");
			}
			for (int column = 1; column < numberOfColumns; column = column + 2) {
				board[row][column] = new Tile(new int[] {row, column}, "black");
			}
		}
		for (int row = 1; row < numberOfRows; row = row + 2) {
			for (int column = 0; column < numberOfColumns; column = column + 2) {
				board[row][column] = new Tile(new int[] {row, column}, "black");
			}
			for (int column = 1; column < numberOfColumns; column = column + 2) {
				board[row][column] = new Tile(new int[] {row, column}, "white");
			}
		}
	}
	
	public static Tile getTile(int[] coordinates) {
		return board[coordinates[0]][coordinates[1]];
	}
	
	public void populateBoard() {
		for (int column = 0; column < numberOfColumns; column ++) {
			board[0][column].updateTile(blackPieces[numberOfColumns - 1 - column]);
			board[1][column].updateTile(blackPieces[numberOfColumns + column]);
			board[6][column].updateTile(whitePieces[numberOfColumns + column]);
			board[7][column].updateTile(whitePieces[column]);
		}
	}

	public void printBoard() {
		System.out.print("    ");
//		for (int letter = 'A'; letter <= 'H'; letter++) {
//			System.out.print("   " + (char)letter + "  ");
//		} 
		for (int row = 1; row <= numberOfRows; row++) {
			System.out.print("   " + row + "  ");
		} 
			System.out.print("\n");
		for (int row = 0; row < numberOfRows; row++) {
			System.out.print("  " + (char)(row + MainChess.letterToNumberDifference) + "  ");
			for (int column = 0; column < numberOfColumns; column++) {
				System.out.print(board[row][column].getTileString());
			}
			System.out.print("\n");
		}
	}
	
	// mit nem 2d array überladen? kann man da auch so was wie this machen oder muss man da alles doppelt aufschreiben
	
	public void checkForNextPiece(Tile currentTile, int[] direction) {
		// while currentTile + direction is within board
			//check Tile if currentPiece == null
				//if not: return Piece
		// else return null
	}
	
	public void checkForNextPiece(Tile currentTile, int[] direction, Tile targetTile) {
		// while currentTile + direction is < targetTile
			//check Tile if currentPiece == null
				//if not: return Piece
		// else return null
	}
}
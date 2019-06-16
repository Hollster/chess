package chess;

public class Board {
	private int numberOfRows;
	private int numberOfColumns;
	protected Tile[][] board;
	private String[] listOfPieces;
	private String nameOfPawn;
	
	Board(){
		numberOfRows = 8;
		numberOfColumns = 8;
		board = new Tile[8][8];
		listOfPieces = new String[]{"Rook", "Knight", "Bishop", "King", "Queen", "Bishop", "Knight", "Rook"};
		nameOfPawn = "Pawn";
	}
	
	public void makeBoard() {
		for (int row = 0; row < numberOfRows; row = row + 2) {
			for (int column = 0; column < numberOfColumns; column = column + 2) {
				board[row][column] = new Tile("white");
			}
			for (int column = 1; column < numberOfColumns; column = column + 2) {
				board[row][column] = new Tile("black");
			}
		}
		for (int row = 1; row < numberOfRows; row = row + 2) {
			for (int column = 0; column < numberOfColumns; column = column + 2) {
				board[row][column] = new Tile("black");
			}
			for (int column = 1; column < numberOfColumns; column = column + 2) {
				board[row][column] = new Tile("white");
			}
		}
	}
	
	public void populateBoard() {
		Piece[][] whitePieces = new Piece[2][8]; 
		Piece[][] blackPieces = new Piece[2][8]; 
		generatePieces("white", whitePieces);
		generatePieces("black", blackPieces);
		populateRow(blackPieces, 0, 0);
		populateRow(blackPieces, 1, 1);
		populateRow(whitePieces, 7, 0);
		populateRow(whitePieces, 6, 1);
	}
	
	public void populateRow(Piece[][] pieces, int boardRow, int piecesRow) {
		for (int column = 0; column < numberOfColumns; column++) {	
			board[boardRow][column] = board[boardRow][column].addPieceToTile(pieces[piecesRow][column]);
		}	
	}
	
	private void generatePieces(String color, Piece[][] pieces) {
		for (int column = 0; column < numberOfColumns; column++) {
			pieces[0][column] = new Piece(color.substring(0, 1) + listOfPieces[column].substring(0, 1));
			pieces[1][column] = new Piece(color.substring(0, 1) + nameOfPawn.substring(0, 1));
		}
	}
	
	public void printBoard() {
		System.out.print("     ");
		for (int letter = 'A'; letter <= 'H'; letter++) {
			System.out.print("   " + (char)letter + "  ");
		} 
			System.out.print("\n");
		for (int row = 0; row < numberOfRows; row++) {
			System.out.print("  " + (row + 1) + "  ");
			for (int column = 0; column < numberOfColumns; column++) {
				System.out.print(board[row][column].content);
			}
			System.out.print("\n");
		}
	}
}
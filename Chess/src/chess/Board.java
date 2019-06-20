package chess;

import java.util.ArrayList;

public class Board {
	public static int numberOfRows = 8;
	public static int numberOfColumns = 8;
	protected static Tile[][] board = new Tile[numberOfRows][numberOfColumns];

		
	public static void makeBoard() {		
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
	
	public static void populateBoard() {
		for (int column = 0; column < numberOfColumns; column ++) {
			board[0][column].updateTile(MainChess.getActivePlayer().getPiecesOnBoard().get(numberOfColumns - 1 - column));
			board[1][column].updateTile(MainChess.getActivePlayer().getPiecesOnBoard().get(numberOfColumns + column));
			board[6][column].updateTile(MainChess.getInactivePlayer().getPiecesOnBoard().get(numberOfColumns + column));
			board[7][column].updateTile(MainChess.getInactivePlayer().getPiecesOnBoard().get(column));
		}
	}

	public static void printBoard() {
		System.out.print("    ");
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
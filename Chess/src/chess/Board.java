package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Board {
	public static int numberOfRows = 8;
	public static int numberOfColumns = 8;
	protected static Tile[][] board = new Tile[numberOfRows][numberOfColumns];
	public static HashSet<int[]> whitePossibleMoves;
	public static HashSet<int[]> blackPossibleMoves;

		
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
	
	//TODO
	//checkForCheck
	// for each king, get the position (how? das muss iwie extra getrackt werden)
	// vergleiche die position des königs mit allen möglichen gegnerpositionen
	// falls drin: schach
	//	falls der eigene könig: ist nicht gülitig
	
	//TODO
	//update KingPosition
	
	//TODO
	private void updateLists() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				if(getTile(new int[] {row, column}) != null) {
					getTile(new int[] {row, column}).getPiece().updatePossibleTargetLocations(new int[] {row, column});
					whitePossiblemoves = // go through list of 
				}
			}
		}
	}
	//nach jedem einzelnen Zug muss die Liste der jeweiligen Figuren geupdatet werden
	// und im Set zusammengeführt werden // ODER // es gibt für Bauen etwas spezielles
	// das wäre wahrshcienlich besser weil dann muss nicht jedes mal gerechnet werden und falls ja 
	// wieder alles zurückgenommen werden
	// pawn movement liste muss jedes mal geupdatet werden
	// 1) gucken ob der move valid ist
	// a) ist er in dem gegnerPossibleMovesFeld (das hat für Pawns nur die schrägen moves)
	// b) falls ja, ist der zug nicht erlaubt
	// c) falls nein, ist der zug erlaubt und der könig wird dort platziert, alle anderen spieler und die 
	// große liste müssen geupdatet werden
	
	}
}
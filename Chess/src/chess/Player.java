package chess;

import java.util.ArrayList;

public class Player {
	String name;
	String color;
	boolean isActive;
	boolean hasLost;
	private ArrayList<Piece> piecesOnBoard;
	private ArrayList<Piece> fallenPieces;
	
	Player(String name, String color, boolean isActive){
		this.name = name;
		this.color = color;
		this.isActive = isActive;
		piecesOnBoard = makePieces(color);
		fallenPieces = new ArrayList<Piece>();
	}

	
	
	private  ArrayList<Piece> makePieces(String color) {
		ArrayList<Piece>pieces = new ArrayList<Piece>();
		pieces.add(new Rook(color));
		pieces.add(new Knight(color));
		pieces.add(new Bishop(color));
		pieces.add(new Queen(color));
		pieces.add(new King(color));
		pieces.add(new Bishop(color));
		pieces.add(new Knight(color));
		pieces.add(new Rook(color));
		for (int column = 0; column < Board.numberOfColumns; column++) {
			pieces.add(new Pawn(color));
		}
		return pieces;
	}
	
	public ArrayList<Piece> getPiecesOnBoard() {
		return piecesOnBoard;
	}
	
	public ArrayList<Piece> getFallenPieces() {
		return fallenPieces;
	}
	
	public void removePiece(Piece currentpiece) {
		piecesOnBoard.remove(currentpiece);
	}
	
	public void addPiece(Piece currentpiece) {
		piecesOnBoard.add(currentpiece);
	}
	
	public void movePiece(String originTile, String targetTile) {
		
	}
	
	public void giveUp() {
		this.hasLost = true;
	}
	

	private void getTileFromCoordinates(String coordinates) {
		if (isValidTile(coordinates)) {
	//		Board.board[1][0];
		}
	}
	
	private boolean isValidTile(String coordinates) {
		if (coordinates.length() == 2) {
			char letter = Character.toUpperCase(coordinates.charAt(0));
			char number = coordinates.charAt(1);
			if (letter >= 'A' && letter <= 'H' && number >= 1 && number <= 8)
			{
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
}

package chess;

import java.util.ArrayList;

public class Player {
	String name;
	String color;
	boolean isActive;
	boolean hasLost;
	private ArrayList<Piece> piecesOnBoard;
	private Piece removedPiece;
	private int[] kingPosition;
	private King king;
	
	Player(String name, String color, boolean isActive){
		this.name = name;
		this.color = color;
		this.isActive = isActive;
		this.kingPosition = "white".equals(color) ? new int[] {4, 7} : new int[] {4, 0};
		this.king = "white".equals(color) ? new King(new int[] {4,7}, color) : new King(new int[] {4,0}, color);
		piecesOnBoard = makePieces(color);
		removedPiece = null;
		}

	private  ArrayList<Piece> makePieces(String color) {
		ArrayList<Piece>pieces = new ArrayList<Piece>();
		
		if(isActive) {
			for (int column = 0; column < Board.numberOfColumns; column++) {
				pieces.add(new Pawn(new int[] {column, 1}, color));
			}
			pieces.add(new Rook(new int[] {0, 0}, color));
			pieces.add(new Knight(new int[] {1,0}, color));
			pieces.add(new Bishop(new int[] {2,0}, color));
			pieces.add(king);
			pieces.add(new Queen(new int[] {3,0}, color));
			pieces.add(new Bishop(new int[] {5,0}, color));
			pieces.add(new Knight(new int[] {6,0}, color));
			pieces.add(new Rook(new int[] {7,0}, color));
		} else {
			for (int column = 0; column < Board.numberOfColumns; column++) {
				pieces.add(new Pawn(new int[] {column, 6}, color));
			}
			pieces.add(new Rook(new int[] {0,7}, color));
			pieces.add(new Knight(new int[] {1,7}, color));
			pieces.add(new Bishop(new int[] {2,7}, color));
			pieces.add(new Queen(new int[] {3,7}, color));
			pieces.add(king);
			pieces.add(new Bishop(new int[] {5,7}, color));
			pieces.add(new Knight(new int[] {6,7}, color));
			pieces.add(new Rook(new int[] {7,7}, color));
		}
		return pieces;
	}

	
	public void updateAllPossiblePieces() {
		for(Piece piece : piecesOnBoard) {
			piece.updatePossibleTargetLocations();
		}
	}
	
	public Piece getRemovedPiece() {
		return removedPiece;
	}
	
	public void setRemovedPiece(Piece piece) {
		removedPiece = piece;
	}
	
	public int[] getKingPosition() {
		return king.position;
	}
	
	public King getKing() {
		return king;
	}
	
	
	public ArrayList<Piece> getPiecesOnBoard() {
		return piecesOnBoard;
	}
	
	
	public void removePiece(Piece currentpiece) {
		for (int i = 0; i < piecesOnBoard.size(); i++) {
			if(Vectors.areEqual(currentpiece.position, piecesOnBoard.get(i).position)) {
				piecesOnBoard.remove(i);
				return;
			}
		}
	}
	
	public void removePieceForMate(Piece currentpiece) {
		for (int i = 0; i < piecesOnBoard.size(); i++) {
			if(Vectors.areEqual(currentpiece.position, piecesOnBoard.get(i).position)) {
				removedPiece = piecesOnBoard.get(i);
				piecesOnBoard.remove(i);
				return;
			}
		}
	}
	
	public void restorePiece() {
		piecesOnBoard.add(removedPiece);
		removedPiece = null;
	}
	
	public void addPiece(Piece currentpiece) {
		piecesOnBoard.add(currentpiece);
	}
		
	public void giveUp() {
		this.hasLost = true;
	}
	
	public void toggleActivity() {
		this.isActive = !this.isActive;
	}
}
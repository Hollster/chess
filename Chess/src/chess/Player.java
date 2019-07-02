package chess;

import java.awt.Point;
import java.util.ArrayList;

public class Player {
	final String name;
	final String color;
	boolean isActive;
	boolean hasLost;
	private ArrayList<Piece> piecesOnBoard;
	private Piece removedPiece;
	private King king;
	
	Player(String name, String color, boolean isActive){
		this.name = name;
		this.color = color;
		this.isActive = isActive;
		this.king = "white".equals(color) ? new King(4, 7, color) : new King(4, 0, color);
		piecesOnBoard = makePieces(color);
		removedPiece = null;
		}

	private  ArrayList<Piece> makePieces(String color) {
		ArrayList<Piece>pieces = new ArrayList<Piece>();
		
		if(isActive) {
			for (int column = 0; column < Board.NUMBER_OF_COLUMNS; column++) {
				pieces.add(new Pawn(column, 1, color));
			}
			pieces.add(new Rook(0, 0, color));
			pieces.add(new Knight(1,0, color));
			pieces.add(new Bishop(2,0, color));
			pieces.add(new Queen(3,0, color));
			pieces.add(king);
			pieces.add(new Bishop(5,0, color));
			pieces.add(new Knight(6,0, color));
			pieces.add(new Rook(7,0, color));
		} else {
			for (int column = 0; column < Board.NUMBER_OF_COLUMNS; column++) {
				pieces.add(new Pawn(column, 6, color));
			}
			pieces.add(new Rook(0,7, color));
			pieces.add(new Knight(1,7, color));
			pieces.add(new Bishop(2,7, color));
			pieces.add(new Queen(3,7, color));
			pieces.add(king);
			pieces.add(new Bishop(5,7, color));
			pieces.add(new Knight(6,7, color));
			pieces.add(new Rook(7,7, color));
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
	
	public Point getKingPosition() {
		return new Point(king.x, king.y);
	}
	
	public King getKing() {
		return king;
	}
	
	
	public ArrayList<Piece> getPiecesOnBoard() {
		return piecesOnBoard;
	}
	
	public void removePiece(Piece currentpiece) {
		for (int i = 0; i < piecesOnBoard.size(); i++) {
			if(currentpiece.equals(piecesOnBoard.get(i))) {
				removedPiece = piecesOnBoard.get(i);
				piecesOnBoard.remove(i);
				return;
			}
		}
	}
	
	public void deleteRemovedPiece() {
		removedPiece = null;
	}
	
	public void restorePiece() {
		if(removedPiece != null) {
			piecesOnBoard.add(removedPiece);
			removedPiece = null;
		}
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
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
		int row = 0;
		int pawnRow = 1;
		if(!isActive) {
			row = Board.NUMBER_OF_ROWS - 1;
			pawnRow = Board.NUMBER_OF_ROWS - 2;
		}
		for (int column = 0; column < Board.NUMBER_OF_COLUMNS; column++) {
			pieces.add(new Pawn(column, pawnRow, color));
		}
		pieces.add(new Rook(0, row, color));
		pieces.add(new Knight(1,row, color));
		pieces.add(new Bishop(2,row, color));
		pieces.add(new Queen(3, row, color));
		pieces.add(king);
		pieces.add(new Bishop(5,row, color));
		pieces.add(new Knight(6,row, color));
		pieces.add(new Rook(7,row, color));
		return pieces;
	}

	
	void updateAllPossiblePieces() {
		for(Piece piece : piecesOnBoard) {
			piece.updatePossibleTargetLocations();
		}
	}
	
	Point getKingPosition() {
		return new Point(king.x, king.y);
	}
		
	ArrayList<Piece> getPiecesOnBoard() {
		return piecesOnBoard;
	}
	
	void removePiece(Piece currentpiece) {
		for (int i = 0; i < piecesOnBoard.size(); i++) {
			if(currentpiece.equals(piecesOnBoard.get(i))) {
				removedPiece = piecesOnBoard.get(i);
				piecesOnBoard.remove(i);
				return;
			}
		}
	}
	
	void deleteRemovedPiece() {
		removedPiece = null;
	}
	
	void restorePiece() {
		if(removedPiece != null) {
			piecesOnBoard.add(removedPiece);
			removedPiece = null;
		}
	}
	
	void toggleActivity() {
		this.isActive = !this.isActive;
	}
}
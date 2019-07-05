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
	private Piece king;
	
	Player(String name, String color, boolean isActive){
		this.name = name;
		this.color = color;
		this.isActive = isActive;
		this.king = "white".equals(color) ?  new Piece(color, 4, 7, MyType.KING) : new Piece(color, 4, 0, MyType.KING);
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
			pieces.add(new Piece(color, column, pawnRow, MyType.PAWN));
		}
		pieces.add(new Piece(color, 0, row, MyType.ROOK));
		pieces.add(new Piece(color, 1, row, MyType.KNIGHT));
		pieces.add(new Piece(color, 2, row, MyType.BISHOP));
		pieces.add(new Piece(color, 3, row, MyType.QUEEN));
		pieces.add(new Piece(color, 4, row, MyType.KING));
		pieces.add(new Piece(color, 5, row, MyType.BISHOP));
		pieces.add(new Piece(color, 6, row, MyType.KNIGHT));
		pieces.add(new Piece(color, 7, row, MyType.ROOK));

		return pieces;
	}

	
	void updateAllPossiblePieces() {
		for(Piece piece : piecesOnBoard) {
			piece.updatePossibleTargetLocations();
		}
	}
	
	Point getKingPosition() {
		return new Point(king.getLocation().x, king.getLocation().y);
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
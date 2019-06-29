package chess;

import java.awt.Point;

public class Pawn extends Piece{
boolean hasMoved;
	
	Pawn(int x, int y, String color){
		super.x = x;
		super.y = y;
		super.limitOfReach = 1;
		this.color = color;
		super.color = color;
		super.directions = "black".equals(super.color) ? 
				new Point[] {new Point(0, 1), new Point(1, 1), new Point(-1, 1), new Point(0, 2)} 
				: new Point[] {new Point(0, -1), new Point(1, -1),  new Point(-1, -1), new Point(0, -2)};
	}
	
	@Override
	public void updatePossibleTargetLocations(){
		super.possibleTargetLocations.clear();
		Point possibleLocationFront = new Point(this);
		possibleLocationFront.translate(directions[0].x, directions[0].y);
		if(noPieceInFrontOfMe(possibleLocationFront)) {
			super.possibleTargetLocations.add(new Point(possibleLocationFront));
			if(this.directions[3] != null) {
				Point possibleLocationTwoFront = new Point(this);
				possibleLocationTwoFront.translate(directions[3].x, directions[3].y);
				if(noPieceTwoFieldsInFrontOfMe(possibleLocationTwoFront)) {
					super.possibleTargetLocations.add(new Point(possibleLocationTwoFront));
			}
			
			
			}
		}
		for(int i = 1; i < 3; i++) {
			Point possibleLocationDiagonal = new Point(this);
			possibleLocationDiagonal.translate(directions[i].x, directions[i].y);	
			if(pieceDiagonalInFrontOfMe(possibleLocationDiagonal)) {
				super.possibleTargetLocations.add(new Point (possibleLocationDiagonal));
			}
		}
		
	}
	
	private boolean pieceDiagonalInFrontOfMe(Point possibleLocation) {
		String opponentColor = "white".equals(this.color) ? "black" : "white";
		for(Piece piece : MainChess.getPlayer(opponentColor).getPiecesOnBoard()) {
			if(piece.equals(possibleLocation)){
				return true;
			}
		}
		return false;
	}
	
	// TODO
	// Zusammenfassen
	
	private boolean noPieceInFrontOfMe(Point possibleLocation) {
	
		for(Piece piece : MainChess.getPlayer(true).getPiecesOnBoard()) {
			if(piece.equals(possibleLocation)){
				return false;
			}
		}
		for(Piece piece : MainChess.getPlayer(false).getPiecesOnBoard()) {
			if(piece.equals(possibleLocation)){
				return false;
			}
		}
		return true;
	}
	
	private boolean noPieceTwoFieldsInFrontOfMe(Point possibleLocation) {
		
		for(Piece piece : MainChess.getPlayer(true).getPiecesOnBoard()) {
			if(piece.equals(possibleLocation)){
				return false;
			}
		}
		for(Piece piece : MainChess.getPlayer(false).getPiecesOnBoard()) {
			if(piece.equals(possibleLocation)){
				return false;
			}
		}
		return true;
	}
}
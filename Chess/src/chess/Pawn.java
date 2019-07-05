package chess;

import java.awt.Point;

public class Pawn{
//boolean hasMoved;
//	
//	Pawn(int x, int y, String color){
//		super.setLocation(x, y);
//		super.limitOfReach = 1;
//		this.color = color;
//		super.color = color;
//		super.directions = "black".equals(super.color) ? 
//				new Point[] {new Point(0, 1), new Point(1, 1), new Point(-1, 1), new Point(0, 2)} 
//				: new Point[] {new Point(0, -1), new Point(1, -1),  new Point(-1, -1), new Point(0, -2)};
//	}
//	
//	@Override
//	void updatePawnsPossibleTargetLocations(){
//		super.possibleTargetLocations.clear();
//		Point possibleLocationFront = new Point(this.getLocation());
//		possibleLocationFront.translate(directions[0].x, directions[0].y);
//		if(noPieceInFrontOfMe(possibleLocationFront)) {
//			super.possibleTargetLocations.add(new Point(possibleLocationFront));
//			if(this.directions[3] != null) {
//				Point possibleLocationTwoFront = new Point(this.getLocation());
//				possibleLocationTwoFront.translate(directions[3].x, directions[3].y);
//				if(noPieceInFrontOfMe(possibleLocationTwoFront)) {
//					super.possibleTargetLocations.add(new Point(possibleLocationTwoFront));
//				}
//			}
//		}
//		for(int i = 1; i < 3; i++) {
//			Point possibleLocationDiagonal = new Point(this.getLocation());
//			possibleLocationDiagonal.translate(directions[i].x, directions[i].y);	
//			if(pieceDiagonalInFrontOfMe(possibleLocationDiagonal)) {
//				super.possibleTargetLocations.add(new Point (possibleLocationDiagonal));
//			}
//		}
//	}
//	
//	@Override
//	void movePawn() {
//		super.move();
//		directions[3] = null;
//		if(getLocation().y == Board.NUMBER_OF_ROWS - 1 || getLocation().y == 0) {
//			promotion();
//		}
//	}
//	
//	private void promotion() {
//		String promotionAnswer = PlayerInput.promotion();
//			switch(promotionAnswer) {
//			case "QUEEN":
//				MainChess.getActivePlayer().getPiecesOnBoard().add(new Queen (getLocation().x, getLocation().y, MainChess.getActivePlayer().color));
//				break;
//			case "KNIGHT":
//				MainChess.getActivePlayer().getPiecesOnBoard().add(new Knight (getLocation().x, getLocation().y, MainChess.getActivePlayer().color));
//				break;
//			case "ROOK":
//				MainChess.getActivePlayer().getPiecesOnBoard().add(new Rook (getLocation().x, getLocation().y, MainChess.getActivePlayer().color));
//				break;
//			case "BISHOP":
//				MainChess.getActivePlayer().getPiecesOnBoard().add(new Bishop (getLocation().x, getLocation().y, MainChess.getActivePlayer().color));
//				break;
//			default:
//				return;
//			}
//			MainChess.getActivePlayer().getPiecesOnBoard().remove(this);
//		}
//	
//	
//	private boolean pieceDiagonalInFrontOfMe(Point possibleLocation) {
//		String opponentColor = "white".equals(this.color) ? "black" : "white";
//		for(Piece piece : MainChess.getPlayer(opponentColor).getPiecesOnBoard()) {
//			if(piece.getLocation().equals(possibleLocation)){
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private boolean noPieceInFrontOfMe(Point possibleLocation) {
//		if(!thereIsPiece(MainChess.getActivePlayer(), possibleLocation) && !thereIsPiece(MainChess.getInactivePlayer(), possibleLocation)) {
//			return true;
//		}
//		return false;
//	}
//	
//	private boolean thereIsPiece(Player player, Point possibleLocation) {
//		for(Piece piece : player.getPiecesOnBoard()) {
//			if(piece.getLocation().equals(possibleLocation)){
//				return true;
//			}
//		}
//		return false;
//	}

}
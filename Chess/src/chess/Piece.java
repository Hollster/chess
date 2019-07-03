package chess;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Piece extends Point{
	protected String color;
	protected int limitOfReach;
	protected Point[] directions;
	protected ArrayList<Point> possibleTargetLocations;

	Piece(){
		possibleTargetLocations = new ArrayList<Point>();
	}
	
	Piece(int x, int y, String color){
		this.x = x;
		this.y = y;
		this.color = color;
		possibleTargetLocations = new ArrayList<Point>();
	}

	void updatePossibleTargetLocations(){
		possibleTargetLocations.clear();
		int reach = 1;
		Point possibleLocation;
		for (Point currentDirection : directions) {
			possibleLocation = new Point(this);
			CheckThisDirection:
				for(reach = 1; reach <= limitOfReach; reach++) {
					possibleLocation.setLocation(this);
					possibleLocation.translate(currentDirection.x * reach, currentDirection.y * reach);
					if((possibleLocation.x < Board.NUMBER_OF_COLUMNS && possibleLocation.x >= 0) 
					&& (possibleLocation.y < Board.NUMBER_OF_ROWS && possibleLocation.y >= 0)) {
						for(Piece piece : MainChess.getPlayer(color).getPiecesOnBoard()) {
							if (possibleLocation.equals(piece)) {
								break CheckThisDirection;
							}
						}
						for(Piece piece : MainChess.getPlayer(getOtherColor(color)).getPiecesOnBoard()) {
							if (possibleLocation.equals(piece)) {
								possibleTargetLocations.add(new Point(possibleLocation));
								break CheckThisDirection;
							}
						}
						possibleTargetLocations.add(new Point(possibleLocation));
					}
				}
		}
	}
	
	
	void move() {
		String secondPlayerInput;
		Point previousLocation = new Point(this);
		do {
			secondPlayerInput = PlayerInput.targetLocation(this);
			if ("REDO".equals(secondPlayerInput)) {
				MainChess.oneTurn();
				return;
			}
			if(!Board.entryIsOnBoard(secondPlayerInput)) {
				System.err.println("7 - That's not a valid tile. Please choose again.");
				move();
				return;
				}
		} while(!moveIsValid(PlayerInput.entryToPoint(secondPlayerInput)));
		this.setLocation(PlayerInput.entryToPoint(secondPlayerInput));
		capture(MainChess.getInactivePlayer());
		if(MainChess.inCheck(MainChess.getActivePlayer())) {
			System.err.println("6 - Not valid, you would be in check");
			undoMove(MainChess.getActivePlayer(), previousLocation);
			move();
			return;
		}
		MainChess.getInactivePlayer().updateAllPossiblePieces();
		MainChess.getActivePlayer().updateAllPossiblePieces();
	}
	
	
	private void undoMove(Player currentPlayer, Point previousLocation) {
		this.setLocation(previousLocation);
		MainChess.getOtherPlayer(currentPlayer).restorePiece();
		MainChess.getActivePlayer().updateAllPossiblePieces();
		MainChess.getInactivePlayer().updateAllPossiblePieces();
	}
	
	private void capture(Player opponent) {
		for(Piece piece : opponent.getPiecesOnBoard()) {
			if(this.equals(piece)) {
				opponent.removePiece(piece);
				MainChess.getActivePlayer().updateAllPossiblePieces();
				MainChess.getInactivePlayer().updateAllPossiblePieces();
				return;
			}
		}
	}
	
	private boolean moveIsValid(Point targetCoordinates) {
		for(Point possibleTargetLocation : possibleTargetLocations) {
			if(targetCoordinates.equals(possibleTargetLocation)) {
				return true;
			}
		}
		System.err.println("6 - Sorry, you can't go there. Either something is in the way or the movement pattern does not match");
		return false;
	}
	
	private String getOtherColor(String color) {
		return "white".equals(color) ? "black" : "white";
	}
}

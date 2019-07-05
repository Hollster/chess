package chess;

import java.awt.Point;
import java.util.ArrayList;




public class Piece {

	MyType myType;
	String color;
	int limitOfReach;
	Point[] directions;
	ArrayList<Point> possibleTargetLocations;
	Point location = new Point();
	
	
	Piece(String color, int x, int y, MyType myType){
		this.myType = myType;
		this.color = color;
		setLocation(x, y);
		this.limitOfReach = setLimitOfReach();
		this.directions = setDirections();
		possibleTargetLocations = new ArrayList<Point>();
	}
	
	private int setLimitOfReach() {
		switch (myType) {
		case PAWN:// fall through
		case KNIGHT:
		case KING:
			return 1;
		case QUEEN:// fall through
		case BISHOP:
		case ROOK:
			return 7;
		default:
			System.err.println("9 - Switch in Piece");
			return 0;
		}
	}
	
	private Point[] setDirections() {
		switch (myType) {
		case PAWN:
			return "black".equals(this.color) ? 
					new Point[] {new Point(0, 1), new Point(1, 1), new Point(-1, 1), new Point(0, 2)} 
					: new Point[] {new Point(0, -1), new Point(1, -1),  new Point(-1, -1), new Point(0, -2)};
		case KNIGHT: 
			return new Point[] {new Point(1, 2), new Point(1, -2), new Point(-1, 2), new Point(-1, -2), 
				new Point(2, 1), new Point(2, -1), new Point(-2, 1), new Point(-2, -1)};
		case BISHOP:
			return new Point[] {new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)};
		case ROOK:
			return new Point[] {new Point(1,0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)};
		case KING: // fall through
		case QUEEN:
			return new Point[] {new Point(0,1), new Point(0,-1), new Point(1,0), new Point(-1, 0), 
					new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)};
		default:
			System.err.println("9 - Switch in Piece");
			return new Point[] {};
		}
	}

//	Piece(){
//		possibleTargetLocations = new ArrayList<Point>();
//	}
//	
//	Piece(int x, int y, String color){
//		setLocation(x, y);
//		this.color = color;
//		possibleTargetLocations = new ArrayList<Point>();
//	}
	
	public Point getLocation() {
		return location;
	}
	
	public Piece setLocation(Point location) {
		this.location = location;
		return this;
	}
	
	public Piece setLocation(int x, int y) {
		this.location.x = x;
		this.location.y = y;
		return this;
	}

	public Piece setColor(String color) {
		this.color = color;
		return this;
	}
	
	void updatePossibleTargetLocations() {
		if(myType == MyType.PAWN) {
			updatePawn();
		}
		else {
			updateRegular();
		}
	}
	
	private void updateRegular(){
		possibleTargetLocations.clear();
		int reach = 1;
		Point possibleLocation;
		for (Point currentDirection : directions) {
			possibleLocation = new Point(this.getLocation());
			CheckThisDirection:
				for(reach = 1; reach <= limitOfReach; reach++) {
					possibleLocation.setLocation(this.getLocation());
					possibleLocation.translate(currentDirection.x * reach, currentDirection.y * reach);
					if((possibleLocation.x < Board.NUMBER_OF_COLUMNS && possibleLocation.x >= 0) 
					&& (possibleLocation.y < Board.NUMBER_OF_ROWS && possibleLocation.y >= 0)) {
						for(Piece piece : MainChess.getPlayer(color).getPiecesOnBoard()) {
							if (possibleLocation.equals(piece.getLocation())) {
								break CheckThisDirection;
							}
						}
						for(Piece piece : MainChess.getPlayer(getOtherColor(color)).getPiecesOnBoard()) {
							if (possibleLocation.equals(piece.getLocation())) {
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
		Point previousLocation = new Point(this.getLocation());
		do {
			secondPlayerInput = PlayerInput.targetLocation(this.getLocation());
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
		if(myType == MyType.PAWN) {
			directions[3] = null;
			if(getLocation().y == Board.NUMBER_OF_ROWS - 1 || getLocation().y == 0) {
				promotion();
			}
		}
	}
	
	
	private void undoMove(Player currentPlayer, Point previousLocation) {
		this.setLocation(previousLocation);
		MainChess.getOtherPlayer(currentPlayer).restorePiece();
		MainChess.getActivePlayer().updateAllPossiblePieces();
		MainChess.getInactivePlayer().updateAllPossiblePieces();
	}
	
	private void capture(Player opponent) {
		for(Piece piece : opponent.getPiecesOnBoard()) {
			if(this.getLocation().equals(piece.getLocation())) {
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
	

	
	
	private void updatePawn(){
		possibleTargetLocations.clear();
		Point possibleLocationFront = new Point(this.getLocation());
		possibleLocationFront.translate(directions[0].x, directions[0].y);
		if(noPieceInFrontOfMe(possibleLocationFront)) {
			possibleTargetLocations.add(new Point(possibleLocationFront));
			if(this.directions[3] != null) {
				Point possibleLocationTwoFront = new Point(this.getLocation());
				possibleLocationTwoFront.translate(directions[3].x, directions[3].y);
				if(noPieceInFrontOfMe(possibleLocationTwoFront)) {
					possibleTargetLocations.add(new Point(possibleLocationTwoFront));
				}
			}
		}
		for(int i = 1; i < 3; i++) {
			Point possibleLocationDiagonal = new Point(this.getLocation());
			possibleLocationDiagonal.translate(directions[i].x, directions[i].y);	
			if(pieceDiagonalInFrontOfMe(possibleLocationDiagonal)) {
				possibleTargetLocations.add(new Point (possibleLocationDiagonal));
			}
		}
	}
	
	private void promotion() {
		String promotionAnswer = PlayerInput.promotion();
			switch(promotionAnswer) {
			case "QUEEN":
				MainChess.getActivePlayer().getPiecesOnBoard().add(new Piece (MainChess.getActivePlayer().color, getLocation().x, getLocation().y, MyType.QUEEN));
				break;
			case "KNIGHT":
				MainChess.getActivePlayer().getPiecesOnBoard().add(new Piece (MainChess.getActivePlayer().color, getLocation().x, getLocation().y, MyType.KNIGHT));
				break;
			case "ROOK":
				MainChess.getActivePlayer().getPiecesOnBoard().add(new Piece (MainChess.getActivePlayer().color, getLocation().x, getLocation().y, MyType.ROOK));
				break;
			case "BISHOP":
				MainChess.getActivePlayer().getPiecesOnBoard().add(new Piece (MainChess.getActivePlayer().color, getLocation().x, getLocation().y, MyType.BISHOP));
				break;
			default:
				return;
			}
			MainChess.getActivePlayer().getPiecesOnBoard().remove(this);
		}
	
	
	private boolean pieceDiagonalInFrontOfMe(Point possibleLocation) {
		String opponentColor = "white".equals(this.color) ? "black" : "white";
		for(Piece piece : MainChess.getPlayer(opponentColor).getPiecesOnBoard()) {
			if(piece.getLocation().equals(possibleLocation)){
				return true;
			}
		}
		return false;
	}

	private boolean noPieceInFrontOfMe(Point possibleLocation) {
		if(!thereIsPiece(MainChess.getActivePlayer(), possibleLocation) && !thereIsPiece(MainChess.getInactivePlayer(), possibleLocation)) {
			return true;
		}
		return false;
	}
	
	private boolean thereIsPiece(Player player, Point possibleLocation) {
		for(Piece piece : player.getPiecesOnBoard()) {
			if(piece.getLocation().equals(possibleLocation)){
				return true;
			}
		}
		return false;
	}
	
}


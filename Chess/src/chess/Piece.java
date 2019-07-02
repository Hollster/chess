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

	public ArrayList<Point> getPossibleTargetLocations() {
		return possibleTargetLocations;
	}
	
	public String getColor() {
		return color;
	}
	
	private String getOtherColor(String color) {
		return "white".equals(color) ? "black" : "white";
	}
	
	public void multiplyWithInt(int factor) {
		this.x = this.x * factor;
		this.y = this.x * factor;
	}
	
	public void translate(Point point) {
		this.x = this.x + point.x;
		this.y = this.y + point.y;
	}
	
//	public void translateNegative(Point point) {
//		this.x = this.x - point.x;
//		this.y = this.y - point.y;
//	}
	

	public void updatePossibleTargetLocations(){
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
}

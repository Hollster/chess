package chess;

import java.util.ArrayList;

public class Piece {
	public String color;
	public Pattern pattern;
	public int[] position;
	protected int limitOfReach;
	protected int[][] directions;
	ArrayList<int[]> possibleTargetLocations;

	Piece(){
		possibleTargetLocations = new ArrayList<int[]>();
	}
	
	Piece(int[] position, String color){
		this.position = position;
		this.color = color;
		possibleTargetLocations = new ArrayList<int[]>();
	}


	public ArrayList<int[]> getPossibleTargetLocations() {
		return possibleTargetLocations;
	}
	
	private String getOtherColor(String color) {
		return "white".equals(color) ? "black" : "white";
	}
	
	public void updatePossibleTargetLocations(){
		int reach = 1;
		int[] possibleLocation; 
		for (int[] currentDirection : directions) {
			possibleLocation = Vectors.addVectors(position, Vectors.multiplyIntWithVector(reach, currentDirection));
			CheckThisDirection:
				for(reach = 1; reach <= limitOfReach; reach++) {
					possibleLocation = Vectors.addVectors(position, Vectors.multiplyIntWithVector(reach, currentDirection));
					if((possibleLocation[0] 
							< Board.numberOfColumns && possibleLocation[0] >= 0) 
					&& (possibleLocation[1] 
							< Board.numberOfRows && possibleLocation[1] >= 0)) {
						for(Piece piece : MainChess.getPlayer(color).getPiecesOnBoard()) {
							if (Vectors.areEqual(piece.position , possibleLocation)) {
								break CheckThisDirection;
							}
						}
						for(Piece piece : MainChess.getPlayer(getOtherColor(color)).getPiecesOnBoard()) {
							if (Vectors.areEqual(piece.position , possibleLocation)) {
								possibleTargetLocations.add(possibleLocation);
								break CheckThisDirection;
							}
						}
						possibleTargetLocations.add(possibleLocation);
					}
				}
		}
	}
}

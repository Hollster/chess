package chess;

import java.util.ArrayList;

public class Piece {
	public String color;
	public Pattern pattern;
	public int[] position;
	protected int limitOfReach;
	protected int[][] directions;
	ArrayList<int[]> possibleTargetLocations;
	protected boolean canJump;

//	Piece (){
//		this("", null);
//	}
	

	Piece(String color){
		this.color = color;
	}


	public ArrayList<int[]> getPossibleTargetLocations() {
		return possibleTargetLocations;
	}
	
	public void updatePossibleTargetLocations(int[] position){
		int[] possibleLocation = new int[] {0, 0};
		for (int[] direction : directions) {
			for (int reach = 1; reach <= limitOfReach && possibleLocation[0] < 8 && possibleLocation[1] < 8; reach++) {
				possibleLocation = Vectors.addVectors(position, Vectors.multiplyIntWithVector(reach, direction));
				if(Board.getTile(possibleLocation).getPiece() != null) {
					if(Board.getTile(possibleLocation).getPiece().color.equalsIgnoreCase(color)) {
						break;
					} else {
						possibleTargetLocations.add(possibleLocation);
						break;
					}
				} else {
					possibleTargetLocations.add(possibleLocation);
				}
			} 
		}
	}
}

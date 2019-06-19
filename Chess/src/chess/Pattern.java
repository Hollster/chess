package chess;

public class Pattern {
	private int[][] directions;
	private int limit;
	private boolean canJump;
	
	
	public void isValid(int[]originCoordinates, int[]targetCoordinates) {
		// get difference of the two
		// check if originCoordinates * pattern = targetCoordinates
		// if !canJump, check if something is in the way => board? general method which checks other pieces
	}
}

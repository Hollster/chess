package chess;

public class Pattern {
	int[][] directions;
	int limit;
	boolean canJump;
	
	Pattern(int[][] directions, int limit, boolean canJump){
		this.directions = directions;
		this.limit = limit;
		this.canJump = canJump;
	}
	
	public int[] movementIsValidPattern(int[] attemptedMovement) {
		for(int[] direction : directions ) {
			for(int reach = 1; reach <= limit; reach++) {
				if(direction[0] * reach == attemptedMovement[0] && direction[1] * reach == attemptedMovement[1]) {
					return direction;
				}		
			}
		}
		return null;
	}
	
}

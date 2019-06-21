package chess;

public class Knight extends Piece{

	
	Knight(String color){
		super(color);
		super.canJump = true;
		super.limitOfReach = 1;
		super.directions = directions = new int[][] {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
		super.pattern = new Pattern (directions, limitOfReach, canJump);
	}
}
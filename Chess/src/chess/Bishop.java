package chess;

public class Bishop extends Piece{
	
	Bishop(String color){
		super(color);
		super.canJump = false;
		super.limitOfReach = 7;
		super.directions = new int[][] {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		super.pattern = new Pattern (directions, limitOfReach, canJump);
	}
}

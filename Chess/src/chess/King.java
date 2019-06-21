package chess;

public class King extends Piece{

	King(String color){
		super(color);
		super.canJump = false;
		super.limitOfReach = 1;
		super.directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		super.pattern = new Pattern (directions, limitOfReach, canJump);
	}
}
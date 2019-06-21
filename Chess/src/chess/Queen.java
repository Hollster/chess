package chess;

public class Queen extends Piece{


	Queen(String color){
		super(color);
		super.canJump = false;
		super.limitOfReach = 7;
		super.directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		super.pattern = new Pattern (directions, limitOfReach, canJump);
	}
}

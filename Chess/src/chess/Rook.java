package chess;

public class Rook extends Piece{

	Rook(String color){
		super(color);
		super.canJump = false;
		super.limitOfReach = 7;
		super.directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}};
		super.pattern = new Pattern (directions, limitOfReach, canJump);
	}
}

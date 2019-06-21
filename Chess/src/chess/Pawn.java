package chess;

public class Pawn extends Piece{

	
	Pawn(String color){
		super(color);
		super.canJump = false;
		super.limitOfReach = 1;
		super.directions = new int[][] {{0,1}, {1, 1},  {-1, 1}};
		super.pattern = new Pattern (directions, limitOfReach, canJump);
	}
}
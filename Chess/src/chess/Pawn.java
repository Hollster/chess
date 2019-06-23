package chess;

public class Pawn extends Piece{

	
	Pawn(int[] position, String color){
		super.position = position;
		super.limitOfReach = 1;
		this.color = color;
		super.color = color;
		super.directions = "black".equals(super.color) ? 
				new int[][] {{0,1}, {1, 1},  {-1, 1}} 
				: new int[][] {{0,-1}, {1, -1},  {-1, -1}};
	}
}
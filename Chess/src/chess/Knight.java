package chess;

public class Knight extends Piece{

	
	Knight(int[] position, String color){
		super.position = position;
		super.limitOfReach = 1;
		super.directions = directions = new int[][] {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
		super.color = color;
	}
}
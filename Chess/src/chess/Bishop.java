package chess;

public class Bishop extends Piece{
	
	Bishop(int[] position, String color){
		super.position = position;
		super.limitOfReach = 7;
		super.directions = new int[][] {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		super.color = color;
	}
}

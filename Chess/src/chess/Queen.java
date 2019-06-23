package chess;

public class Queen extends Piece{


	Queen(int[] position, String color){
		super.position = position;
		super.limitOfReach = 7;
		super.directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		super.color = color;
	}
}

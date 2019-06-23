package chess;

public class King extends Piece{

	King(int[] position, String color){
		super.position = position;
		super.limitOfReach = 1;
		super.directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		super.color = color;
	}
}
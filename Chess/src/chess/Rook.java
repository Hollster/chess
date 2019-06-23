package chess;

public class Rook extends Piece{

	Rook(int[] position, String color){
		super.position = position;
		super.limitOfReach = 7;
		super.directions = new int[][] {{1,0}, {-1, 0}, {0, 1}, {0, -1}};
		super.color = color;
	}
}

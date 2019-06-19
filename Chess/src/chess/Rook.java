package chess;

public class Rook extends Piece{
	private static int[][] directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}};
	private static int limit = 7;
	private static boolean canJump = false;

	Rook(String color){
		super(color);
		Pattern pattern = new Pattern (directions, limit, canJump);
	}
}

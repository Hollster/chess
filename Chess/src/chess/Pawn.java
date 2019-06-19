package chess;

public class Pawn extends Piece{
	private static int[][] directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
	private static int limit = 1;
	private static boolean canJump = false;
	public Pattern pattern;

	Pawn(String color){
		super(color);
		pattern = new Pattern (directions, limit, canJump);
	}
}
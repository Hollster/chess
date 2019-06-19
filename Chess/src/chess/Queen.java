package chess;

public class Queen extends Piece{
	private static int[][] directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
	private static int limit = 7;
	private static boolean canJump = false;

	Queen(String color){
		super(color);
		super.pattern = new Pattern (directions, limit, canJump);
	}
}
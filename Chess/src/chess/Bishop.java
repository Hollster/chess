package chess;

public class Bishop extends Piece{
	private static int[][] directions = new int[][] {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
	private static int limit = 7;
	private static boolean canJump = false;

	Bishop(String color){
		super(color);
		super.pattern = new Pattern (directions, limit, canJump);
	}
}

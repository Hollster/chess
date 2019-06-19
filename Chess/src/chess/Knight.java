package chess;

public class Knight extends Piece{

	private static int[][] directions = new int[][] {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
	private static int limit = 1;
	private static boolean canJump = true;

	Knight(String color){
		super(color);
		Pattern pattern = new Pattern (directions, limit, canJump);
	}
}

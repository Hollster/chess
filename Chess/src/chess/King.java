package chess;

public class King extends Piece{
	private static int[][] directions = new int[][] {{0,1}, {0,-1}, {1,0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
	private static int limit = 1;
	private static boolean canJump = false;

	King(String color){
		super(color);
		super.pattern = new Pattern (directions, limit, canJump);
	}	
}

package chess;

import java.awt.Point;

public class Bishop extends Piece{
	
	Bishop(int x, int y, String color){
		this.x = x;
		this.y = y;
		super.limitOfReach = 7;
		super.directions = new Point[] {new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)};
		super.color = color;
	}
}

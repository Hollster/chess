package chess;

import java.awt.Point;

public class Knight extends Piece{

	
	Knight(int x, int y, String color){
		super.x = x;
		super.y = y;
		super.limitOfReach = 1;
		super.directions = new Point[] 
				{new Point(1, 2), new Point(1, -2), new Point(-1, 2), new Point(-1, -2), 
				new Point(2, 1), new Point(2, -1), new Point(-2, 1), new Point(-2, -1)};
		super.color = color;
	}
}
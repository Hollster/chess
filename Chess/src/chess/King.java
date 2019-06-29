package chess;

import java.awt.Point;

public class King extends Piece{

	King(int x, int y, String color){
		super.x = x;
		super.y = y;
		super.limitOfReach = 1;
		super.directions = new Point[] 
				{new Point(0,1), new Point(0,-1), new Point(1,0), new Point(-1, 0), 
				new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)};
		super.color = color;
	}
}
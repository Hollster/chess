package chess;

import java.awt.Point;

public class PointExtension extends  Point{
	
	public int x;
	public int y;


	PointExtension(){
		super();
	}
	
	PointExtension(Point point){
		this.x = point.x;
		this.y = point.y;
	}
	
	PointExtension(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void multiplyWithInt(int factor) {
		this.x = this.x * factor;
		this.y = this.x * factor;
	}
	
	public void translate(Point point) {
		this.x = this.x + point.x;
		this.y = this.y + point.y;
	}
	
	public void translateNegative(Point point) {
		this.x = this.x - point.x;
		this.y = this.y - point.y;
	}
	
}





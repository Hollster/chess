package chess;

public class Piece {
	public String color;
	public Pattern pattern;

	Piece (){
		this("");
	}
	
	Piece(String color){
		this.color = color;
	}

//	
//	public void updateColor(String color) {
//		this.color = color;
//	}
//	
//	public void move(String newPosition) {
//		PlayerInput.checkEntryValidity(newPosition);
//	}
//	
	
//	public void checkMovementValidity(String newPosition) {
//		// is currentPosition + pattern = newPosition
//		
//	}
}

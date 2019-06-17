package chess;

public class Piece {
	public String name;
	public String color;
	private Pattern pattern;
	
	
	Piece(String name, String color){
		this(name);
		this.color = color;
	}
	
	Piece (String name){
		this.name = name;
	}
	

	
	public void updateColor(String color) {
		this.color = color;
	}
	
	public void move(String newPosition) {
		checkEntryValidity(newPosition);
	}
	
	public boolean checkEntryValidity(String newPosition) {
		if(newPosition.length() != 2) {
			Help.checkForHelp(newPosition);
			System.out.println("Please enter the new position in the following format: Letter, Number (A1 - I8)");
			return false;
		} else {
			if (Character.toUpperCase(newPosition.charAt(0)) >= 'A' && Character.toUpperCase(newPosition.charAt(0)) <= 'I' 
					&& newPosition.charAt(1) >= '1' && newPosition.charAt(1) <= '9'){
				return true;
			} else {
				return false;
			}
		}
	}
	
	public void checkMovementValidity(String newPosition) {
		// is currentPosition + pattern = newPosition
		
	}
}

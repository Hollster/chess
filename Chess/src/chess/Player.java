package chess;

public class Player {
	String name;
	String color;
	boolean isActive;
	boolean hasLost;
	
	Player(String name, String color, boolean isActive){
		this.name = name;
		this.color = color;
		this.isActive = isActive;
	}

	public void movePiece(String originTile, String targetTile) {
		
	}
	
	public void giveUp() {
		this.hasLost = true;
	}
	

	private void getTileFromCoordinates(String coordinates) {
		if (isValidTile(coordinates)) {
	//		Board.board[1][0];
		}
	}
	
	private boolean isValidTile(String coordinates) {
		if (coordinates.length() == 2) {
			char letter = Character.toUpperCase(coordinates.charAt(0));
			char number = coordinates.charAt(1);
			if (letter >= 'A' && letter <= 'H' && number >= 1 && number <= 8)
			{
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
}

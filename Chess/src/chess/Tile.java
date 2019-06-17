package chess;

public class Tile {

	private Piece currentPiece;
	private String color;
	public char[] colorIndicator;
	
	Tile(String color, Piece piece){
		this(color);
		this.currentPiece = piece;
	}
	
	Tile(String color){
		this.color = color;
		if (color == "white") {
			colorIndicator =  new char[]{'[', ']'};
		}
		else {
			colorIndicator =  new char[]{'{', '}'};
		}
	}

	public String getTileString () {
		if (currentPiece != null) {
			return colorIndicator[0] + " " + currentPiece.color.charAt(0) + currentPiece.getClass().getName().charAt(6) + " " + colorIndicator[1];
		} else {
			return colorIndicator[0] + "    " + colorIndicator[1];
		}	
	}
	
	public void setPiece(Piece piece) {
		currentPiece = piece;
	}
	
	public void removePiece() {
		currentPiece = null;
	}
	
	public Piece getPiece() {
		return currentPiece;
	}
	
	public String updateTile() {
		removePiece();
		return getTileString();
	}
	
	public String updateTile(Piece piece) {
		setPiece(piece);
		return getTileString();
	}
}
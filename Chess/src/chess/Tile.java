package chess;

public class Tile extends Board{
	//public boolean TileIsWhite;
	//private boolean occupied;
	private int[] idCoordinates;
	public String content;

	
	Tile(String tileColor){
		if(tileColor == "white") {
			this.content = "[    ]";
		} else {
			this.content = "{    }";
		}
	}
	
	private int[] getNumberCoordinates(String inputCoordinates) {
		idCoordinates = new int[2];
		this.idCoordinates[0] = (int)inputCoordinates.charAt(0) - (int)'A'; 
		this.idCoordinates[1] = (int)inputCoordinates.charAt(0); 
		return idCoordinates;
	}
	
	public void updateTile(Piece piece) {
			addPieceToTile(piece);
	}
	
	private void removePieceFromTile(int[] idCoordinates) {
	//	board[idCoordinates[0]][idCoordinates[1]] = this.emptyTile;
	}
	
	public Tile addPieceToTile(Piece piece) {
		this.content = this.content.replace("    ", " " + piece.name + " ");
		return this;
	}
	
	private boolean isOccupied(int[] idCoordinates){
//		if (board[idCoordinates[0]][idCoordinates[1]] == this.emptyTile){
//			return false;
//		} else {
//			return true;
//		}
		return true;
	}
}
package chess;

public class Tile extends Board{
	//public boolean TileIsWhite;
	//private boolean occupied;
	private int[] idCoordinates;
	private String emptyTile;

	
	Tile(String inputCoordinates, boolean isWhiteTile){
		if(isWhiteTile) {
			this.emptyTile = "[    ]";
		} else {
			this.emptyTile = "{    }";
		}
		idCoordinates = getNumberCoordinates(inputCoordinates);
	}
	
	private int[] getNumberCoordinates(String inputCoordinates) {
		idCoordinates = new int[2];
		this.idCoordinates[0] = (int)inputCoordinates.charAt(0) - (int)'A'; 
		this.idCoordinates[1] = (int)inputCoordinates.charAt(0); 
		return idCoordinates;
	}
	
	public void updateTile(String inputCoordinates) {
		idCoordinates = getNumberCoordinates(inputCoordinates);
		if(isOccupied(idCoordinates)) {
			removePieceFromTile();
		} else {
			addPieceToTile(idCoordinates);
		}
	}
	
	private void removePieceFromTile(int[] idCoordinates) {
		board[idCoordinates[0]][idCoordinates[1]] = this.emptyTile;
	}
	
	private void addPieceToTile(int[] idCoordinates, String pieceName) {
		board[idCoordinates[0]][idCoordinates[1]] = this.emptyTile.replace("    ", " " + pieceName + " ");
	}
	
	private boolean isOccupied(int[] idCoordinates){
		if (board[idCoordinates[0]][idCoordinates[1]] == this.emptyTile){
			return false;
		} else {
			return true;
		}
	}
}
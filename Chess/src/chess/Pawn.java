package chess;

public class Pawn extends Piece{
boolean hasMoved;
	
	Pawn(int[] position, String color){
		super.position = position;
		super.limitOfReach = 1;
		this.color = color;
		super.color = color;
		super.directions = "black".equals(super.color) ? 
				new int[][] {{0, 1}, {1, 1}, {-1, 1}, {0, 2}} 
				: new int[][] {{0, -1}, {1, -1},  {-1, -1}, {0, -2}};
	}
	
	@Override
	public void updatePossibleTargetLocations(){
		super.possibleTargetLocations.clear();
		if(noPieceInFrontOfMe()) {
			super.possibleTargetLocations.add(Vectors.addVectors(this.position, this.directions[0]));
			if(this.directions[3] != null && noOtherPieceInFrontOfMe()) {
				super.possibleTargetLocations.add(Vectors.addVectors(this.position, this.directions[3]));
			}
		}
		for(int i = 1; i < 3; i++) {
			if(pieceDiagonalInFrontOfMe(this.directions[i])) {
				super.possibleTargetLocations.add(Vectors.addVectors(this.position, this.directions[i]));
			}
		}
		
	}
	
	private boolean pieceDiagonalInFrontOfMe(int[] direction) {
		String opponentColor = "white".equals(this.color) ? "black" : "white";
		for(Piece piece : MainChess.getPlayer(opponentColor).getPiecesOnBoard()) {
			if(Vectors.areEqual(piece.position, Vectors.addVectors(this.position, direction))){
				return true;
			}
		}
		return false;
	}
	
	private boolean noPieceInFrontOfMe() {
		for(Piece piece : MainChess.getPlayer(true).getPiecesOnBoard()) {
			if(Vectors.areEqual(piece.position, Vectors.addVectors(this.position, this.directions[0]))){
				return false;
			}
		}
		for(Piece piece : MainChess.getPlayer(false).getPiecesOnBoard()) {
			if(Vectors.areEqual(piece.position, Vectors.addVectors(this.position, this.directions[0]))){
				return false;
			}
		}
		return true;
	}
	
	private boolean noOtherPieceInFrontOfMe() {
		for(Piece piece : MainChess.getPlayer(true).getPiecesOnBoard()) {
			if(Vectors.areEqual(piece.position, Vectors.addVectors(this.position, this.directions[3]))){
				return false;
			}
		}
		for(Piece piece : MainChess.getPlayer(false).getPiecesOnBoard()) {
			if(Vectors.areEqual(piece.position, Vectors.addVectors(this.position, this.directions[3]))){
				return false;
			}
		}
		return true;
	}
}
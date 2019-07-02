package chess;

import java.awt.Point;

public class MainChess {
	private static Player player1;
	private static Player player2;
	public static final int LETTER_TO_NUMBER_DIFFERENCE = 65;
	
	public static void main(String[] args) {
		PlayerInput.makeScanner();
		player1 = makePlayer(1, "white", false);
		player2 = makePlayer(2, "black", true);
		initializePossiblePositions();
		Board.populate();
		Board.print();
		System.out.println();
		while(!player1.hasLost || !player2.hasLost) {
			toggleActivePlayer();
			oneTurn();
			Board.populate();
			Board.print();
		} 
		PlayerInput.closeScanner();
		System.out.println(getPlayer(false).name + " wins!");	
	}
	
	private static void initializePossiblePositions() {
		for(Piece piece : player1.getPiecesOnBoard()) {
			piece.updatePossibleTargetLocations();
		}
		for(Piece piece : player2.getPiecesOnBoard()) {
			piece.updatePossibleTargetLocations();
		}
	}
	
	public static void toggleActivePlayer() {
		player1.toggleActivity();
		player2.toggleActivity();
	}
		
	static void oneTurn() {
		Piece chosenPiece = PlayerInput.getPiece();
		chosenPiece.move();
		checkForMate();
		if(inCheck(getPlayer(false))) {
			System.out.println("CHECK!");
		}
		getPlayer(false).deleteRemovedPiece();
	}
	
	private static void capture(Player opponent, Point positionOfMovedPiece) {
		for(Piece piece : opponent.getPiecesOnBoard()) {
			if(positionOfMovedPiece.equals(piece)) {
				opponent.removePiece(piece);
				opponent.updateAllPossiblePieces();
				return;
			}
		}
	}
	
	static boolean inCheck(Player playerInCheck) {
		Player possibleWinner = getOtherPlayer(playerInCheck);
		Point kingPosition = playerInCheck.getKingPosition();
		possibleWinner.updateAllPossiblePieces();
		for (Piece piece : possibleWinner.getPiecesOnBoard()) {
			for (Point location : piece.possibleTargetLocations) {
				if(location.equals(kingPosition)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static void undoMove(Player currentPlayer, Piece piece, Point previousLocation) {
		piece.setLocation(previousLocation);
		getOtherPlayer(currentPlayer).restorePiece();
		getOtherPlayer(currentPlayer).updateAllPossiblePieces();
	}
	
	private static void checkForMate() {
		Point originalPosition = new Point(); 
		for(Piece piece: getPlayer(false).getPiecesOnBoard()) { 
			originalPosition.setLocation(piece.x, piece.y);
			for (Point possibleLocation : piece.possibleTargetLocations) {
				piece.setLocation(possibleLocation);
				capture(getPlayer(true), piece);
				if(!inCheck(getPlayer(false))) {
					undoMove(getPlayer(false), piece, originalPosition);
					return;
				} else {
					undoMove(getPlayer(false), piece, originalPosition);
				}
			}
		}
		System.out.println("CHECK MATE\n" + getPlayer(true).name + " wins!");
		Board.populate();
		Board.print();
		System.exit(0);
	}

	private static Player makePlayer(int playerNumber, String playerColor, boolean isActive) {
		return new Player(PlayerInput.getPlayerName(playerNumber, playerColor), playerColor, isActive);
	}
		
	static Player getPlayer(String color) {
		if ("white".equals(color)){
			return player1;
		} else if ("black".equals(color)) {
			return player2;
		} else {
			System.err.println("3 - Something went wrong with fetching the player, look into getPlayer in MainChess");
			System.exit(4);
			return player1;
		}
	}
	
	static Player getPlayer(boolean activeIsTrue) {
		return player1.isActive == activeIsTrue ? player1 : player2;
	}
	
	static Player getOtherPlayer(Player inputPlayer) {
		return inputPlayer == player1 ? player2 : player1;
	}
	
}
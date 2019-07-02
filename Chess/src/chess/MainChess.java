package chess;

import java.awt.Point;

public class MainChess {
	private static Player player1;
	private static Player player2;
	public static final int LETTER_TO_NUMBER_DIFFERENCE = 65;
	//private static final int NUMBER_TO_NUMBER_DIFFERENCE = 49;
	
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
		Piece chosenPiece = PlayerInput.getPiece();//getChosenPiece();
		movePiece(chosenPiece);
		checkForMate();
		if(inCheck(getPlayer(false))) {
			System.out.println("CHECK!");
		}
		getPlayer(false).deleteRemovedPiece();
	}


	
	

	static void movePiece(Piece piece) {
		String secondPlayerInput;
		Point previousLocation = new Point(piece.x, piece.y);
		do {
			secondPlayerInput = PlayerInput.targetLocation(piece);
			if ("REDO".equals(secondPlayerInput)) {
				oneTurn();
				return;
			}
			if(!Board.entryIsOnBoard(secondPlayerInput)) {
				System.err.println("7 - That's not a valid tile. Please choose again.");
				movePiece(piece);
				return;}
		} while(!moveIsValid(piece, PlayerInput.entryToPoint(secondPlayerInput)));
		piece.setLocation(PlayerInput.entryToPoint(secondPlayerInput));
		capture(getPlayer(false), piece);
		if(inCheck(getPlayer(true))) {
			System.err.println("6 - Not valid, you would be in check");
			undoMove(getPlayer(true), piece, previousLocation);
			movePiece(piece);
			return;
		}
		if(piece instanceof Pawn) {
			piece.directions[3] = null;
			if(piece.y == Board.NUMBER_OF_ROWS - 1 || piece.y == 0) {
				promotion(piece);
			}
		}
		getPlayer(false).updateAllPossiblePieces();
		getPlayer(true).updateAllPossiblePieces();
	}

	private static void undoMove(Player currentPlayer, Piece piece, Point previousLocation) {
		piece.setLocation(previousLocation);
		getOtherPlayer(currentPlayer).restorePiece();
		getPlayer(true).updateAllPossiblePieces();
		getPlayer(false).updateAllPossiblePieces();
	}
	
	private static void undoMove(Player currentPlayer, Piece piece, Point previousLocation, boolean update) {
		piece.setLocation(previousLocation);
		getOtherPlayer(currentPlayer).restorePiece();
		getOtherPlayer(currentPlayer).updateAllPossiblePieces();
		if(update) {
			currentPlayer.updateAllPossiblePieces();
		}
	}
	
	private static void checkForMate() {
		Point originalPosition = new Point(); 
		for(Piece piece: getPlayer(false).getPiecesOnBoard()) { 
			originalPosition.setLocation(piece.x, piece.y);
			for (Point possibleLocation : piece.possibleTargetLocations) {
				piece.setLocation(possibleLocation);
				capture(getPlayer(true), piece, false);
				if(!inCheck(getPlayer(false))) {
					undoMove(getPlayer(false), piece, originalPosition, false);
					return;
				} else {
					undoMove(getPlayer(false), piece, originalPosition, false);
				}
			}
		}
		System.out.println("CHECK MATE\n" + getPlayer(true).name + " wins!");
		Board.populate();
		Board.print();
		System.exit(0);
	}
	

	private static void promotion(Piece piece) {
		String promotionAnswer = PlayerInput.promotion();
			switch(promotionAnswer) {
			case "QUEEN":
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.x, piece.y, getPlayer(true).color));
				break;
			case "KNIGHT":
				getPlayer(true).getPiecesOnBoard().add(new Knight (piece.x, piece.y, getPlayer(true).color));
				break;
			case "ROOK":
				getPlayer(true).getPiecesOnBoard().add(new Rook (piece.x, piece.y, getPlayer(true).color));
				break;
			case "BISHOP":
				getPlayer(true).getPiecesOnBoard().add(new Bishop (piece.x, piece.y, getPlayer(true).color));
				break;
			default:
				return;
			}
			getPlayer(true).getPiecesOnBoard().remove(piece);
		}
	
	private static void capture(Player opponent, Point positionOfMovedPiece) {
		for(Piece piece : opponent.getPiecesOnBoard()) {
			if(positionOfMovedPiece.equals(piece)) {
				opponent.removePiece(piece);
				getPlayer(true).updateAllPossiblePieces();
				getPlayer(false).updateAllPossiblePieces();
				return;
			}
		}
	}
	
	private static void capture(Player opponent, Point positionOfMovedPiece, boolean update) {
		for(Piece piece : opponent.getPiecesOnBoard()) {
			if(positionOfMovedPiece.equals(piece)) {
				opponent.removePiece(piece);
				opponent.updateAllPossiblePieces();
				if(update) {
					getOtherPlayer(opponent).updateAllPossiblePieces();
				}
				return;
			}
		}
	}
	
	private static boolean inCheck(Player playerInCheck) {
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

	
	private static boolean moveIsValid(Piece piece, Point targetCoordinates) {
		for(Point possibleTargetLocation : piece.possibleTargetLocations) {
			if(targetCoordinates.equals(possibleTargetLocation)) {
				return true;
			}
		}
		System.err.println("6 - Sorry, you can't go there. Either something is in the way or the movement pattern does not match");
		return false;
	}
	
	

	

	

	
	// Create Players
	public static Player makePlayer(int playerNumber, String playerColor, boolean isActive) {
		return new Player(PlayerInput.getPlayerName(playerNumber, playerColor), playerColor, isActive);
	}
	

	
	public static Player getPlayer(String color) {
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
	
	public static Player getPlayer(boolean activeIsTrue) {
		return player1.isActive == activeIsTrue ? player1 : player2;
	}
	
	public static Player getOtherPlayer(Player inputPlayer) {
		return inputPlayer == player1 ? player2 : player1;
	}
	
	// Evaluate Entry Validity
		// General
	

	

	
//	private static boolean pieceBelongsToActivePlayer(Player activePlayer, Piece currentPiece) {
//		if(activePlayer.color.equals(currentPiece.color)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	private static int[] inputToCoordinates(String playerInput) {
//		int[] tileCoordinates = new int[2];
//		tileCoordinates[0] = playerInput.charAt(0) - LETTER_TO_NUMBER_DIFFERENCE;
//		tileCoordinates[1] = playerInput.charAt(1) - NUMBER_TO_NUMBER_DIFFERENCE;
//		return tileCoordinates;
//	}
	
	
}
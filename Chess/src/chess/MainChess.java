package chess;

import java.util.ArrayList;

public class MainChess {
	private static Player player1;
	private static Player player2;
	public static final int letterToNumberDifference = 65;
	private static final int numberToNumberDifference = 49;
	
	public static void main(String[] args) {
		PlayerInput.makeScanner();
		player1 = makePlayer(1, "white", false);
		player2 = makePlayer(2, "black", true);
		initializePossiblePositions();
		Board.populateBoard();
		Board.printBoard();
		System.out.println();
		while(!player1.hasLost || !player2.hasLost) {
			toggleActivePlayer();
			oneTurn();
			Board.populateBoard();
			Board.printBoard();
//			for (int[]location : getPlayer("white").getPiecesOnBoard().get(7).possibleTargetLocations) {
//				System.err.println(location[0] + ", " + location[1]);
//			}
//			
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
//		System.out.println(getPlayer("white").name + "s Spielfiguren können hier stehen");
//		for (Piece piece : getPlayer("white").getPiecesOnBoard()) {
//			System.out.println(piece.getClass().getSimpleName() + ", Position: " + piece.position[0] + ", " + piece.position[1]);
//			for(int[] position : piece.possibleTargetLocations) {
//				System.out.println("------Possible Position: " + position[0] + ", " + position[1]);
//			}
//		}
//		
//		System.out.println(getPlayer("black").name + "s Spielfiguren können hier stehen");
//		for (Piece piece : getPlayer("black").getPiecesOnBoard()) {
//			System.out.println(piece.getClass().getSimpleName() + ", Position: " + piece.position[0] + ", " + piece.position[1]);
//			for(int[] position : piece.possibleTargetLocations) {
//				System.out.println("------Possible Position: " + position[0] + ", " + position[1]);
//			}
//		}
	}
	
	
		
	private static void oneTurn() {
		Piece chosenPiece = getChosenPiece();
		move(chosenPiece);
		if(inCheck(getPlayer(false), getPlayer(true))) {
			System.out.println("CHECK!");
			checkForMate();
		}
//		System.out.println(getPlayer(false).name + "s Spielfiguren können hier stehen");
//		for (Piece piece : getPlayer(false).getPiecesOnBoard()) {
//			System.out.println(piece.getClass().getSimpleName() + ", Position: " + piece.position[0] + ", " + piece.position[1]);
//			for(int[] position : piece.possibleTargetLocations) {
//				System.out.println("------Possible Position: " + position[0] + ", " + position[1]);
//			}
//		}
	}

	private static void checkForMate() {
		int[] originalPosition;
		for(Piece piece: getPlayer(false).getPiecesOnBoard()) {
			originalPosition = piece.position;
			for (int[] possibleLocation : piece.possibleTargetLocations) {
				piece.position = possibleLocation;
				mateCapture(possibleLocation);
				if(!inCheck(getPlayer(false), getPlayer(true))) {
					piece.position = originalPosition;
					if(getPlayer(true).getRemovedPiece() != null) {
						getPlayer(true).restorePiece();
					}
					getPlayer(true).updateAllPossiblePieces();
					getPlayer(true).updateAllPossiblePieces();
					return;
				} else {
					piece.position = originalPosition;
					if(getPlayer(true).getRemovedPiece() != null) {
						getPlayer(true).restorePiece();
					}
					getPlayer(true).updateAllPossiblePieces();
					getPlayer(true).updateAllPossiblePieces();
				}
			}
		}
		
		System.out.println("MATE\n" + getPlayer(true).name + " wins!");
		System.exit(0);
	}
	
	private static void mateCapture(int[] positionOfCapture) {
		for(Piece piece : getPlayer(true).getPiecesOnBoard()) {
			for(int[] possibleLocation : piece.getPossibleTargetLocations()) {
				if(Vectors.areEqual(positionOfCapture, possibleLocation)) {
					getPlayer(true).removePieceForMate(piece);
					getPlayer(true).updateAllPossiblePieces();
					return;
				}
			}
		}
	}
	
	private static void move(Piece piece) {
		String secondPlayerInput;
		int[] previousPosition = piece.position;
		do {
			secondPlayerInput = PlayerInput.getPlayerInput(getPlayer(true), "Hey, " + getPlayer(true).name + "! Your move! Select your target tile!" 
					+ "\nEnter redo to choose another tile and help if you need help");
			checkSpecialPlayerInput(secondPlayerInput);
			if ("REDO".equals(secondPlayerInput)) {
				oneTurn();
			}
			if(!entryIsOnBoard(secondPlayerInput)) {
				System.err.println("7 - That's not a valid tile. Please choose again.");
				move(piece);
				return;
			}
		} while(!moveIsValid(piece, entryToCoordinates(secondPlayerInput)));
		piece.position = entryToCoordinates(secondPlayerInput);
		updatePlayersTargetLocations(getPlayer(false));
		capture(getPlayer(false), piece.position);
		// TODO
		// hier muss noch nen capture hin
		if(inCheck(getPlayer(true), getPlayer(false))) {
			System.err.println("6 - Not valid, you would be in check");
			uncapture(getPlayer(false));
			piece.position = previousPosition;
			move(piece);
			return;
		}
		capture(getPlayer(false), piece.position);
		if(piece instanceof Pawn) {
			piece.directions[3] = null;
			if(piece.position[1] == 7 || piece.position[1] == 0) {
				promotion(piece);
			}
		}
		updatePlayersTargetLocations(getPlayer(true));
		updatePlayersTargetLocations(getPlayer(false));
	}
	
	private static void uncapture(Player opponent) {
		opponent.restorePiece();
	}
	
	private static void promotion(Piece piece) {
		String promotionAnswer;
		do {
			promotionAnswer = PlayerInput.getPlayerInput("Would you like to promote your pawn? (yes / no)");
		} while (!"YES".equals(promotionAnswer) && !"NO".equals(promotionAnswer));
		if("YES".equals(promotionAnswer)) {
			do {
				promotionAnswer = PlayerInput.getPlayerInput("Please choose from the following options? + "
						+ "\n Queen Knight Rook Bishop");
			} while (!"QUEEN".equals(promotionAnswer) && !"KNIGHT".equals(promotionAnswer)
					&& !"ROOK".equals(promotionAnswer) && !"BISHOP".equals(promotionAnswer));
			switch(promotionAnswer) {
			case "QUEEN":
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.position, getPlayer(true).color));
			case "KNIGHT":
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.position, getPlayer(true).color));
			case "ROOK":
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.position, getPlayer(true).color));
			case "BISHOP":
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.position, getPlayer(true).color));
			}
			getPlayer(true).getPiecesOnBoard().remove(piece);
		}
	}
	
	private static void capture(Player opponent, int[] positionOfMovedPiece) {
		for(Piece piece : opponent.getPiecesOnBoard()) {
			if (Vectors.areEqual(piece.position, positionOfMovedPiece)) {
				opponent.removePiece(piece);
				return;
			}
		}
	}
	
	private static boolean inCheck(Player playerInCheck, Player possibleWinner) {
		int[] kingPosition = playerInCheck.getKingPosition();
		possibleWinner.updateAllPossiblePieces();
		for (Piece piece : possibleWinner.getPiecesOnBoard()) {
			for (int[] position : piece.possibleTargetLocations) {
				if(Vectors.areEqual(kingPosition, position)) {
					return true;
				}
			}
		}
		return false;
	}
	

	
	private static boolean inCheck(Player activePlayer, Player opponent, int[] kingPosition) {
		for (Piece piece : opponent.getPiecesOnBoard()) {
			for (int[] position : piece.possibleTargetLocations) {
				if(Vectors.areEqual(kingPosition, position)) {
					return true;
				}
			}
		}
		return false;
	}

	private static void updatePlayersTargetLocations(Player player) {
		for(Piece piece : player.getPiecesOnBoard()) {
			piece.updatePossibleTargetLocations();
		}
	}
	
	private static boolean moveIsValid(Piece piece, int[] targetCoordinates) {
		for(int[] possibleTargetLocation : piece.possibleTargetLocations) {
			if(Vectors.areEqual(targetCoordinates, possibleTargetLocation)) {
				return true;
			}
		}
		System.err.println("6 - Sorry, you can't go there. Either something is in the way or the movement pattern does not match");
		return false;
	}
	
	
	private static Piece getChosenPiece() {
		String firstPlayerInput;
		Piece chosenPiece = null;
		do {
			firstPlayerInput = PlayerInput.getPlayerInput(getPlayer(true), "Hey, " 
					+ getPlayer(true).name + "! Your move! Select your piece to move! "
					+ "Enter help if you need help.");
			checkSpecialPlayerInput(firstPlayerInput);
			chosenPiece = getPieceFromValidEntry(firstPlayerInput);
		} while (chosenPiece == null);
		return  chosenPiece;
	}
	
	private static Piece getPieceFromValidEntry(String playerEntry) {
		if(entryIsOnBoard(playerEntry)) {
			for(Piece piece : getPlayer(true).getPiecesOnBoard()) {
				if (Vectors.areEqual(piece.position, entryToCoordinates(playerEntry))){
					return piece;
				}
			}
			System.err.println("5 - You do not have a figure here. Please choose again.");
			return null;
		} else {
			System.err.println("4 - This is not a valid entry. Please enter in this format: LetterNumber");
			return null;
		}
	}
	
	private static int[] entryToCoordinates(String playerEntry) {
		int[]coordinates = new int[2];
		coordinates[0] = (int)playerEntry.charAt(0) - letterToNumberDifference;
		coordinates[1] = Board.numberOfRows - Character.getNumericValue(playerEntry.charAt(1));
		return coordinates;
	}
	
	// Create Players
	public static Player makePlayer(int playerNumber, String playerColor, boolean isActive) {
		return new Player(PlayerInput.getPlayerName(playerNumber, playerColor), playerColor, isActive);
	}
	
	// isActive Parameter
	public static void toggleActivePlayer() {
		player1.toggleActivity();
		player2.toggleActivity();
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
	
	private static boolean entryIsOnBoard(String playerInput) {
		if(entryHasCorrectLength(playerInput)) {
			char letter = playerInput.charAt(0);
			char number = playerInput.charAt(1);
			if(letter >= 'A' && letter <= 'H' && number >='1' && number <='8') {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private static boolean entryHasCorrectLength(String playerInput) {
		if (playerInput.length() == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean pieceBelongsToActivePlayer(Player activePlayer, Piece currentPiece) {
		if(activePlayer.color.equals(currentPiece.color)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static int[] inputToCoordinates(String playerInput) {
		int[] tileCoordinates = new int[2];
		tileCoordinates[0] = playerInput.charAt(0) - letterToNumberDifference;
		tileCoordinates[1] = playerInput.charAt(1) - numberToNumberDifference;
		return tileCoordinates;
	}
	
		// Special Entry
	private static void checkSpecialPlayerInput(String firstPlayerInput) {
		if ("HELP".equals(firstPlayerInput)) {
			Help.startHelp();
		} else if ("DRAW".equals(firstPlayerInput)) {
			//TODO
			itsADraw();
		} else if ("QUIT".equals(firstPlayerInput)) {
			playerQuit();
		} 
	}
	
	private static void playerQuit() {
		String doesPlayerWantToQuit = PlayerInput.getPlayerInput("Are you sure you want to quit? (yes / no)");
		if("YES".equals(doesPlayerWantToQuit)) {
			System.out.println(getPlayer(false).name + " wins!");
			System.exit(0);
		} else if ("NO".equals(doesPlayerWantToQuit)) {
			return;
		} else {
			System.err.println("2 - Sorry, I did not get that. Please answer correctly!");
			playerQuit();
		}
	}
	
	private static void itsADraw(){
		if(checkDrawAnswers("Are you sure this is a draw? (yes / no)") 
				&& checkDrawAnswers(getPlayer(false).name + ", do you think this is a draw, too? (yes / no)")) {
			System.out.println("It's a draw!");
			System.exit(0);
		} else {
			System.out.println("Not a draw, return to game!");
			return;
		}
	}
	
	private static boolean checkDrawAnswers(String message) {
		String activePlayerDraw = PlayerInput.getPlayerInput(message);
		if("YES".equals(activePlayerDraw)) {
			return true;
		} else if ("NO".equals(activePlayerDraw)) 
		{
			return false;
		} else {			
			System.err.println("1 - Sorry, I did not get that. Please answer correctly!");
			checkDrawAnswers("Is this a draw? (yes / no)");
		}
		return false;
	}
	
}
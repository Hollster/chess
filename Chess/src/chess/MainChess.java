package chess;


import java.awt.Point;

public class MainChess {
	private static Player player1;
	private static Player player2;
	public static final int LETTER_TO_NUMBER_DIFFERENCE = 65;
	private static final int NUMBER_TO_NUMBER_DIFFERENCE = 49;
	
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
	
	
		
	private static void oneTurn() {
		Piece chosenPiece = getChosenPiece();
		movePiece(chosenPiece);
		if(inCheck(getPlayer(false), getPlayer(true))) {
			System.out.println("CHECK!");
			checkForMate();
		}
	}

	private static void checkForMate() {
		for(Piece piece: getPlayer(false).getPiecesOnBoard()) { // get all of the pieces of the player who is currently in check
			Point originalPosition = new Point(piece.x, piece.y);
			for (Point possibleLocation : piece.possibleTargetLocations) { // get all of that pieces possible Target locations
				mateCapture(possibleLocation);
				if(!inCheck(getPlayer(false), getPlayer(true))) {
					piece.setLocation(originalPosition);
					piece.y = originalPosition.y;
					if(getPlayer(true).getRemovedPiece() != null) {
						getPlayer(true).restorePiece();
					}
					getPlayer(true).updateAllPossiblePieces();
					getPlayer(false).updateAllPossiblePieces();
					return;
				} else {
					piece.setLocation(originalPosition);
					if(getPlayer(true).getRemovedPiece() != null) {
						getPlayer(true).restorePiece();
					}
				}
			}
		}
		
		System.out.println("MATE\n" + getPlayer(true).name + " wins!");
		Board.populateBoard();
		Board.printBoard();
		System.exit(0);
	}
	
	private static void mateCapture(Point positionOfCapture) {
		for(Piece piece : getPlayer(true).getPiecesOnBoard()) {
			for(Point possibleLocation : piece.getPossibleTargetLocations()) {
				if(positionOfCapture.equals(possibleLocation)) {
					getPlayer(true).removePieceForMate(piece);
					getPlayer(true).updateAllPossiblePieces();
					return;
				}
			}
		}
	}
	
	private static void movePiece(Piece piece) {
		String secondPlayerInput;
		Point previousLocation = new Point(piece.x, piece.y);
		do {
			secondPlayerInput = PlayerInput.getPlayerInput(getPlayer(true), "Hey, " + getPlayer(true).name + "! Your move! Select your target tile!" 
					+ "\nEnter redo to choose another tile and help if you need help");
			checkSpecialPlayerInput(secondPlayerInput);
			if ("REDO".equals(secondPlayerInput)) {
				oneTurn();
			}
			if(!entryIsOnBoard(secondPlayerInput)) {
				System.err.println("7 - That's not a valid tile. Please choose again.");
				movePiece(piece);
				return;
			}
		} while(!moveIsValid(piece, entryToPoint(secondPlayerInput)));
		piece.setLocation(entryToPoint(secondPlayerInput));
		updatePlayersTargetLocations(getPlayer(false));
		capture(getPlayer(false), piece);
		if(inCheck(getPlayer(true), getPlayer(false))) {
			System.err.println("6 - Not valid, you would be in check");
			uncapture(getPlayer(false));
			piece.setLocation(previousLocation);
			movePiece(piece);
			return;
		}
		capture(getPlayer(false), piece);
		// Pawn special rules
		if(piece instanceof Pawn) {
			// move 2 spaces in the beginning
			piece.directions[3] = null;
			//promotion
			if(piece.y == 7 || piece.y == 0) {
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
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.x, piece.y, getPlayer(true).color));
			case "KNIGHT":
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.x, piece.y, getPlayer(true).color));
			case "ROOK":
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.x, piece.y, getPlayer(true).color));
			case "BISHOP":
				getPlayer(true).getPiecesOnBoard().add(new Queen (piece.x, piece.y, getPlayer(true).color));
			}
			getPlayer(true).getPiecesOnBoard().remove(piece);
		}
	}
	
	private static void capture(Player opponent, Point positionOfMovedPiece) {
		for(Piece piece : opponent.getPiecesOnBoard()) {
			if(piece.equals(positionOfMovedPiece)) {
				opponent.removePiece(piece);
				return;
			}
		}
	}
	
	private static boolean inCheck(Player playerInCheck, Player possibleWinner) {
		Point kingPosition = playerInCheck.getKingPosition();
		possibleWinner.updateAllPossiblePieces();
		for (Piece piece : possibleWinner.getPiecesOnBoard()) {
			for (Point location : piece.possibleTargetLocations) {
				if(location.equals(kingPosition)) {//Vectors.areEqual(kingPosition, location)) {
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
	
	private static boolean moveIsValid(Piece piece, Point targetCoordinates) {
		for(Point possibleTargetLocation : piece.possibleTargetLocations) {
			if(targetCoordinates.equals(possibleTargetLocation)) {
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
				if (piece.equals(entryToPoint(playerEntry))){//Vectors.areEqual(piece.position, entryToCoordinates(playerEntry))){
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
	
	private static Point entryToPoint(String playerEntry) {
		int x = (int)playerEntry.charAt(0) - LETTER_TO_NUMBER_DIFFERENCE;
		int y = Board.NUMBER_OF_ROWS - Character.getNumericValue(playerEntry.charAt(1));
		return new Point(x, y);
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
		tileCoordinates[0] = playerInput.charAt(0) - LETTER_TO_NUMBER_DIFFERENCE;
		tileCoordinates[1] = playerInput.charAt(1) - NUMBER_TO_NUMBER_DIFFERENCE;
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
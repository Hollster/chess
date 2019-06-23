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
		move(chosenPiece);
		//TODO 
		// hier liegt der Fehler, überlegen was passiert, wenn der Spieler ins Schach kommt undnen neuer Zug anfängt
		// was wird dann nicht vernünftig geupdated
		updatePlayersTargetLocations(getPlayer(true));
		if(inCheck(getPlayer(false), getPlayer(true))) {
			System.out.println("CHECK!");
		}
	}

	
	private static void move(Piece piece) {
		String secondPlayerInput;
		int[] enteredCoordinates;
		int[] previousPosition = piece.position;
		do {
			secondPlayerInput = PlayerInput.getPlayerInput(getPlayer(true), "Hey, " + getPlayer(true).name + "! Your move! Select your target tile!" 
					+ "\nEnter redo to choose another tile and help if you need help");
			checkSpecialPlayerInput(secondPlayerInput);
			if ("REDO".equals(secondPlayerInput)) {
				oneTurn();
			}
			enteredCoordinates = entryToCoordinates(secondPlayerInput);
		} while(!moveIsValid(piece, enteredCoordinates));
		piece.position = enteredCoordinates;
		updatePlayersTargetLocations(getPlayer(false));
		if(inCheck(getPlayer(true), getPlayer(false))) {
			System.out.println("Not valid, you would be in check");
			piece.position = previousPosition;
			move(piece);
		}
		
		// hier sind wir jetzt wenn der move valid ist
		// +++++++++++++jetzt müssen wir die position von dem piece updaten 
		// +++++++++++++++++und die possible target locations von dem gegner 
		//+++++++++++++++und gucken ob der eigene könig im schach steht. falls ja: müssen wir ne warnung ausgeben und 
		// ++++++++++++++++++nochmal sagen, dass der move nicht geht, weil wir im schach stehen würden
		// ++++++++++++++++dann passiert move nochmal
		// ++++++++++++weil jedes mal nach dem move, der gegner wieder neu kalkuliert, müssen wir da nichts zurücksetzen
		// ++++++++++++das bleibt erst gleich, wenn der move valid ist
		// ist das so, updaten wir auch die possible targetcoordinates von dem eigenen spieler
		// und gucken als nächstes, ob der andere könig im schach steht
		// falls ja, geben wir eine warnung an den gegner aus.
		// und da
	}
	
	private static boolean inCheck(Player activePlayer, Player opponent) {
		int[] kingPosition = activePlayer.getKingPosition();
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
			return null;
		} else {
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
			System.err.println("Something went wrong with fetching the player, look into getPlayer in MainChess");
			System.exit(4);
			return player1;
		}
	}
	
	public static Player getPlayer(boolean activeIsTrue) {
		return player1.isActive == activeIsTrue ? player1 : player2;
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
			System.out.println("Sorry, I did not get that. Please answer correctly!");
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
			System.out.println("Sorry, I did not get that. Please answer correctly!");
			checkDrawAnswers("Is this a draw? (yes / no)");
		}
		return false;
	}
	
}
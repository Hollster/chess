package chess;

public class MainChess {
	private static Player player1;
	private static Player player2;
	public static final int letterToNumberDifference = 65;
	private static final int numberToNumberDifference = 49;
	
	public static void main(String[] args) {
		PlayerInput.makeScanner();
		player1 = makePlayer(1, "white", false);
		player2 = makePlayer(2, "black", true);
		Board.makeBoard();
		Board.populateBoard();
		Board.printBoard();
		System.out.println();
		while(!player1.hasLost || !player2.hasLost) {
			toggleActivePlayer();
			oneTurn();
			Board.printBoard();
		} 
		PlayerInput.closeScanner();
		System.out.println(getInactivePlayer().name + " wins!");	
	}
	

	
	private static void oneTurn() {
		Tile firstTile;
		Tile secondTile;
		firstTile = getFirstTile(player1, player2);
		do {
			secondTile = getSecondTile(player1, player2);
			if(secondTile == null) {
				return;
			}
		} while(!isValidMove(firstTile.getPiece(), firstTile.getCoordinates(), secondTile.getCoordinates()));
		secondTile.updateTile(firstTile.getPiece());
		firstTile.updateTile(); // sollte ich das überschriebene Piece löschen?
	}
	
	
	private static boolean kingIsInCheck(Tile kingTile) {
		//walk in each direction until you find either the edge or a piece
		//if that piece is the opponent's piece, check if the king is in its pattern
		//if the direction was cross and it was: rook, queen => check
		//if the direction was diagonal and it was: bishop, queen => check
		//if the direction was diagonal AND is within 1 and was: pawn => check
		//if the direction is diagonal OR cross AND is within 1 and was: king => check
		//check the 6 knight positions=> only if knight or king was moved
		return false;
	}
	
	//checkTheKnightPosition 

	//updateKingPosition(){}
	
	private static void walkToPieceOrEdge() {
		
	}
	
	private static boolean isValidMove(Piece currentPiece, int[]originCoordinates, int[]targetCoordinates) {
		int[]attemptedMovement = new int[] {originCoordinates[0] - targetCoordinates[0], originCoordinates[1] - targetCoordinates[1]};
		int [] direction = currentPiece.pattern.movementIsValidPattern(attemptedMovement);
		if (direction != null) {
			return !pieceIsInTheWay(direction, originCoordinates, targetCoordinates);
		} else {
			return false;
		}
	}
	
	private static boolean pieceIsInTheWay(int[] direction, int[] originCoordinates, int[] targetCoordinates) {
		while (!Vectors.areEqual(originCoordinates, targetCoordinates)) {
			originCoordinates = Vectors.subtractVectors(originCoordinates, direction);
			if(Board.getTile(originCoordinates).getPiece() != null) {
				return true;
			}
		}
		return false;
	}
	
	private static Tile getFirstTile(Player player1, Player player2) {
		String firstPlayerInput;
		do {
			firstPlayerInput = PlayerInput.getPlayerInput(getActivePlayer(), "Hey, " 
					+ getActivePlayer().name + "! Your move! Select your piece to move! "
					+ "Enter help if you need help.");
			checkSpecialPlayerInput(firstPlayerInput);
		} while (!entryIsTile(firstPlayerInput) || !tileHasPiece(firstPlayerInput) || !pieceBelongsToActivePlayer(getActivePlayer(), Board.getTile(inputToCoordinates(firstPlayerInput)).getPiece()));
		return Board.getTile(inputToCoordinates(firstPlayerInput));
	}
	
	private static Tile getSecondTile(Player player1, Player player2) {
		String secondPlayerInput;
		do {
			secondPlayerInput = PlayerInput.getPlayerInput(getActivePlayer(), "Hey, " + getActivePlayer().name + "! Your move! Select your target tile!" 
					+ "\nEnter redo to choose another tile and help if you need help");
			checkSpecialPlayerInput(secondPlayerInput);
			if ("REDO".equals(secondPlayerInput)) {
				oneTurn();
				return null;
			}
		} while (!entryIsTile(secondPlayerInput) 
				|| (tileHasPiece(secondPlayerInput) &&  pieceBelongsToActivePlayer(getActivePlayer(), Board.getTile(inputToCoordinates(secondPlayerInput)).getPiece())));
		return Board.getTile(inputToCoordinates(secondPlayerInput));
	}
	

	
	// Create Players
	public static Player makePlayer(int playerNumber, String playerColor, boolean isActive) {
		return new Player(PlayerInput.getPlayerName(playerNumber, playerColor), playerColor, isActive);
	}
	
	// isActive Parameter
	public static void toggleActivePlayer() {
		if(player1.isActive) {
			player1.isActive = false;
			player2.isActive = true;
		} else {
			player1.isActive = true;
			player2.isActive = false;
		}
	}
	
	public static Player getActivePlayer() {
		if(player1.isActive) {
			return player1;
		} else {
			return player2;
		}
	}
	
	public static Player getInactivePlayer() {
		if(player1.isActive) {
			return player2;
		} else {
			return player1;
		}
	}
	
	// Evaluate Entry Validity
		// General
	private static boolean entryIsTile(String playerInput) {
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
	
		//Tile and Piece examination
	private static boolean tileHasPiece(String playerInput) {
		if (Board.getTile(inputToCoordinates(playerInput)).getPiece() == null) {
			return false;
		} else {
			return true;
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
			System.out.println(getInactivePlayer().name + " wins!");
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
				&& checkDrawAnswers(getInactivePlayer().name + ", do you think this is a draw, too? (yes / no)")) {
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

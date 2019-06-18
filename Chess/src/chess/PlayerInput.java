package chess;

import java.util.Scanner;

public class PlayerInput {
	private static Scanner scanner;
	private static final int letterToNumberDifference = 64;
	
	public static void makeScanner() {
		scanner = new Scanner(System.in);
	}
	
	public static void closeScanner() {
		scanner.close();
	}
	
	public static Player makePlayer(int playerNumber, String playerColor, boolean isActive) {
		return new Player(getPlayerName(playerNumber, playerColor), playerColor, isActive);
	}

	public static String getPlayerInput(Player activePlayer, String outputMessage) {
		System.out.println();
		String playerInput = scanner.nextLine();
		return playerInput;
	}
	
	private static boolean playerHasQuitTheGame(Player activePlayer, String playerInput) {
		if("q".equals(playerInput)) {
			activePlayer.hasLost = true;
			return true;
		} else {
			return false;
		}
	}

	private static boolean tileHasPiece(String playerInput) {
		int[] tileCoordinates = new int[2];
		tileCoordinates[0] = playerInput.charAt(0) - letterToNumberDifference;
		tileCoordinates[1] = (int)playerInput.charAt(1);
		if (Board.getTile(tileCoordinates).getPiece() == null) {
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
	
	private static boolean entryIsTile(String playerInput) {
		playerInput = playerInput.toUpperCase();
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
	
	private static String getPlayerName(int playerNumber, String color) {
		System.out.println("Player " + playerNumber + ", please enter your name!");
		String playerName =scanner.nextLine();
		System.out.println("Player 1: " + playerName + ", You are " + color + ".");
		return playerName;
	}
	
//	public static String getCoordinates(Player activePlayer) {
//		System.out.println("\n\n\n" + activePlayer.name + ", please enter the tile of the piece you want to move (A1-H8    I quit    Help)\n");
//		String coordinates = scanner.nextLine();
//		return coordinates;
//	}
//	

}
package chess;

import java.util.Arrays;
import java.util.Scanner;

public class MainChess {
	public static final int letterToNumberDifference = 65;
	private static final int numberToNumberDifference = 49;

	public static void main(String[] args) {
		PlayerInput.makeScanner();
		Player player1 = makePlayer(1, "white", false);
		Player player2 = makePlayer(2, "black", true);
		Board myChessBoard = new Board();
		myChessBoard.makeBoard();
		myChessBoard.populateBoard();
		myChessBoard.printBoard();
		while(!player1.hasLost || !player2.hasLost) {
			toggleActivePlayer(player1, player2);
			getFirstTile(player1, player2);
			// if quit: break
			getSecondTile(player1, player2);
			//currentPiece.Pattern.isValid(coordinates1, coordinates2)
			//check if other piece is in the way
			// updateBoard
		} 
		PlayerInput.closeScanner();
		System.out.println(getInactivePlayer(player1, player2).name + " wins!");	
	}
	

	private static void getFirstTile(Player player1, Player player2) {
		String firstPlayerInput;
		do {
			firstPlayerInput = PlayerInput.getPlayerInput(getActivePlayer(player1, player2), "Hey, " + getActivePlayer(player1, player2).name + "! Your move! Select your piece to move!");
		} while (!entryIsTile(firstPlayerInput) || !tileHasPiece(firstPlayerInput) || !pieceBelongsToActivePlayer(getActivePlayer(player1, player2), Board.getTile(inputToCoordinates(firstPlayerInput)).getPiece()));
	}
	
	private static void getSecondTile(Player player1, Player player2) {
		String secondPlayerInput;
		do {
			secondPlayerInput = PlayerInput.getPlayerInput(getActivePlayer(player1, player2), "Hey, " + getActivePlayer(player1, player2).name + "! Your move! Select your target tile!");
		} while (!entryIsTile(secondPlayerInput) 
				|| (tileHasPiece(secondPlayerInput) &&  pieceBelongsToActivePlayer(getActivePlayer(player1, player2), Board.getTile(inputToCoordinates(secondPlayerInput)).getPiece())));
	}
	
	// Create Players
	public static Player makePlayer(int playerNumber, String playerColor, boolean isActive) {
		return new Player(PlayerInput.getPlayerName(playerNumber, playerColor), playerColor, isActive);
	}
	
	// isActive Parameter
	public static void toggleActivePlayer(Player player1, Player player2) {
		if(player1.isActive) {
			player1.isActive = false;
			player2.isActive = true;
		} else {
			player1.isActive = true;
			player2.isActive = false;
		}
	}
	
	public static Player getActivePlayer(Player player1, Player player2) {
		if(player1.isActive) {
			return player1;
		} else {
			return player2;
		}
	}
	
	public static Player getInactivePlayer(Player player1, Player player2) {
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
	private static boolean playerHasQuitTheGame(Player activePlayer, String playerInput) {
		if("q".equals(playerInput)) {
			activePlayer.hasLost = true;
			return true;
		} else {
			return false;
		}
	}
	
	// help
	
	// draw
}

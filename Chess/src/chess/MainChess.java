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
		Tile firstTile;
		Tile secondTile;
		System.out.println();
		while(!player1.hasLost || !player2.hasLost) {
			toggleActivePlayer(player1, player2);
			firstTile = getFirstTile(player1, player2);
			do {
				secondTile = getSecondTile(player1, player2);
			} while(!isValidMove(firstTile.getPiece(), firstTile.getCoordinates(), secondTile.getCoordinates()));
			secondTile.updateTile(firstTile.getPiece());
				// sollte ich das überschriebene Piece löschen?
			firstTile.updateTile();
			myChessBoard.printBoard();
		} 
		PlayerInput.closeScanner();
		System.out.println(getInactivePlayer(player1, player2).name + " wins!");	
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
			if(Board.getTile(originCoordinates).getPiece() != null) {
				return true;
			}
			originCoordinates = Vectors.addVectors(originCoordinates, direction);
		}
		return false;
	}
	
	private static Tile getFirstTile(Player player1, Player player2) {
		String firstPlayerInput;
		do {
			firstPlayerInput = PlayerInput.getPlayerInput(getActivePlayer(player1, player2), "Hey, " + getActivePlayer(player1, player2).name + "! Your move! Select your piece to move!");
		} while (!entryIsTile(firstPlayerInput) || !tileHasPiece(firstPlayerInput) || !pieceBelongsToActivePlayer(getActivePlayer(player1, player2), Board.getTile(inputToCoordinates(firstPlayerInput)).getPiece()));
		return Board.getTile(inputToCoordinates(firstPlayerInput));
	}
	
	private static Tile getSecondTile(Player player1, Player player2) {
		String secondPlayerInput;
		do {
			secondPlayerInput = PlayerInput.getPlayerInput(getActivePlayer(player1, player2), "Hey, " + getActivePlayer(player1, player2).name + "! Your move! Select your target tile!");
		} while (!entryIsTile(secondPlayerInput) 
				|| (tileHasPiece(secondPlayerInput) &&  pieceBelongsToActivePlayer(getActivePlayer(player1, player2), Board.getTile(inputToCoordinates(secondPlayerInput)).getPiece())));
		return Board.getTile(inputToCoordinates(secondPlayerInput));
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

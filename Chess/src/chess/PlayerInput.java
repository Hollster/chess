package chess;

import java.awt.Point;
import java.util.Scanner;

public class PlayerInput {
	private static Scanner scanner;
		
	public static void makeScanner() {
		scanner = new Scanner(System.in);
	}
	
	public static void closeScanner() {
		scanner.close();
	}
	
	public static String getPlayerName(int playerNumber, String color) {
		System.out.println("Player " + playerNumber + ", please enter your name!");
		String playerName =scanner.nextLine();
		System.out.println("Player 1: " + playerName + ", You are " + color + ".");
		return playerName;
	}
	
	public static String getPlayerInput(Player activePlayer, String outputMessage) {
		System.out.println(outputMessage);
		String playerInput = scanner.nextLine();
		return playerInput.toUpperCase();
	}
	
	public static String getPlayerInput(String outputMessage) {
		System.out.println(outputMessage);
		String playerInput = scanner.nextLine();
		return playerInput.toUpperCase();
	}
	
	public static Scanner getScanner() {
		return scanner;
	}
	
	static Piece getPiece() {
		String firstPlayerInput;
		Piece chosenPiece = null;
		//chosenPiece = PlayerInput.getPiece();
		do {
			firstPlayerInput = getPlayerInput(MainChess.getPlayer(true), "Hey, " 
					+ MainChess.getPlayer(true).name + "! Your move! Select your piece to move! "
					+ "Enter help if you need help.");
			checkForSpecialInput(firstPlayerInput);
			chosenPiece = getPieceFromEntry(firstPlayerInput);
		} while (chosenPiece == null);
		return  chosenPiece;
	}
	
	private static Piece getPieceFromEntry(String playerEntry) {
		if(Board.entryIsOnBoard(playerEntry)) {
			for(Piece piece : MainChess.getPlayer(true).getPiecesOnBoard()) {
				if (piece.equals(entryToPoint(playerEntry))){
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
	
	static Point entryToPoint(String playerEntry) {
		int x = (int)playerEntry.charAt(0) - MainChess.LETTER_TO_NUMBER_DIFFERENCE;
		int y = Board.NUMBER_OF_ROWS - Character.getNumericValue(playerEntry.charAt(1));
		return new Point(x, y);
	}
	
	static String targetLocation(Piece piece) {
		String secondPlayerInput = PlayerInput.getPlayerInput(MainChess.getPlayer(true), "Hey, " + MainChess.getPlayer(true).name + "! Your move! Select your target tile!" 
				+ "\nEnter redo to choose another tile and help if you need help");
		PlayerInput.checkForSpecialInput(secondPlayerInput);
		return secondPlayerInput;
	}
	
	static String promotion() {
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
		}
		return promotionAnswer;
	}

	
	// Special Entry
	static void checkForSpecialInput(String firstPlayerInput) {
		if ("HELP".equals(firstPlayerInput)) {
			Help.startHelp();
		} else if ("DRAW".equals(firstPlayerInput)) {
			itsADraw();
		} else if ("QUIT".equals(firstPlayerInput)) {
			playerQuit();
		} 
		else {
			return;
		}
	}
	
	private static void playerQuit() {
		String doesPlayerWantToQuit = PlayerInput.getPlayerInput("Are you sure you want to quit? (yes / no)");
		if("YES".equals(doesPlayerWantToQuit)) {
			System.out.println(MainChess.getPlayer(false).name + " wins!");
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
				&& checkDrawAnswers(MainChess.getPlayer(false).name + ", do you think this is a draw, too? (yes / no)")) {
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
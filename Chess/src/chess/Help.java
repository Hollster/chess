package chess;

import java.util.Scanner;

public class Help {
	private static String initialHelpMessage = "Welcome to the help function!\nIf you want to exit the help menu please press the q key.\nIf you want to know about the board, please press the b key.\nIf you want to know about the pieces abbreviations please press the p key. If you want to know how to enter a move, please enter the m key." ;
	private static Scanner userInput = new Scanner(System.in);
	
	public static void checkForHelp(String entry) {
		if (entry.toUpperCase().equals("HELP")) {
			System.out.println(initialHelpMessage);
			
		}
	}
	
	public static void delegateHelpEntry(String helpEntry) {
		switch (helpEntry) {
		case "b":
			board();
			break;
		case "p":
			pieces();
			break;
		case "m":
			move();
			break;
		case "q":
			quitHelp();
			break;
		default:
			System.out.println("Invalid Entry.\n" + initialHelpMessage);
		}
	}
	
	public static void board() {
		System.out.println("white square: []\nblack square: {}");
	}
	
	public static void pieces() {
		System.out.println("wP: white pawn\nbP: black pawn\n\nETC \n \nother pieces: \n\n"
				+ "K: knight\nB: bishop\nR: rook\nQ: Queen\nK: King");
	}
	
	public static void move() {
		System.out.println("Please enter the new position in the following format: Letter, Number (A1 - I8)");
		
	}
	
	public static void quitHelp() {
		
	}
}

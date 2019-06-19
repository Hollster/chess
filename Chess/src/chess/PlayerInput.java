package chess;

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
}
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
	
	public static String getPlayerInput(String outputMessage) {
		System.out.println(outputMessage);
		String playerInput = scanner.nextLine();
		return playerInput.toUpperCase();
	}
	
	public static Scanner getScanner() {
		return scanner;
	}
	
//	public static boolean checkEntryValidity(String newPosition) {
//		if(newPosition.length() != 2) {
//			Help.move();
//			return false;
//		} else {
//			//TODO
//			// check if toUpper is neccessary and if statement can be shortened
//			if (Character.toUpperCase(newPosition.charAt(0)) >= 'A' && Character.toUpperCase(newPosition.charAt(0)) <= 'I' 
//					&& newPosition.charAt(1) >= '1' && newPosition.charAt(1) <= '9'){
//				return true;
//			} else {
//				return false;
//			}
//		}
//	}
}
package chess;

import java.util.Arrays;
import java.util.Scanner;

public class MainChess {

	public static void main(String[] args) {
		PlayerInput.makeScanner();
		Player player1 = PlayerInput.makePlayer(1, "white", false);
		Player player2 = PlayerInput.makePlayer(2, "black", true);
		Board myChessBoard = new Board();
		myChessBoard.makeBoard();
		myChessBoard.populateBoard();
		myChessBoard.printBoard();
		while(!player1.hasLost || !player2.hasLost) {
			toggleActivePlayer(player1, player2);
			PlayerInput.getPlayerInput(getActivePlayer(player1, player2), "Hey, " + getActivePlayer(player1, player2).name + "! Your move!");
		}
		PlayerInput.closeScanner();
		System.out.println(getInactivePlayer(player1, player2).name + " wins!");	
	}
	
	public static Player getActivePlayer(Player player1, Player player2) {
		if(player1.isActive) {
			return player1;
		} else {
			return player2;
		}
	}
	
	public static void toggleActivePlayer(Player player1, Player player2) {
		if(player1.isActive) {
			player1.isActive = false;
			player2.isActive = true;
		} else {
			player1.isActive = true;
			player2.isActive = false;
		}
	}
	
	
	public static Player getInactivePlayer(Player player1, Player player2) {
		if(player1.isActive) {
			return player2;
		} else {
			return player1;
		}
	}
	
}

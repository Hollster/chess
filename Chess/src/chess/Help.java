package chess;

public class Help {
	private static String initialHelpMessage = "Welcome to the help function! ";
	private static String options = "If you want to exit the help menu please press the q key.\nIf you want to know about the board, please press the b key.\nIf you want to know about the pieces abbreviations please press the p key.\nIf you want to know how to enter a move, please enter the m key.";

	static void startHelp() {
		delegateHelpEntry(PlayerInput.getPlayerInput(initialHelpMessage + options));
	}
	
	static void startHelp(String message) {
			delegateHelpEntry(PlayerInput.getPlayerInput(message));
	}
	
	private static void delegateHelpEntry(String helpEntry) {
		switch (helpEntry) {
		case "HELP":
			break;
		case "B":
			board();
			startHelp(options);
			break;
		case "P":
			pieces();
			startHelp(options);
			break;
		case "M":
			move();
			startHelp(options);
			break;
		case "Q":
			quitHelp();
			break;
		default:
			startHelp("Invalid Entry.\n" + options);
		}
	}
	
	private static void board() {
		System.out.println("white square: []\nblack square: {}");
	}
	
	private static void pieces() {
		System.out.println("wP: white pawn\nbP: black pawn\n\nETC \n \nother pieces: \n\n"
				+ "K: knight\nB: bishop\nR: rook\nQ: Queen\nK: King");
	}
	
	private static void move() {
		System.out.println("Please enter the new position in the following format: Letter, Number (A1 - I8)");
	}
	
	private static void quitHelp() {
		Board.print();
		return;
	}
}

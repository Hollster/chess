package chess;

import java.util.Arrays;

public class MainChess {

	public static void main(String[] args) {
		Board myChessBoard = new Board();
		myChessBoard.makeBoard();
		myChessBoard.initializeBoard();
		myChessBoard.printBoard();
	}


}

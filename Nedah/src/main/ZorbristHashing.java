package main;

import java.util.Random;

public class ZorbristHashing {
	private int[][] piecePositions;
	private int pawn = 0, horse = 1, bishop =  2, king = 3;
	private int cpawn = 4, chorse = 5, cbishop = 6, cking = 7;
	
	public ZorbristHashing() {
		init();
	}
	
	private void init() {
		piecePositions = new int[48][8];
		for(int i = 0; i < 48; i++) { //for each board space
			for(int j = 0; j < 8; j++) { //for each piece
				piecePositions[i][j] = randomNumber();
			}
		}
	}
	
	private int randomNumber() {
		Random random = new Random();
		return random.nextInt(2000000000);
	}
	
	public int hash(int[][] board) {
		int h =0;
		for(int i =0; i < 6;i++) {
			for(int j=0;j<8;j++) {
				if(board[i][j] != 0) {
					int piece = board[i][j] - 1; //subtract one because the board uses pawn =1, horse =2, bishop =3, etc.
					int translatedPiecePosition = i * 8 + j; //translate from a board position i,j to an equivalent position in a one dimensional array.
					h ^= piecePositions[translatedPiecePosition][piece];
				}
			}
		}
		return h;
	}
	
	private void printPiecePositions() {
		for(int i =0; i < 48; i++) {
			for (int j=0; j<8;j++) {
				System.out.println(piecePositions[i][j]);
			}
		}
	}
	
	public static void main(String[] args) {
		ZorbristHashing zh = new ZorbristHashing();
		GameBoardInitializer gbi = new GameBoardInitializer();
		GameBoardUpdater gbu = new GameBoardUpdater();
		int[][] board1 = gbi.generateNewBoard();
		int[][] board2 = gbi.generateNewBoard();
		System.out.println(zh.hash(board1));
		System.out.println(zh.hash(board2));
		gbu.updateBoard(board1, "a1b3");
		System.out.println(zh.hash(board1));
		System.out.println(zh.hash(board2));
		System.out.println(zh.hash(board2));
	}
}

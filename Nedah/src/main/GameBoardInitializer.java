package main;

public class GameBoardInitializer {
	private int pawn = 1;
	private int horse = 2;
	private int bishop = 3;
	private int king = 4; 

	private int cpawn = 5;
	private int chorse = 6;
	private int cbishop = 7;
	private int cking = 8;

	public GameBoardInitializer(){}
	
	public int[][] generateNewBoard(){
		int[][] board = new int[6][8];
		//set computer pieces
		board[0][0] = chorse;
		board[0][1] = chorse;
		board[0][3] = cking;
		board[0][4] = cking;
		board[0][6] = cbishop;
		board[0][7] = cbishop;
		board[1][1] = cpawn;
		board[1][2] = cpawn;
		board[1][3] = cpawn;
		board[1][4] = cpawn;
		board[1][5] = cpawn;
		board[1][6] = cpawn;
		//set player pieces
		board[4][1] = pawn;
		board[4][2] = pawn;
		board[4][3] = pawn;
		board[4][4] = pawn;
		board[4][5] = pawn;
		board[4][6] = pawn;
		board[5][0] = horse;
		board[5][1] = horse;
		board[5][3] = king;
		board[5][4] = king;
		board[5][6] = bishop;
		board[5][7] = bishop;
		
		return board;
	}
	
	public static void main(String[] args){
		GameBoardInitializer bgi = new GameBoardInitializer();
		int[][] b = bgi.generateNewBoard();
		for(int i = 0; i < b.length; i++){
			for(int j = 0; j < b[0].length ; j++){
				System.out.print(b[i][j] + " ");
			}
			System.out.println("");
		}
	}
}

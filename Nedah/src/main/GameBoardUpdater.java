package main;

public class GameBoardUpdater {
	
	public int[][] updateBoard(int[][] board, String move){
		int[][] newBoard = board;
		if(!(move.length() < 4)) {
			String oldPos = move.substring(0,2);
			String newPos = move.substring(2);
			
			int piece = removeFromOldPos(newBoard, oldPos);
			moveToNewPos(newBoard, piece, newPos);
			checkPiecesForTypeUpdate(newBoard);
			return newBoard;
		} else {
			return board;
		}
	}
	
	public String getReverseMove(String move) {
		String oldPos = move.substring(0,2);
		String newPos = move.substring(2);
		return newPos+oldPos;
	}
	
	private void checkPiecesForTypeUpdate(int[][] board){
		for(int i =0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				if(j <= 3){
					if(board[i][j] == PIECES.BISHOP.value){
						board[i][j] = PIECES.HORSE.value;
					}
					
					if(board[i][j] == PIECES.CBISHOP.value){
						board[i][j] = PIECES.CHORSE.value;
					}
				} else{
					if(board[i][j] == PIECES.HORSE.value){
						board[i][j] = PIECES.BISHOP.value;
					}
					if(board[i][j] == PIECES.CHORSE.value){
						board[i][j] = PIECES.CBISHOP.value;
					}
				}
			}
		}
	}
	
	private int removeFromOldPos(int[][] board, String oldPos){
		int row = translateRow(oldPos.substring(1)) - 1;
		int column = translateColumn(oldPos.substring(0,1));
		
		int piece = board[row][column];
		board[row][column] = 0;
		return piece;
	}
	
	private void moveToNewPos(int[][] board, int piece, String newPos){
		int column = translateColumn(newPos.substring(0,1));
		int row = translateRow(newPos.substring(1)) - 1;

		board[row][column] = piece;
	}
	
	private int translateColumn(String s){
		if(s.equals("a")){return 0;}
		else if(s.equals("b")){return 1;}
		else if(s.equals("c")){return 2;}
		else if(s.equals("d")){return 3;}
		else if(s.equals("e")){return 4;}
		else if(s.equals("f")){return 5;}
		else if(s.equals("g")){return 6;}
		else if(s.equals("h")){return 7;}
		return -1;
	}
	
	private int translateRow(String row) {
		if(row.equals("6")) {
			return 1;
		}
		else if(row.equals("5")) {
			return 2;
		}
		else if(row.equals("4")) {
			return 3;
		}
		else if(row.equals("3")) {
			return 4;
		}
		else if(row.equals("2")) {
			return 5;
		}
		else if(row.equals("1")) {
			return 6;
		} else {
			return -1;
		}		
	}
	
	public static void main(String[] args) {
		GameBoardInitializer gbi = new GameBoardInitializer();
		GameBoardDisplay gbd = new GameBoardDisplay();
		GameBoardUpdater gbu = new GameBoardUpdater();

		int[][] board = gbi.generateNewBoard();
		gbd.displayBoard(board);
		gbu.updateBoard(board, "b2b3");
		System.out.println("\nplaying b2b3");
		gbd.displayBoard(board);
		
		gbu.updateBoard(board, "c5c4");
		System.out.println("\nplaying c5c4");
		gbd.displayBoard(board);
		
		gbu.updateBoard(board, "a6b4");
		System.out.println("\nplaying a6b4");
		gbd.displayBoard(board);
	}
}

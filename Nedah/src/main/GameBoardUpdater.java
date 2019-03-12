package main;

public class GameBoardUpdater {
	
	public int[][] updateBoard(int[][] board, String move){
		int[][] newBoard = board;
		String oldPos = move.substring(0,2);
		String newPos = move.substring(2);
		
		int piece = removeFromOldPos(newBoard, oldPos);
		moveToNewPos(newBoard, piece, newPos);
		checkPiecesForTypeUpdate(board);
		return newBoard;
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
		int column = getColumn(oldPos.substring(0,1));
		int row = Integer.parseInt(oldPos.substring(1))-1;
		int piece = board[row][column];
		board[row][column] = 0;
		return piece;
	}
	
	private void moveToNewPos(int[][] board, int piece, String newPos){
		int column = getColumn(newPos.substring(0,1));
		int row = Integer.parseInt(newPos.substring(1))-1;

		board[row][column] = piece;
	}
	
	private int getColumn(String s){
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
}

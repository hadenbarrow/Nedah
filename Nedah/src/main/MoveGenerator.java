package main;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
	public MoveGenerator(){}
	
	public void generateMoves(String player, int[][] board){
		if(player.equals("computer")){
			generateComputerMoves(board);
		} else{
			generatePlayerMoves(board);
		}
	} 
	
	public void generateComputerMoves(int[][] board){
		List<String> moves = new ArrayList<String>();
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				
				if(board[i][j] == PIECES.CPAWN.value){
					if(!checkCollision(board, i+1, j) && isOnBoard(i+1, j)){
						moves.add(createMoveStringForComputer(i, j, i+1, j));
					}
				}
				
				else if(board[i][j] == PIECES.CHORSE.value){
					
				} 
				else if(board[i][j] == PIECES.CBISHOP.value){
					
				}
				else if(board[i][j] == PIECES.CKING.value){
					
				}
			}
		}
	}
	
	public void generatePlayerMoves(int[][] board){}
	
	public String createMoveStringForComputer(int oldi, int oldj, int newi, int newj){
		return "";
	}
	
	private boolean isOnBoard(int i, int j){
		return (i < 6 && j < 8);
	}
	
	private boolean checkCollision(int[][] board, int i, int j){
		if(board[i][j] == PIECES.CPAWN.value || board[i][j] == PIECES.CHORSE.value 
				|| board[i][j] == PIECES.CBISHOP.value || board[i][j] == PIECES.CKING.value){
			return true;
		}
		return false;
	}
}

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
					if(!checkCollisionWithComputerPiece(board, i+1, j) && isOnBoard(i+1, j)){
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
		String oldColumn = getCorrespondingColumn(oldj);
		String newColumn = getCorrespondingColumn(newj);
		return oldColumn + oldi + newColumn + newi; 
	}
	
	public String getCorrespondingColumn(int n){
		if(n > 8 || n < 1){
			return "";
		}
		if(n == 1){return "a";} 
		else if(n == 2){return "b";}
		else if(n == 3){return "c";}
		else if(n == 4){return "d";}
		else if(n == 5){return "e";}
		else if(n == 6){return "f";}
		else if(n == 7){return "g";}
		else if(n == 8){return "h";}
		return "couldn't figure out move string";
	}
	
	private boolean isOnBoard(int i, int j){
		return (i < 6 && j < 8);
	}
	
	private boolean checkCollisionWithComputerPiece(int[][] board, int i, int j){
		if(board[i][j] == PIECES.CPAWN.value || board[i][j] == PIECES.CHORSE.value 
				|| board[i][j] == PIECES.CBISHOP.value || board[i][j] == PIECES.CKING.value){
			return true;
		}
		return false;
	}
}

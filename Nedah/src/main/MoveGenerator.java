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
	
	public List<String> generateComputerMoves(int[][] board){
		List<String> moves = new ArrayList<String>();
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				
				if(board[i][j] == PIECES.CPAWN.value){
					//pawn only has one potential move, moving forward by 1
					if(!checkCollisionWithComputerPiece(board, i + 1, j) && isOnBoard(i + 1, j)){
						moves.add(createMoveStringForComputer(i, j, i + 1, j)); 
					}
				}
				
				else if(board[i][j] == PIECES.CHORSE.value){
					//check all 8 of the horses potential moves
					if(!checkCollisionWithComputerPiece(board, i + 1, j + 2) && isOnBoard(i + 1, j + 2)){
						moves.add(createMoveStringForComputer(i, j, i + 1, j + 2)); 
					}
					
					if(!checkCollisionWithComputerPiece(board, i + 1, j - 2) && isOnBoard(i + 1, j - 2)){
						moves.add(createMoveStringForComputer(i, j, i + 1, j - 2)); 
					}
					
					if(!checkCollisionWithComputerPiece(board, i + 2, j + 1) && isOnBoard(i +2, j + 1)){
						moves.add(createMoveStringForComputer(i, j, i + 2, j + 1)); 
					}
					
					if(!checkCollisionWithComputerPiece(board, i + 2, j - 1) && isOnBoard(i + 2, j - 1)){
						moves.add(createMoveStringForComputer(i, j, i + 2, j - 1)); 
					}
					
					//check moving backwards, needs to be "senior" to move backwards
					//For the computer, "senior" is rows 1,2,3
					if(i <= 3){
						if(!checkCollisionWithComputerPiece(board, i - 1, j - 2) && isOnBoard(i - 1, j - 2)){
							moves.add(createMoveStringForComputer(i, j, i - 1, j - 2)); 
						}
						
						if(!checkCollisionWithComputerPiece(board, i - 1, j + 2) && isOnBoard(i - 1, j + 2)){
							moves.add(createMoveStringForComputer(i, j, i - 1, j + 2)); 
						}
						
						if(!checkCollisionWithComputerPiece(board, i - 2, j - 1) && isOnBoard(i - 2, j - 1)){
							moves.add(createMoveStringForComputer(i, j, i - 2, j - 1)); 
						}
						
						if(!checkCollisionWithComputerPiece(board, i - 2, j + 1) && isOnBoard(i - 2, j + 1)){
							moves.add(createMoveStringForComputer(i, j, i - 2, j + 2)); 
						}
					}
				} 
				
				else if(board[i][j] == PIECES.CBISHOP.value){
					//check forward moves
					if(!checkCollisionWithComputerPiece(board, i + 1 , j + 1) && isOnBoard(i + 1, j + 1)){
						moves.add(createMoveStringForComputer(i, j, i + 1, j + 1));
					}
					if(!checkCollisionWithComputerPiece(board, i + 2 , j + 2) && isOnBoard(i + 2, j + 2)){
						moves.add(createMoveStringForComputer(i, j, i + 2, j + 2));
					}
					if(!checkCollisionWithComputerPiece(board, i + 3 , j + 3) && isOnBoard(i + 3, j + 3)){
						moves.add(createMoveStringForComputer(i, j, i + 3, j + 3));
					}
					if(!checkCollisionWithComputerPiece(board, i + 4 , j + 4) && isOnBoard(i + 4, j + 4)){
						moves.add(createMoveStringForComputer(i, j, i + 4, j + 4));
					}
					if(!checkCollisionWithComputerPiece(board, i + 5 , j + 5) && isOnBoard(i + 5, j + 5)){
						moves.add(createMoveStringForComputer(i, j, i + 5, j + 5));
					}
					if(!checkCollisionWithComputerPiece(board, i + 1 , j - 1) && isOnBoard(i + 1, j - 1)){
						moves.add(createMoveStringForComputer(i, j, i + 1, j - 1));
					}
					if(!checkCollisionWithComputerPiece(board, i + 2 , j - 2) && isOnBoard(i + 2, j - 2)){
						moves.add(createMoveStringForComputer(i, j, i + 2, j - 2));
					}
					if(!checkCollisionWithComputerPiece(board, i + 3 , j - 3) && isOnBoard(i + 3, j - 3)){
						moves.add(createMoveStringForComputer(i, j, i + 3, j - 3));
					}
					if(!checkCollisionWithComputerPiece(board, i + 4 , j  - 4) && isOnBoard(i + 4, j - 4)){
						moves.add(createMoveStringForComputer(i, j, i + 4, j- 4));
					}
					if(!checkCollisionWithComputerPiece(board, i + 5 , j - 5) && isOnBoard(i + 5, j - 5)){
						moves.add(createMoveStringForComputer(i, j, i + 5, j - 5));
					}
					
					//check backwards moves
					if(i <= 3){
						if(!checkCollisionWithComputerPiece(board, i + 1 , j - 1) && isOnBoard(i -1, j -1)){
							moves.add(createMoveStringForComputer(i, j, i-1, j-1));
						}
						if(!checkCollisionWithComputerPiece(board, i + 2 , j - 2) && isOnBoard(i -2, j -2)){
							moves.add(createMoveStringForComputer(i, j, i-2, j-2));
						}
						if(!checkCollisionWithComputerPiece(board, i + 3 , j - 3) && isOnBoard(i -3, j -3)){
							moves.add(createMoveStringForComputer(i, j, i-3, j-3));
						}
						if(!checkCollisionWithComputerPiece(board, i + 4 , j - 4) && isOnBoard(i -4, j -4)){
							moves.add(createMoveStringForComputer(i, j, i-4, j-4));
						}
						if(!checkCollisionWithComputerPiece(board, i + 5 , j - 5) && isOnBoard(i -5, j -5)){
							moves.add(createMoveStringForComputer(i, j, i-5, j-5));
						}
						if(!checkCollisionWithComputerPiece(board, i + 1 , j + 1) && isOnBoard(i -1, j -1)){
							moves.add(createMoveStringForComputer(i, j, i-1, j+1));
						}
						if(!checkCollisionWithComputerPiece(board, i + 2 , j + 2) && isOnBoard(i -2, j -2)){
							moves.add(createMoveStringForComputer(i, j, i-2, j+2));
						}
						if(!checkCollisionWithComputerPiece(board, i + 3 , j +3) && isOnBoard(i -3, j -3)){
							moves.add(createMoveStringForComputer(i, j, i-3, j+3));
						}
						if(!checkCollisionWithComputerPiece(board, i + 4 , j +4) && isOnBoard(i -4, j -4)){
							moves.add(createMoveStringForComputer(i, j, i-4, j+4));
						}
						if(!checkCollisionWithComputerPiece(board, i + 5 , j + 5) && isOnBoard(i -5, j -5)){
							moves.add(createMoveStringForComputer(i, j, i-5, j+5));
						}
					}
					
				}
				
				else if(board[i][j] == PIECES.CKING.value){
					//left king moves
					if(j <= 3){
						if(!checkCollisionWithComputerPiece(board, i , j - 1) && isOnBoard(i, j - 1)){
							moves.add(createMoveStringForComputer(i, j, i, j-1));
						}
					}
					//right king moves
					else{
						if(!checkCollisionWithComputerPiece(board, i , j + 1) && isOnBoard(i, j + 1)){
							moves.add(createMoveStringForComputer(i, j, i, j+1));
						}
					}
					
				}
			}
		}
		return moves;
	}
	
	public void generatePlayerMoves(int[][] board){}
	
	public String createMoveStringForComputer(int oldi, int oldj, int newi, int newj){
		String oldColumn = getCorrespondingColumn(oldj);
		String newColumn = getCorrespondingColumn(newj);
		return oldColumn + (oldi+1) + newColumn + (newi+1); 
	}
	
	public String getCorrespondingColumn(int n){
		if(n > 7 || n < 0){
			return "column doesn't make sense";
		}
		if(n == 0){return "a";} 
		else if(n == 1){return "b";}
		else if(n == 2){return "c";}
		else if(n == 3){return "d";}
		else if(n == 4){return "e";}
		else if(n == 5){return "f";}
		else if(n == 6){return "g";}
		else if(n == 7){return "h";}
		return "couldn't figure out move string";
	}
	
	private boolean isOnBoard(int i, int j){
		return ((i < 6 && j < 8) && (i >= 0 && j >= 0));
	}
	
	private boolean checkCollisionWithComputerPiece(int[][] board, int i, int j){
		if((i < 6 && j < 8) && (i > -1 && j > -1)){
			int pieceValue = board[i][j];
			if(pieceValue == PIECES.CPAWN.value || pieceValue == PIECES.CHORSE.value 
					|| pieceValue == PIECES.CBISHOP.value || pieceValue == PIECES.CKING.value){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args){
		GameBoardInitializer gbi = new GameBoardInitializer();
		int[][] board = gbi.generateNewBoard();
		MoveGenerator mg = new MoveGenerator();
		List<String> moves = mg.generateComputerMoves(board);
		for(String s : moves){
			System.out.println(s);
		}
	}
}

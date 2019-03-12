package main; 

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
	public MoveGenerator(){}
	
	public List<String> generateMoves(String player, int[][] board){
		if(player.equals("computer")){
			return generateComputerMoves(board);
		}
		else if(player.equals("player")){
			return generatePlayerMoves(board);
		}
		else{
			System.err.println("Couldn't generate moves. 'computer' or 'player' are the only valid inputs into the generateMoves function.");
			return null;
		}
	} 
	
	private List<String> generatePlayerMoves(int[][] board){
		List<String> moves = new ArrayList<String>();
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				if(board[i][j] == PIECES.PAWN.value){
					if(!checkCollisionWithPlayerPiece(board, i - 1, j) && isOnBoard(i - 1,j)){
						moves.add(createMoveString(i, j, i - 1, j));
					}
				}
				else if(board[i][j] == PIECES.HORSE.value){
					moves.addAll(checkPlayerForwardHorseMoves(board, i, j));
					if(playerPieceIsSenior(i, j)){
						moves.addAll(checkPlayerBackwardHorseMoves(board, i, j));
					}
				}
				else if(board[i][j] == PIECES.BISHOP.value){
					moves.addAll(checkPlayerForwardRightBishopMoves(board, i, j));
					moves.addAll(checkPlayerForwardLeftBishopMoves(board, i, j));
					if(playerPieceIsSenior(i, j)){
						moves.addAll(checkPlayerBackwardRightBishopMoves(board, i, j));
						moves.addAll(checkPlayerBackwardLeftBishopMoves(board, i, j));

					}
				}
				else if(board[i][j] == PIECES.KING.value){
					//left king moves
					if(j <= 3){
						if(!checkCollisionWithPlayerPiece(board, i , j - 1) && isOnBoard(i, j - 1)){
							moves.add(createMoveString(i, j, i, j-1));
						}
					}
					//right king moves
					else{
						if(!checkCollisionWithComputerPiece(board, i , j + 1) && isOnBoard(i, j + 1)){
							moves.add(createMoveString(i, j, i, j+1));
						}
					}
				}
			}
		}
		return moves;
	}
	
	private List<String> checkPlayerBackwardLeftBishopMoves(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		int newi = i - 1;
		int newj = j - 1;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
			checkPlayerBackwardLeftBishopMoves(board, newi, newj);
		}
		
		return moves;
	}
	
	private List<String> checkPlayerBackwardRightBishopMoves(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		int newi = i - 1;
		int newj = j + 1;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
			checkPlayerBackwardRightBishopMoves(board, newi, newj);
		}
		
		return moves;
	}
	
	private List<String> checkPlayerForwardRightBishopMoves(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		int newi = i + 1;
		int newj = j + 1;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
			checkPlayerForwardRightBishopMoves(board, newi, newj);
		}
		
		return moves;
	}
	
	private List<String> checkPlayerForwardLeftBishopMoves(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		int newi = i + 1;
		int newj = j - 1;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
			checkPlayerForwardLeftBishopMoves(board, newi, newj);
		}
		
		return moves;
	}
	
	private List<String> checkPlayerBackwardBishopMoves(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		return moves;
	}
	
	private List<String> checkPlayerForwardHorseMoves(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		int newi = i + 1;
		int newj = j + 2;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		newi = i + 1;
		newj = j - 2;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		newi = i + 2;
		newj = j + 1;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		newi = i + 2;
		newj = j - 1;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		return moves;
	}
	
	private List<String> checkPlayerBackwardHorseMoves(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		
		int newi = i - 1;
		int newj = j + 2;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		newi = i - 1;
		newj = j - 2;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		newi = i - 2;
		newj = j + 1;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		newi = i - 2;
		newj = j - 1;
		if(!checkCollisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		return moves;
	}
	
	private boolean playerPieceIsSenior(int i, int j){
		return (i >= 4);
	}
	
	private List<String> generateComputerMoves(int[][] board){
		List<String> moves = new ArrayList<String>();
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				
				if(board[i][j] == PIECES.CPAWN.value){
					//pawn only has one potential move, moving forward by 1
					if(!checkCollisionWithComputerPiece(board, i + 1, j) && isOnBoard(i + 1, j)){
						moves.add(createMoveString(i, j, i + 1, j)); 
					}
				}
				
				else if(board[i][j] == PIECES.CHORSE.value){
					//check all 8 of the horses potential moves
					if(!checkCollisionWithComputerPiece(board, i + 1, j + 2) && isOnBoard(i + 1, j + 2)){
						moves.add(createMoveString(i, j, i + 1, j + 2)); 
					}
					
					if(!checkCollisionWithComputerPiece(board, i + 1, j - 2) && isOnBoard(i + 1, j - 2)){
						moves.add(createMoveString(i, j, i + 1, j - 2)); 
					}
					
					if(!checkCollisionWithComputerPiece(board, i + 2, j + 1) && isOnBoard(i +2, j + 1)){
						moves.add(createMoveString(i, j, i + 2, j + 1)); 
					}
					
					if(!checkCollisionWithComputerPiece(board, i + 2, j - 1) && isOnBoard(i + 2, j - 1)){
						moves.add(createMoveString(i, j, i + 2, j - 1)); 
					}
					
					//check moving backwards, needs to be "senior" to move backwards
					//For the computer, "senior" is rows 1,2,3
					if(i <= 3){
						if(!checkCollisionWithComputerPiece(board, i - 1, j - 2) && isOnBoard(i - 1, j - 2)){
							moves.add(createMoveString(i, j, i - 1, j - 2)); 
						}
						
						if(!checkCollisionWithComputerPiece(board, i - 1, j + 2) && isOnBoard(i - 1, j + 2)){
							moves.add(createMoveString(i, j, i - 1, j + 2)); 
						}
						
						if(!checkCollisionWithComputerPiece(board, i - 2, j - 1) && isOnBoard(i - 2, j - 1)){
							moves.add(createMoveString(i, j, i - 2, j - 1)); 
						}
						
						if(!checkCollisionWithComputerPiece(board, i - 2, j + 1) && isOnBoard(i - 2, j + 1)){
							moves.add(createMoveString(i, j, i - 2, j + 2)); 
						}
					}
				} 
				
				else if(board[i][j] == PIECES.CBISHOP.value){
					//check forward moves
					moves.addAll(checkForwardRightDiagonal(i,j, board));
					moves.addAll(checkForwardLeftDiagonal(i,j, board));
					
					//check backwards moves
					if(i <= 3){
						moves.addAll(checkBackwardRightDiagonal(i,j,board));
						moves.addAll(checkBackwardLeftDiagonal(i,j,board));
					}
					
				}
				
				else if(board[i][j] == PIECES.CKING.value){
					//left king moves
					if(j <= 3){
						if(!checkCollisionWithComputerPiece(board, i , j - 1) && isOnBoard(i, j - 1)){
							moves.add(createMoveString(i, j, i, j-1));
						}
					}
					//right king moves
					else{
						if(!checkCollisionWithComputerPiece(board, i , j + 1) && isOnBoard(i, j + 1)){
							moves.add(createMoveString(i, j, i, j+1));
						}
					}
					
				}
			}
		}
		return moves;
	}
	
	private List<String> checkForwardRightDiagonal(int i, int j, int[][] board){
		List<String> moves = new ArrayList<String>();
		int newi = i + 1;
		int newj = j + 1;
		if(!checkCollisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i,j,newi,newj));
			checkForwardRightDiagonal(newi, newj, board);
		}
		return moves;
	}
	
	private List<String> checkForwardLeftDiagonal(int i, int j, int[][] board){
		List<String> moves = new ArrayList<String>();
		int newi = i + 1;
		int newj = j - 1;
		if(!checkCollisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i,j,newi,newj));
			checkForwardRightDiagonal(newi, newj, board);
		}
		return moves;
	}
	
	private List<String> checkBackwardRightDiagonal(int i, int j, int[][] board){
		List<String> moves = new ArrayList<String>();
		int newi = i - 1;
		int newj = j + 1;
		if(!checkCollisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i,j,newi,newj));
			checkForwardRightDiagonal(newi, newj, board);
		}
		return moves;
	}
	
	private List<String> checkBackwardLeftDiagonal(int i, int j, int[][] board){
		List<String> moves = new ArrayList<String>();
		int newi = i - 1;
		int newj = j - 1;
		if(!checkCollisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)){
			moves.add(createMoveString(i,j,newi,newj));
			checkForwardRightDiagonal(newi, newj, board);
		}
		return moves;
	}
		
	private String createMoveString(int oldi, int oldj, int newi, int newj){
		String oldColumn = getCorrespondingColumn(oldj);
		String newColumn = getCorrespondingColumn(newj);
		return oldColumn + (oldi+1) + newColumn + (newi+1); 
	}
	
	private String getCorrespondingColumn(int n){
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
	
	private boolean checkCollisionWithPlayerPiece(int[][] board, int i, int j){
		if((i < 6 && j < 8) && (i > -1 && j > -1)){
			int pieceValue = board[i][j];
			if(pieceValue == PIECES.PAWN.value || pieceValue == PIECES.HORSE.value 
					|| pieceValue == PIECES.BISHOP.value || pieceValue == PIECES.KING.value){
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
		System.out.println("Computer moves: ");
		for(String s : moves){
			System.out.println(s);
		}
		System.out.println("");
		System.out.println("Player moves: ");
		List<String> pmoves = mg.generatePlayerMoves(board);
		for(String s : pmoves){
			System.out.println(s);
		}
	}
}

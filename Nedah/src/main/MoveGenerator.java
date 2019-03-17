package main;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
	
	public List<String> generateMoves(String player, int[][] board){
		if(player.equals("player")) {
			return generatePlayerMoves(board);
		}
		else if(player.equals("computer")) {
			return generateComputerMoves(board);
		} else {
			System.out.println("The generate moves function only takes arguments 'player' or 'computer'");
			return null;
		}
	}
	
	private List<String> generatePlayerMoves(int[][] board){
		List<String> playerMoves = new ArrayList<String>();
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				int piece = board[i][j];
				
				if(piece == PIECES.PAWN.value) {
					playerMoves.addAll(playerPawnForward(board, i, j));
					playerMoves.addAll(playerPawnCaptures(board, i, j));
				}
				else if(piece == PIECES.HORSE.value) {
					playerMoves.addAll(playerHorseForward(board, i, j));
					if(playerPieceIsSenior(i, j + 1)) {
						playerMoves.addAll(playerHorseBackward(board, i, j));
					}
				}
				else if(piece == PIECES.BISHOP.value) {
					playerMoves.addAll(playerBishopForward(board, i, j));
					if(playerPieceIsSenior(i, j + 1)) {
						playerMoves.addAll(playerBishopBackward(board, i, j));
					}
				}
				else if(piece == PIECES.KING.value) {
					playerMoves.addAll(playerKing(board, i, j));
				}
			}
		}
		
		return playerMoves;
	}
	
	private List<String> playerPawnForward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		int newi = i - 1;
		int newj = j;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj) && !collisionWithComputerPiece(board, newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));

		}
		
		return moves;
	}
	
	private List<String> playerPawnCaptures(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		int newi = i - 1;
		int newj = j + 1;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && collisionWithComputerPiece(board, newi, newj) && isOnBoard(i, j) && collisionWithComputerPiece(board, newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		newi = i - 1;
		newj = j - 1;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && collisionWithComputerPiece(board, newi, newj) && isOnBoard(i, j) && collisionWithComputerPiece(board, newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		return moves;
	}
	
	private List<String> playerHorseForward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		//forward 1, right 2
		int newi = i - 1;
		int newj = j + 2;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//forward 1, left 2
		newi = i - 1;
		newj = j - 2;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//forward 2, right 1
		newi = i - 2;
		newj = j + 1;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//forward 2, left 1
		newi = i - 2;
		newj = j - 1;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		return moves;
	}
	
	private List<String> playerHorseBackward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		//In order to move backwards, a piece must be senior and must capture an opponents piece.
		
		//backward 1, right 2
		int newi = i + 1;
		int newj = j + 2;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//backward 1, left 2
		newi = i + 1;
		newj = j - 2;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//backward 2, right 1
		newi = i + 2;
		newj = j + 1;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//backward 2, left 1
		newi = i + 2;
		newj = j - 1;
		
		if(!collisionWithPlayerPiece(board, newi, newj) && collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		return moves;
	}
	
	private List<String> playerBishopForward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		//forward 1, right 1, repeat
		int newi = i - 1;
		int newj = j + 1;
		
		for(int k = 0; k < 5; k++){
			if(collisionWithPlayerPiece(board, newi, newj)){
				break;
			}
			if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
				moves.add(createMoveString(i, j, newi, newj));
				if(collisionWithComputerPiece(board, newi, newj)){
					break;
				}
			}
			newi--;
			newj++;
		}
		
		//forward 1, left 1, repeat
		newi = i - 1;
		newj = j - 1;
		
		for(int k = 0; k < 5; k++){
			if(collisionWithPlayerPiece(board, newi, newj)){
				break;
			}
			if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)){
				moves.add(createMoveString(i, j, newi, newj));
				if(collisionWithComputerPiece(board, newi, newj)){
					break;
				}
			}
			newi--;
			newj--;
		}
		
		return moves;
	}
	
	private List<String> playerBishopBackward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		//backward 1, right 1, repeat
		int newi = i + 1;
		int newj = j + 1;
		
		for(int k = 0; k < 5; k++){
			if(collisionWithPlayerPiece(board, newi, newj)){
				break;
			}
			if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj) && collisionWithComputerPiece(board, newi, newj)){
				moves.add(createMoveString(i, j, newi, newj));
				if(collisionWithComputerPiece(board, newi, newj)){
					break;
				}
			}
			newi++;
			newj++;
		}
		
		//backward 1, left 1, repeat
		newi = i + 1;
		newj = j - 1;
		
		for(int k = 0; k < 5; k++){
			if(collisionWithPlayerPiece(board, newi, newj)){
				break;
			}
			if(!collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)&& collisionWithComputerPiece(board, newi, newj)){
				moves.add(createMoveString(i, j, newi, newj));
				if(collisionWithComputerPiece(board, newi, newj)){
					break;
				}
			}
			newi++;
			newj--;
		}
		
		return moves;
	}
	
	private List<String> playerKing(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		int newj;
		
		if(j >= 4) {
			//right king
			newj = j + 1;
			if(!collisionWithPlayerPiece(board, i, newj) && isOnBoard(i, newj)) {
				moves.add(createMoveString(i, j, i, newj));
			}
		} else {
			//left king
			newj = j - 1;
			if(!collisionWithPlayerPiece(board, i, newj) && isOnBoard(i, newj)) {
				moves.add(createMoveString(i, j, i, newj));
			}
		}
		
		return moves;
	}
	
	private List<String> generateComputerMoves(int[][] board){
		List<String> computerMoves = new ArrayList<String>();
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				int piece = board[i][j];
				
				if(piece == PIECES.CPAWN.value) {
					computerMoves.addAll(computerPawnForward(board, i, j));
					computerMoves.addAll(computerPawnCaptures(board, i, j));
				}
				else if(piece == PIECES.CHORSE.value) {
					computerMoves.addAll(computerHorseForward(board, i, j));
					if(computerPieceIsSenior(i, j)) {
						computerMoves.addAll(computerHorseBackward(board, i, j));
					}
				}
				else if(piece == PIECES.CBISHOP.value) {
					computerMoves.addAll(computerBishopForward(board, i, j));
					if(computerPieceIsSenior(i, j)) {
						computerMoves.addAll(computerBishopBackward(board, i, j));
					}
				}
				else if(piece == PIECES.CKING.value) {
					computerMoves.addAll(computerKing(board, i, j));
				}
			}
		}
		return computerMoves;
	}
	
	private List<String> computerPawnForward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		int newi = i + 1;
		int newj = j;
		
		if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj) && !collisionWithPlayerPiece(board, newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));

		}
		
		return moves;
	}
	
	private List<String> computerPawnCaptures(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		int newi = i + 1;
		int newj = j + 1;
		
		if(!collisionWithComputerPiece(board, newi, newj) && collisionWithPlayerPiece(board, newi, newj) && isOnBoard(i, j) && collisionWithPlayerPiece(board, newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		newi = i + 1;
		newj = j - 1;
		
		if(!collisionWithComputerPiece(board, newi, newj) && collisionWithPlayerPiece(board, newi, newj) && isOnBoard(i, j) && collisionWithPlayerPiece(board, newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		return moves;
	}
	
	private List<String> computerHorseForward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		//forward 1, right 2
		int newi = i + 1;
		int newj = j + 2;
		
		if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//forward 1, left 2
		newi = i + 1;
		newj = j - 2;
		
		if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//forward 2, right 1
		newi = i + 2;
		newj = j + 1;
		
		if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//forward 2, left 1
		newi = i + 2;
		newj = j - 1;
		
		if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		return moves;
	}
	
	private List<String> computerHorseBackward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		//In order to move backwards, a piece must be senior and must capture an opponents piece.
		
		//backward 1, right 2
		int newi = i - 1;
		int newj = j + 2;
		
		if(!collisionWithComputerPiece(board, newi, newj) && collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//backward 1, left 2
		newi = i - 1;
		newj = j - 2;
		
		if(!collisionWithComputerPiece(board, newi, newj) && collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//backward 2, right 1
		newi = i - 2;
		newj = j + 1;
		
		if(!collisionWithComputerPiece(board, newi, newj) && collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		//backward 2, left 1
		newi = i - 2;
		newj = j - 1;
		
		if(!collisionWithComputerPiece(board, newi, newj) && collisionWithPlayerPiece(board, newi, newj) && isOnBoard(newi, newj)) {
			moves.add(createMoveString(i, j, newi, newj));
		}
		
		return moves;
	}
	
	private List<String> computerBishopForward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		//forward 1, right 1, repeat
		int newi = i + 1;
		int newj = j + 1;
		
		for(int k = 0; k < 5; k++){
			if(collisionWithComputerPiece(board, newi, newj)){
				break;
			}
			if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)){
				moves.add(createMoveString(i, j, newi, newj));
				if(collisionWithPlayerPiece(board, newi, newj)){
					break;
				}
			}
			newi++;
			newj++;
		}
		
		//forward 1, left 1, repeat
		newi = i + 1;
		newj = j - 1;
		
		for(int k = 0; k < 5; k++){
			if(collisionWithComputerPiece(board, newi, newj)){
				break;
			}
			if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj)){
				moves.add(createMoveString(i, j, newi, newj));
				if(collisionWithPlayerPiece(board, newi, newj)){
					break;
				}
			}
			newi++;
			newj--;
		}
		
		return moves;
	}
	
	private List<String> computerBishopBackward(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();

		//backward 1, right 1, repeat
		int newi = i - 1;
		int newj = j + 1;
		
		for(int k = 0; k < 5; k++){
			if(collisionWithComputerPiece(board, newi, newj)){
				break;
			}
			if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj) && collisionWithPlayerPiece(board, newi, newj)){
				moves.add(createMoveString(i, j, newi, newj));
				if(collisionWithPlayerPiece(board, newi, newj)){
					break;
				}
			}
			newi--;
			newj++;
		}
		
		//forward 1, left 1, repeat
		newi = i - 1;
		newj = j - 1;
		
		for(int k = 0; k < 5; k++){
			if(collisionWithComputerPiece(board, newi, newj)){
				break;
			}
			if(!collisionWithComputerPiece(board, newi, newj) && isOnBoard(newi, newj) && collisionWithPlayerPiece(board, newi, newj)){
				moves.add(createMoveString(i, j, newi, newj));
				if(collisionWithPlayerPiece(board, newi, newj)){
					break;
				}
			}
			newi--;
			newj--;
		}
		
		return moves;
	}
	
	private List<String> computerKing(int[][] board, int i, int j){
		List<String> moves = new ArrayList<String>();
		int newj;
		
		if(j >= 4) {
			//right king
			newj = j + 1;
			if(!collisionWithComputerPiece(board, i, newj) && isOnBoard(i, newj)) {
				moves.add(createMoveString(i, j, i, newj));
			}
		} else {
			//left king
			newj = j - 1;
			if(!collisionWithComputerPiece(board, i, newj) && isOnBoard(i, newj)) {
				moves.add(createMoveString(i, j, i, newj));
			}
		}
		
		return moves;
	}
	
	private boolean playerPieceIsSenior(int i, int j) {
		return i >= 4;
	}
	
	private boolean computerPieceIsSenior(int i, int j) {
		return i <= 3;
	}
	
	private boolean collisionWithPlayerPiece(int[][] board, int i, int j) {
		if(isOnBoard(i, j)) {
			int piece = board[i][j];
			if(piece == PIECES.PAWN.value || piece == PIECES.HORSE.value || piece == PIECES.BISHOP.value || piece == PIECES.KING.value) {
				return true;
			}
		}
		return false;
	}
	
	private boolean collisionWithComputerPiece(int[][] board, int i, int j) {
		if(isOnBoard(i, j)) {
			int piece = board[i][j];
			if(piece == PIECES.CPAWN.value || piece == PIECES.CHORSE.value || piece == PIECES.CBISHOP.value || piece == PIECES.CKING.value) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isOnBoard(int i, int j) {
		return ((i < 6 && j < 8) && (i > -1 && j > -1));
	}
	
	private String createMoveString(int oldi, int oldj, int newi, int newj){
		String oldRow = translateRow(oldi);
		String oldColumn = translateColumn(oldj);
		
		String newRow = translateRow(newi);
		String newColumn = translateColumn(newj);
		
		return oldColumn + oldRow + newColumn + newRow; 
	}
	
	private String translateColumn(int column){
		if(column > 7 || column < 0){
			return "column doesn't make sense";
		}
		if(column == 0){return "a";} 
		else if(column == 1){return "b";}
		else if(column == 2){return "c";}
		else if(column == 3){return "d";}
		else if(column == 4){return "e";}
		else if(column == 5){return "f";}
		else if(column == 6){return "g";}
		else if(column == 7){return "h";}
		return "couldn't figure out column";
	}
	
	private String translateRow(int row) {
		if(row == 5) {
			return Integer.toString(1);
		}
		if(row == 4) {
			return Integer.toString(2);
		}
		if(row == 3) {
			return Integer.toString(3);
		}
		if(row == 2) {
			return Integer.toString(4);
		}
		if(row == 1) {
			return Integer.toString(5);
		}
		if(row == 0) {
			return Integer.toString(6);
		} else {
			return Integer.toString(-1);
		}
		
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

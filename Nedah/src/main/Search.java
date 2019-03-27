package main;

import java.util.HashMap;
import java.util.List;

public class Search{
	private String threadMove;
	private GameBoardUpdater gameBoardUpdater;
	private MoveGenerator moveGenerator;
	private int leafNodes;
	private int maxDepth;
	private int[][] board;
	private boolean searchWasCutOff;
	private HashMap<Integer, MoveSet> killerMove;
	
	public Search(int[][] board) {
		this.board = board;
		leafNodes = 0;
		gameBoardUpdater = new GameBoardUpdater();
		moveGenerator = new MoveGenerator();
		searchWasCutOff = false;
		killerMove = new HashMap<Integer, MoveSet>();
	}
	
	public String getComputerMove(long startTime, int maxDepth){
		this.searchWasCutOff = false;
		this.maxDepth = maxDepth;
		int[][] newBoard = copyBoard(board);
		int best = -5000, depth = 0, score = 0;
		String move = "";
		List<String> computerMoves = moveGenerator.generateMoves("computer", newBoard);
		for(String s : computerMoves) {
			newBoard = copyBoard(board);
			newBoard = gameBoardUpdater.updateBoard(newBoard, s); //make move
			score = min(newBoard, depth+1, -5000, 5000, startTime);
			if(score >= best) {
				best = score;
				move = s;
			}
		}
		return move;
	}
	
	private int min(int[][] board, int depth, int a, int b, long startTime) {
		int best = 5000, score = 0;
		
		if(System.currentTimeMillis() - startTime > 4990) {
			searchWasCutOff = true;
			return best;
		}
		
		if(checkForWinner(board, depth) != -1) {return checkForWinner(board, depth);}
		if(depth == maxDepth) {return minEval(board, depth);}
		
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		if(killerMove.containsKey(depth)) {
			if(killerMove.get(depth).getFirst() != null) {
				playerMoves.remove(killerMove.get(depth).getFirst());
				playerMoves.add(0, killerMove.get(depth).getFirst());
			}
			if(killerMove.get(depth).getSecond() != null) {
				playerMoves.remove(killerMove.get(depth).getSecond());
				playerMoves.add(1, killerMove.get(depth).getSecond());
			}
		}
		
		for(String s : playerMoves) {
			int[][] oldBoard = copyBoard(board);
			board = gameBoardUpdater.updateBoard(board, s);
			score = max(board, depth+1, a, b, startTime);
			if(score < best) {
				best = score;
			}
			if(score <= a) {
				addKillerMove(depth, s);
				return best; //prune
			}
			if(score < b) {
				b = score;
			}
			board = oldBoard;
		}
		return best;
	}
	
	private int max(int[][] board, int depth, int a, int b, long startTime) {
		int best = -5000, score = 0;
		
		if(System.currentTimeMillis() - startTime > 4990) {
			searchWasCutOff = true;
			return best;
		}
		
		if(checkForWinner(board, depth) != -1) {return checkForWinner(board, depth);}
		if(depth == maxDepth) {return maxEval(board, depth);}
		
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);
		if(killerMove.containsKey(depth)) {
			if(killerMove.get(depth).getFirst() != null) {
				computerMoves.remove(killerMove.get(depth).getFirst());
				computerMoves.add(0, killerMove.get(depth).getFirst());
			}
			if(killerMove.get(depth).getSecond() != null) {
				computerMoves.remove(killerMove.get(depth).getSecond());
				computerMoves.add(1, killerMove.get(depth).getSecond());
			}
		}
		for(String s : computerMoves) {
			int[][] oldBoard = copyBoard(board);
			board = gameBoardUpdater.updateBoard(board, s);
			score = min(board, depth+1, a, b, startTime);
			if(score > best) {
				best = score;
			}
			if(score >= b) {
				addKillerMove(depth, s);
				return best; //prune
			}
			if(score > a) {
				a = score;
			}
			board = oldBoard;
		}
		return best;
	}
	
	private int checkForWinner(int[][] board, int depth) {
		int computerKings = 0;
		int playerKings = 0;
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				if(board[i][j] == PIECES.KING.value){
					playerKings++;
				}
				if(board[i][j] == PIECES.CKING.value){
					computerKings++;
				}
			}
		}
		
		int pm = moveGenerator.generateMoves("player", board).size();
		int cm = moveGenerator.generateMoves("computer", board).size();
		
		if(computerKings == 0 || cm == 0) {
			return -5000 + depth;
		}
		else if(playerKings == 0 || pm == 0) {
			return 5000 - depth;
		} else {
			return -1;
		}
	}
	
	private int minEval(int[][] board, int depth){
		int computerMaterial = getComputerMaterial(board);
		int playerMaterial = getPlayerMaterial(board);
		int computerMoves = moveGenerator.generateMoves("computer", board).size();
		int playerMoves = moveGenerator.generateMoves("player", board).size();
		int computerMovesVal = computerMoves/2;
		int playerMovesVal = playerMoves/2;
		int m = computerMovesVal + playerMovesVal;
		int t = computerMaterial + playerMaterial;
		
		leafNodes++;
		return (computerMaterial + computerMovesVal) - (playerMaterial + playerMovesVal);
	}
	
	private int maxEval(int[][] board, int depth){
		int computerMaterial = getComputerMaterial(board);
		int playerMaterial = getPlayerMaterial(board);
		int computerMoves = moveGenerator.generateMoves("computer", board).size();
		int playerMoves = moveGenerator.generateMoves("player", board).size();
		int computerMovesVal = computerMoves/2;
		int playerMovesVal = playerMoves/2;
		int m = computerMovesVal + playerMovesVal;
		int t = computerMaterial + playerMaterial;
		
		leafNodes++;
		return (computerMaterial + computerMovesVal) - (playerMaterial + playerMovesVal);
	}
	
	private int getComputerMaterial(int[][] board){
		int computerMaterial = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				int piece = board[i][j];
				if(piece == PIECES.CPAWN.value){
					computerMaterial += 1;
				}
				else if(piece == PIECES.CBISHOP.value){
					computerMaterial += 3;
				}
				else if(piece == PIECES.CHORSE.value){
					computerMaterial += 3;
				}
				else if(piece == PIECES.CKING.value) {
					computerMaterial += 10;
				}
			}
		}
		return computerMaterial;
	}
	
	private int getPlayerMaterial(int[][] board){
		int playerMaterial = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				int piece = board[i][j];
				if(piece == PIECES.PAWN.value){
					playerMaterial += 1;
				}
				else if(piece == PIECES.BISHOP.value){
					playerMaterial += 3;
				}
				else if(piece == PIECES.HORSE.value){
					playerMaterial += 3;
				}
				else if(piece == PIECES.KING.value) {
					playerMaterial += 10;
				}
			}
		}
		return playerMaterial;
	}
	
	private int[][] copyBoard(int[][] board) {
		int[][] copy = new int[6][8];
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}
	
	private void addKillerMove(int depth, String move) {
		if(killerMove.get(depth) == null) {
			MoveSet set = new MoveSet();
			set.setFirst(move);
			killerMove.put(depth, set);
		} else {
			String moveOne = killerMove.get(depth).getFirst();
			MoveSet set = new MoveSet();
			
			set.setFirst(move);
			
			if(moveOne != null) {
				set.setSecond(moveOne);
			}
			
			killerMove.put(depth, set);
		}
	}
	
	public void resetKillerMove() {
		killerMove = new HashMap<Integer, MoveSet>();
	}
	
	public String getThreadsMove() {
		return threadMove;
	}
	
	public int getDepth() {
		return maxDepth;
	}
	
	public int getLeafNodes() {
		return leafNodes;
	}
	
	public boolean getSearchWasCutOff() {
		return searchWasCutOff;
	}
}

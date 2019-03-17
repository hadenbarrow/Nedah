package main;

import java.util.List;

public class Search extends Thread{
	private volatile String threadMove;
	private GameBoardUpdater gameBoardUpdater;
	private MoveGenerator moveGenerator;
	private volatile int maxDepth;
	private int leafNodes;
	private int[][] board;
	private boolean running;
	
	public Search(int[][] board) {
		this.board = board;
		maxDepth = 1;
		leafNodes = 0;
		gameBoardUpdater = new GameBoardUpdater();
		moveGenerator = new MoveGenerator();
		running = true;
	}
	
	public void run() {
		getComputerMove();
	}
	
	private void getComputerMove(){
		while(running) { //iterative deepening
			int[][] newBoard = copyBoard(board);
			int best = -5000, depth = 0, score = 0;
			String move = "";
			List<String> computerMoves = moveGenerator.generateMoves("computer", newBoard);
			for(String s : computerMoves) {
				newBoard = copyBoard(board);
				newBoard = gameBoardUpdater.updateBoard(newBoard, s); //make move
				score = min(newBoard, depth+1, -5000, 5000);
				if(score > best) {
					best = score;
					move = s;
				}
			}
			threadMove = move;
			maxDepth++; //deepen search by 1
		}
	}
	
	private int min(int[][] board, int depth, int a, int b) {
		int best = 5000, score = 0;
		
		if(checkForWinner(board) != -1) {return checkForWinner(board);}
		if(depth == maxDepth) {return minEval(board, depth);}
		
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		for(String s : playerMoves) {
			int[][] oldBoard = copyBoard(board);
			board = gameBoardUpdater.updateBoard(board, s);
			score = max(board, depth+1, a, b);
			if(score < best) {
				best = score;
			}
			if(score <= a) {
				return best;
			}
			if(score < b) {
				b = score;
			}
			board = oldBoard;
		}
		return best;
	}
	
	private int max(int[][] board, int depth, int a, int b) {
		int best = -5000, score = 0;
		
		if(checkForWinner(board) != -1) {return checkForWinner(board);}
		if(depth == maxDepth) {return maxEval(board, depth);}
		
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);
		for(String s : computerMoves) {
			int[][] oldBoard = copyBoard(board);
			board = gameBoardUpdater.updateBoard(board, s);
			score = min(board, depth+1, a, b);
			if(score > best) {
				best = score;
			}
			if(score >= b) {
				return best;
			}
			if(score > a) {
				a = score;
			}
			board = oldBoard;
		}
		return best;
	}
	
	private int checkForWinner(int[][] board) {
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
		if(computerKings == 0) {
			return -5000;
		}
		else if(playerKings == 0) {
			return 5000;
		} else {
			return -1;
		}
	}
	
	private int minEval(int[][] board, int depth){
		int computerMaterial = getComputerMaterial(board);
		int playerMaterial = getPlayerMaterial(board);
		
		leafNodes++;
		return computerMaterial - (playerMaterial - depth);
	}
	
	private int maxEval(int[][] board, int depth){
		int computerMaterial = getComputerMaterial(board);
		int playerMaterial = getPlayerMaterial(board);
		
		leafNodes++;
		return computerMaterial - (playerMaterial + depth);
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
					computerMaterial += 3;
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
					playerMaterial += 3;
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
	
	public String getThreadsMove() {
		return threadMove;
	}
	
	public int getDepth() {
		return maxDepth;
	}
	
	public int getLeafNodes() {
		return leafNodes;
	}
	
	public void stopSearch() {
		running = false;
	}
}

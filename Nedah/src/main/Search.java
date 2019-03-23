package main;

import java.util.List;

public class Search{
	private String threadMove;
	private GameBoardUpdater gameBoardUpdater;
	private MoveGenerator moveGenerator;
	private int leafNodes;
	private int maxDepth;
	private boolean searchWasCutOff;
	private PositionLookUpTable positionLookup;
	
	public Search() {
		leafNodes = 0;
		gameBoardUpdater = new GameBoardUpdater();
		moveGenerator = new MoveGenerator();
		searchWasCutOff = false;
		positionLookup = new PositionLookUpTable();
	}
	
	public String getComputerMove(int[][] board, long startTime, int maxDepth){
		searchWasCutOff = false;
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
		int best = 5000, score = 0; String bestMove = "";
		
		if(System.currentTimeMillis() - startTime > 4990) {
			searchWasCutOff = true;
			return best;
		}
		
		if(checkForWinner(board, depth) != -1) {return checkForWinner(board, depth);}
		if(depth == maxDepth) {
			return minEval(board, depth);
		}
		
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		
		
		if(positionLookup.containsBoard(board) && positionLookup.getDepth(board) >= depth) {
			return positionLookup.getEvaluation(board);
		}
		
		for(String s : playerMoves) {
			int[][] oldBoard = copyBoard(board);
			board = gameBoardUpdater.updateBoard(board, s);
			score = max(board, depth+1, a, b, startTime);
			if(score < best) {
				best = score;
				bestMove = s;
			}
			if(score <= a) {
				
				return best; //prune
			}
			if(score < b) {
				b = score;
			}
			positionLookup.addToTable(board, score, a, b, bestMove, depth);
			board = oldBoard;
		}
		return best;
	}
	
	private int max(int[][] board, int depth, int a, int b, long startTime) {
		int best = -5000, score = 0; String bestMove ="";
		
		if(System.currentTimeMillis() - startTime > 4990) {
			searchWasCutOff = true;
			return best;
		}
		
		if(checkForWinner(board, depth) != -1) {return checkForWinner(board, depth);}
		if(depth == maxDepth) {
			return maxEval(board, depth);
		}
		
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);
		
		if(positionLookup.containsBoard(board) && positionLookup.getDepth(board) >= depth) {
			return positionLookup.getEvaluation(board);
		}
		
		for(String s : computerMoves) {
			int[][] oldBoard = copyBoard(board);
			board = gameBoardUpdater.updateBoard(board, s);
			score = min(board, depth+1, a, b, startTime);
			if(score > best) {
				best = score;
				bestMove = s;
			}
			if(score >= b) {
				return best; //prune
			}
			if(score > a) {
				a = score;
			}
			positionLookup.addToTable(board, score, a, b, bestMove, depth);
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
		if(computerKings == 0) {
			return -5000 + depth;
		}
		else if(playerKings == 0) {
			return 5000 - depth;
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
	
	public boolean getSearchWasCutOff() {
		return searchWasCutOff;
	}
	
	public static void main(String[] args) {
		GameBoardInitializer gbi = new GameBoardInitializer();
		GameBoardUpdater gbu = new GameBoardUpdater();
		int[][] board = gbi.generateNewBoard();
		Search search = new Search();
		String s = search.getComputerMove(board, System.currentTimeMillis(), 8);
		System.out.println(s);
		gbu.updateBoard(board, s);
		Search2 search2 = new Search2(board);
		String s2 = search2.getComputerMove(System.currentTimeMillis(), 8);
		System.out.println(s2);
		gbu.updateBoard(board, s2);
		s = search.getComputerMove(board, System.currentTimeMillis(), 8);
		System.out.println(s);
	}
}

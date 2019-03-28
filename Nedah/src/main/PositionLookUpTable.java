package main;

import java.util.HashMap;

public class PositionLookUpTable {
	//hash to board position
	private HashMap<Integer, BoardPosition> positionTable;
	ZorbristHashing hasher;
	
	public PositionLookUpTable() {
		positionTable = new HashMap<Integer, BoardPosition>();
		hasher = new ZorbristHashing();
	}
	
	public boolean containsBoard(int[][] board) {
		int hash = hasher.hash(board);
		return positionTable.containsKey(hash);
	}
	
	public void addToTable(int[][] board, int evaluation, int a, int b, String bestMove, int depth) {
		BoardPosition bp = new BoardPosition(evaluation, a, b, bestMove, depth);
		int hash = hasher.hash(board);
		positionTable.put(hash, bp);
	}
	
	public int getEvaluation(int[][] board) {
		int hash = hasher.hash(board);
		return positionTable.get(hash).getEvaluation();
	}
	
	public int getDepth(int[][] board) {
		int hash = hasher.hash(board);
		return positionTable.get(hash).getDepth();
	}
	
	public int getAlpha(int[][] board) {
		int hash = hasher.hash(board);
		return positionTable.get(hash).getA();
	}
	
	public int getBeta(int[][] board) {
		int hash = hasher.hash(board);
		return positionTable.get(hash).getB();
	}
	
	public String getBestMove(int[][] board) {
		int hash = hasher.hash(board);
		return positionTable.get(hash).getBestMove();
	}
}

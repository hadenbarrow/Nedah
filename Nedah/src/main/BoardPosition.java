package main;

public class BoardPosition {
	private int evaluation, depth, a, b;
	private String bestMove;
	
	public BoardPosition(int evaluation, int a, int b, String bestMove, int depth) {
		this.evaluation = evaluation;
		this.depth = depth;
		this.a = a;
		this.b = b;
		this.setBestMove(bestMove);
	}

	public int getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
	
	public void setDepth() {
		this.depth = depth;
	}
	
	public int getDepth() {
		return depth;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public String getBestMove() {
		return bestMove;
	}

	public void setBestMove(String bestMove) {
		this.bestMove = bestMove;
	}
}

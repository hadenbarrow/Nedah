package main;

import java.util.TimerTask;

public class Searcher extends TimerTask {
	Search searchThread;
	String move;
	int depth;
	int leafNodes;

	public Searcher(int[][] board) {
		move = "";
		depth = 0;
		searchThread = new Search(board);
		searchThread.start();
	}

	@Override
	public void run() {
 		move = searchThread.getThreadsMove();
		depth = searchThread.getDepth();
		leafNodes = searchThread.getLeafNodes();
	}

	public String getMove() {
		return move;
	}

	public int getDepth() {
		return depth;
	}
	
	public int getLeafNodes() {
		return leafNodes;
	}

}
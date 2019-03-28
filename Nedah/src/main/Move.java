package main;

public class Move implements Comparable{
	String move;
	int weight;
	
	public Move(String move, int weight) {
		this.move = move;
		this.weight = weight;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	
	public int compareTo(Object move) {
		if(this.weight > ((Move) move).getWeight()) {
			return 1;
		}
		else if(this.weight < ((Move) move).getWeight()) {
			return -1;
		}
		return 0;
	}
	
}

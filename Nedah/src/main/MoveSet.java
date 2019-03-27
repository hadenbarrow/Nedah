package main;

public class MoveSet {
	String first, second;
	
	public MoveSet() {
		this.first = "";
		this.second = "";
	}
	
	public MoveSet(String first, String second) {
		this.first = first;
		this.second = second;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}
}

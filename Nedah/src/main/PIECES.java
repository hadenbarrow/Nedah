package main;

public enum PIECES {
	PAWN(1), HORSE(2), BISHOP(3), KING(4), CPAWN(5), CHORSE(6), CBISHOP(7), CKING(8);
	
	public int value;

	PIECES(int code){
		this.value = code;
	}
}

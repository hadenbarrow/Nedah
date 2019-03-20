package main;

public class MoveTranslator {

	public String translateMove(String move) {
		if(move.isEmpty()) {
			return "move is empty!";
		}
		String oldPos = move.substring(0,2);
		String newPos = move.substring(2);
		
		String oldColumn = oldPos.substring(0,1);
		String oldRow = oldPos.substring(1);
		oldRow = translateRow(oldRow);
		
		String newColumn = newPos.substring(0,1);
		String newRow = newPos.substring(1);
		newRow = translateRow(newRow);
		
		return oldColumn+oldRow+newColumn+newRow;
	}
	
	private String translateRow(String row) {
		if(row.equals("6")) {
			return "1";
		}
		else if(row.equals("5")) {
			return "2";
		}
		else if(row.equals("4")) {
			return "3";
		}
		else if(row.equals("3")) {
			return "4";
		}
		else if(row.equals("2")) {
			return "5";
		}
		else if(row.equals("1")) {
			return "6";
		} else {
			return "couldn't translate row";
		}
		
	}
	
	public static void main(String[] args) {
		MoveTranslator mt = new MoveTranslator();
		System.out.println(mt.translateMove("g6c2"));
	}
}

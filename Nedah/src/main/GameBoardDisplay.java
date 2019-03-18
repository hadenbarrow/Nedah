package main;

public class GameBoardDisplay {
	
	public GameBoardDisplay(){}
	
	public void displayBoard(int[][] board){
		System.out.println("\n");
		System.out.println("-------------------- Computer");
		for(int i = 0; i < board.length; i++){
			System.out.print(6-i+ "  "); //label the rows
			for(int j = 0; j < board[0].length ; j++){
				System.out.print(pieceLookup(board[i][j]) + " ");
			}
			System.out.println("");
		}
		System.out.println("-------------------- Human");
		System.out.println("   A B C D E F G H");
	}
	
	public String pieceLookup(int code){
		//players pieces
		if(code == PIECES.PAWN.value){
			return "p";
		}
		if(code == PIECES.HORSE.value){
			return "h";
		}
		if(code == PIECES.BISHOP.value){
			return "b";
		}
		if(code == PIECES.KING.value){
			return "k";
		}
		//computer pieces
		if(code == PIECES.CPAWN.value){
			return "P";
		}
		if(code == PIECES.CHORSE.value){
			return "H";
		}
		if(code == PIECES.CBISHOP.value){
			return "B";
		}
		if(code == PIECES.CKING.value){
			return "K";
		}
		return "-";
	}
	
	public char columnLookup(int code){
		if(code == 7){
			return 'A';
		}
		if(code == 6){
			return 'B';
		}
		if(code == 5){
			return 'C';
		}
		if(code == 4){
			return 'D';
		}
		if(code == 3){
			return 'E';
		}
		if(code == 2){
			return 'F';
		}
		if(code == 1){
			return 'G';
		}
		if(code == 0){
			return 'H';
		}
		return ' ';
	}
	
	public static void main(String[] args){
		GameBoardInitializer gbi = new GameBoardInitializer();
		int[][] board = gbi.generateNewBoard();
		GameBoardDisplay gbd = new GameBoardDisplay();
		gbd.displayBoard(board);
	}
}

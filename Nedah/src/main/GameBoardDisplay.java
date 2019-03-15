package main;

public class GameBoardDisplay {
	
	public GameBoardDisplay(){}
	
	public void displayBoard(int[][] board){
		System.out.println("\n");
		System.out.println("-------------------- Computer");
		for(int i = 0; i < board.length; i++){
			System.out.print(i+1+ "  "); //label the rows
			for(int j = 0; j < board[0].length ; j++){
				System.out.print(pieceLookup(board[i][j]) + " ");
			}
			System.out.println("");
		}
		System.out.println("-------------------- Human");
		System.out.println("   A B C D E F G H");
	}
	
	public char pieceLookup(int code){
		//players pieces
		if(code == 1){
			return 'p';
		}
		if(code == 2){
			return 'h';
		}
		if(code == 3){
			return 'b';
		}
		if(code == 4){
			return 'k';
		}
		//computer pieces
		if(code == 5){
			return 'P';
		}
		if(code == 6){
			return 'H';
		}
		if(code == 7){
			return 'B';
		}
		if(code == 8){
			return 'K';
		}
		return '-';
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

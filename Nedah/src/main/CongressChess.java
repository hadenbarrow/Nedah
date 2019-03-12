package main;

import java.util.List;
import java.util.Scanner;

public class CongressChess {
	private boolean run, computerTurn, illegalMoveFlag, playerWins, computerWins;
	private int[][] board;
	private Scanner scanner;
	private GameBoardDisplay gameBoardDisplay;
	private MoveGenerator moveGenerator;
	private GameBoardInitializer gameBoardInitializer;
	private GameBoardUpdater gameBoardUpdater;
	
	public CongressChess(){
		init();
		intro();
		run();
	}
	
	public void init(){
		scanner = new Scanner(System.in);
		gameBoardInitializer = new GameBoardInitializer();
		board = gameBoardInitializer.generateNewBoard();
		gameBoardDisplay = new GameBoardDisplay();
		moveGenerator = new MoveGenerator();
		gameBoardUpdater = new GameBoardUpdater();
		illegalMoveFlag = false;
		run = true;
	}
	
	public void intro(){
		System.out.println("Welcome to Congress Chess.");
		System.out.println("You will be playing against an AI called Nedah.");
		System.out.println("Who would you like to make the first move? Enter 1 for human or 2 for computer: ");
		String s = scanner.nextLine();
		if(s.charAt(0) == '2'){
			computerTurn = true;
		} else {
			computerTurn = false;
		}
		displayBoard();
	}
	
	
	public void run(){
		while(run){
			if(computerTurn){
				String move = makeComputerMove();
				updateBoardState(move);
				checkGameOver();
				displayBoard();
				System.out.println("My move is : " + move);
				computerTurn = false;
			} else {
				String userMove = promptUserForMove();
				illegalMoveFlag = checkMove(userMove);
				while(illegalMoveFlag){
					userMove = promptUserForMove();
					illegalMoveFlag = checkMove(userMove);
				}
				updateBoardState(userMove);
				checkGameOver();
				displayBoard();
				computerTurn = true;
			}
			
			if(computerTurn){
				String move = makeComputerMove();
				updateBoardState(move);
				checkGameOver();
				displayBoard();
				System.out.println("My move is : " + move);
				computerTurn = false;
			} else {
				String userMove = promptUserForMove();
				illegalMoveFlag = checkMove(userMove);
				while(illegalMoveFlag){
					userMove = promptUserForMove();
					illegalMoveFlag = checkMove(userMove);
				}
				updateBoardState(userMove);
				checkGameOver();
				displayBoard();
				computerTurn = true;
			}

		}
		if(playerWins){
			System.out.println("The player wins!");
		}
		else if(computerWins){
			System.out.println("Nedah wins!");
		} else{
			System.out.println("The game is a draw.");
		}
	}
	
	public String promptUserForMove(){
		String validMoves = getValidMoves();
		System.out.println("Valid moves for player: " + validMoves);
		System.out.println("Enter your move: ");
		return scanner.nextLine();
	}
	
	public boolean checkMove(String move){
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		if(!playerMoves.contains(move)){
			System.err.println("That move is not valid");
			System.out.println("");
			return true;
		}
		return false;
	}
	
	public void updateBoardState(String move){
		board = gameBoardUpdater.updateBoard(board, move);
	}
	
	public void checkGameOver(){
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
		if(computerKings == 0){
			computerWins = true;
			run = false;
		}
		else if(playerKings == 0){
			playerWins =  true;
			run = false;
		}
	}
	
	public String makeComputerMove(){
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);
		System.out.print("Valid moves for Nedah: ");
		
		for(String s : computerMoves){
			System.out.print(s + " ");
		}
		System.out.println("");
		//System.out.println("My move is : " + computerMoves.get(1));
		return computerMoves.get(1);
	}
	
	public void displayBoard(){
		gameBoardDisplay.displayBoard(board);
	}
	
	public String getValidMoves(){
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		String temp ="";
		for(String s : playerMoves){
			temp += s + " ";
		}
		return temp;
	}
	
	
	
	public static void main(String[] args){
		CongressChess instance = new CongressChess();
	}
	
	
}

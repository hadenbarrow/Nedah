package main;

import java.util.List;
import java.util.Scanner;

public class CongressChess {
	private boolean run, computerTurn;
	private int[][] board;
	private Scanner scanner;
	private GameBoardDisplay gameBoardDisplay;
	private MoveGenerator moveGenerator;
	private GameBoardInitializer gameBoardInitializer;
	
	public static final int PAWN = 1;
	public static final int HORSE = 2;
	public static final int BISHOP = 3;
	public static final int KING = 4;
	
	public static final int CPAWN = 5;
	public static final int CHORSE = 6;
	public static final int CBISHOP = 7;
	public static final int CKING = 8;
	
	
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
				computerTurn = false;
			} else {
				String userMove = promptUserForMove();
				checkMove(userMove);
				updateBoardState(userMove);
				checkGameOver();
				displayBoard();
			}
			
			if(computerTurn){
				String move = makeComputerMove();
				updateBoardState(move);
				checkGameOver();
				displayBoard();
				computerTurn = false;
			} else {
				String userMove = promptUserForMove();
				checkMove(userMove);
				updateBoardState(userMove);
				checkGameOver();
				displayBoard();
			}

		}
	}
	
	public String promptUserForMove(){
		String validMoves = getValidMoves();
		System.out.println("Valid moves are: " + validMoves);
		System.out.println("Enter your move: ");
		return scanner.nextLine();
	}
	
	public void checkMove(String move){}
	
	public void updateBoardState(String move){
		
	}
	
	public void checkGameOver(){}
	
	public String makeComputerMove(){
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);
		System.out.println("Possible computer moves: ");
		
		for(String s : computerMoves){
			System.out.println(s);
		}
		return computerMoves.get(0);
	}
	
	public void displayBoard(){
		gameBoardDisplay.displayBoard(board);
	}
	
	public String getValidMoves(){
		return "";
	}
	
	
	
	public static void main(String[] args){
		CongressChess instance = new CongressChess();
	}
	
	
}

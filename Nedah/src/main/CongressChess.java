package main;

import java.util.List;
import java.util.Random;
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
	
	private void init(){
		scanner = new Scanner(System.in);
		gameBoardInitializer = new GameBoardInitializer();
		board = gameBoardInitializer.generateNewBoard();
		gameBoardDisplay = new GameBoardDisplay();
		moveGenerator = new MoveGenerator();
		gameBoardUpdater = new GameBoardUpdater();
		illegalMoveFlag = false;
		run = true;
	}
	
	private void intro(){
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
				checkGameOver(board);
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
				checkGameOver(board);
				displayBoard();
				computerTurn = true;
			}
			
			if(computerTurn){
				String move = makeComputerMove();
				updateBoardState(move);
				checkGameOver(board);
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
				checkGameOver(board);
				displayBoard();
				computerTurn = true;
			}

		}
		if(playerWins){
			System.out.println("Both of Nedahs kings have been captured.");
			System.out.println("The player wins!");
		}
		else if(computerWins){
			System.out.println("Both of the players kings have been captured.");
			System.out.println("Nedah wins!");
		} else{
			System.out.println("The game is a draw.");
		}
	}
	
	private String promptUserForMove(){
		String validMoves = getValidMoves();
		System.out.println("Valid moves for player: " + validMoves);
		System.out.println("Enter your move: ");
		return scanner.nextLine();
	}
	
	private boolean checkMove(String move){
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		if(!playerMoves.contains(move)){
			System.err.println("That move is not valid");
			System.out.println("");
			return true;
		}
		return false;
	}
	
	private void updateBoardState(String move){
		board = gameBoardUpdater.updateBoard(board, move);
	}
	
	private boolean checkGameOver(int[][] board){
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
			playerWins = true;
			run = false;
			return true;
		}
		else if(playerKings == 0){
			computerWins =  true;
			run = false;
			return true;
		}
		return false;
	}
	
	private String makeComputerMove(){
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);
		System.out.print("Valid moves for Nedah: ");
		
		for(String s : computerMoves){
			System.out.print(s + " ");
		}
		System.out.println("");
		Random rand = new Random();
		int selection = rand.nextInt(computerMoves.size());
		return computerMoves.get(selection);
	}
	
	private void displayBoard(){
		gameBoardDisplay.displayBoard(board);
	}
	
	private String getValidMoves(){
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		String temp ="";
		for(String s : playerMoves){
			temp += s + " ";
		}
		return temp;
	}
	
	
	private int minimax(int[][] board, int depth, boolean maximizingPlayer){
		if(depth == 0 || checkGameOver(board)){
			return eval(board);
		}
		return 0;
	}
	
	private int eval(int[][] board){
		int computerMaterial = getComputerMaterial(board);
		int playerMaterial = getPlayerMaterial(board);
		
		return computerMaterial - playerMaterial;
	}
	
	private int getComputerMaterial(int[][] board){
		int computerMaterial = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				int piece = board[i][j];
				if(piece == PIECES.CPAWN.value){
					computerMaterial += 1;
				}
				else if(piece == PIECES.CBISHOP.value){
					computerMaterial += 3;
				}
				else if(piece == PIECES.CHORSE.value){
					computerMaterial += 3;
				}
			}
		}
		return computerMaterial;
	}
	
	private int getPlayerMaterial(int[][] board){
		int playerMaterial = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				int piece = board[i][j];
				if(piece == PIECES.PAWN.value){
					playerMaterial += 1;
				}
				else if(piece == PIECES.BISHOP.value){
					playerMaterial += 3;
				}
				else if(piece == PIECES.HORSE.value){
					playerMaterial += 3;
				}
			}
		}
		return playerMaterial;
	}
	
	
	
	public static void main(String[] args){
		CongressChess instance = new CongressChess();
	}
	
	
}

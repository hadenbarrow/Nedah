package main;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CongressChess {
	private boolean run, computerTurn, illegalMoveFlag, playerWinsByKings, computerWinsByKings, playerWinsByMoves, computerWinsByMoves;
	private int[][] board;
	private int[][] backupBoard;
	private Scanner scanner;
	private GameBoardDisplay gameBoardDisplay;
	private MoveGenerator moveGenerator;
	private GameBoardInitializer gameBoardInitializer;
	private GameBoardUpdater gameBoardUpdater;
	private int maxDepth;
	
	public CongressChess(){
		init();
		intro();
		run();
	}
	
	private void init(){
		scanner = new Scanner(System.in);
		gameBoardInitializer = new GameBoardInitializer();
		board = gameBoardInitializer.generateNewBoard();
		backupBoard = board;
		gameBoardDisplay = new GameBoardDisplay();
		moveGenerator = new MoveGenerator();
		gameBoardUpdater = new GameBoardUpdater();
		illegalMoveFlag = false;
		maxDepth = 3;
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
				String computerMove = getComputerMove();
				System.out.println("Nedah's move is: " + computerMove);
				updateBoardState(computerMove);
				checkGameOver(board);
				displayBoard();
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
				String computerMove = getComputerMove();
				System.out.println("Nedah's move is: " + computerMove);
				updateBoardState(computerMove);
				checkGameOver(board);
				displayBoard();
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
		if(playerWinsByKings){
			System.out.println("Both of Nedahs kings have been captured.");
			System.out.println("The player wins!");
		}
		else if(computerWinsByKings){
			System.out.println("Both of the players kings have been captured.");
			System.out.println("Nedah wins!");
		} 
		else if(playerWinsByMoves) {
			System.out.println("The computer has no valid moves.");
			System.out.println("Nedah wins!");
		}
		else if(computerWinsByMoves) {
			System.out.println("The player has no valid moves.");
			System.out.println("Nedah wins!");
		}
		else{
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
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);

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
			playerWinsByKings = true;
			run = false;
			return true;
		}
		else if(playerKings == 0){
			computerWinsByKings =  true;
			run = false;
			return true;
		}
		else if(playerMoves.isEmpty()) {
			computerWinsByMoves = true;
			run = false;
			return true;
		}
		else if(computerMoves.isEmpty()) {
			playerWinsByMoves = true;
			run = false;
			return true;
		}
		return false;
	}
	
	private String getComputerMove() {
		Timer timer = new Timer();
		Searcher searchTask = new Searcher(board);
		long startTime = System.currentTimeMillis()/1000;
		timer.schedule(searchTask, 4500);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long timeElapsed = (System.currentTimeMillis()/1000) - startTime;
		System.out.println("Searched " + searchTask.getDepth() + " plys in " + timeElapsed + " seconds");
		return searchTask.getMove();
	}
	
	/*
	private String getComputerMove(){
		maxDepth = 3;
		String bestMove = "";
		long startTime = System.currentTimeMillis()/1000;
		long elapsedTime = 0;
		while(elapsedTime < 5) {
			int[][] newBoard = copyBoard(board);
			int best = -5000, depth = 0, score = 0;
			String move = "";
			List<String> computerMoves = moveGenerator.generateMoves("computer", newBoard);
			for(String s : computerMoves) {
				newBoard = copyBoard(board);
				newBoard = gameBoardUpdater.updateBoard(newBoard, s); //make move
				score = min(newBoard, depth+1);
				if(score > best) {
					best = score;
					move = s;
				}
			}
			bestMove = move;
			maxDepth++;
			elapsedTime = (System.currentTimeMillis()/1000) - startTime;
		}
		System.out.println("Searched " + maxDepth + " plys in " + elapsedTime + " seconds");
		return bestMove;
	}
	*/
	
	private int min(int[][] board, int depth) {
		int best = 5000, score = 0;
		
		if(checkForWinner(board) != -1) {return checkForWinner(board);}
		if(depth == maxDepth) {return eval(board);}
		
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		for(String s : playerMoves) {
			int[][] oldBoard = copyBoard(board);
			board = gameBoardUpdater.updateBoard(board, s);
			score = max(board, depth+1);
			if(score < best) {
				best = score;
			}
			board = oldBoard;
		}
		return best;
	}
	
	private int max(int[][] board, int depth) {
		int best = -5000, score = 0;
		
		if(checkForWinner(board) != -1) {return checkForWinner(board);}
		if(depth == maxDepth) {return eval(board);}
		
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);
		for(String s : computerMoves) {
			int[][] oldBoard = copyBoard(board);
			board = gameBoardUpdater.updateBoard(board, s);
			score = min(board, depth+1);
			if(score > best) {
				best = score;
			}
			board = oldBoard;
		}
		return best;
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
		
	private int checkForWinner(int[][] board) {
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
		if(computerKings == 0) {
			return -5000;
		}
		else if(playerKings == 0) {
			return 5000;
		} else {
			return -1;
		}
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
	
	private int[][] copyBoard(int[][] board) {
		int[][] copy = new int[6][8];
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}
		
	public static void main(String[] args){
		CongressChess instance = new CongressChess();
	}
	
	
}

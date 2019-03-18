package main;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

public class CongressChess {
	private boolean run, computerTurn, illegalMoveFlag, playerWinsByKings, computerWinsByKings, playerWinsByMoves, computerWinsByMoves;
	private int[][] board;
	private Scanner scanner;
	private GameBoardDisplay gameBoardDisplay;
	private MoveGenerator moveGenerator;
	private GameBoardInitializer gameBoardInitializer;
	private GameBoardUpdater gameBoardUpdater;
	private MoveTranslator moveTranslator;
	public boolean nedahWon;
	public int turns;
	public int gameLength;
	
	public CongressChess(boolean nedahFirst){
		init();
		//intro();
		computerTurn = nedahFirst; 
		run();
	}
	
	private void init(){
		scanner = new Scanner(System.in);
		gameBoardInitializer = new GameBoardInitializer();
		board = gameBoardInitializer.generateNewBoard();
		gameBoardDisplay = new GameBoardDisplay();
		moveGenerator = new MoveGenerator();
		gameBoardUpdater = new GameBoardUpdater();
		moveTranslator = new MoveTranslator();
		illegalMoveFlag = false;
		//analyitcs stuff
		nedahWon = false;
		turns = 0;
		gameLength = 0;
		//
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
		long startTime = System.currentTimeMillis();
		while(run){
			if(computerTurn){
				String computerMove = getComputerMove();
				System.out.println("Nedah's move is: " + computerMove + " (" + moveTranslator.translateMove(computerMove)+")");
				updateBoardState(computerMove);
				if (checkGameOverByKings(board)) {
					displayBoard();
					break;
				}
				checkGameOverByMoves(board);
				displayBoard();
				computerTurn = false;
			} else {
				String opposingAiMove = getOpposingAiMove();
				updateBoardState(opposingAiMove);
				System.out.println("Opposing AI's move is: " + opposingAiMove + " (" + moveTranslator.translateMove(opposingAiMove)+")");
				if (checkGameOverByKings(board)) {
					displayBoard();
					break;
				}
				checkGameOverByMoves(board);
				displayBoard();
				computerTurn = true;
			}
			
			if(computerTurn){
				String computerMove = getComputerMove();
				System.out.println("Nedah's move is: " + computerMove + " (" + moveTranslator.translateMove(computerMove)+")");
				updateBoardState(computerMove);
				if (checkGameOverByKings(board)) {
					displayBoard();
					break;
				}
				checkGameOverByMoves(board);
				displayBoard();
				computerTurn = false;
			} else {
				String opposingAiMove = getOpposingAiMove();
				updateBoardState(opposingAiMove);
				System.out.println("Opposing AI's move is: " + opposingAiMove + " (" + moveTranslator.translateMove(opposingAiMove)+")");
				if (checkGameOverByKings(board)) {
					displayBoard();
					break;
				}
				checkGameOverByMoves(board);
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
			nedahWon = true;
		} 
		else if(playerWinsByMoves) {
			System.out.println("The computer has no valid moves.");
			System.out.println("The player wins!");
		}
		else if(computerWinsByMoves) {
			System.out.println("The player has no valid moves.");
			System.out.println("Nedah wins!");
			nedahWon = true;
		}
		else{
			System.out.println("The game is a draw.");
		}
		gameLength = (int) (System.currentTimeMillis() - startTime)/1000;
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
	
	private boolean checkGameOverByKings(int[][] board){
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
		return false;
	}
	
	private boolean checkGameOverByMoves(int[][] board) {
		List<String> playerMoves = moveGenerator.generateMoves("player", board);
		List<String> computerMoves = moveGenerator.generateMoves("computer", board);
		
		if(playerMoves.isEmpty()) {
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
		String move = "";
		int depth = 1;
		Search search = new Search(board);
		long startTime = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - startTime < 4990) {
			String temp = search.getComputerMove(startTime, depth);
			if(!search.getSearchWasCutOff()) {
				move = temp;
				depth++;
			}
		}
		int timeElapsed = (int) ((System.currentTimeMillis()-startTime)/1000);
		System.out.println("Nedah searched " + depth + " plys");
		turns++;
		return move;
	}
	
	private String getOpposingAiMove() {
		String move = "";
		int depth = 1;
		Search2 search = new Search2(board);
		long startTime = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - startTime < 4990) {
			String temp = search.getComputerMove(startTime, depth);
			if(!search.getSearchWasCutOff()) {
				move = temp;
				depth++;
			}
		}
		System.out.println("The opposing AI searched " + depth + " plys");
		turns++;
		return move;
	}
	
	private String getRandomUserMove() {
		List<String> moves = moveGenerator.generateMoves("player", board);
		Random rand = new Random();
		int r =rand.nextInt(moves.size());
		return moves.get(r);
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
		
	public static void main(String[] args){
		int games = 4;
		int nedahGames = 0;
		int gameLength = 0;
		int turns = 0;
		
		for(int i =0; i < games/2; i++) {
			CongressChess instance = new CongressChess(true);
			if(instance.nedahWon) {
				nedahGames++;
			}
			gameLength += instance.gameLength;
			turns += instance.turns;
		}
		
		for(int i =0; i < games/2; i++) {
			CongressChess instance = new CongressChess(false);
			if(instance.nedahWon) {
				nedahGames++;
			}
			gameLength += instance.gameLength;
			turns += instance.turns;
		}
		
		System.out.println("Nedah won " + nedahGames + " out of " + games);
		System.out.println("Games lasted " + turns/games + " turns on average");
		System.out.println("Games lasted " + gameLength/games + " seconds on average");
	}
	
	
}

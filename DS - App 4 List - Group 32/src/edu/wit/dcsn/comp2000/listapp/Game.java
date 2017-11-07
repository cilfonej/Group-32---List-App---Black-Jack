package edu.wit.dcsn.comp2000.listapp;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Joshua Cilfone
 *
 */
public class Game {
	private List<Player> players;
	private Dealer dealer;
	
	private Scanner scan;
	
	public Game() {
		players = new ArrayList<>();
		scan = new Scanner(System.in);
		
		players.add(dealer = new Dealer());
	}
	
	private void createPlayer() {
		int playerCount = -1;
		
		do {
			System.out.print("How many people will be playing? ");
			try { playerCount = scan.nextInt(); }
			catch(InputMismatchException e) { playerCount = -1; scan.nextLine(); }
			
			if(playerCount < 0) { System.err.println("Invalid Number of Players!"); System.err.flush(); }
		} while(playerCount < 0);
		
		System.out.println(); // Blank Line for formatting
		
		for(int i = 0; i < playerCount; i ++) {
			System.out.print("What is Player #" + (i + 1) + "'s name? ");
			
			String name = "";
			while((name = scan.nextLine()).isEmpty());
			
			players.add(new Player(i + 1, name));
		}
	}
	
	private void prepNewRound() {
		for(Player player : players) {
			player.getHand().clear();
		}
		
		// Add 2 cards to All players hands, Makes sure no player gets two cards in a row, Standard Poker Deal
		for(int i = players.size() - 1; i >= 0; i --) players.get(i).getHand().hit(dealer.getDeck());
		for(int i = players.size() - 1; i >= 0; i --) players.get(i).getHand().hit(dealer.getDeck()); 
	}
	
	private Player[] playRound() {
		for(int i = players.size() - 1; i >= 0; i --) {
			Player player = players.get(i);
			
			System.out.println("\n" + player);
			while(yesNoPrompt("Take another card?") && player.hit(dealer.getDeck()))
				System.out.println("\t" + player + "\n");
		}
		
		ArrayList<Player> winners = new ArrayList<>();
		int bestScore = 0;
		
		for(Player player : players) {
			if(!player.getHand().isBusted()) {
				int playerScore = player.getHand().sum();
				
				if(playerScore > bestScore) {
					winners.clear();
					winners.add(player);
					bestScore = playerScore;
					
				} else {
					winners.add(player);
				}
			}
		}
		
		return winners.toArray(new Player[0]); // Return winners
	}
	
	private boolean playNextRound() {
		return yesNoPrompt("Do you want to Play another Round?");
	}
	
	private boolean yesNoPrompt(String message) {
		System.out.print(message + " ");
		String in = scan.nextLine().toLowerCase();
		
		if(in.equals("yes") || in.equals("y")) return true;
		if(in.equals("no") || in.equals("n")) return false;
		
		System.err.println("Invalid Input!");
		return yesNoPrompt(message);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.createPlayer();
		
		do {
//			game.prepNewRound();
			Player[] winners = game.playRound();
			
			System.out.println("\nThe Final scores are:");
			
			for(Player player : game.players) {
				System.out.println("\t" + player.getName() + ": " + (player.getHand().isBusted() ? "BUST!" : player.getHand().sum()));
			}
			
			System.out.println("\nAnd the Winners are: ");
			
			if(winners.length > 0) {
				for(Player player : winners) {
					System.out.println("\t" + player.getName());
				}
			
			} else {
				System.out.println("\tNo One!");
			}
			
			System.out.println("\n");
		} while(game.playNextRound());
		
		System.out.println("Thanks for Playing!");
	}
}

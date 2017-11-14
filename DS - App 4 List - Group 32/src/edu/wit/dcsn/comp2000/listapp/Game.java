package edu.wit.dcsn.comp2000.listapp;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *  This class is responsible for taking in Input from the users,
 *  and controlling the flow of the game.
 * 
 *  @author Joshua Cilfone
 */
public class Game {
	private List<Player> players;
	private Dealer dealer;
	
	private Scanner scan;
	
	/**
	 *  Initializes fields and Creates Dealer
	 */
	public Game() {
		players = new ArrayList<>();
		scan = new Scanner(System.in);
		
		players.add(dealer = new Dealer());
	}
	
	/**
	 *  Prompts the Users for the number / names of all players. 
	 *  From this information, the players list is populated.
	 */
	private void createPlayer() {
		int playerCount = -1;
		
		do {
			System.out.print("How many people will be playing? ");
			try { playerCount = scan.nextInt(); if(playerCount > 5) throw new InputMismatchException(); }
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
	
	/**
	 *  Prepares the Deck and Players for the next Round.
	 *  This is done by:
	 *  <ul>
	 *  	<li> Removing all Cards from the players </li> 
	 *  	<li> {@link Deck#reset() Reseting} the Deck </li>
	 *  	<li> Dealing 2 cards to all Players </li>
	 *  	<li> Flips initially cards Face Down </li>
	 *  </ul>
	 */
	private void prepNewRound() {
		for(Player player : players) {
			player.getHand().clear();
		}
		
		dealer.getDeck().reset();
		
		// Add 2 cards to All players hands, Makes sure no player gets two cards in a row, Standard Poker Deal
		for(int i = players.size() - 1; i >= 0; i --) players.get(i).getHand().hit(dealer.getDeck());
		for(int i = players.size() - 1; i >= 0; i --) players.get(i).getHand().hit(dealer.getDeck()); 
		
		for(Player player : players) {
			for(int i = 0; i < player.getHand().getSize(); i ++) {
				player.getHand().get(i).flip();
			}
		}
	}
	
	/**
	 *  Conducts one round of Black Jack. 
	 *  All players are asked for cards, then the winners are calculated.
	 *  
	 *  @return An array of the Winning Players
	 */
	private Player[] playRound() {
		for(int i = players.size() - 1; i >= 0; i --) {
			Player player = players.get(i);
			
			if(player instanceof Dealer) {
				player.hit(dealer.getDeck());
				continue;
			}
			
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
					
				} else if(playerScore == bestScore) {
					winners.add(player);
				}
			}
		}
		
		return winners.toArray(new Player[0]); // Return winners
	}
	
	/**
	 *  Prompts the users for whether they want to play another Round
	 *  @return True if the players want to play another Round; False otherwise
	 */
	private boolean playNextRound() {
		return yesNoPrompt("Do you want to Play another Round?");
	}
	
	/**
	 *  Prompts the user for yes or no input
	 *  @param message The message to prompt the user with
	 *  @return True for a answer of yes; False for an answer of no
	 */
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
			game.prepNewRound();
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

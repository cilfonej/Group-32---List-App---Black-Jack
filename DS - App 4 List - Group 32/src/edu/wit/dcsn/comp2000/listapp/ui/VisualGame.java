package edu.wit.dcsn.comp2000.listapp.ui;

import java.util.ArrayList;
import java.util.List;

import edu.wit.dcsn.comp2000.listapp.Dealer;
import edu.wit.dcsn.comp2000.listapp.Player;

public class VisualGame {
	private List<Player> players;
	private Dealer dealer;
	
	private int currentPlayer;

	public VisualGame(String[] names) {
		players = new ArrayList<>();
		players.add(dealer = new Dealer());
		
		for(String name : names) {
			players.add(new Player(players.size(), name));
		}
		
		prepNewRound();
	}
	
	public void prepNewRound() {
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
		
		currentPlayer = players.size() - 1;
	}
	
	public Player[] findWinners() {
		ArrayList<Player> winners = new ArrayList<>();
		int bestScore = 0;
		
		for(Player player : players) {
			if(!player.getHand().isBusted()) {
				int playerScore = player.getHand().sum();
				
				if(playerScore > bestScore) {
					winners.clear();
					bestScore = playerScore;
					winners.add(player);
					
				} else if(playerScore == bestScore) {
					winners.add(player);
				}
			}
		}
		
		return winners.toArray(new Player[0]); // Return winners
	}
	
	public Player[] simulateRound(boolean hit) {
		Player player = players.get(currentPlayer);
		
		if(!hit || !player.hit(dealer.getDeck())) {
			currentPlayer --;
			
			if(currentPlayer == 0) {
				dealer.hit(dealer.getDeck());
				
				for(Player p : players) {
				for(int i = 0; i < p.getHand().getSize(); i ++) {
					if(!p.getHand().get(i).isFaceUp()) p.getHand().get(i).flip();
				}}
				
				return findWinners();
			}
		}
		
		return null;
	}
	
	public Player getCurrentPlayer() { return players.get(currentPlayer); }
	public List<Player> getPlayers() { return players; }
	public Dealer getDealer() 		 { return dealer; }
}

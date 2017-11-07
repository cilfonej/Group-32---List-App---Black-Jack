package edu.wit.dcsn.comp2000.listapp;

public class Dealer extends Player {
	private Deck deck;
	
	public Dealer() {
		super(0, "Dealer");
	}

	public Deck getDeck() {
		return deck;
	}
	
	public boolean hit(Deck deck) {
		return false; // TODO: Implement Dealer Specific Rules
	}
}

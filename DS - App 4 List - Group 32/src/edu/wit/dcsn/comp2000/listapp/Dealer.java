package edu.wit.dcsn.comp2000.listapp;

/**
 * 
 *  @author Joshua Cilfone
 *
 */
public class Dealer extends Player {
	private Deck deck;
	
	public Dealer() {
		super(0, "Dealer");
		deck = new Deck();
	}

	public Deck getDeck() {
		return deck;
	}
	
	public boolean hit(Deck deck) {
		while(getHand().sum() <= 16)
			super.hit(deck);
		return false;
	}
}

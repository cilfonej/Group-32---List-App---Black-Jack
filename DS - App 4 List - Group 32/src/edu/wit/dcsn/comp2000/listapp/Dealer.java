package edu.wit.dcsn.comp2000.listapp;

import edu.wit.dcsn.comp2000.listapp.Card.Type;
import edu.wit.dcsn.comp2000.listapp.Card.Value;

/**
 *  This class represents a Black Jack Dealer. Generally this is a Computer Controlled Player.
 *  
 *  <p>
 *  The rules that define how this player plays are as followed:
 *  
 *  <ul>
 *  	<li> If <code> hand.sum() <= 16 <\code>, then take another card </li>
 *  	<li> Always takes turn last </li>
 *  </ul>
 *  
 *  <p>
 *  
 *  This class is responsible for housing the deck.
 * 
 *  @author Joshua Cilfone
 */
public class Dealer extends Player {
	private Deck deck;
	
	public Dealer() {
		super(0, "Dealer");
		deck = new Deck();
	}

	/**
	 *  Provides access to the Dealer's Deck
	 *  @return This dealers Deck
	 */
	public Deck getDeck() {
		return deck;
	}
	
	/**
	 *  Overwrites Players implementation of {@link Player#hit(Deck)}.
	 *  <br>
	 *  This version of hit is subject to the rules of the Dealer
	 *  
	 *  @param deck The deck to pull cards from
	 *  
	 *  @return false
	 *  
	 *  @see Dealer
	 */
	public boolean hit(Deck deck) {
		while(getHand().sum() <= 16)
			super.hit(deck);
		return false;
	}
	
//	------------------------------------------------ Tests ----------------------------------------------------------- \\
	
	public static void main(String[] args) {
		// Check Constructor
		Dealer dealer = new Dealer();
		
		// Check that Deck has been Created
		System.out.println("Deck Created: " + (dealer.getDeck() != null ? "PASSED" : "FAILED"));
		
		Deck deck = new Deck();
		deck.add(new Card(Type.Clubs, Value._10));
		deck.add(new Card(Type.Spades, Value._10));
		deck.add(new Card(Type.Hearts, Value._10));
		deck.add(new Card(Type.Diamonds, Value._6));
		deck.add(new Card(Type.Diamonds, Value._10));
		
		// Prep Hand -- Nothing Tested here, should be tested by Hand / Player
		dealer.getHand().hit(deck);
		dealer.getHand().hit(deck);
		
		// Test to see if Dealer will take card at a sum of 16
		boolean returnVal = dealer.hit(deck);

		System.out.println();
		System.out.println("Check Hit Return Value:       " + (returnVal == false ? "PASSED" : "FAILED"));
		System.out.println("Took Correct Number of Cards: " + (dealer.getHand().sum() == 26 ? "PASSED" : "FAILED"));
		
		// Clear Dealer
		dealer = new Dealer();
		
		// Test to see if Dealer will not over draw
		returnVal = dealer.hit(deck);

		System.out.println();
		System.out.println("Check Hit Return Value:       " + (returnVal == false ? "PASSED" : "FAILED"));
		System.out.println("Took Correct Number of Cards: " + (dealer.getHand().sum() == 20 ? "PASSED" : "FAILED"));
	}
}

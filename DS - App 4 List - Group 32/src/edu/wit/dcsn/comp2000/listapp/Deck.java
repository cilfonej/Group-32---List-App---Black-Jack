package edu.wit.dcsn.comp2000.listapp;

import edu.wit.dcsn.comp2000.listapp.Card.Value;
import edu.wit.dcsn.comp2000.listapp.Card.Type;

/**
 * This class allows the deck to be shuffled and for players to draw cards.
 * 
 * @author Noah D'Alelio
 */
public class Deck extends Pile {
	
	public Deck() {
        
	}
	
	/**
	 * Randomly shuffles the deck.
	 */
	public void shuffle() {
        
		for (int i = 0; i < getSize(); i++) {
			
			add(remove((int) (Math.random() * getSize())));
			
		}
		
	}
	
	/**
	 * Draws card from the deck and returns it.
	 * @return Returns the card that is drawn.
	 */
	public Card draw() {
		return remove(getSize()-1);
	}
	
	/**
	 * CLears the deck, re-initiates it and shuffles the deck.
	 */
	public void reset() {
		
		clear();
		
		for (Type t : Type.values()) {
			for (Value v : Value.values()) {
				add(new Card(t, v));
			}
		}
		
		shuffle();
		
	}
}

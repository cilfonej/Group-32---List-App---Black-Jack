package edu.wit.dcsn.comp2000.listapp;

import edu.wit.dcsn.comp2000.listapp.Card.Value;
import edu.wit.dcsn.comp2000.listapp.Card.Type;

/**
 * Noah D'Alelio
 */

public class Deck extends Pile {
	
	public Deck() {
        
	}
	
	public void shuffle() {
        
		for (int i = 0; i < getSize(); i++) {
			
			add(remove((int) (Math.random() * getSize())));
			
		}
		
	}
	
	public Card draw() {
		return remove(getSize()-1);
	}
	
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

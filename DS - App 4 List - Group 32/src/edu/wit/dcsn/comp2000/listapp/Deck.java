package edu.wit.dcsn.comp2000.listapp;

import edu.wit.dcsn.comp2000.listapp.Card.Type;
import edu.wit.dcsn.comp2000.listapp.Card.Value;

/**
 * Noah D'Alelio
 */

public class Deck extends Pile {
	
	public Deck() { }
	
	public void shuffle() {
		for(int i = 0; i < getSize(); i ++) {
			add(remove((int) (Math.random() * getSize())));
		}
	}
	
	public Card draw() {
		return remove(getSize() - 1);
	}
	
	public void reset() {
		clear();
		
		for(Type type : Type.values()) {
		for(Value value : Value.values()) {
			add(new Card(type, value));
		}}
		
		shuffle();
	}
}

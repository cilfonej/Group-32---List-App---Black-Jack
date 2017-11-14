package edu.wit.dcsn.comp2000.listapp;

import java.util.ArrayList;

/**
 * Noah D'Alelio
 */

public abstract class Pile {
	private ArrayList<Card> cards;
	
	public Pile() {
		cards = new ArrayList<Card>();
	}
	
	public int getSize() {
		return cards.size();
	}
	
	public void add(Card card) {
		cards.add(card);
	}
	
	public Card get(int index) {
		return cards.get(index);
	}
	
	public Card remove(int index) {
		return cards.remove(index);
	}
	
	public void clear() {
		cards.clear();
	}
}

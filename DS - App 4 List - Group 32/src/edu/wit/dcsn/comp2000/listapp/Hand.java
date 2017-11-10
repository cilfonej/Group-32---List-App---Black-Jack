package edu.wit.dcsn.comp2000.listapp;

import edu.wit.dcsn.comp2000.listapp.Card.Value;

/**
 * Noah D'Alelio
 */
public class Hand extends Pile {
	public Hand() { }
	
	public void hit(Deck deck) {
		add(deck.draw());
	}
	
	public int sum() {
		int sum = 0;
		int aceCount = 0;
		
		for(int i = 0; i < getSize(); i ++) {
			Card card = get(i);
			
			if(card.getValue() == Value.Ace) {
				aceCount ++;
			}
			
			sum += card.getValue().getValue();
			
			if(sum > 21 && aceCount > 0) {
				sum -= 10 * aceCount --;
			}
		}
		
		return sum;
	}
	
	public boolean isBusted() {
		return sum() > 21;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		
		for(int i = 0; i < getSize() - 1; i ++) {
			builder.append(get(i) + ", ");
		}
		
		builder.append(get(getSize() - 1));
		builder.append(']');
		
		return builder.toString();
	}
}

package edu.wit.dcsn.comp2000.listapp;

/**
 * Noah D'Alelio
 */


public class Hand extends Pile {

	public Hand() {
		
	}
	
	public void hit(Deck deck) {
		add(deck.draw());
	}
	
	public int sum() {
		int sum = 0, numAces = 0;
		for (int i = 0; i < getSize(); i++ ) {
			if (get(i).getValue().equals(Card.Value.Ace)) {
				numAces++;
			}
			sum += get(i).getValue().getValue();
		}
		
		while (sum > 21 && numAces > 0) {
			sum -= 10;
			--numAces;
		}
		
		return sum;
	}
	
	public boolean isBusted() {
		return sum() > 21;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Card c;
		for (int i = 0; i < getSize(); i++) {
			c = get(i);
			sb.append(" [" + c.getValue() + " of " + c.getType() + "] ");
		}
		
		return sb.toString();
	}
}

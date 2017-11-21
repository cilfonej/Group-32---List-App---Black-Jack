package edu.wit.dcsn.comp2000.listapp;

/**
 * This class controls the players individual cards.
 * 
 * @author Noah D'Alelio
 */
public class Hand extends Pile {

	public Hand() {

	}

	/**
	 * Draws a card from the deck and adds it to the players hand.
	 * 
	 * @param deck
	 *            Deck that you want to hit from.
	 */
	public void hit(Deck deck) {
		add(deck.draw());
	}

	/**
	 * Adds up the total value of your cards, also handles aces (1 or 11).
	 * 
	 * @return Sum of the cards in your hand.
	 */
	public int sum() {
		int sum = 0, numAces = 0;
		for (int i = 0; i < getSize(); i++) {
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

	/**
	 * @return True if the sum is over 21, false if less than or equal to.
	 */
	public boolean isBusted() {
		return sum() > 21;
	}

	public static void main(String args[]) {
		Deck d = new Deck();

		d.reset();

		Hand h = new Hand();

		h.hit(d);
		h.hit(d);

		System.out.println(h.toString());
		System.out.println("Sum: " + h.sum());
		System.out.println("Is busted?: " + h.isBusted());

		h.hit(d);
		h.hit(d);

		System.out.println(h.toString());
		System.out.println("Sum: " + h.sum());
		System.out.println("Is busted?: " + h.isBusted());

	}
}
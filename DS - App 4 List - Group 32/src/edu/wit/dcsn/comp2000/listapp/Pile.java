package edu.wit.dcsn.comp2000.listapp;

import java.util.ArrayList;

/**
 * This class defines the main functionality for a pile of cards. Extended by
 * Deck and Hand.
 * 
 * @author Noah D'Alelio
 */
public abstract class Pile {
	private ArrayList<Card> cards;

	/**
	 * Initialize the Pile of cards;
	 */
	public Pile() {
		cards = new ArrayList<Card>();
	}

	/**
	 * @return Number of cards in Pile.
	 */
	public int getSize() {
		return cards.size();
	}

	/**
	 * Adds a card to the Pile.
	 * 
	 * @param card
	 *            The card you want to add to the Pile.
	 */
	public void add(Card card) {
		cards.add(card);
	}

	/**
	 * @param index
	 *            Index of the card you want returned.
	 * @return Card at specified index.
	 */
	public Card get(int index) {
		return cards.get(index);
	}

	/**
	 * @param index
	 *            Index of card you want removed.
	 * @return Returns the card that was removed.
	 */
	public Card remove(int index) {
		return cards.remove(index);
	}

	/**
	 * Clears the card list for the Pile.
	 */
	public void clear() {
		cards.clear();
	}

	@Override
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
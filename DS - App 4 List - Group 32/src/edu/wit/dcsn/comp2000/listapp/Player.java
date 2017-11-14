package edu.wit.dcsn.comp2000.listapp;
/** 
 * 
 * @author PapaPajama
 *
 */
public class Player extends Deck{
	private Hand hand;
	private String name;
	
	private final int ID;
	
	public Player(int id, String name) {
		this.ID = id;
		this.name = name;
	}
	
	public boolean hit(Deck deck) {
		hand.hit(deck);
		if (hand.sum()>21)
			return false;
		else
			return true;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public String toString() {
		return "The Player " + name + "has a hand of " + hand + "totalling " + hand.sum();
	}
}

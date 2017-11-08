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
	}
	
	public boolean hit(Deck deck) {
		draw();
		return false;
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
		return null;
	}
}

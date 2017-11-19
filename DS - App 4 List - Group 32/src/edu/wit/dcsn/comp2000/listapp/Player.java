package edu.wit.dcsn.comp2000.listapp;
/** 
 * This class represents a player in the game. The player can draw cards from the deck as well as see their cards
 * and count the total. If the total in hand is over 21, the player is unable to draw from the deck again.
 * @author Alec Carbonell
 *
 */
public class Player extends Deck{
	private Hand hand;
	private String name;
	
	private final int ID;
	
	/** constructs the player object
	 * 
	 * @param id
	 * @param name
	 */
	public Player(int id, String name) {
		this.ID = id;
		this.name = name;
		hand = new Hand();
	}
	
	/** allows the player to draw from the deck to their hand. If the player's hand count totals more than 21.
	 * the player is unable to draw again.
	 * @param deck
	 * @return
	 */
	public boolean hit(Deck deck) {
		hand.hit(deck);
		if (hand.sum()>21)
			return false;
		else
			return true;
	}
	
	/** Gets the name of the player
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/** gets the ID of the player.
	 * 
	 * @return
	 */
	public int getID() {
		return ID;
	}
	
	/** gets the cards in a player's hand.
	 * 
	 * @return
	 */
	public Hand getHand() {
		return hand;
	}
	
	/** returns the player's, hand, and sum in hand as a string.
	 * 
	 */
	public String toString() {
		return "The Player " + name + " has a hand of " + hand + " totalling " + hand.sum();
	}
//	------------------------------------------------ Tests ----------------------------------------------------------- \\
	
	public static void main(String[]args) {
		Player player1 = new Player (1, "Alec");
		System.out.println(player1.getName());
		System.out.println(player1.getID());
		System.out.println(player1.getHand());
		System.out.println(player1.toString());
	}
}

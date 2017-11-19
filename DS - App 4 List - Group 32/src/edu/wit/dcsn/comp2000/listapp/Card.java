package edu.wit.dcsn.comp2000.listapp;
/**
 * This class represents the cards in a deck. It gives a card an enum Type, and an enum Value. 
 * There are A card can have one of four types as well as one value.
 * @author Alec Carbonell
 *
 */
public class Card {
	public static enum Type {
		Hearts, Diamonds, Spades, Clubs;
	}
	
	public static enum Value {
		Ace(11), _2(2), _3(3), _4(4), _5(5), _6(6), _7(7), _8(8), _9(9), _10(10),
		Jack(10), Queen(10), King(10);
		
		private int value;
		private Value(int value) {
			this.value = value;
		}
		
		public int getValue() { return value; }
	}
	
	private Type type;
	private Value value;
	private boolean isFaceUp;
	
	/** constructs the object Card
	 * @param type
	 * @param value
	 */
	public Card(Type type, Value value) {
		this.type = type;
		this.value = value;
	}
	
	/** gets the type of the card object
	 * 
	 * @return 
	 */
	public Type getType() {
		return type;
	}
	/** gets the value of the card object
	 * 
	 * @return 
	 */
	public Value getValue() {
		return value;
	}
	
	/** changes the boolean isFaceUp to the opposite of what it currently is.
	 * 
	 */
	public void flip() {
		isFaceUp = !isFaceUp;
		// TODO: Implement 
	}
	
	/** determines whether or not the card in question is facing up
	 * 
	 * @return
	 */
	public boolean isFaceUp() {
		return isFaceUp;
	}
	
	/** A toString method that will return the value and type of the card as a string.
	 * 
	 */
	public String toString() { 
		return "This is the " + value + " of " + type;
		//displays the the Type and value of card
	}
//------------------------------------------------ Tests ----------------------------------------------------------- \\

	public static void main(String[]args) {
		Card card = new Card(Type.Hearts, Value._2 );
		System.out.println(card.getType());
		System.out.println(card.getValue());
		System.out.println(card.isFaceUp());
		card.flip();
		System.out.println(card.isFaceUp());
		System.out.println(card.toString());
	}
}
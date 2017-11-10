package edu.wit.dcsn.comp2000.listapp;
/**
 * 
 * @author PapaPajama
 *
 */
public class Card {
	public static enum Type {
		Heart, Diamond, Club, Spade;
	}
	
	public static enum Value {
		Ace(11), _2(2), _3(3), _4(4), _5(5), _6(6), _7(7), _8(8), _9(9), _10(10),
		Jack(10), Queen(10), King(10);
		
		private int value;
		private Value(int value) {
			this.value = value;
		}
		
		public int getValue() { return value; }
		
		public String toString() {
			String s = super.toString();
			return s.startsWith("_") ? s.substring(1) : s;
		}
	}
	
	private Type type;
	private Value value;
	private boolean isFaceUp;
	
	public Card(Type type, Value value) {
		this.type = type;
		this.value = value;
		this.isFaceUp = true;
	}
	
	public Type getType() {
		return type;
	}
	
	public Value getValue() {
		return value;
	}
	
	public void flip() {
		isFaceUp = !isFaceUp; 
	}
	
	public boolean isFaceUp() {
		return isFaceUp;
	}
	
	public String toString() { 
		return value + " of " + type;
	}
}

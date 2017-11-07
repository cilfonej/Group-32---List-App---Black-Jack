package edu.wit.dcsn.comp2000.listapp;

public class Card {
	public static enum Type {
		Heart, Diamond, Spade, Club;
	}
	
	public static enum Value {
		_2(2), _3(3), _4(4), _5(5), _6(6), _7(7), _8(8), _9(9), _10(10),
		Jack(10), Queen(10), King(10), Ace_Low(1), Ace_High(11);
		
		private int value;
		private Value(int value) {
			this.value = value;
		}
		
		public int getValue() { return value; }
	}
	
	private Type type;
	private Value value;
	private boolean isFaceUp;
	
	public Card(Type type, Value value) {
		this.type = type;
		this.value = value;
	}
	
	public Type getType() {
		return type;
	}
	
	public Value getValue() {
		return value;
	}
	
	public void flip() {
		// TODO: Implement 
	}
	
	public boolean isFaceUp() {
		return isFaceUp;
	}
	
	public String toString() { 
		return null;
	}
}

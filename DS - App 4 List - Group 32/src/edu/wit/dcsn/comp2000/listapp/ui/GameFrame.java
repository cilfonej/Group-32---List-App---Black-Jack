package edu.wit.dcsn.comp2000.listapp.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = -8519016569888612830L;

	public static void main(String[] args) { new GameFrame(); }
	
	public GameFrame() {
		setTitle("Back Jack - Group 32");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 400);
		
		setLayout(new BorderLayout());
		add(new GamePanel());
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

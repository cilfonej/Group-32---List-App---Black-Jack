package edu.wit.dcsn.comp2000.listapp.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import edu.wit.dcsn.comp2000.listapp.Card;
import edu.wit.dcsn.comp2000.listapp.Card.Value;

public class GameFrame extends JFrame implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = -8519016569888612830L;
	
	private JButton addButton;
	private JButton removeButton;
	private JButton startButton;
	
	private JTable list;
	private DefaultTableModel playerListModel;
	private CardLayout layout;

	public static void main(String[] args) { new GameFrame(); }
	
	public GameFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setTitle("Back Jack - Group 32");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 300);
		getContentPane().setLayout(layout = new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(3, 3, 3, 3));
		getContentPane().add(panel, "start");
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setViewportBorder(new EmptyBorder(3, 5, 3, 3));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		list = new JTable(playerListModel = new DefaultTableModel(new Object[][] {}, new String[] { "Players" }));
		list.setShowHorizontalLines(false);
		list.setRowHeight(24);
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.setFillsViewportHeight(true);
		list.setShowVerticalLines(false);
		list.setShowGrid(false);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		JPanel laftPanel = new JPanel();
		laftPanel.setBorder(new EmptyBorder(0, 0, 0, 3));
		panel.add(laftPanel, BorderLayout.WEST);
		laftPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		laftPanel.add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.setLayout(new BorderLayout(0, 1));
		
		addButton = new JButton("Add Player");
		addButton.setFocusable(false);
		buttonPanel.add(addButton, BorderLayout.NORTH);
		
		removeButton = new JButton("Remove Player");
		removeButton.setFocusable(false);
		buttonPanel.add(removeButton, BorderLayout.SOUTH);
		
		startButton = new JButton("Start Game");
		startButton.setFocusable(false);
		laftPanel.add(startButton, BorderLayout.SOUTH);
		
		JPanel label = new JPanel() {
			private static final long serialVersionUID = 1L;
			private Card card = new Card(edu.wit.dcsn.comp2000.listapp.Card.Type.Spade, Value.Jack);
			protected void paintComponent(Graphics g) {
				g.drawImage(GamePanel.getCard(card), 5, (getHeight() - (int)((getWidth() - 10) * 1.5)) / 2, getWidth() - 10, (int)((getWidth() - 10) * 1.5), null);
			}
		};
		laftPanel.add(label, BorderLayout.CENTER);
		
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		startButton.addActionListener(this);
		
		list.getSelectionModel().addListSelectionListener(this);
		removeButton.setEnabled(false);
		
		actionPerformed(new ActionEvent(addButton, 0, ""));
		layout.show(getContentPane(), "start");
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void valueChanged(ListSelectionEvent e) { 
		removeButton.setEnabled(list.getSelectedRow() != -1 && list.getRowCount() > 1); 
	}

	private int playerIndex;
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton) {
			playerListModel.addRow(new Object[] { "Player #" + (++ playerIndex) });
			removeButton.setEnabled(list.getSelectedRow() != -1 && list.getRowCount() > 1);
			return;
		}
		
		if(e.getSource() == removeButton) {
			playerListModel.removeRow(list.getSelectedRow());
			return;
		}
		
		if(e.getSource() == startButton) {
			String[] names = new String[list.getRowCount()];
			for(int i = 0; i < names.length; i ++) {
				names[i] = (String) list.getValueAt(i, 0);
			}
			
			getContentPane().add(new GamePanel(new VisualGame(names)), "game");
			layout.show(getContentPane(), "game");
			return;
		}
	}
}

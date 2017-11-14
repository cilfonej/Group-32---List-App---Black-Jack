package edu.wit.dcsn.comp2000.listapp.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.PanelUI;

import edu.wit.dcsn.comp2000.listapp.Card;
import edu.wit.dcsn.comp2000.listapp.Deck;
import edu.wit.dcsn.comp2000.listapp.Hand;
import edu.wit.dcsn.comp2000.listapp.Player;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1916308115840033683L;
	public static Image getCard(Card card) { return card.isFaceUp() ? UI.CARDS[card.getType().ordinal() * 13 + card.getValue().ordinal()] : UI.CARD_BACK; }
	
	private VisualGame game;
	
	public GamePanel(VisualGame game) {
		this.game = game;
		
		ArrayList<Player> noDealer = new ArrayList<>(game.getPlayers());
		noDealer.remove(0);
		setUI(new UI(noDealer));
	}
	
	private static class UI extends PanelUI implements MouseInputListener {
		private static final Image BACKDROP = new ImageIcon(GamePanel.class.getResource("Backdrop.png")).getImage();
		private static final Image FLOOR = new ImageIcon(GamePanel.class.getResource("tableTop.png")).getImage();

		private static final Image PASS_IMAGE = new ImageIcon(GamePanel.class.getResource("passImage.png")).getImage();
		private static final Image HIT_IMAGE = new ImageIcon(GamePanel.class.getResource("hitImage.png")).getImage();
		private static final Image NEXT_IMAGE = new ImageIcon(GamePanel.class.getResource("nextRound.png")).getImage();
		
		private static final Image CARD_BACK = new ImageIcon(GamePanel.class.getResource("cardBack.png")).getImage();
		
		private static final Image[] CARDS = new Image[52]; static {
			try {
				BufferedImage CARD_SHEET = ImageIO.read(GamePanel.class.getResource("CardsNew.png"));
				for(int i = 0; i < 4; i ++)  {
				for(int j = 0; j < 13; j ++) {
					CARDS[j + i * 13] = CARD_SHEET.getSubimage(j * (72 + 0), i * (100 + 0), 72, 100);
				}}
			} catch(IOException e) { }
		}

		private static final float CARD_DISPLAY_HEIGHT = (float) (125 * GameFrame.LENGTH_SCALE);
		private static final float CARD_DISPLAY_WIDTH = CARD_DISPLAY_HEIGHT / 1.5f;
		
		private static final Rectangle2D CARD_BOUNDS = new Rectangle2D.Float(0, 0, CARD_DISPLAY_WIDTH, CARD_DISPLAY_HEIGHT);
		
		private final Shape TABLE; 
		private final float SIZE_ANGLE;
		
		private boolean isCtrlDown;
		private Point mouseLoc;
		private Card drawCard;
		private Player drawCardOwner;
		
		private Point clickPoint;

		private Collection<Player> players;
		private GamePanel component;
		
		private Player[] winners;
		
		public UI(Collection<Player> players) {
			Deck deck = new Deck();
			deck.reset();
			
			this.players = players;
			
//			this.players = new ArrayList<>();
//			players.add(new Player(1, "Bob"));
//			players.add(new Player(2, "Joe"));
//			players.add(new Player(3, "Sam"));
//			players.add(new Player(4, "Wam"));
//			players.add(new Player(5, "Flam"));
//			players.add(new Player(6, "Cam"));
//			players.add(new Player(7, "Shawaser"));
//			
//			for(Player player : players) {
//				player.getHand().hit(deck);
//				player.getHand().hit(deck);
//			}
//			
//			for(Player player : players)
//				player.getHand().get(0).flip();
			
			GeneralPath path = new GeneralPath();
			
			int sides = players.size();
			SIZE_ANGLE = (float) Math.toRadians(180 / sides);
			float LEN = 100;
			
			path.moveTo(-100, 0);
			float angle = SIZE_ANGLE;
			
			for(int i = 0; i < sides; i ++) {
				path.lineTo(-Math.cos(angle) * LEN, Math.sin(angle) * LEN);
				angle += SIZE_ANGLE;
			}
			
//			path.closePath();
			TABLE = path;
		}
		
		public void installUI(JComponent c) {
			c.addMouseListener(this);
			c.addMouseMotionListener(this);
			c.setBackground(new Color(168, 119, 90).darker());
			
			component = (GamePanel) c;
		}
		
		public void paint(Graphics g, JComponent c) {
			Graphics2D g2d = (Graphics2D) g;
			
			int width = c.getWidth();
			int height = c.getHeight();
			
			g2d.setColor(c.getBackground());
			g2d.fillRect(0, 0, width, height);

			int floorWidth = (int) (FLOOR.getWidth(null) * GameFrame.LENGTH_SCALE);
			int floorHeight = (int) (FLOOR.getHeight(null) * GameFrame.HEIGHT_SCALE);
			
			g2d.translate(0, height / 2);
			for(int i = 0; i < Math.ceil((float) width / floorWidth); i ++)   {
			for(int j = 0; j < Math.ceil((float) height / 2 / floorHeight); j ++) {
				g2d.drawImage(FLOOR, floorWidth * i, floorHeight * j, floorWidth, floorHeight, null);
			}}
			g2d.translate(0, -height / 2);
			
			AffineTransform transform = new AffineTransform();
			transform.translate(width / 2, height / 2);
			transform.scale(width / 200f, height / (3 * 100f));
			Shape maskBase = transform.createTransformedShape(TABLE);
			Rectangle2D maskTop = new Rectangle2D.Float(0, 0, width, height / 2);
			Area mask = new Area(maskTop); mask.add(new Area(maskBase));
			g2d.setClip(mask);
			
			int backWidth = (int) (BACKDROP.getWidth(null) / 2f * GameFrame.LENGTH_SCALE);
			int backHeight = (int) (BACKDROP.getHeight(null) / 2f * GameFrame.HEIGHT_SCALE);
			
			for(int i = 0; i < Math.ceil((float) width / backWidth); i ++)   {
			for(int j = 0; j < Math.ceil((float) height / backHeight); j ++) {
				g2d.drawImage(BACKDROP, backWidth * i, backHeight * j, backWidth, backHeight, null);
			}}
			
			g2d.setColor(Color.DARK_GRAY.darker());
			g2d.setStroke(new BasicStroke(32, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
			g2d.draw(maskBase);
			
			g2d.setClip(null);
			drawCard = null;
			drawCardOwner = null;
			
			Card drawCardStart = drawCard;
			for(Player player : players) {
				drawPlayer(g2d, player, c);
				
				if(drawCardStart != drawCard) {
					drawCardOwner = player;
					drawCardStart = drawCard;
				}
			}
			
			drawDealer(g2d, c);
			drawButtons(g2d, c);
			
			if(drawCard != null) {
				boolean faceUp = drawCard.isFaceUp();
				if(isCtrlDown && !faceUp && drawCardOwner == component.game.getCurrentPlayer()) drawCard.flip();
				
				g2d.drawImage(getCard(drawCard), mouseLoc.x, (int) (mouseLoc.y - CARD_DISPLAY_HEIGHT), 
						(int) (CARD_DISPLAY_WIDTH * 2), (int) (CARD_DISPLAY_HEIGHT * 2), null);
				
				if(drawCard.isFaceUp() != faceUp) drawCard.flip();
			}
			
			c.repaint();
		}
		
		private void drawButtons(Graphics2D g2d, JComponent c) {
			int width = c.getWidth();
			int height = c.getHeight();
			
			AffineTransform baseTransform = g2d.getTransform();
			
			AffineTransform buttonTransform = new AffineTransform();
			buttonTransform.translate(width * 5 / 9f, height / 12f);
			g2d.setTransform(buttonTransform);
			
			if(winners != null) {
				drawWinners(g2d);
			} else {
				drawHit(g2d);
				g2d.translate(CARD_DISPLAY_WIDTH * 1.5, 0);
				drawPass(g2d);
			}
			
			g2d.setTransform(baseTransform);
		}
		
		private void drawWinners(Graphics2D g2d) {
			if(winners == null) return;
			
			g2d.drawImage(NEXT_IMAGE, 0, (int) -CARD_BOUNDS.getWidth() / 4, (int) CARD_BOUNDS.getHeight() * 5/4, (int) CARD_BOUNDS.getWidth() * 5/4, null);
			Shape largerBounds = new Rectangle2D.Float(0, (int) -CARD_BOUNDS.getWidth() / 4, (int) CARD_BOUNDS.getHeight() * 5/4, (int) CARD_BOUNDS.getWidth() * 5/4);
			largerBounds = g2d.getTransform().createTransformedShape(largerBounds);
			
			if(clickPoint != null && largerBounds.contains(clickPoint)) {
				winners = null;
				component.game.prepNewRound();
				clickPoint = null;

				if(winners == null) {
					component.repaint();
					return;
				}
			}
			
			
			g2d.setFont(new Font("", Font.BOLD, (int) (CARD_DISPLAY_HEIGHT * 3 / 8)));
			g2d.setColor(new Color(225, 200, 72));
			g2d.translate(0, largerBounds.getBounds().getHeight());
			
			for(Player player : winners) {
				g2d.translate(0, (CARD_DISPLAY_HEIGHT * 3 / 8));
				g2d.drawString(player.getName(), 0, 0);
			}

		}
		
		private void drawPass(Graphics2D g2d) {
			if(winners != null) return;
			
			if(clickPoint != null && g2d.getTransform().createTransformedShape(CARD_BOUNDS).contains(clickPoint)) {
				winners = component.game.simulateRound(false);
				clickPoint = null;

				if(winners != null) {
					component.repaint();
					return;
				}
			}
			
			g2d.drawImage(PASS_IMAGE, 0, 0, (int) CARD_BOUNDS.getWidth(), (int) CARD_BOUNDS.getHeight(), null);
			
		}
		
		private void drawHit(Graphics2D g2d) {
			if(winners != null) return;
			
			if(clickPoint != null && g2d.getTransform().createTransformedShape(CARD_BOUNDS).contains(clickPoint)) {
				winners = component.game.simulateRound(true);
				clickPoint = null;

				if(winners != null) {
					component.repaint();
					return;
				}
			}
			
			g2d.drawImage(HIT_IMAGE, 0, 0, (int) CARD_BOUNDS.getWidth(), (int) CARD_BOUNDS.getHeight(), null);
		}
		
		private void drawDealer(Graphics2D g2d, JComponent c) {
			int width = c.getWidth();
			int height = c.getHeight();
			
			AffineTransform baseTransform = g2d.getTransform();
			
			Hand hand = component.game.getDealer().getHand();
			float cardsWidth = (hand.getSize() - 1) * CARD_DISPLAY_WIDTH / 2 + CARD_DISPLAY_WIDTH;
			
			AffineTransform handTransform = new AffineTransform();
			handTransform.translate(width * 4 / 9f - cardsWidth, height / 12f);
			g2d.setTransform(handTransform);
			
			drawHand(g2d, hand, 0);
			
			g2d.setTransform(baseTransform);
		}
		
		private void drawPlayer(Graphics2D g2d, Player player, JComponent c) {
			AffineTransform baseTransform = g2d.getTransform();
			
			float p0x = (float) (c.getWidth() / 2 * (Math.cos((player.getID() - 1) * SIZE_ANGLE)) + c.getWidth() / 2);
			float p1x = (float) (c.getWidth() / 2 * (Math.cos((player.getID() - 0) * SIZE_ANGLE)) + c.getWidth() / 2);
			
			float p0y = (float) (c.getHeight() / 3 * (Math.sin((player.getID() - 1) * SIZE_ANGLE)) + c.getHeight() / 2);
			float p1y = (float) (c.getHeight() / 3 * (Math.sin((player.getID() - 0) * SIZE_ANGLE)) + c.getHeight() / 2);
			
			float len = (float) Math.sqrt((p1x - p0x)*(p1x - p0x) + (p1y - p0y)*(p1y - p0y));
			
			double a = Math.atan2(p1y - p0y, p1x - p0x) + Math.PI / 2;
			
			drawName(g2d, player, a, len, p0x, p0y);
			drawPlayerHand(g2d, player.getID(), player.getHand(), a, len, p0x, p0y, p1x, p1y);

			g2d.setTransform(baseTransform);
		}
		
		private void drawName(Graphics2D g2d, Player player, double a, float len, float p0x, float p0y) {
			g2d.setFont(new Font("", Font.BOLD, (int) (CARD_DISPLAY_HEIGHT / 4)));
			Rectangle2D nameBounds = g2d.getFont().getStringBounds(player.getName(), g2d.getFontRenderContext());
			
			float slideX = (float) (len - nameBounds.getWidth()) / 2;

			float nx = p0x - (float) Math.cos(a) * CARD_DISPLAY_HEIGHT / 2;
			float ny = p0y - (float) Math.sin(a) * CARD_DISPLAY_HEIGHT / 2;
			
			AffineTransform nameTransform = new AffineTransform();
			nameTransform.translate(nx, ny);
			nameTransform.rotate(a + Math.PI / 2);
			g2d.setTransform(nameTransform);
			
			g2d.translate(slideX - len, 0);
			
			g2d.setStroke(new BasicStroke(1));
			g2d.translate(-nameBounds.getWidth() / 7, nameBounds.getHeight() / 7);
			g2d.scale(1.25, 1.25);
			
			g2d.setColor(component.game.getCurrentPlayer() == player ? new Color(126, 192, 126) : Color.LIGHT_GRAY);
			g2d.fill(nameBounds);
			g2d.setColor(Color.DARK_GRAY);
			g2d.draw(nameBounds);
			
			g2d.scale(1 / 1.25, 1 / 1.25);
			g2d.translate(nameBounds.getWidth() / 7, -nameBounds.getHeight() / 7);
			
			g2d.setColor(Color.DARK_GRAY);
			g2d.drawString(player.getName(), 0, 0);
		}

		private void drawPlayerHand(Graphics2D g2d, int pIndex, Hand hand, double a, float len, float p0x, float p0y, float p1x, float p1y) {
			float dx = p1x - p0x, dy = p1y - p0y;

			float cardsWidth = (hand.getSize() - 1) * CARD_DISPLAY_WIDTH / 2 + CARD_DISPLAY_WIDTH;
			float shiftX = (len - cardsWidth) / 2;
			
			if(dx*dx < dy*dy) {
				if(dy > 0) {
					shiftX = cardsWidth / 2;
				} else {
					shiftX = 0;
				}
			}

			float px = (float) (p0x + Math.cos(a) * CARD_DISPLAY_HEIGHT * 1.5f);
			float py = (float) (p0y + Math.sin(a) * CARD_DISPLAY_HEIGHT * 1.5f);
			
			AffineTransform handTransform = new AffineTransform();
			handTransform.translate(px, py); // Translate to First card Location (Upper-Left)
			handTransform.rotate(a + Math.PI / 2);
			g2d.setTransform(handTransform);
			
			g2d.translate(shiftX - len, 0);
			
			drawHand(g2d, hand, pIndex);
		}
		
		private void drawHand(Graphics2D g2d, Hand hand, int offset) {
			if(winners != null) {
				int sum = hand.sum();
				String num = (sum > 21 ? "BUST!" : sum) + "";
				float cardsWidth = (hand.getSize() - 1) * CARD_DISPLAY_WIDTH / 2 + CARD_DISPLAY_WIDTH;
				g2d.setFont(new Font("", Font.BOLD, (int) (CARD_DISPLAY_HEIGHT / 5)));
				Rectangle2D nameBounds = g2d.getFont().getStringBounds(num, g2d.getFontRenderContext());
				
				cardsWidth -= nameBounds.getWidth(); cardsWidth /= 2;
				
				g2d.setColor(Color.RED.brighter());
				g2d.translate(cardsWidth, -nameBounds.getHeight());
				g2d.drawString(num, 0, 0);
				g2d.translate(-cardsWidth, nameBounds.getHeight());
			}
			
			for(int i = 0; i < hand.getSize(); i ++) {
				Card card = hand.get(i);
				drawCard(g2d, card);
				g2d.translate(CARD_DISPLAY_WIDTH / 2, Math.sin(i + 2 * Math.PI / hand.getSize() + offset) * 10);
			}
		}
		
		private void drawCard(Graphics2D g2d, Card card) {
			Image image = card.isFaceUp() ? CARDS[card.getType().ordinal() * 13 + card.getValue().ordinal()] : CARD_BACK;
			g2d.drawImage(image, 0, 0, (int) CARD_DISPLAY_WIDTH, (int) CARD_DISPLAY_HEIGHT, null);

			if(mouseLoc != null && g2d.getTransform().createTransformedShape(CARD_BOUNDS).contains(mouseLoc)) 
				drawCard = card;
		}

		public void mouseMoved(MouseEvent e)    { mouseLoc = e.getPoint(); 	component.repaint(); }
		public void mouseDragged(MouseEvent e)  { mouseLoc = e.getPoint(); 	component.repaint(); }
		
		public void mousePressed(MouseEvent e)  { isCtrlDown = true;  		component.repaint(); }
		public void mouseReleased(MouseEvent e) { isCtrlDown = false; 		component.repaint(); }
		
		public void mouseClicked(MouseEvent e) { clickPoint = e.getPoint(); }
		
		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
	}
}

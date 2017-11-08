package edu.wit.dcsn.comp2000.listapp.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.PanelUI;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1916308115840033683L;

	public GamePanel() {
		setUI(new UI());
	}
	
	private static class UI extends PanelUI {
		private static final Image BACKDROP = new ImageIcon(GamePanel.class.getResource("Backdrop.png")).getImage();
		
		private static final Shape TABLE; static {
			GeneralPath path = new GeneralPath();
			
			int sides = 6;
			float a = (float) Math.toRadians(360 / sides / 2);
			float LEN = 100;
			
			path.moveTo(0, 0);
			float angle = 0;
			for(int i = 0; i < sides; i ++) {
				path.lineTo(Math.cos(angle) * LEN * i, Math.sin(angle) * LEN / 2 * i);
				angle += a;
			}
			
//			path.lineTo(Math.cos(a) * LEN, Math.sin(a) * LEN / 2);
//			path.lineTo(Math.cos(a) * LEN * 2, Math.sin(a) * LEN / 2);
//			path.lineTo(Math.cos(a) * LEN * 3, 0);
			path.closePath();
			
			TABLE = path;
		}
		
		public void installUI(JComponent c) {
			c.setBackground(new Color(168, 119, 90).darker());
		}
		
		public void paint(Graphics g, JComponent c) {
			Graphics2D g2d = (Graphics2D) g;
			
			int width = c.getWidth();
			int height = c.getHeight();
			
			g2d.setColor(c.getBackground());
			g2d.fillRect(0, 0, width, height);
			
			for(int i = 0; i < Math.ceil((float) width / BACKDROP.getWidth(null)); i ++)   {
			for(int j = 0; j < Math.ceil((float) height / BACKDROP.getHeight(null)); j ++) {
				g2d.drawImage(BACKDROP, BACKDROP.getWidth(null) * i, BACKDROP.getHeight(null) * j, null);
			}}
			
//			g2d.scale(1/10f, 1/10f);
			g2d.translate(1000, 100);
			g2d.fill(TABLE);
		}
	}
}

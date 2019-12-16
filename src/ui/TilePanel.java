package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import model.Symbol;

public class TilePanel extends Canvas {

	public boolean visible = true;
	private Shape shape;

	public TilePanel() {
		super.setBackground(Color.WHITE);
		super.setSize(60, 00);
	}
	
	private void setXShape()
	{
		int xPol[] = new int[12];
	//	this.shape = new Polygon
	}

	public void paintSymbol(Symbol symbol) {
		switch (symbol) {
		case X:
			this.shape = new Rectangle(this.getSize());
			break;
		case O:
			shape = new Rectangle(10,10,40,40);
			break;
		default:
			this.shape = new Rectangle(40,40);
			break;
		}
		super.repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.WHITE);
		g2.setColor(Color.RED);
		if (this.shape != null) {
			g2.fill(shape);
		}
//		if (visible)
//		{	
//			Shape circle = new Ellipse2D.Double(10, 10, 10, 10);
//			g2.setColor(Color.RED);
//			g2.fillOval(0,0,70,70);
//			g2.draw(circle);
//		}
//		else
//		{
//			Shape circle = new Ellipse2D.Double(10, 10, 10, 10);
//			g2.setColor(Color.GREEN);
//			g2.fillOval(80, 80, 80, 80);
//			g2.draw(circle);
		// Shape x = null;
		

	}
	// super.repaint();
}

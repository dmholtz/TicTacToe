package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Optional;

import javax.swing.JPanel;

import model.Symbol;

public class TilePanel extends Canvas {

	private static final long serialVersionUID = 1L;
	
	private Optional<Shape> shape = Optional.empty();
	private Color shapeColor = Color.BLACK;
	
	private boolean previewEnabled;

	public TilePanel() {
		super.setBackground(Color.WHITE);
		super.setSize(60, 00);
		this.previewEnabled = true;
	}
	
	public boolean isPreviewEnabled()
	{
		return this.previewEnabled;
	}
	
	public void enablePreview()
	{
		this.previewEnabled = true;
	}
	
	public void disablePreview()
	{
		this.previewEnabled = false;
	}

	private void defineXShape() {
		int s = Math.min(this.getSize().width, this.getSize().height) * 9 / 10;
		int x = s / 15;
		int xPol[] = new int[12];
		int yPol[] = new int[12];
		xPol[0] = 0;
		xPol[1] = x;
		xPol[2] = s / 2;
		xPol[3] = s - x;
		xPol[4] = s;
		xPol[5] = s / 2 + x;
		xPol[6] = s;
		xPol[7] = s - x;
		xPol[8] = s / 2;
		xPol[9] = x;
		xPol[10] = 0;
		xPol[11] = s / 2 - x;

		yPol[3] = 0;
		yPol[4] = x;
		yPol[5] = s / 2;
		yPol[6] = s - x;
		yPol[7] = s;
		yPol[8] = s / 2 + x;
		yPol[9] = s;
		yPol[10] = s - x;
		yPol[11] = s / 2;
		yPol[0] = x;
		yPol[1] = 0;
		yPol[2] = s / 2 - x;

		for (int i = 0; i < 12; i++) {
			xPol[i] += (this.getSize().width - s) / 2;
			yPol[i] += (this.getSize().height - s) / 2;
		}
		this.shape = Optional.of(new Polygon(xPol, yPol, 12));
	}

	public void defineOShape() {
		double centerX = this.getSize().getWidth() / 2.0;
		double centerY = this.getSize().getHeight() / 2.0;
		double radius = Math.min(centerX, centerY) * 9 / 10;
		double thickness = radius * 0.1;
		this.shape = Optional.of(createRingShape(centerX, centerY, radius, thickness));
	}

	private static Shape createRingShape(double centerX, double centerY, double outerRadius, double thickness) {
		Ellipse2D outer = new Ellipse2D.Double(centerX - outerRadius, centerY - outerRadius, outerRadius + outerRadius,
				outerRadius + outerRadius);
		Ellipse2D inner = new Ellipse2D.Double(centerX - outerRadius + thickness, centerY - outerRadius + thickness,
				outerRadius + outerRadius - thickness - thickness, outerRadius + outerRadius - thickness - thickness);
		Area area = new Area(outer);
		area.subtract(new Area(inner));
		return area;
	}

	public void paintSymbol(Symbol symbol, Color shapeColor) {
		this.disablePreview();
		this.shapeColor = shapeColor;
		switch (symbol) {
		case X:
			defineXShape();
			break;
		case O:
			defineOShape();
			break;
		default:
			this.shape = Optional.empty();
			break;
		}
		super.repaint();
	}
	
	public void previewSymbol(Symbol symbol, Color shapeColor)
	{
		if (!this.isPreviewEnabled())
		{
			throw new IllegalCallerException("Previewing symbol has been disabled.");
		}
		this.shapeColor = ColorModifier.lightUp(shapeColor, 30);
		switch (symbol) {
		case X:
			defineXShape();
			break;
		case O:
			defineOShape();
			break;
		default:
			this.shape = Optional.empty();
			break;
		}
		super.repaint();
		
	}

	public void clearTile() {
		shape = Optional.empty();
		super.repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.WHITE);
		g2.setColor(shapeColor);
		if (this.shape.isPresent()) {
			g2.fill(shape.get());
		}
	}
}

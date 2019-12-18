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

	public void paintMarker(Marker marker)
	{
		this.disablePreview();
		this.shapeColor = marker.getColor();
		this.shape = Optional.of(marker.generateShapeForDimension(this.getSize()));
		super.repaint();
	}
	
	public void previewMarker(Marker marker)
	{
		if (!this.isPreviewEnabled())
		{
			throw new IllegalCallerException("Previewing symbol has been disabled.");
		}
		this.shapeColor = ColorModifier.lightUp(marker.getColor(), 30);
		this.shape = Optional.of(marker.generateShapeForDimension(this.getSize()));
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

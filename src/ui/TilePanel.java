package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Optional;
import datatypes.Coordinate;

public class TilePanel extends Canvas {

	private static final long serialVersionUID = 1L;
	
	private Optional<Shape> shape = Optional.empty();
	private Color shapeColor = Color.BLACK;
	
	private boolean previewEnabled;
	private final Coordinate coordinate;

	public TilePanel(Coordinate coordinate) {
		super.setBackground(Color.WHITE);
		this.coordinate = coordinate;
		this.previewEnabled = true;
	}
	
	public boolean isPreviewEnabled()
	{
		return this.previewEnabled;
	}
	
	/**
	 * @return the coordinate
	 */
	public final Coordinate getCoordinate() {
		return coordinate;
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
		if (this.isPreviewEnabled())
		{
			this.shapeColor = ColorModifier.lightUp(marker.getColor(), 30);
			this.shape = Optional.of(marker.generateShapeForDimension(this.getSize()));
			super.repaint();
		}	
	}
	
	public void removePreview()
	{
		if (this.isPreviewEnabled())
		{
			shape = Optional.empty();
			super.repaint();
		}	
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

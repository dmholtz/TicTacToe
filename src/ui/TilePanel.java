package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Optional;
import datatypes.Coordinate;

/**
 * TilePanel is a component of the graphical user interface and represents a
 * single tile in the grid. Shapes can be drawn on every instance of this class,
 * since TilePanel extends java.awt.Canvas
 * 
 * @author David Holtz
 * @version 1.0
 *
 */
public class TilePanel extends Canvas {

	private static final long serialVersionUID = 1L;

	private Optional<Shape> shape = Optional.empty();
	private Color shapeColor = Color.BLACK;

	private boolean previewEnabled;
	private final Coordinate coordinate;

	/**
	 * Constructor creates a new TilePanel object and associates a coordinate
	 * 
	 * @param coordinate in the grid
	 */
	public TilePanel(Coordinate coordinate) {
		this.coordinate = coordinate;
		this.setBackground(Color.WHITE);
		this.enablePreview();
	}

	/**
	 * Returns whether preview mode is enabled. Preview mode makes the graphical
	 * user interface feel more responsive to user interaction. Preview is enabled
	 * by default, however, it is automatically disabled once a tile has been
	 * marked.
	 * 
	 * @return
	 */
	public boolean isPreviewEnabled() {
		return this.previewEnabled;
	}

	/**
	 * @return the coordinate
	 */
	public final Coordinate getCoordinate() {
		return coordinate;
	}

	public void enablePreview() {
		this.previewEnabled = true;
	}

	public void disablePreview() {
		this.previewEnabled = false;
	}

	/**
	 * Paints a marker on a tile and disables preview. Therefore, the method
	 * retrieves a color from the marker object and assigns it to this class color
	 * attribute. Then it generates the shape of the given marker according to this
	 * TilePanel's size. Finally, the repaint command (inherited from Canvas) is
	 * called.
	 * 
	 * @param marker: special marker object which extends the marker class.
	 */
	public void paintMarker(Marker marker) {
		this.disablePreview();
		this.shapeColor = marker.getColor();
		this.shape = Optional.of(marker.generateShapeForDimension(this.getSize()));
		this.repaint();
	}

	/**
	 * Previews a marker on a tile, provided that preview is enabled. If preview is
	 * disabled, nothing happens. Similarly to paintMarker, shape and color are
	 * retrieved from the given Marker object. Please note, that the color is lit up
	 * to denote preview mode.
	 * 
	 * @param marker: special marker object which extends the marker class.
	 * @see paintMarker
	 * 
	 */
	public void previewMarker(Marker marker) {
		if (this.isPreviewEnabled()) {
			this.shapeColor = ColorModifier.lightUp(marker.getColor(), 30);
			this.shape = Optional.of(marker.generateShapeForDimension(this.getSize()));
			super.repaint();
		}
	}

	/**
	 * Removes a preview from the tile and repaints the (emptied) canvas if preview
	 * mode is enabled. If preview is disabled, nothing happens.
	 */
	public void removePreview() {
		if (this.isPreviewEnabled()) {
			shape = Optional.empty();
			super.repaint();
		}
	}

	/**
	 * Draws and fills the optional shape attribute of TilePanel, provided that it is
	 * present. If the Optional is empty, nothing happens.
	 */
	@Override
	public void paint(Graphics g) {
		if (this.shape.isPresent()) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setBackground(Color.WHITE);
			g2.setColor(shapeColor);
			g2.fill(shape.get());
		}
	}
}

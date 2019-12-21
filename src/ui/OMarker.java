package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Inherited from Marker. Represents special O-Markers in Tic Tac Toe game
 * 
 * @author David Holtz
 * @version 1.0
 *
 */
public class OMarker extends Marker {

	public OMarker(Color color) {
		super.setColor(color);
	}

	/**
	 * Returns a O-shape which fits the given dimensions. The O-shape is centered
	 * and takes up 90% of the available space.
	 */
	@Override
	public Shape generateShapeForDimension(Dimension size) {
		double centerX = size.getWidth() / 2.0;
		double centerY = size.getHeight() / 2.0;
		double radius = Math.min(centerX, centerY) * 9 / 10;
		double thickness = radius * 0.1;
		return OMarker.createRingShape(centerX, centerY, radius, thickness);
	}

	/**
	 * Static method which creates a ring shape by cutting out a smaller circle from
	 * a larger circle.
	 * 
	 * @param centerX
	 * @param centerY
	 * @param outerRadius
	 * @param thickness
	 * @return
	 */
	private static Shape createRingShape(double centerX, double centerY, double outerRadius, double thickness) {
		Ellipse2D outer = new Ellipse2D.Double(centerX - outerRadius, centerY - outerRadius, outerRadius + outerRadius,
				outerRadius + outerRadius);
		Ellipse2D inner = new Ellipse2D.Double(centerX - outerRadius + thickness, centerY - outerRadius + thickness,
				outerRadius + outerRadius - thickness - thickness, outerRadius + outerRadius - thickness - thickness);
		Area area = new Area(outer);
		area.subtract(new Area(inner));
		return area;
	}
}

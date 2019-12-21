package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;

/**
 * Abstract specification for markers, which can be drawn on a canvas
 * (concretely on TilePanels)
 * 
 * @author David
 * @version 1.0
 *
 */
public abstract class Marker {

	private Color color;
	
	/**
	 * Sets the marker's color
	 * @param color of this marker
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/** 
	 * Returns the marker's color
	 * @return
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Returns a Shape which fits the given dimensions. 
	 * @param size
	 * @return
	 */
	public abstract Shape generateShapeForDimension(Dimension size);
}

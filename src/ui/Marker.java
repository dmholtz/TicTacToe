package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Optional;

public abstract class Marker {
	
	private Shape shape;
	private Color color;
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public abstract Shape generateShapeForDimension(Dimension size) ;
}

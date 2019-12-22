package ui.markers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Shape;

/**
 * Inherited from Marker. Represents special X-Markers in Tic Tac Toe game
 * 
 * @author dmholtz
 * @version 1.0
 *
 */
public class XMarker extends Marker {

	public XMarker(Color color) {
		super.setColor(color);
	}

	/**
	 * Generates an X-shape which fits the given dimensions. The X-shape is centered
	 * and takes up 90% of the space available. Firstly, edge points of the polygon
	 * are calculated. Then, the polygon is centered and returned as a shape object.
	 *
	 * Please condone style infringements.
	 */
	@Override
	public Shape generateShapeForDimension(Dimension size) {
		// edgeLength := edgeLength of the square, which surrounds the X-Shape
		int edgeLength = Math.min(size.width, size.height) * 9 / 10;
		int offset = edgeLength / 15;
		int xPol[] = new int[12];
		int yPol[] = new int[12];
		xPol[0] = 0;
		xPol[1] = offset;
		xPol[2] = edgeLength / 2;
		xPol[3] = edgeLength - offset;
		xPol[4] = edgeLength;
		xPol[5] = edgeLength / 2 + offset;
		xPol[6] = edgeLength;
		xPol[7] = edgeLength - offset;
		xPol[8] = edgeLength / 2;
		xPol[9] = offset;
		xPol[10] = 0;
		xPol[11] = edgeLength / 2 - offset;

		yPol[3] = 0;
		yPol[4] = offset;
		yPol[5] = edgeLength / 2;
		yPol[6] = edgeLength - offset;
		yPol[7] = edgeLength;
		yPol[8] = edgeLength / 2 + offset;
		yPol[9] = edgeLength;
		yPol[10] = edgeLength - offset;
		yPol[11] = edgeLength / 2;
		yPol[0] = offset;
		yPol[1] = 0;
		yPol[2] = edgeLength / 2 - offset;

		for (int i = 0; i < 12; i++) {
			xPol[i] += (size.width - edgeLength) / 2;
			yPol[i] += (size.height - edgeLength) / 2;
		}
		return new Polygon(xPol, yPol, 12);
	}

}

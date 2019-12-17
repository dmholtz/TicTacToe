package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Optional;

public class XMarker extends Marker {
	
	public XMarker(Color color)
	{
		super.setColor(color);
	}

	@Override
	public Shape generateShapeForDimension(Dimension size) {
		int s = Math.min(size.width, size.height) * 9 / 10;
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
			xPol[i] += (size.width - s) / 2;
			yPol[i] += (size.height - s) / 2;
		}
		return new Polygon(xPol, yPol, 12);
	}

}

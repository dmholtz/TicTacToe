package ui;

import java.awt.Color;

public final class ColorModifier {
	
	private static int lightUpFactor = 3;
	
	public static Color lightUp(Color darker)
	{
		return new Color(darker.getRed(), darker.getGreen(), darker.getBlue(), darker.getAlpha()/lightUpFactor);
	}
	
	/**
	 * Lights up a given color by modifying its alpha value.
	 * 
	 * @param darker: darker color 
	 * @param percentage: percentage according to which the alpha value is scaled. Percentage must be an integer
	 * between (including) 0 and 100. The lower the percentage according to which the alpha value is changed,
	 * the lighter will be the result. If percentage equals 100, the same color is returned.
	 * 
	 * @return lighter color
	 */
	public static Color lightUp(Color darker, int percentage)
	{
		return new Color(darker.getRed(), darker.getGreen(), darker.getBlue(), darker.getAlpha()*percentage/100);
	}

}

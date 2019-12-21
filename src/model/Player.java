package model;
import java.awt.Color;

import datatypes.Coordinate;
import ui.Marker;
import ui.OMarker;
import ui.XMarker;

public class Player {
	
	private String name;
	private Color color = Color.BLACK;
	private Symbol symbol;
	
	private Game game;
	
	public Player(String name, Symbol symbol)
	{
		this.name = name;
		this.symbol = symbol;
	}
	
	public Player(String name, Symbol symbol, Color color)
	{
		this.name = name;
		this.symbol = symbol;
		this.color = color;
	}
	
	public void markTile(Coordinate c)
	{
		this.markTile(c.getX(), c.getY());
	}
	
	public void markTile(final int x, final int y)
	{
		// Argument Check
		if (x < 0 || x >= Grid.gridSize || y < 0 || y >= Grid.gridSize)
		{
			throw new IllegalArgumentException("Given tile is not within the boundries of this grid.");
		}
		// Semantic Check
		if (!this.game.getGrid().getTileFrom(x, y).isEmpty())
		{
			throw new IllegalArgumentException("Cannot mark a marked tile. Player may only mark empty tiles.");
		}
		// Mark
		this.game.getGrid().getTileFrom(x, y).setMarker(this);
	}
	
	public void setName(String name)
	{
		if (name == null || name.isEmpty())
		{
			throw new IllegalArgumentException("Name must not be null or empty.");
		}
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Symbol getSymbol()
	{
		return this.symbol;
	}
	
	/**
	 * @return the color
	 */
	public final Color getColor() {
		return color;
	}
	
	public void assignGame(Game assignedGame)
	{
		this.game = assignedGame;
	}
	
	@Override
	public String toString()
	{
		return this.getName();
	}

}

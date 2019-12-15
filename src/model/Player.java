package model;
import java.awt.Color;

public class Player {
	
	private String name;
	//private Color color;
	private Symbol symbol;
	
	private Game game;
	
	public Player(String name, Symbol symbol)
	{
		this.name = name;
		this.symbol = symbol;
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
	
	public void assignGame(Game assignedGame)
	{
		this.game = assignedGame;
	}

}

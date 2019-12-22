package logic;

import datatypes.Coordinate;
import model.Grid;
import model.Player;
import model.Symbol;

public class VirtualPlayer extends Player {

	public VirtualPlayer(String name, Symbol symbol) {
		super(name, symbol);
		// TODO Auto-generated constructor stub
	}
	
	public void removeMarker(final int x, final int y)
	{
		if (x < 0 || x >= Grid.gridSize || y < 0 || y >= Grid.gridSize) {
			throw new IllegalArgumentException("Given tile is not within the boundries of this grid.");
		}
		if (!this.isGameAssigned()) {
			throw new IllegalCallerException(
					"this.game has not been assigned yet. Must assign a game to this player before calling this method.");
		}
		// Semantic Check
		if (this.game.get().getGrid().getTileFrom(x, y).isEmpty()) {
			throw new IllegalArgumentException("Cannot mark a marked tile. Player may only mark empty tiles.");
		}
		// Mark
		this.game.get().getGrid().getTileFrom(x, y).removeMarker();
	}
	
	public void removeMarker(Coordinate c)
	{
		this.removeMarker(c.getX(), c.getY());
	}

}

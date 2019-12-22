package model;

import java.awt.Color;
import java.util.Optional;

import datatypes.Coordinate;

/**
 * Players are "actors" in a TicTacToe game. Every player has a name, a color, a
 * symbol and might be part of a game.
 * 
 * @author David Holtz
 * @version 1.0
 *
 */
public class Player {

	private String name;
	private Color color = Color.BLACK;
	private Symbol symbol;

	protected Optional<Game> game = Optional.empty();

	/**
	 * Set up a player with name and symbol. Color attribute will be default color.
	 * 
	 * @param name   (String): must not be null or empty
	 * @param symbol
	 */
	public Player(String name, Symbol symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	/**
	 * Set up a player with name, symbol and color. Use in games with GUI
	 * 
	 * @param name   (String): must not be null or empty
	 * @param symbol
	 * @param color
	 */
	public Player(String name, Symbol symbol, Color color) {
		this.name = name;
		this.symbol = symbol;
		this.color = color;
	}

	/**
	 * Assign a game to the optional game attribute
	 * 
	 * @param assignedGame
	 */
	public void assignGame(Game assignedGame) {
		this.game = Optional.of(assignedGame);
	}

	/**
	 * Returns whether a game has been assigned.
	 * 
	 * @return
	 */
	public boolean isGameAssigned() {
		return game.isPresent();
	}

	/**
	 * Player sets his marker on the tile with the given coordinates. Tile
	 * coordinates must be valid TicTacToe coordinates and the target tile must be
	 * empty.
	 * 
	 * @requires: this.isGameAssigned();
	 * @ensures: !game.get().getGrid().getTileFrom(x, y).isEmpty();
	 * 
	 * @param x: x-coordinate of the tile in the grid
	 * @param y: y-coordinate of the tile in the grid
	 */
	public void markTile(final int x, final int y) {
		// Argument Check
		if (x < 0 || x >= Grid.gridSize || y < 0 || y >= Grid.gridSize) {
			throw new IllegalArgumentException("Given tile is not within the boundries of this grid.");
		}
		if (!this.isGameAssigned()) {
			throw new IllegalCallerException(
					"this.game has not been assigned yet. Must assign a game to this player before calling this method.");
		}
		// Semantic Check
		if (!this.game.get().getGrid().getTileFrom(x, y).isEmpty()) {
			throw new IllegalArgumentException("Cannot mark a marked tile. Player may only mark empty tiles.");
		}
		// Mark
		this.game.get().getGrid().getTileFrom(x, y).setMarker(this);
	}

	/**
	 * Overload of markTile(final int x, final int y)
	 * 
	 * @see markTile(final int x, final int y) for detailed describtion and
	 *      preconditions.
	 * @param c: Coordinate tuple, must be valid Tic Tac Toe coordinates
	 */
	public void markTile(Coordinate c) {
		this.markTile(c.getX(), c.getY());
	}

	/**
	 * Sets a (new) name of this player
	 * 
	 * @param name: must not be null or empty
	 */
	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name must not be null or empty.");
		}
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Symbol getSymbol() {
		return this.symbol;
	}

	/**
	 * @return the color
	 */
	public final Color getColor() {
		return color;
	}
}

package model;

import datatypes.Coordinate;

/**
 * Represents the organisation of the TicTacToe grid. Every TicTacToe game has 9
 * tiles, which are arranged in three rows and three columns.
 * 
 * @author David Holtz
 * @verson 1.0
 *
 */
public class Grid {

	public static final int gridSize = 3;

	private final Tile allTiles[][] = new Tile[gridSize][gridSize];

	/**
	 * Creates a new Grid object and initializes all tiles
	 */
	public Grid() {
		for (int x = 0; x < allTiles.length; x++) {
			for (int y = 0; y < allTiles[x].length; y++) {
				allTiles[x][y] = new Tile();
			}
		}
	}

	/**
	 * Retrieves a Tile object at the given coordinate in the grid. Coordinates must
	 * be valid TicTacToe coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTileFrom(final int x, final int y) {
		if (x < 0 || x >= gridSize || y < 0 || y >= gridSize) {
			throw new IllegalArgumentException("Given tile is not within the boundries of this grid.");
		}
		return allTiles[x][y];
	}

	/**
	 * Overload of getTileFrom(final int x, final int y)
	 * 
	 * @see getTileFrom(final int x, final int y) for detailed describtion and
	 *      preconditions
	 * @param c: c must be a valid TicTacToe coordinate tuple
	 * @return
	 */
	public Tile getTileFrom(Coordinate c) {
		return this.getTileFrom(c.getX(), c.getY());
	}

}

package model;

import java.util.Optional;

/**
 * A TicTacToe consists of nine tiles. Each of the tile can be marked by either
 * of the two players involved in a game. A tile might also be empty.
 * 
 * @author dmholtz
 * @version 1.0
 *
 */
public class Tile {

	private Optional<Player> marker;

	public Tile() {
		this.marker = Optional.empty();
	}

	/**
	 * Returns whether a tile is empty.
	 * 
	 * @return true if tile is empty and false if tile has been marked
	 */
	public boolean isEmpty() {
		return marker.isEmpty();
	}

	/**
	 * Gets the marker of tile, provided that it has been marked yet
	 * 
	 * @requires !this.isEmpty();
	 * 
	 * @throws IllegalCallerException if tile has not been marked yet
	 * @return marker of this tile as a Player object
	 */
	public Player getMarker() {
		if (this.isEmpty()) {
			throw new IllegalCallerException("Tile has not been marked by a player yet, cannot return a marker");
		}
		return this.marker.get();
	}

	/**
	 * Sets a marker to this tile
	 * 
	 * @ensures !this.isEmpty();
	 * @param marker as a Player
	 */
	public void setMarker(Player marker) {
		this.marker = Optional.of(marker);
	}

	/**
	 * Removes a marker to this tile
	 * 
	 * @ensures this.isEmpty();
	 */
	public void removeMarker() {
		this.marker = Optional.empty();
	}

	/**
	 * toString returns the current marker of this tile as String. Convenient to
	 * print grid in terminal.
	 * 
	 * @returns "-" if tile is empty and String-representation of marker if tile has
	 *          been marked
	 */
	@Override
	public String toString() {
		if (this.isEmpty()) {
			return "-";
		} else {
			return this.marker.get().getSymbol().toString();
		}

	}

}

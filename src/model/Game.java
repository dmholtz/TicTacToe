package model;

import java.util.Optional;

import datatypes.Coordinate;

/**
 * Model representation of TicTacToe games
 * 
 * Each game has a grid and 
 * @author dmholtz
 *
 */
public abstract class Game {

	private Optional<Player> player1 = Optional.empty();
	private Optional<Player> player2 = Optional.empty();

	protected Grid grid;

	public Game() {
		this.grid = new Grid();
	}

	public Grid getGrid() {
		return grid;
	}

	/**
	 * Assign the given player to this game's first player and reassign the given
	 * game to the given Player object.
	 * 
	 * @param player: the symbol of the assigned player must defer from the second
	 *                player's symbol
	 * @throw IllegalArgumentException in case the assigned player's symbol is
	 *        already in use
	 */
	public void assignFirstPlayer(final Player player) {
		if (player2.isEmpty() || !player2.get().getSymbol().equals(player.getSymbol())) {
			this.player1 = Optional.of(player);
			this.player1.get().assignGame(this);
		} else {
			throw new IllegalArgumentException("Symbol is already in use. Assign a player with a different symbol");
		}
	}

	/**
	 * Returns the first player, provided that he has been assigned. Otherwise, an
	 * exception is thrown
	 * 
	 * @return
	 */
	public Player getFirstPlayer() {
		if (player1.isEmpty()) {
			throw new IllegalCallerException("First player has not been assigned yet.");
		}
		return player1.get();
	}

	/**
	 * Assign the given player to this game's second player and reassign the given
	 * game to the given Player object
	 * 
	 * @param player: the symbol of the assigned player must defer from the first
	 *                player's symbol
	 * @throw IllegalArgumentException in case the assigned player's symbol is
	 *        already in use
	 */
	public void assignSecondPlayer(final Player player) {
		if (player1.isEmpty() || !player1.get().getSymbol().equals(player.getSymbol())) {
			this.player2 = Optional.of(player);
			this.player2.get().assignGame(this);
		} else {
			throw new IllegalArgumentException("Symbol is already in use. Assign a player with a different symbol");
		}
	}

	/**
	 * Returns the second player, provided that he has been assigned. Otherwise, an
	 * exception is thrown.
	 * 
	 * @return
	 */
	public Player getSecondPlayer() {
		if (player2.isEmpty()) {
			throw new IllegalCallerException("Second player has not been assigned yet.");
		}
		return player2.get();
	}

	/**
	 * Returns whether game is initialized with players
	 * 
	 * @return
	 */
	public boolean isInitialized() {
		return this.player1.isPresent() && this.player2.isPresent();
	}

	/**
	 * Returns the number of tiles which have been marked
	 * 
	 * @return
	 */
	public int getNumberOfMarkers() {
		int numberOfMarkers = 0;
		for (int i = 0; i < Grid.gridSize; i++) {
			for (int j = 0; j < Grid.gridSize; j++) {
				if (!this.grid.getTileFrom(i, j).isEmpty()) {
					numberOfMarkers++;
				}
			}
		}
		return numberOfMarkers;
	}
	
	/**
	 * Returns whether a tile at a given coordinate is empty
	 * @param tileCoordinate must be a valid coordinate object
	 * @return
	 */
	public boolean isTileEmptyAt(Coordinate tileCoordinate) {
		return this.grid.getTileFrom(tileCoordinate).isEmpty();
	}

	/**
	 * Resets the game by creating a new grid and sets the optional player
	 * attributes to empty.
	 */
	public void resetGame() {
		this.grid = new Grid();
		this.player1 = Optional.empty();
		this.player2 = Optional.empty();
	}

}

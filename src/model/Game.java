package model;

import java.util.Optional;

public abstract class Game {

	protected Optional<Player> player1 = Optional.empty();
	protected Optional<Player> player2 = Optional.empty();

	protected final Grid grid;

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
	 * Returns the first player, provided that he has been assigned.
	 * Otherwise, an exception is thrown
	 * @return
	 */
	public Player getFirstPlayer()
	{
		if (player1.isEmpty())
		{
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
	 * Returns the second player, provided that he has been assigned.
	 * Otherwise, an exception is thrown.
	 * @return
	 */
	public Player getSecondPlayer()
	{
		if (player2.isEmpty())
		{
			throw new IllegalCallerException("Second player has not been assigned yet.");
		}
		return player2.get();
	}
	
	/**
	 * Returns whether game is initialized with players
	 * @return
	 */
	public boolean isInitialized()
	{
		return this.player1.isPresent() && this.player2.isPresent();
	}

}

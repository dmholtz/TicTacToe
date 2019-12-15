package model;

import java.util.Optional;

public abstract class Game {
	
	protected Optional<Player> player1;
	protected Optional<Player> player2;
	
	protected final Grid grid;
	
	public Game()
	{
		this.grid = new Grid();
	}
	
	public Grid getGrid()
	{
		return grid;
	}
	
	public void assignFirstPlayer(final Player player)
	{
		this.player1 = Optional.of(player);
		this.player1.get().assignGame(this);
	}
	
	public void assignSecondPlayer(final Player player)
	{
		this.player2 = Optional.of(player);
		this.player2.get().assignGame(this);
	}
	
	

}

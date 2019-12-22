package logic;

import java.awt.Color;
import java.util.Optional;

import datatypes.Coordinate;
import model.Game;
import model.Grid;
import model.Player;
import model.Symbol;

public class SimpleTicTacToeGame extends Game {

	public Player activePlayer;
	boolean gameStatus = true;
	private Optional<Player> winner = Optional.empty();

	public SimpleTicTacToeGame() {
		this.setDefaultPlayers();
		activePlayer = (this.player1.get());
		gameStatus = true;
	}
	
	private void setDefaultPlayers()
	{
		Player player1 = new Player("Player O", Symbol.O, Color.GREEN);
		Player player2 = new Player("Player X", Symbol.X, Color.RED);
		this.assignFirstPlayer(player1);
		this.assignSecondPlayer(player2);
	}

	public void swapActivePlayer() {
		if (activePlayer.equals(this.player1.get())) {
			activePlayer = this.player2.get();
		} else {
			activePlayer = this.player1.get();
		}
		/*if(this.getNumberOfMarkers() >= 9)
		{
			gameStatus = false;
		}*/
	}

	private boolean hasPlayerWonARow(Player p, int row) {
		boolean returnVal = true;
		for (int x = 0; x < Grid.gridSize; x++) {
			returnVal &= (!this.getGrid().getTileFrom(x, row).isEmpty()
					&& this.getGrid().getTileFrom(x, row).getMarker().equals(p));
		}
		return returnVal;
	}

	private boolean hasPlayerWonAColumn(Player p, int column) {
		boolean returnVal = true;
		for (int y = 0; y < Grid.gridSize; y++) {
			returnVal &= (!this.getGrid().getTileFrom(column, y).isEmpty()
					&& this.getGrid().getTileFrom(column, y).getMarker().equals(p));
		}
		return returnVal;
	}
	
	private boolean hasPlayerWonFirstDiagonal(Player p)
	{
		boolean returnVal = true;
		for (int i = 0; i< Grid.gridSize; i++) {
			returnVal &= (!this.getGrid().getTileFrom(i, i).isEmpty()
					&& this.getGrid().getTileFrom(i, i).getMarker().equals(p));
		}
		return returnVal;
	}
	
	private boolean hasPlayerWonSecondDiagonal(Player p)
	{
		boolean returnVal = true;
		for (int i = 0; i< Grid.gridSize; i++) {
			returnVal &= (!this.getGrid().getTileFrom(Grid.gridSize-i-1, i).isEmpty()
					&& this.getGrid().getTileFrom(Grid.gridSize-i-1, i).getMarker().equals(p));
		}
		return returnVal;
	}

	private boolean isPlayerWinner(Player p) {
		for (int i = 0; i < Grid.gridSize; i++) {
			if (this.hasPlayerWonARow(p, i)) {
				return true;
			}
		}
		for (int i = 0; i < Grid.gridSize; i++) {
			if (this.hasPlayerWonAColumn(p, i)) {
				return true;
			}
		}
		if (this.hasPlayerWonFirstDiagonal(p)) {
			return true;
		}
		if (this.hasPlayerWonSecondDiagonal(p)) {
			return true;
		}
		return false;
	}

	public void determineWinner()
	{
		if (isPlayerWinner(this.player1.get()))
		{
			winner = Optional.of(this.player1.get());
			gameStatus = false;
		}
		else if (isPlayerWinner(this.player2.get()))
		{
			winner = Optional.of(this.player2.get());
			gameStatus = false;
		}
		else
		{
			winner = Optional.empty();
			if (this.getNumberOfMarkers() >= 9)
			{
				gameStatus = false;
			}
			else {
				gameStatus = true;
			}
		}
	}
	
	public boolean getGameStatus()
	{
		return this.gameStatus;
	}

	public Optional<Player> getWinner() {
		return this.winner;
	}

	public boolean isTileEmpty(Coordinate tileCoordinate) {
		return this.grid.getTileFrom(tileCoordinate).isEmpty();
	}
	
	public void printGrid(Game g) {
		for (int x = 0; x < Grid.gridSize; x++) {
			for (int y = 0; y < Grid.gridSize; y++) {
				System.out.print(g.getGrid().getTileFrom(x, y).toString()+" ");
			}
			System.out.println();
		}
		
		if (this.getWinner().isPresent())
		{
			System.out.println("And the winner iiiisss: "+this.getWinner().get().getName());
		}
		else if(!gameStatus)
		{
			System.out.println("Draw");
		}
		System.out.println();
	}
}

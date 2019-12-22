package model;

import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;

import datatypes.Coordinate;
import datatypes.Selection;
import datatypes.SelectionComparator;
import logic.SimpleTicTacToeGame;

public class ComputerPlayer extends Player {
	
	private SimpleTicTacToeGame copy;
	
	public ComputerPlayer(String name, Symbol symbol) {
		super(name, symbol);
	}
	
	public ComputerPlayer(String name, Symbol symbol, Color color) {
		super(name, symbol, color);
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
			throw new IllegalArgumentException("Cannot remove a marker from an empty tile.");
		}
		// Mark
		this.game.get().getGrid().getTileFrom(x, y).removeMarker();
	}
	
	public void removeMarker(Coordinate c)
	{
		this.removeMarker(c.getX(), c.getY());
	}
	
	public Coordinate selectTileAutomatically()
	{
		this.createGameCopy();
		return this.choose().getCoordinate();
	}
	
	private void createGameCopy()
	{
		this.copy = new SimpleTicTacToeGame();
		this.createVirtualPlayers();
		this.copyGrid(this.game.get());
	}
	
	private void createVirtualPlayers()
	{
		ComputerPlayer virtualMe = new ComputerPlayer("me", this.getSymbol());
		ComputerPlayer virtualOpponent;
		if (this.getSymbol().equals(Symbol.O))
		{
			virtualOpponent = new ComputerPlayer("opponent", Symbol.X);
		}
		else
		{
			virtualOpponent = new ComputerPlayer("opponent", Symbol.O);
		}
		this.copy.assignFirstPlayer(virtualMe);
		this.copy.assignSecondPlayer(virtualOpponent);
		this.copy.setActivePlayer(virtualMe);
	}

	private void copyGrid(Game from) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!from.getGrid().getTileFrom(i, j).isEmpty()) {
					if (from.getGrid().getTileFrom(i, j).getMarker().getSymbol().equals(this.getSymbol()))
					{
						this.copy.getGrid().getTileFrom(i, j).setMarker(this.copy.getFirstPlayer());
					}
					else
					{
						this.copy.getGrid().getTileFrom(i, j).setMarker(this.copy.getSecondPlayer());
					}
				}
			}
		}
	}
	
	private LinkedList<Coordinate> getOptions()
	{
		LinkedList<Coordinate> options = new LinkedList<Coordinate>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.copy.getGrid().getTileFrom(i, j).isEmpty()) {
					options.add(new Coordinate(i,j));
				}
			}
		}
		return options;
	}
	
	private Selection choose()
	{
		LinkedList<Coordinate> potentialTileCoordinates = this.getOptions();
		LinkedList<Selection> selections = new LinkedList<Selection>();
		for (Coordinate potentialCoordinate : potentialTileCoordinates)
		{
			this.copy.getActivePlayer().markTile(potentialCoordinate);
			this.copy.determineWinner();
			if (this.copy.isGameActive())
			{
				this.copy.swapActivePlayer();
				int result = this.choose().getResult();
				selections.add(new Selection(potentialCoordinate, result));
			}
			else
			{
				selections.add(this.generateSimpleSelection(potentialCoordinate));
			}
			ComputerPlayer helper = (ComputerPlayer) this.copy.getActivePlayer();
			helper.removeMarker(potentialCoordinate);
		}
		
		if(this.copy.getActivePlayer().equals(this.copy.getFirstPlayer()))
		{
			this.copy.swapActivePlayer();
			return this.assumeBestCase(selections);
		}
		else
		{
			this.copy.swapActivePlayer();
			return this.assumeWortsCase(selections);
		}
	}
	
	private Selection generateSimpleSelection(Coordinate selectionCoordinate)
	{
		if (this.copy.getWinner().isEmpty())
		{
			return new Selection(selectionCoordinate, 0);
		}
		else if (this.copy.getWinner().get().equals(this.copy.getFirstPlayer()))
		{
			return new Selection(selectionCoordinate, 1);
		}
		else
		{
			return new Selection(selectionCoordinate, -1);
		}
	}
	
	private Selection assumeWortsCase(final LinkedList<Selection> selections)
	{
		Collections.shuffle(selections); // fun way to demonstrate different results
		Collections.sort(selections, new SelectionComparator());
		return selections.peekFirst();
	}
	
	private Selection assumeBestCase(final LinkedList<Selection> selections)
	{
		Collections.shuffle(selections); // fun way to demonstrate different results
		Collections.sort(selections, new SelectionComparator());
		return selections.peekLast();
	}
}

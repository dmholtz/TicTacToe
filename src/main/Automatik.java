package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import datatypes.Coordinate;
import datatypes.Selection;
import datatypes.SelectionComparator;
import logic.SimpleTicTacToeGame;
import logic.VirtualPlayer;
import model.Game;
import model.Player;
import model.Symbol;

public class Automatik extends SimpleTicTacToeGame {
	
	

	public Automatik(Game copy) {
		
		VirtualPlayer me = new VirtualPlayer("me", Symbol.O);
		VirtualPlayer op = new VirtualPlayer("op", Symbol.X);
		this.assignFirstPlayer(me);
		this.assignSecondPlayer(op);
		this.activePlayer = me;
		copyGame(copy);
		
	}

	private void copyGame(Game copy) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!copy.getGrid().getTileFrom(i, j).isEmpty()) {
					if (copy.getGrid().getTileFrom(i, j).getMarker().getSymbol() == Symbol.O)
					{
						this.getGrid().getTileFrom(i, j).setMarker(this.getFirstPlayer());
					}
					else
					{
						this.getGrid().getTileFrom(i, j).setMarker(this.getSecondPlayer());
					}
				}
			}
		}
		System.out.println("Copied Game");
		this.printGrid(this);
	}
	
	private Selection choose()
	{
		LinkedList<Coordinate> l = this.getOptions();
		LinkedList<Selection> selections = new LinkedList<Selection>();
		for (Coordinate c : l)
		{
			this.activePlayer.markTile(c);
			this.determineWinner();
			this.printGrid(this);
			if (this.getGameStatus())
			{
				// dig in deeper
				this.swapActivePlayer();
				int result = this.choose().getResult();
				selections.add(new Selection(c, result));
			}
			else
			{
				if (this.getWinner().isEmpty())
				{
					selections.add(new Selection(c, 0));
				}
				else if (this.getWinner().get().equals(this.getFirstPlayer()))
				{
					selections.add(new Selection(c, 1));
				}
				else
				{
					selections.add(new Selection(c, -1));
				}
			}
			VirtualPlayer x = (VirtualPlayer) this.activePlayer;
			x.removeMarker(c);
		}
		
		if(this.activePlayer.equals(this.getFirstPlayer()))
		{
			this.swapActivePlayer();
			return this.assumeBestCase(selections);
		}
		else
		{
			this.swapActivePlayer();
			return this.assumeWortsCase(selections);
		}
		//Selection se = this.assumeBestCase(selections);
		//System.out.println(se.getC().toString()+" "+se.getResult());
	}
	
	private Selection assumeWortsCase(final LinkedList<Selection> selections)
	{
		Collections.sort(selections, new SelectionComparator());
		System.out.println("assuming worst case....");
		for (Selection s : selections)
		{
			System.out.println(s.getCoordinate().toString()+" "+s.getResult());
		}
		System.out.print("Chosse: ");
		Selection s = selections.peekFirst();
		System.out.println(s.getCoordinate().toString()+" "+s.getResult());
		return selections.peekFirst();
	}
	
	private Selection assumeBestCase(final LinkedList<Selection> selections)
	{
		Collections.sort(selections, new SelectionComparator());
		return selections.peekLast();
	}
	
	private LinkedList<Coordinate> getOptions()
	{
		LinkedList<Coordinate> l = new LinkedList<Coordinate>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.getGrid().getTileFrom(i, j).isEmpty()) {
					l.add(new Coordinate(i,j));
				}
			}
		}
		//System.out.println(l);
		return l;
	}

	public static void main(String[] args) {
		SimpleTicTacToeGame s = new SimpleTicTacToeGame();
		Player me = s.getFirstPlayer();
		Player op = s.getSecondPlayer();
//		me.markTile(0,0);
//		op.markTile(1,1);
//		me.markTile(2,2);
//		op.markTile(2,0);
		
//		op.markTile(0,2);
//		op.markTile(1,2);
//		me.markTile(2,1);
//		me.markTile(0,0);
		
		
		
//		me.markTile(1,2);
//		op.markTile(2,0);
//		op.markTile(2, 2);
//		op.markTile(1,0);
//		me.markTile(0, 1);
//		me.markTile(1, 1);
//		me.markTile(2, 0);

		System.out.println("Start");
		Automatik a = new Automatik(s);
		Coordinate choice = a.choose().getCoordinate();
		System.out.println("Vorher");
		s.printGrid(s);
		System.out.println("Ergebnis:");
		me.markTile(choice);
		s.printGrid(s);
	}

}

package main;

import java.util.LinkedList;

import datatypes.Coordinate;
import logic.SimpleTicTacToeGame;
import model.Game;
import model.Player;

public class Automatik extends SimpleTicTacToeGame {

	public Automatik(Game copy) {
		copyGame(copy);
	}

	private void copyGame(Game copy) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!copy.getGrid().getTileFrom(i, j).isEmpty()) {
					this.getGrid().getTileFrom(i, j).setMarker(copy.getGrid().getTileFrom(i, j).getMarker());
				}
			}
		}
		this.printGrid(this);
	}
	
	private void choose()
	{
		LinkedList<Coordinate> l = this.getOptions();
		LinkedList<Integer> erg = new LinkedList<Integer>();
		for (Coordinate c : l)
		{
			this.getFirstPlayer().markTile(c);
			this.determineWinner();
			if (this.getWinner().isEmpty())
			{
				erg.add(0);
			}
			else if (this.getWinner().get().equals(this.getFirstPlayer()))
			{
				erg.add(1);
			}
			else
			{
				erg.add(-1);
			}
		}
		for (int i = 0; i< l.size(); i++)
		{
			System.out.println(l.get(i)+" "+erg.get(i));
		}
		this.printGrid(this);
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
		System.out.println(l);
		return l;
	}

	public static void main(String[] args) {
		SimpleTicTacToeGame s = new SimpleTicTacToeGame();
		Player me = s.getFirstPlayer();
		Player op = s.getSecondPlayer();
		me.markTile(0, 0);
		op.markTile(1, 1);
		me.markTile(0, 1);
		op.markTile(1, 0);
		me.markTile(2, 2);
		op.markTile(2, 1);
		s.printGrid(s);
		Automatik a = new Automatik(s);
		a.choose();
	}

}

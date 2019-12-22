package main;

import model.Game;
import model.Grid;
import model.Player;
import model.Symbol;

public class GameTester extends Game {

	public void printGrid(Game g) {
		for (int x = 0; x < Grid.gridSize; x++) {
			for (int y = 0; y < Grid.gridSize; y++) {
				System.out.print(g.getGrid().getTileFrom(x, y).toString()+" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameTester myGame = new GameTester();
		Player a = new Player("a", Symbol.O);
		Player b = new Player("b", Symbol.X);
		myGame.assignFirstPlayer(a);
		myGame.assignSecondPlayer(b);
		myGame.printGrid(myGame);
		a.markTile(1, 1);
		b.markTile(2, 2);
		myGame.printGrid(myGame);

	}

}

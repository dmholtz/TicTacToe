package logic;

import model.ComputerPlayer;
import model.Symbol;

public class ComputerBattleGame extends SimpleTicTacToeGame{
	
	public ComputerBattleGame() {
		this.setDefaultPlayers();
	}
	
	@Override
	protected void setDefaultPlayers()
	{
		ComputerPlayer player1 = new ComputerPlayer("Computer O", Symbol.O);
		ComputerPlayer player2 = new ComputerPlayer("Computer X", Symbol.X);
		this.assignFirstPlayer(player1);
		this.assignSecondPlayer(player2);
		this.setActivePlayer(player1);
	}
	
	public void run()
	{
		while(this.isGameActive())
		{
			ComputerPlayer currentPlayer = (ComputerPlayer) this.getActivePlayer();
			this.getActivePlayer().markTile(currentPlayer.selectTileAutomatically());
			this.swapActivePlayer();
			this.determineWinner();
			this.printGrid(this);
		}
	}

}

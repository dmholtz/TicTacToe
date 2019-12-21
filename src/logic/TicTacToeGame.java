package logic;

import datatypes.Coordinate;
import datatypes.TileUpdateTask;
import model.Game;
import model.Grid;
import model.Player;
import model.Symbol;
import ui.GraphicalUserInterface;
import ui.events.UserRequest;
import ui.events.UserRequestEvent;
import ui.events.UserRequestEventListener;
import ui.markers.Marker;
import ui.markers.OMarker;
import ui.markers.XMarker;

public class TicTacToeGame extends SimpleTicTacToeGame implements UserRequestEventListener {
	
	private GraphicalUserInterface gui;
	
	public TicTacToeGame ()
	{
		this.gui = new GraphicalUserInterface();
		this.gui.setUserRequestEventListener(this);
		this.updateStatus();
	}
	
	public static Marker generateMarkerForPlayer(Player player)
	{
		if (player.getSymbol() == Symbol.X)
		{
			return new XMarker(player.getColor());
		}
		else
		{
			return new OMarker(player.getColor());
		}
	}

	@Override
	public void requestReceived(UserRequestEvent incomingEvent) {
		
		Coordinate requestedTileCoordinate = incomingEvent.getSource();
		UserRequest requestType = incomingEvent.getRequestType();
		
		Marker marker = generateMarkerForPlayer(super.activePlayer);
		
		if (super.isTileEmpty(requestedTileCoordinate)&& this.gameStatus)	
		{
			TileUpdateTask task = new TileUpdateTask(requestedTileCoordinate, requestType, marker);
			gui.updateGame(task);
			
			if (requestType.equals(UserRequest.MARK_TILE) )
			{
				activePlayer.markTile(requestedTileCoordinate);
				this.swapActivePlayer();
				
				this.determineWinner();
				this.printGrid(this);
				this.updateStatus();
			}	
		}	
	}
	
	private void updateStatus()
	{
		if (this.gameStatus)
		{
			gui.setStatus(activePlayer.getName()+", it's your turn!");
		}
		else
		{
			if (this.getWinner().isEmpty())
			{
				gui.setStatus("Draw!");
			}
			else
			{
				gui.setStatus("Congratulations, "+this.getWinner().get().getName()+ "! You've won that match!");
			}
		}
	}
}

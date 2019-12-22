package logic;

import java.awt.Color;

import javax.swing.JOptionPane;

import datatypes.Coordinate;
import datatypes.TileUpdateTask;
import model.ComputerPlayer;
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
		super.setDefaultPlayers();
		this.gui = new GraphicalUserInterface();
		this.gui.requestAndSetPlayers(this);
		this.gui.setUserRequestEventListener(this);
		this.controlGameFlow();
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
	
	public void controlGameFlow()
	{
		if (this.isGameActive())
		{
			if (this.getActivePlayer() instanceof ComputerPlayer)
			{
				ComputerPlayer currentPlayer = (ComputerPlayer) this.getActivePlayer();
				Coordinate c = currentPlayer.selectTileAutomatically();
				currentPlayer.markTile(c);
				Marker marker = generateMarkerForPlayer(currentPlayer);
				TileUpdateTask task = new TileUpdateTask(c, UserRequest.MARK_TILE, marker);
				gui.updateGame(task);
				this.determineWinner();
				this.updateStatus();
				this.swapActivePlayer();
				this.controlGameFlow();
			}
		}	
	}

	@Override
	public void requestReceived(UserRequestEvent incomingEvent) {
		
		Coordinate requestedTileCoordinate = incomingEvent.getSource();
		UserRequest requestType = incomingEvent.getRequestType();
		
		Marker marker = generateMarkerForPlayer(this.getActivePlayer());
		
		if (super.isTileEmpty(requestedTileCoordinate)&& this.gameStatus)	
		{
			TileUpdateTask task = new TileUpdateTask(requestedTileCoordinate, requestType, marker);
			gui.updateGame(task);
			
			if (requestType.equals(UserRequest.MARK_TILE) )
			{
				this.getActivePlayer().markTile(requestedTileCoordinate);
				this.swapActivePlayer();
				
				this.determineWinner();
				this.controlGameFlow();
				this.printGrid(this);
				this.updateStatus();
			}	
		}	
	}
	
	private void updateStatus()
	{
		if (this.gameStatus)
		{
			gui.setStatus(this.getActivePlayer().getName()+", it's your turn!");
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

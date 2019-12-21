package logic;

import java.awt.Color;

import datatypes.Coordinate;
import model.Game;
import ui.GraphicalUserInterface;
import ui.Marker;
import ui.TileUpdateTask;
import ui.UserRequest;
import ui.UserRequestEvent;
import ui.UserRequestEventListener;
import ui.XMarker;

public class TicTacToeGame extends Game implements UserRequestEventListener {
	
	private GraphicalUserInterface gui;
	
	public TicTacToeGame ()
	{
		this.gui = new GraphicalUserInterface();
		this.gui.setUserRequestEventListener(this);
	}

	@Override
	public void requestReceived(UserRequestEvent incomingEvent) {
		
		Coordinate requestedTileCoordinate = incomingEvent.getSource();
		UserRequest requestType = incomingEvent.getRequestType();
		
		/* Informationen
		 * - Welches Feld?
		 * - Welche Aktion?
		 * 
		 * Kontextbezogenen Interpretation:
		 * - Welcher Spieler ist am Zug?
		 * - Ist der Zug in diesem Zusammenhang gülitg?
		 * 
		 * Umsetzung
		 * - ggf. Spielzustand aktualisieren
		 * - GUI updaten (Feld, Aktion, Aktionsparamter (Markerobjekt)
		 * 
		 * 
		 */
		
		Marker marker = new XMarker(Color.BLUE);
		
		if (true)	// check if request is legal
		{
			TileUpdateTask task = new TileUpdateTask(requestedTileCoordinate, requestType, marker);
			gui.updateGame(task);
		}	
	}

	

}

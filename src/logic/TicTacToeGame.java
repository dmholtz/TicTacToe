package logic;

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

	public TicTacToeGame() {

	}

	/**
	 * Starts the graphical user interface and starts a new game. Beforehand,
	 * players are defined according to user input.
	 * 
	 * @requires: game must be reseted beforehand or brand new
	 */
	public void run() {
		this.gui = new GraphicalUserInterface();
		this.gui.requestAndSetPlayers(this);
		this.gui.setUserRequestEventListener(this);
		this.monitorGameStatus();
	}

	private static Marker generateMarkerForPlayer(Player player) {
		if (player.getSymbol() == Symbol.X) {
			return new XMarker(player.getColor());
		} else {
			return new OMarker(player.getColor());
		}
	}

	private void monitorGameStatus() {
		if (this.isGameActive()) {
			if (this.isComputerActionRequired()) {
				this.performComputerAction();
			}
		} else {
			if (gui.isRematchRequested()) {
				this.resetGame();
				this.gui.close();
				this.run();
			}
		}
	}

	private boolean isComputerActionRequired() {
		return this.getActivePlayer() instanceof ComputerPlayer;
	}

	private void performComputerAction() {
		ComputerPlayer currentPlayer = (ComputerPlayer) this.getActivePlayer();
		Coordinate c = currentPlayer.selectTileAutomatically();
		currentPlayer.markTile(c);
		Marker marker = generateMarkerForPlayer(currentPlayer);
		TileUpdateTask task = new TileUpdateTask(c, UserRequest.MARK_TILE, marker);
		gui.updateGame(task);
		this.finishMove();
	}

	private void finishMove() {
		this.swapActivePlayer();
		this.determineWinner();
		this.printGrid(this);
		this.updateStatus();
		this.monitorGameStatus();
	}

	@Override
	public void requestReceived(UserRequestEvent incomingEvent) {

		Coordinate requestedTileCoordinate = incomingEvent.getSource();
		UserRequest requestType = incomingEvent.getRequestType();

		if (this.isTileEmpty(requestedTileCoordinate) && this.gameStatus) {
			Marker marker = generateMarkerForPlayer(this.getActivePlayer());
			TileUpdateTask task = new TileUpdateTask(requestedTileCoordinate, requestType, marker);
			gui.updateGame(task);

			if (requestType.equals(UserRequest.MARK_TILE)) {
				this.getActivePlayer().markTile(requestedTileCoordinate);
				this.finishMove();
			}
		}
	}

	private void updateStatus() {
		if (this.gameStatus) {
			gui.setStatus(this.getActivePlayer().getName() + ", it's your turn!");
		} else {
			if (this.getWinner().isEmpty()) {
				gui.setStatus("Draw!");
			} else {
				gui.setStatus("Congratulations, " + this.getWinner().get().getName() + "! You've won that match!");
			}
		}
	}
}

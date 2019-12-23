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
	 * Generates a marker for a given player. Both marker type (X- or O-Marker) and
	 * color are retrieved from the player parameter.
	 * 
	 * @param player: requires a non-null player object
	 * @return marker object with corresponding symbol and requested color
	 */
	private static Marker generateMarkerForPlayer(Player player) {
		if (player.getSymbol() == Symbol.X) {
			return new XMarker(player.getColor());
		} else {
			return new OMarker(player.getColor());
		}
	}

	/**
	 * Starts the graphical user interface and starts a new game. Beforehand,
	 * players are defined according to user input.
	 * 
	 * @requires: game must be reseted beforehand or brand new
	 */
	public void run() {
		if (this.isInitialized()) {
			throw new IllegalCallerException("Game has not been reset before calling this method.");
		}
		this.gui = new GraphicalUserInterface();
		this.gui.requestAndSetPlayers(this);
		this.gui.setUserRequestEventListener(this);
		this.monitorGameStatus();
	}

	/**
	 * Monitors the current game status. If the game is still active and if a
	 * computer action is required, it will call a method, which lets the computer
	 * perform the desired action. If the game is not active, the user will be asked
	 * whether he wants a rematch and
	 */
	private void monitorGameStatus() {
		if (this.isGameActive()) {
			if (this.isComputerActionRequired()) {
				this.performComputerAction();
			}
		} else {
			if (gui.isRematchRequested()) {
				restartGame();
			}
		}
	}

	/**
	 * Stops the current game and starts a new game. Firstly, the current game is
	 * reset and the graphical user interface is closed. Then, a new game is started
	 * by calling this class's run method.
	 * 
	 * @requires: an active game, i.e. gui must be non-null
	 */
	private void restartGame() {
		this.resetGame();
		this.gui.close();
		this.run();
	}

	/**
	 * Returns whether the next move has to be performed by a computer. In
	 * particular, if the inherited activePlayer attribute is an instance of
	 * ComputerPlayer, a computer action is required.
	 * 
	 * @return true if computer action is required and false if not
	 */
	private boolean isComputerActionRequired() {
		return this.getActivePlayer() instanceof ComputerPlayer;
	}

	/**
	 * Performs an entire TicTacToe move from a ComputerPlayer. Firstly, it lets the
	 * player select its tile, then the tile is marked and the gui is updated.
	 * Finally, the finish move method is called.
	 * 
	 * @requires: this.isComputerActionRequired();
	 */
	private void performComputerAction() {
		gui.removeUserRequestEventListener();;
		if (!this.isComputerActionRequired()) {
			throw new IllegalCallerException("Current player is not an instance of ComputerPlayer");
		}
		ComputerPlayer currentPlayer = (ComputerPlayer) this.getActivePlayer();
		Coordinate c = currentPlayer.selectTileAutomatically();
		currentPlayer.markTile(c);
		Marker marker = generateMarkerForPlayer(currentPlayer);
		TileUpdateTask task = new TileUpdateTask(c, UserRequest.MARK_TILE, marker);
		gui.updateGame(task);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui.setUserRequestEventListener(this);
		this.finishMove();
	}

	/**
	 * Finishes a TicTacToe move: Swaps the active player, determines winner,
	 * updates game status in console and gui. Finally, the game monitoring routine
	 * is called.
	 * 
	 * @requires: A valid TicTacToe move (either by human or computer player) must
	 *            have been performed beforehand. Otherwise, swapping the active
	 *            player corrupts the game.
	 * @requires: Game must be active and gui non-null
	 */
	private void finishMove() {
		this.swapActivePlayer();
		this.determineWinner();
		this.printGrid();
		this.updateStatus();
		this.monitorGameStatus();
	}

	/**
	 * Receives UserRequestEvents from this class's gui object. Performs a move by a
	 * human player.
	 * 
	 * Firstly, a request type and a requested tile coordinate are retrieved from
	 * the incoming event. If the requested move is valid, it will be either preview
	 * or performed depending on the user request.
	 */
	@Override
	public void requestReceived(UserRequestEvent incomingEvent) {

		Coordinate requestedTileCoordinate = incomingEvent.getSource();
		UserRequest requestType = incomingEvent.getRequestType();

		if (this.isTileEmptyAt(requestedTileCoordinate) && this.gameStatus) {
			Marker marker = generateMarkerForPlayer(this.getActivePlayer());
			TileUpdateTask task = new TileUpdateTask(requestedTileCoordinate, requestType, marker);
			gui.updateGame(task);

			if (requestType.equals(UserRequest.MARK_TILE)) {
				this.getActivePlayer().markTile(requestedTileCoordinate);
				this.finishMove();
			}
		}
	}

	/**
	 * Updates this games status in console by either writing the final standings or
	 * the active player.
	 * 
	 * @requires: gui must be non-null and game active
	 */
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

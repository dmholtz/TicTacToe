package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import datatypes.Coordinate;
import datatypes.TileUpdateTask;
import logic.SimpleTicTacToeGame;
import model.ComputerPlayer;
import model.Player;
import model.Symbol;
import ui.events.UserRequest;
import ui.events.UserRequestEvent;
import ui.events.UserRequestEventListener;

/**
 * Provides a graphical user interface for TicTacToe games. This class handles
 * every output on UI elements and receives user input from UI elements.
 * 
 * @author dmholtz
 * @version v2.0
 *
 */
public class GraphicalUserInterface implements MouseListener {

	private TilePanel allTiles[][] = new TilePanel[3][3];
	private JFrame applicationWindow;

	private JPanel gamePanel;
	private JPanel controlPanel;

	private JLabel statusLabel;

	private Optional<UserRequestEventListener> userRequestEventListeners = Optional.empty();

	public GraphicalUserInterface() {
		this.setupWindow();
	}

	/**
	 * Initial setup of this instance's swing components. May be only called when
	 * generating new instance of GraphicalUserInterface
	 */
	private void setupWindow() {
		applicationWindow = new JFrame("TicTacToe Game by dmholtz");
		applicationWindow.setSize(700, 600);
		applicationWindow.setResizable(false);

		this.setupGamePanel();
		this.setupControlPanel();
		this.mergeAndAddPanels();

		this.addTilesToGamePanel();

		applicationWindow.setVisible(true);
		applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Setup for gamePanel. May only be called within setupWindow() method
	 */
	private void setupGamePanel() {
		GridLayout gl = new GridLayout(3, 3);
		gl.setHgap(7);
		gl.setVgap(7);
		this.gamePanel = new JPanel(gl);
		gamePanel.setBackground(Color.black);
	}

	/**
	 * Setup for controlPanel. May only be called within setupWindow() method
	 */
	private void setupControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setBackground(Color.white);
		statusLabel = new JLabel("Tic Tac Toe");
		statusLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		controlPanel.add(statusLabel);
	}

	/**
	 * Merges gamePanel and splitPanel into a splitpane and adds it to this class's
	 * applicationWindow attribute
	 * 
	 * @requires: setupGamePanel() and setupControlPanel must have been called
	 *            beforehand.
	 */
	private void mergeAndAddPanels() {
		JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);
		splitpane.setResizeWeight(0.05);
		splitpane.setRightComponent(this.gamePanel);
		splitpane.setLeftComponent(this.controlPanel);
		splitpane.setEnabled(false);

		applicationWindow.add(splitpane);
	}

	/**
	 * Adds all tiles to the game panel and attaches MouseListeners to each of them
	 * 
	 * @requires setupGamePanel() must have been called beforehand
	 */
	private void addTilesToGamePanel() {
		for (int i = 0; i < allTiles.length; i++) {
			for (int j = 0; j < allTiles[i].length; j++) {
				Coordinate c = new Coordinate(i, j);
				allTiles[i][j] = new TilePanel(c);
				allTiles[i][j].addMouseListener(this);
				this.gamePanel.add(allTiles[i][j]);
			}
		}
	}

	public void requestAndSetPlayers(SimpleTicTacToeGame game) {
		int response = JOptionPane.showOptionDialog(applicationWindow, "Select the first player:", "Game configurator",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Human", "Computer" },
				"Human");

		if (response == JOptionPane.YES_OPTION) {
			game.assignFirstPlayer(new Player("Player O", Symbol.O, Color.GREEN));
		} else {
			game.assignFirstPlayer(new ComputerPlayer("Computer O", Symbol.O, Color.GREEN));
		}

		response = JOptionPane.showOptionDialog(applicationWindow, "Select the second player:", "Game configurator",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Human", "Computer" },
				"Human");

		if (response == JOptionPane.YES_OPTION) {
			game.assignSecondPlayer(new Player("Player X", Symbol.X, Color.RED));
		} else {
			game.assignSecondPlayer(new ComputerPlayer("Computer X", Symbol.X, Color.RED));
		}
		game.setActivePlayer(game.getFirstPlayer());
	}

	public void setStatus(String text) {
		statusLabel.setText(text);
	}

	/**
	 * Updates the gui according to the TileUpdateTask parameter.
	 * 
	 * @param task: task parameter must have valid attributes in TicTacToe context
	 */
	public void updateGame(TileUpdateTask task) {
		TilePanel updateableTilePanel = allTiles[task.getCoordinate().getX()][task.getCoordinate().getY()];
		switch (task.getRequestType()) {
		case MARK_TILE:
			updateableTilePanel.paintMarker(task.getMarker());
			break;
		case PREVIEW_MARKER:
			updateableTilePanel.previewMarker(task.getMarker());
			break;
		case CLEAR_PREVIEW:
			updateableTilePanel.removePreview();
			break;
		default:
			break;
		}
	}

	/**
	 * Ask the user for a rematch and returns response as boolean
	 * 
	 * @return
	 */
	public boolean isRematchRequested() {
		int response = JOptionPane.showOptionDialog(null, "Do you want a rematch?", "TicTacToe Game",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Yes", "No" }, "No");
		if (response == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Closes the GUI and disposes the java swing frame.
	 */
	public void close() {
		applicationWindow.dispose();
	}

	/**
	 * Sets a UserRequestEventListener to this instance
	 * 
	 * @param listener: attached listener must implement the methods of the
	 *                  interface
	 */
	public synchronized void setUserRequestEventListener(UserRequestEventListener listener) {
		this.userRequestEventListeners = Optional.of(listener);
	}

	/**
	 * Removes the UserRequestEventListener from this instance by setting the
	 * optional attribute to empty.
	 */
	public synchronized void removeUserRequestEventListener() {
		this.userRequestEventListeners = Optional.empty();
	}

	/**
	 * If EventListener is attached to this instance, it will fire a new
	 * userRequestEvent with the given parameters.
	 * 
	 * @param source:     coordinate of the TilePanel which triggered the event
	 * @param requestType
	 */
	private synchronized void fireUserRequestEvent(Coordinate source, UserRequest requestType) {
		if (this.userRequestEventListeners.isPresent()) {
			UserRequestEvent requestEvent = new UserRequestEvent(source, requestType);
			this.userRequestEventListeners.get().requestReceived(requestEvent);
		}
	}

	/**
	 * Retrieves the coordinate object of the source of a MouseEvent e. In this
	 * class, mouse listeners may only added to TilePanel objects, so that the
	 * explicit type conversion is correct.
	 * 
	 * @requires: e.getSource() instanceof TilePanel;
	 * 
	 * @param e: incoming MouseEvent
	 * @return
	 */
	private Coordinate retrieveSourceTileCoordinate(MouseEvent e) {
		TilePanel sourceTilePanel = (TilePanel) e.getSource();
		return sourceTilePanel.getCoordinate();
	}

	/**
	 * mouseClicked: human player wants to mark a certain tile he has clicked on.
	 * The method fires an UserRequestEvent with the coordinate of the given tile
	 * and the request type MARK_TILE
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		this.fireUserRequestEvent(this.retrieveSourceTileCoordinate(e), UserRequest.MARK_TILE);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// no action needed
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// no action needed

	}

	/**
	 * mouseEntered: human player wants to preview a certain tile he points on with
	 * his mouse. The method fires an UserRequestEvent with the coordinate of the
	 * given tile and the request type PREVIEW_MARKER
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		this.fireUserRequestEvent(this.retrieveSourceTileCoordinate(e), UserRequest.PREVIEW_MARKER);

	}

	/**
	 * mouseExited: human player no longer wants to preview a certain tile he points
	 * on with his mouse. The method fires an UserRequestEvent with the coordinate
	 * of the given tile and the request type CLEAR_PREVIW
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		this.fireUserRequestEvent(this.retrieveSourceTileCoordinate(e), UserRequest.CLEAR_PREVIEW);
	}
}

package logic;

import java.awt.Color;
import java.util.Optional;

import model.Game;
import model.Grid;
import model.Player;
import model.Symbol;

/**
 * Extends game by providing basic TicTacToe game logic. SimpleTicTacToe games
 * have a activePlayer attribute, which must be equal to either of this class's
 * inherited player attributes
 * 
 * @author dmholtz
 *
 */
public class SimpleTicTacToeGame extends Game {

	private Player activePlayer;
	boolean gameStatus = true;
	private Optional<Player> winner = Optional.empty();

	public SimpleTicTacToeGame() {
	}

	protected void setDefaultPlayers() {
		Player player1 = new Player("Player O", Symbol.O, Color.GREEN);
		Player player2 = new Player("Player X", Symbol.X, Color.RED);
		this.assignFirstPlayer(player1);
		this.assignSecondPlayer(player2);
		activePlayer = (this.getFirstPlayer());
	}

	public void swapActivePlayer() {
		if (activePlayer.equals(this.getFirstPlayer())) {
			activePlayer = this.getSecondPlayer();
		} else {
			activePlayer = this.getFirstPlayer();
		}
	}

	/**
	 * @return the activePlayer
	 */
	public final Player getActivePlayer() {
		return activePlayer;
	}

	/**
	 * Sets the activePlayer. Active player must be equal to either of this class's
	 * player attributes
	 * 
	 * @requires: game is initalized
	 * @param activePlayer the activePlayer to set: must be equal to either of this
	 *                     class's player attributes
	 */
	public final void setActivePlayer(Player activePlayer) {
		if(!this.isInitialized())
		{
			throw new IllegalCallerException("Cannot set an active player to a non-initilized game");
		}
		if(!activePlayer.equals(this.getFirstPlayer()) && !activePlayer.equals(this.getSecondPlayer()))
		{
			throw new IllegalArgumentException("Given player is not one of the two players of this instance");
		}
		this.activePlayer = activePlayer;
	}

	private boolean hasPlayerWonARow(Player p, int row) {
		boolean returnVal = true;
		for (int x = 0; x < Grid.gridSize; x++) {
			returnVal &= (!this.getGrid().getTileFrom(x, row).isEmpty()
					&& this.getGrid().getTileFrom(x, row).getMarker().equals(p));
		}
		return returnVal;
	}

	private boolean hasPlayerWonAColumn(Player p, int column) {
		boolean returnVal = true;
		for (int y = 0; y < Grid.gridSize; y++) {
			returnVal &= (!this.getGrid().getTileFrom(column, y).isEmpty()
					&& this.getGrid().getTileFrom(column, y).getMarker().equals(p));
		}
		return returnVal;
	}

	private boolean hasPlayerWonFirstDiagonal(Player p) {
		boolean returnVal = true;
		for (int i = 0; i < Grid.gridSize; i++) {
			returnVal &= (!this.getGrid().getTileFrom(i, i).isEmpty()
					&& this.getGrid().getTileFrom(i, i).getMarker().equals(p));
		}
		return returnVal;
	}

	private boolean hasPlayerWonSecondDiagonal(Player p) {
		boolean returnVal = true;
		for (int i = 0; i < Grid.gridSize; i++) {
			returnVal &= (!this.getGrid().getTileFrom(Grid.gridSize - i - 1, i).isEmpty()
					&& this.getGrid().getTileFrom(Grid.gridSize - i - 1, i).getMarker().equals(p));
		}
		return returnVal;
	}

	private boolean isPlayerWinner(Player p) {
		for (int i = 0; i < Grid.gridSize; i++) {
			if (this.hasPlayerWonARow(p, i)) {
				return true;
			}
		}
		for (int i = 0; i < Grid.gridSize; i++) {
			if (this.hasPlayerWonAColumn(p, i)) {
				return true;
			}
		}
		if (this.hasPlayerWonFirstDiagonal(p)) {
			return true;
		}
		if (this.hasPlayerWonSecondDiagonal(p)) {
			return true;
		}
		return false;
	}

	public void determineWinner() {
		if (isPlayerWinner(this.getFirstPlayer())) {
			winner = Optional.of(this.getFirstPlayer());
			gameStatus = false;
		} else if (isPlayerWinner(this.getSecondPlayer())) {
			winner = Optional.of(this.getSecondPlayer());
			gameStatus = false;
		} else {
			winner = Optional.empty();
			if (this.getNumberOfMarkers() >= 9) {
				gameStatus = false;
			} else {
				gameStatus = true;
			}
		}
	}

	public boolean isGameActive() {
		return this.gameStatus;
	}

	public Optional<Player> getWinner() {
		return this.winner;
	}

	public void printGrid(Game g) {
		for (int x = 0; x < Grid.gridSize; x++) {
			for (int y = 0; y < Grid.gridSize; y++) {
				System.out.print(g.getGrid().getTileFrom(x, y).toString() + " ");
			}
			System.out.println();
		}

		if (this.getWinner().isPresent()) {
			System.out.println("And the winner iiiisss: " + this.getWinner().get().getName());
		} else if (!gameStatus) {
			System.out.println("Draw");
		}
		System.out.println();
	}

	/**
	 * Resets a SimpleTicTacToe Game by resetting all the inherited game attributes.
	 * Moreover, the gameStatus is set true, activePlayer will be a null-pointer and
	 * the optional winner attribute is empty
	 */
	@Override
	public void resetGame() {
		super.resetGame();
		gameStatus = true;
		activePlayer = null;
		this.winner = Optional.empty();

	}
}

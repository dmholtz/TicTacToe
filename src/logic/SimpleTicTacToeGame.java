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

	/**
	 * Assigns this game with default players.
	 */
	protected void setDefaultPlayers() {
		Player player1 = new Player("Player O", Symbol.O, Color.GREEN);
		Player player2 = new Player("Player X", Symbol.X, Color.RED);
		this.assignFirstPlayer(player1);
		this.assignSecondPlayer(player2);
		activePlayer = (this.getFirstPlayer());
	}

	/**
	 * Swaps the active player. If the activePlayer has not been set, this games
	 * first player is set as active Player.
	 * 
	 * @requires: this.isInitialized();
	 */
	public void swapActivePlayer() {
		if (!this.isInitialized()) {
			throw new IllegalCallerException(
					"Game has not been initialized with two players yet. Cannot swap the active player.");
		}
		if (activePlayer == null) {
			activePlayer = this.getFirstPlayer();
		} else if (activePlayer.equals(this.getFirstPlayer())) {
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
		if (!this.isInitialized()) {
			throw new IllegalCallerException("Cannot set an active player to a non-initilized game");
		}
		if (!activePlayer.equals(this.getFirstPlayer()) && !activePlayer.equals(this.getSecondPlayer())) {
			throw new IllegalArgumentException("Given player is not one of the two players of this instance");
		}
		this.activePlayer = activePlayer;
	}

	/**
	 * Checks whether a given player p has won a row. In TicTacToe games, a player
	 * wins a row if and only every tile in the corresponding row is marked with
	 * player p's symbol.
	 * 
	 * @param p:   player to check for.
	 * @param row: row number: must be within 0 and 2 (including)
	 * @return true if all tiles in that row are marked with player p's symbol and
	 *         otherwise false
	 */
	private boolean hasPlayerWonARow(Player p, int row) {
		boolean returnVal = true;
		for (int x = 0; x < Grid.gridSize; x++) {
			returnVal &= (!this.getGrid().getTileFrom(x, row).isEmpty()
					&& this.getGrid().getTileFrom(x, row).getMarker().equals(p));
		}
		return returnVal;
	}

	/**
	 * Checks whether a given player p has won a column. In TicTacToe games, a
	 * player wins a column if and only every tile in the corresponding column is
	 * marked with player p's symbol.
	 * 
	 * @param p:      player to check for.
	 * @param column: column number: must be within 0 and 2 (including)
	 * @return true if all tiles in that column are marked with player p's symbol
	 *         and otherwise false
	 */
	private boolean hasPlayerWonAColumn(Player p, int column) {
		boolean returnVal = true;
		for (int y = 0; y < Grid.gridSize; y++) {
			returnVal &= (!this.getGrid().getTileFrom(column, y).isEmpty()
					&& this.getGrid().getTileFrom(column, y).getMarker().equals(p));
		}
		return returnVal;
	}

	/**
	 * Checks whether a given player p has won the first diagonal, starting in the
	 * upper left hand corner and ending in the lower right hand corner. In
	 * TicTacToe games, a player wins a diagonal if and only every tile in that
	 * diagonal is marked with player p's symbol.
	 * 
	 * @param p: player to check for.
	 * @return true if all tiles in that diagonal are marked with player p's symbol
	 *         and otherwise false
	 */
	private boolean hasPlayerWonFirstDiagonal(Player p) {
		boolean returnVal = true;
		for (int i = 0; i < Grid.gridSize; i++) {
			returnVal &= (!this.getGrid().getTileFrom(i, i).isEmpty()
					&& this.getGrid().getTileFrom(i, i).getMarker().equals(p));
		}
		return returnVal;
	}

	/**
	 * Checks whether a given player p has won the second diagonal, starting in the
	 * lower left hand corner and ending in the upper right hand corner. In
	 * TicTacToe games, a player wins a diagonal if and only every tile in that
	 * diagonal is marked with player p's symbol.
	 * 
	 * @param p: player to check for.
	 * @return true if all tiles in that diagonal are marked with player p's symbol
	 *         and otherwise false
	 */
	private boolean hasPlayerWonSecondDiagonal(Player p) {
		boolean returnVal = true;
		for (int i = 0; i < Grid.gridSize; i++) {
			returnVal &= (!this.getGrid().getTileFrom(Grid.gridSize - i - 1, i).isEmpty()
					&& this.getGrid().getTileFrom(Grid.gridSize - i - 1, i).getMarker().equals(p));
		}
		return returnVal;
	}

	/**
	 * Checks whether a given player p has either won any of the row, the columns or
	 * the diagonals. Returns true, if player has won at least one of them. Return
	 * false otherwise.
	 * 
	 * @param p: Player to check for
	 * @return
	 */
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

	/**
	 * This method tries to determine a winner in the TicTacToe game. If a winner
	 * can be determined, the optional winner attribute of this class is set to the
	 * Player object that won the game. Moreover, the gameStatus is set false in
	 * that case. If there is no winner but the number of markers in the grid has
	 * reached 9 or above (i.e. no more moves are possible, the game status is set
	 * false and the winner attribute remains empty. In that case, the game has
	 * ended with draw.
	 * 
	 * Please note: This method must be called after every move in the TicTacToe
	 * game. Otherwise, a winner might be undetected. There may be only one winner
	 * at any time.
	 * 
	 * @requires: this.isInitalized();
	 */
	public void determineWinner() {
		if(!this.isInitialized())
		{
			throw new IllegalCallerException("Cannot determine winner of an uninitialized game.");
		}
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

	/**
	 * Returns true if game is still active and false otherwise
	 * @return
	 */
	public boolean isGameActive() {
		return this.gameStatus;
	}

	public Optional<Player> getWinner() {
		return this.winner;
	}

	/**
	 * Prints the TicTacToe grid on the console by calling the inherited method
	 * printGrid(). If a winner can be determined, it prints his name. If is finish
	 * with a draw, it prints "Draw"
	 * 
	 * Please note: The output might be deprecated, if this.determineWinner() has
	 * not been called beforehand
	 * 
	 * @requires: call this.determineWinner() before printing the grid.
	 */
	@Override
	public void printGrid() {
		super.printGrid();
		if (this.getWinner().isPresent()) {
			System.out.println("Winner: " + this.getWinner().get().getName());
		} else if (!gameStatus) {
			System.out.println("Draw!");
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

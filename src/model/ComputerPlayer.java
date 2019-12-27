package model;

import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;

import datatypes.Coordinate;
import datatypes.Selection;
import logic.SimpleTicTacToeGame;

/**
 * ComputerPlayers are special players, that can mark tiles by figuring out
 * their best strategy to win the game.
 * 
 * @author dmholtz
 * @version 2.1.8
 *
 */
public class ComputerPlayer extends Player {

	/**
	 * Copy of a SimpleTicTacToeGame, in order to emulate an existing game and try
	 * some moves.
	 */
	private SimpleTicTacToeGame copy;

	public ComputerPlayer(String name, Symbol symbol) {
		super(name, symbol);
	}

	public ComputerPlayer(String name, Symbol symbol, Color color) {
		super(name, symbol, color);
	}

	/**
	 * ComputerPlayers have the ability to removeMarkers from grid. This method
	 * method is needed to undo a move on this class's copy of a
	 * SimpleTicTacToeGame. However, one may not use this ability during a real
	 * game.
	 * 
	 * Please note: A ComputerPlayer can also remove Marker, which do not belong to
	 * him. This is required to emulate his opponents game moves.
	 * 
	 * Please note: Do not use this method on the assigned game or on other games
	 * but the 'copy' games. This violates Tic Tac Toe rules and may corrupt the
	 * game.
	 * 
	 * Preconditions: The addressed tile must be within a TicTacToe games boundries.
	 * Moreover, this instance must be assigned to a TicTacToeGame. The addressed
	 * tile must not be empty.
	 * 
	 * @ensures: this.game.get().getGrid().getTileFrom(x, y).isEmpty();
	 * 
	 * @param x-coordinate of the tile
	 * @param y-coordinate of the tile
	 */
	private void removeMarker(final int x, final int y) {
		assert x < 0 || x >= Grid.gridSize || y < 0 || y >= Grid.gridSize;
		assert !this.isGameAssigned();
		assert this.game.get().getGrid().getTileFrom(x, y).isEmpty(); // Semantic check

		// Unmark the tile
		this.game.get().getGrid().getTileFrom(x, y).removeMarker();
	}

	/**
	 * Overload of removeMarker. For detailed describtion and preconditions, please
	 * regard the method above.
	 * 
	 * @param c: Coordinate of the tile, at which a marker should be removed.
	 */
	private void removeMarker(Coordinate c) {
		this.removeMarker(c.getX(), c.getY());
	}

	/**
	 * Creates a copy of the existing game, in which this player is currently
	 * involved. Then, the 'best' tile is chosen and its coordinates are returned.
	 * 
	 * Note: 'best' refers to the optimal move, which is possible in the current
	 * game to either win a game or prevent an opponent from winning.
	 * 
	 * Please note: Calling this method only makes sense, if this instance is the
	 * active player in the current game and is allowed to make the next move.
	 * 
	 * @requires: this.game.isPresent(); In particular, the assigned game must be an
	 *            instance of SimpleTicTacToeGame.
	 * @requires: this instance is the active player in the game, he is assigned to
	 * 
	 * @return: Coordinates of the best tile
	 */
	public Coordinate selectTileAutomatically() {
		if (!(this.game.get() instanceof SimpleTicTacToeGame)) {
			throw new IllegalCallerException(
					"Must not call this method in that context. The assigned game must be a SimpleTicTacToeGame or inherit from it");
		}
		SimpleTicTacToeGame assignedGame = (SimpleTicTacToeGame) this.game.get();
		if (!assignedGame.getActivePlayer().equals(this)) {
			throw new IllegalCallerException("This player is not the active player and thus cannot select a tile");
		}
		this.createGameCopy();
		return this.choose().getCoordinate();
	}

	/**
	 * Creates a copy of the existing game, in which this player is currently
	 * involved. In particular, two virtual players are created and the Grid is
	 * copied.
	 * 
	 * @requires: this.game.isPresent();
	 */
	private void createGameCopy() {
		this.copy = new SimpleTicTacToeGame();
		this.createVirtualPlayers();
		this.copyGrid(this.game.get());
	}

	/**
	 * Creates two virtual players for the game copy. Player virtualMe has the same
	 * symbol as this instance and the opponent has the remaining symbol. Moreover,
	 * the virtualPlayer will be set as the active player.
	 */
	private void createVirtualPlayers() {
		ComputerPlayer virtualMe = new ComputerPlayer("me", this.getSymbol());
		ComputerPlayer virtualOpponent;
		if (this.getSymbol().equals(Symbol.O)) {
			virtualOpponent = new ComputerPlayer("opponent", Symbol.X);
		} else {
			virtualOpponent = new ComputerPlayer("opponent", Symbol.O);
		}
		this.copy.assignFirstPlayer(virtualMe);
		this.copy.assignSecondPlayer(virtualOpponent);
		this.copy.setActivePlayer(virtualMe);
	}

	/**
	 * Copies a grid by copying each marker from the existing grid into this class's
	 * copy game grid.
	 * 
	 * @param from: Game to copy grid from.
	 */
	private void copyGrid(Game from) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!from.getGrid().getTileFrom(i, j).isEmpty()) {
					if (from.getGrid().getTileFrom(i, j).getMarker().getSymbol().equals(this.getSymbol())) {
						this.copy.getGrid().getTileFrom(i, j).setMarker(this.copy.getFirstPlayer());
					} else {
						this.copy.getGrid().getTileFrom(i, j).setMarker(this.copy.getSecondPlayer());
					}
				}
			}
		}
	}

	/**
	 * Looks for empty tiles in the copy game grid and returns their coordinates in
	 * a list.
	 * 
	 * @requires: copy.isInitialized();
	 * @return list of coordinates with empty tiles in the copy game grid.
	 */
	private LinkedList<Coordinate> getOptions() {
		LinkedList<Coordinate> options = new LinkedList<Coordinate>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.copy.getGrid().getTileFrom(i, j).isEmpty()) {
					options.add(new Coordinate(i, j));
				}
			}
		}
		return options;
	}

	/**
	 * Lets a player choose a tile. Recursively, a binary tree is created with all
	 * game moves until the game ends.
	 * 
	 * Firstly, all potential moves for the current player are retrieved. Then, a
	 * loop iterates for all the moves: Iterating includes: 1) mark a tile, 2)
	 * evaluate the decision, 3) remove the marker. All evaluated decisions are
	 * stored in a list. Evaluating the decision includes: Check, whether the game
	 * has ended. If yes, a simple selection can be retrieved by looking at the
	 * outcome. If no, players swap and the second player makes a decision by
	 * recursively calling this method. The result of the recursive call will define
	 * the Selection object to append in the list.
	 * 
	 * Finally, the appropriate selection is returned. If the active player is
	 * virtualMe, he selects the best tile to win the game. If the active Player is
	 * virtualOpponent, the tile with the worst outcome for virtualMe is chosen.
	 * 
	 * @return
	 */
	private Selection choose() {
		LinkedList<Coordinate> potentialTileCoordinates = this.getOptions();
		LinkedList<Selection> selections = new LinkedList<Selection>();
		for (Coordinate potentialCoordinate : potentialTileCoordinates) {
			this.copy.getActivePlayer().markTile(potentialCoordinate);
			this.copy.determineWinner();
			if (this.copy.isGameActive()) {
				this.copy.swapActivePlayer();
				int result = this.choose().getResult();
				selections.add(new Selection(potentialCoordinate, result));
			} else {
				selections.add(this.generateSimpleSelection(potentialCoordinate));
			}
			ComputerPlayer helper = (ComputerPlayer) this.copy.getActivePlayer();
			helper.removeMarker(potentialCoordinate);
		}

		if (this.copy.getActivePlayer().equals(this.copy.getFirstPlayer())) {
			this.copy.swapActivePlayer();
			return this.assumeBestCase(selections);
		} else {
			this.copy.swapActivePlayer();
			return this.assumeWortsCase(selections);
		}
	}

	/**
	 * Generates selection objects for moves, which directly let a game end. The
	 * parameter represents the tile which is marked by the player. Then, this
	 * method evaluates, how the game has ended from the perspective of this
	 * ComputerPlayer (in fact from the perspective of his virtualMe Player).
	 * 
	 * @see this.generateVirtualPlayers();
	 * 
	 * @param selectionCoordinate: coordinate, which has been selected.
	 * @return
	 */
	private Selection generateSimpleSelection(Coordinate selectionCoordinate) {
		if (this.copy.getWinner().isEmpty()) {
			return new Selection(selectionCoordinate, 0);
		} else if (this.copy.getWinner().get().equals(this.copy.getFirstPlayer())) {
			return new Selection(selectionCoordinate, 1);
		} else {
			return new Selection(selectionCoordinate, -1);
		}
	}

	/**
	 * Assumes the worst case from the perspective of the virtualMe player.
	 * Therefore, a list of possible selections is first shuffled and then sorted.
	 * The first element is the element with the worst outcome in the game. This
	 * method is usually called to emulate the virtualOppenent players decisions.
	 * 
	 * @param selections list of selection objects.
	 * @return
	 */
	private Selection assumeWortsCase(final LinkedList<Selection> selections) {
		Collections.shuffle(selections); // fun way to demonstrate different results
		Collections.sort(selections); // , new SelectionComparator());
		return selections.peekFirst();
	}

	/**
	 * Assumes the best case from the perspective of the virtualMe player.
	 * Therefore, a list of possible selections is first shuffled and then sorted.
	 * The last element is the element with the best outcome in the game. This
	 * method is usually called to emulate the virtualMe players decisions.
	 * 
	 * @param selections list of selection objects.
	 * @return
	 */
	private Selection assumeBestCase(final LinkedList<Selection> selections) {
		Collections.shuffle(selections); // fun way to demonstrate different results
		Collections.sort(selections);
		return selections.peekLast();
	}
}

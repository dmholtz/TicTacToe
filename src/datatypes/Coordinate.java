package datatypes;

/**
 * Represents a coordinate tuple in the TicTacToe game. Coordinate tuples are
 * made of an x (column) and a y (row) coordinate. In TicTacToe context,
 * coordinates are between 0 and 2 (including). * 
 * 
 * Coordinate is immutable.
 * 
 * @author David Holtz
 * @version 1.0
 *
 */
public final class Coordinate {

	/**
	 * Variable to store the row number.
	 */
	private final int x;

	/**
	 * Variable to store the column number.
	 */
	private final int y;

	/**
	 * Create a new coordinate. In the Tic Tac Toe game, x and y coordiantes are
	 * between 0 and 2
	 * 
	 * (0,0) is in the upper left-hand corner. Going to the right increases the x
	 * coordinate whereas goint down increases the y-coordinate
	 * 
	 * @param x
	 * @param y
	 */
	public Coordinate(final int x, final int y) {
		if (x < 0 || y < 0 || x >= 3 || y >= 3) {
			throw new IllegalArgumentException("Coordinates exceed TicTacToe Grid");
		}
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The non-negative x coordinate of this coordinate tuple.
	 */
	public /* @ pure; helper @ */ int getX() {
		return x;
	}

	/**
	 * @return The non-negative y coordinate of this coordinate tuple.
	 */
	public /* @ pure; helper @ */ int getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Coordinate other = (Coordinate) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

//    /** 
//     * Convienience method to create a new location. The rows and column numbers of locations
//     * in the hamster territory start by (0,0) and increase only to
//     * positive integers. Larger rows are further down. Larger columns
//     * are further right.
//     * @param row The non-negative row count.
//     * @param column The non-negative column count.
//     * @return Returns the non-null location with the given position.
//     */    
//    public static Location from(final int row, final int column) {
//        return new Location(row, column);
//    }
//
//    /**
//     * This method creates a {@link Stream} of locations which enumerate the locations
//     * formed by the box with the from location as upper, left corner and the to location
//     * as the lower, right one. The box' locations are enumerated row by row.
//     * @param from Upper left corner of the box to be enumerated in the stream.
//     * @param to Lower right corner of the box to be enumerated in the stream.
//     * @return The stream which enumerates the locations in the specified box.
//     */
//    public static Stream<Location> getAllLocationsFromTo(final Location from, final Location to) {
//        final Stream<Stream<Location>> stream = IntStream.range(from.getRow(), to.getRow()+1).mapToObj(row -> IntStream.range(from.getColumn(), to.getColumn()+1).mapToObj(column -> Location.from(row, column)));
//        return stream.flatMap(s -> s);
//    }
//
//    /* (non-Javadoc)
//     * @see java.lang.Object#toString()
//     */
//    @Override
//    public String toString() {
//        return "Location [row=" + row + ", column=" + column + "]";
//    }
}
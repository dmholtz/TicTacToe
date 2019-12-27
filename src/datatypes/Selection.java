package datatypes;

public final class Selection implements Comparable<Selection> {

	private final Coordinate c;
	private final int result;

	public Selection(Coordinate c, int result) {
		this.c = c;
		this.result = result;
	}

	/**
	 * @return the c
	 */
	public final Coordinate getCoordinate() {
		return c;
	}

	/**
	 * @return the result
	 */
	public final int getResult() {
		return result;
	}

	/**
	 * Selection objects can be compared by comparing their result attribute. By
	 * Returns how this selection object compares to another given selection object.
	 * 
	 * @see comparable interface for invariants
	 * 
	 * @param: other Selection object, must be non-null
	 */
	@Override
	public int compareTo(Selection other) {
		Integer thisResult = this.getResult();
		Integer otherResult = other.getResult();
		return thisResult.compareTo(otherResult);
	}
}

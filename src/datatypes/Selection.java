package datatypes;

public final class Selection {
	
	private final Coordinate c;
	private final int result;
	
	public Selection(Coordinate c, int result)
	{
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

}

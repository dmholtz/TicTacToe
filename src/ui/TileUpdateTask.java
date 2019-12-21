package ui;

import datatypes.Coordinate;

public final class TileUpdateTask {
	
	private final Coordinate coordinate;
	private final UserRequest requestType;
	private final Marker marker;
	
	public TileUpdateTask(Coordinate coordinate, UserRequest requestType, Marker marker)
	{
		this.coordinate = coordinate;
		this.requestType = requestType;
		this.marker = marker;
	}

	/**
	 * @return the coordinate
	 */
	public final Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * @return the requestType
	 */
	public final UserRequest getRequestType() {
		return requestType;
	}

	/**
	 * @return the marker
	 */
	public final Marker getMarker() {
		return marker;
	}

}

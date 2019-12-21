package datatypes;

import ui.events.UserRequest;
import ui.markers.Marker;

/**
 * Data structure for tile update tasks. When updating a tile, a coordinate, a
 * requestType and a marker are required. 
 * 
 * TileUpdateTask is immutable.
 * 
 * @author David Holtz
 * @version 1.0
 *
 */
public final class TileUpdateTask {

	private final Coordinate coordinate;
	private final UserRequest requestType;
	private final Marker marker;

	/**
	 * Sets up a new TileUpdate task with the given parameters
	 * @param coordinate
	 * @param requestType
	 * @param marker
	 */
	public TileUpdateTask(Coordinate coordinate, UserRequest requestType, Marker marker) {
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

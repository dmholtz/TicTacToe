package ui.events;

import java.util.EventObject;

import datatypes.Coordinate;

/**
 * UserRequestEvent represents requests events generated from user input.
 * 
 * Instances of UserRequestEvents feature a source (as a Coordinate) an
 * requestType from the enum UserRequest.
 * 
 * UserRequestEvent is immutable.
 * 
 * @author David Holtz
 * @version 1.0
 *
 */
public class UserRequestEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final UserRequest requestType;

	/**
	 * 
	 * @param source: Source as Coordinate object
	 * @param requestType
	 */
	public UserRequestEvent(Coordinate source, final UserRequest requestType) {
		super(source);
		this.requestType = requestType;
	}

	public UserRequest getRequestType() {
		return this.requestType;
	}

	/**
	 * Gets the source of the event. Since the source may be only set as a
	 * Coordinate type, the query performs an explicit type conversion from Object
	 * to Coordinate.
	 */
	@Override
	public Coordinate getSource() {
		return (Coordinate) super.getSource();
	}

}

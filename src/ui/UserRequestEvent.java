package ui;

import java.util.EventObject;

import datatypes.Coordinate;

public class UserRequestEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	
	private final UserRequest requestType;

	public UserRequestEvent(Object source, final UserRequest requestType) {
		super(source);
		this.requestType = requestType;
	}
	
	public UserRequest getRequestType()
	{
		return this.requestType;
	}
	
	@Override
	public Coordinate getSource()
	{
		return (Coordinate) super.getSource();
	}
	

}

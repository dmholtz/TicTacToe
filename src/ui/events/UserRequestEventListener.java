package ui.events;

/**
 * Interface for UserRequestEvent handling
 * @author dmholtz
 * @version 1.0
 *
 */
public interface UserRequestEventListener {
	
	public void requestReceived(UserRequestEvent incomingRequestEvent);
}

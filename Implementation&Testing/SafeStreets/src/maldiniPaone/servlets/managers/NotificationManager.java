package maldiniPaone.servlets.managers;

import maldiniPaone.servlets.managers.interfaces.ManageNotification;
import maldiniPaone.utilities.beans.Location;

/**
 * Implements {@link ManageNotification} Singleton design pattern
 **/
public class NotificationManager implements ManageNotification {

	private NotificationManager() {

	}
	// ================================================================================
	// static fields
	// ================================================================================

	private static NotificationManager instance;

	@Override
	public void notify(Integer id, Location location) {
		/* TODO actual notification */
	}

	// ================================================================================
	// Instantiator
	// ================================================================================
	/**
	 * Gets the instance of the NotificationManager. Design pattern Singleton.
	 * 
	 * @return the instance
	 **/
	public static NotificationManager getInstance() {
		return (instance == null) ? instance = new NotificationManager() : instance;
	}

}

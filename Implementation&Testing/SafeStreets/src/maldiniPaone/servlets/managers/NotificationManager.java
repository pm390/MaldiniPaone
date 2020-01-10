package maldiniPaone.servlets.managers;

import java.util.List;

import maldiniPaone.servlets.managers.interfaces.ManageNotification;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.Authority;

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
		//TODO use this
		//List<Authority> temp=AuthorityLocation.getInstance().getAuthorities(location);
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

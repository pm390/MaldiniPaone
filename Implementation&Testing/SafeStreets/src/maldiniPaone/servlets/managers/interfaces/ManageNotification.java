package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.utilities.beans.Location;
/**
 * This interface defines the signatures of the methods to notify 
 * authorities 
 **/
public interface ManageNotification {

	/**
	 * Notify all authorities close to a given location about an assignment.
	 * 
	 * @param id       the id of the assignment which is being notified
	 * @param location the location of the assignment
	 **/
	public void notify(Integer id, Location location);
}

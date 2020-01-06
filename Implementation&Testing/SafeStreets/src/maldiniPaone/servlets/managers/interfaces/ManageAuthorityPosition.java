package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.utilities.beans.Location;

/**
 * This interface defines the signatures of the methods to save and update an
 * authority position
 **/
public interface ManageAuthorityPosition {

	/**
	 * Adds the position of an authority to the system
	 * 
	 * @param username : the username of the user added
	 * @param location : the coordinates added to the system
	 * @return true if the insertion is successful false otherwise
	 * @throws IllegalParameterException when parameters are not valid(empty or
	 *                                   null)
	 **/
	public boolean addPosition(String username, Location location) throws IllegalParameterException;

	/**
	 * Update the position of an authority saved on the system
	 * 
	 * @param username : the username of the user whose position must be updated
	 * @param location : the coordinates to be set after the update
	 * @return true if the update is successful false otherwise
	 * @throws IllegalParameterException when parameters are not valid(empty or
	 *                                   null)
	 **/
	public boolean updatePosition(String username, Location location) throws IllegalParameterException;

}

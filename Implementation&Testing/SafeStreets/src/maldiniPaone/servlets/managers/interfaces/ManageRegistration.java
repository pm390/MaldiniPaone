package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;

/**
 * This interface defines the signatures of the methods to manager registration
 **/
public interface ManageRegistration {
	/**
	 * registers Citizen.
	 * 
	 * @param username : the username of the citizen to be added
	 * @param password : the password of the citizen to be added
	 * @param email    : the email of the citizen to be added
	 * @return true if the creation is successful false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean registerCitizen(String username, String password, String email)
			throws ServerSideDatabaseException, IllegalParameterException;

	
}

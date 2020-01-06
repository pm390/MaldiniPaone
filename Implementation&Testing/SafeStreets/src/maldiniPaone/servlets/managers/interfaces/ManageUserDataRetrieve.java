package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.CityHall;

/**
 * This interface defines the signatures of the methods to get useful
 * informations about users
 **/
public interface ManageUserDataRetrieve {

	/**
	 * Finds the email address of a user
	 * 
	 * @param username : the user name of the user whose email must be retrieved
	 * @return String : the email of the user . null if no user exists with the
	 *         given username
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public String findEmailByUsername(String username) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Finds the username of a user
	 * 
	 * @param email : the email address of the user whose username must be retrieved
	 * @return String : the username of the user . null if no user exists with the
	 *         given email
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public String findUsernameByEmail(String email) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the cityhall where a municipality works
	 * 
	 * @param username: the username of the municipality whose cityhall is being
	 *                  searched
	 * @return CityHall
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public CityHall getCityHall(String username) throws ServerSideDatabaseException, IllegalParameterException;

}

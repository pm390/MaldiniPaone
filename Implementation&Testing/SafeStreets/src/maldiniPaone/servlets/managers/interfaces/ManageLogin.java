package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.UserType;

/**
 * This interface defines the signature of the method to Login
 **/
public interface ManageLogin {
	/**
	 * checks user credentials and returns the type of the user associated to it
	 * 
	 * @param username : string containing the user name to check
	 * @param password : string containing the password of the user
	 * @return UserType the type of the user null if no user matches
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public UserType login(String username, String password)
			throws ServerSideDatabaseException, IllegalParameterException;
}

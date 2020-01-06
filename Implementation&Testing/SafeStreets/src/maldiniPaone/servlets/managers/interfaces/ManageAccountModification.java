package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.UserType;

/**
 * This interface defines the signatures of the methods to modify users and
 * delete users
 **/
public interface ManageAccountModification {

	/**
	 * modifies credentials and email of a user
	 * 
	 * @param oldUsername : the old username of the user
	 * @param oldPassword : the old password of the user
	 * @param newUsername : the new username to be set
	 * @param newPassword : the new password to be set
	 * @param newEmail    : the new email address to be set(null if no change)
	 * @param user        : the type of use to be modified
	 * @return true if the modification is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean modifyUserCredentials(String oldUsername, String oldPassword, String newUsername, String newPassword,
			String newEmail, UserType user) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Removes a user given its username and password
	 * 
	 * @param username : the user name of the user to be deleted
	 * @param password : the password of the user to be deleted
	 * @param user     : the user type of the user to be deleted
	 * @return boolean: true if the deletion is successful, false if fails
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean removeUser(String username, String password, UserType user)
			throws ServerSideDatabaseException, IllegalParameterException;

}

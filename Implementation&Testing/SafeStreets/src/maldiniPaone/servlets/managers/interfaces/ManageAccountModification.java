package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.databaseConnection.databaseExceptions.InvalidParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.UserType;

public interface ManageAccountModification 
{
	
	/**
	 *	modifies credentials and email of a user 
	 * 	@param oldUsername : the old username of the user 
	 * 	@param oldPassword : the old password of the user
	 * 	@param newUsername : the new username to be set
	 *	@param newPassword : the new password to be set
	 *	@param newEmail : the new email address to be set(null if no change)
	 *  @throws ServerSideDatabaseException  when the database can't be found
	 *  @throws InvalidParameterException  when parameters are not valid(empty or null) 
	 **/
	public boolean modifyUserCredentials(String oldUsername,String oldPassword,
			String newUsername,String newPassword,String newEmail,UserType user)
					throws ServerSideDatabaseException, InvalidParameterException;
	
}

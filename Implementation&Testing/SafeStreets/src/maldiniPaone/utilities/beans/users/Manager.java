package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;
/**
 * Extends {@link maldiniPaone.utilities.beans.users.User}.
 * Represents a system manager
 **/
public class Manager extends User 
{

	//================================================================================
    // userType
    //================================================================================
	@Override
	public UserType getUserType() 
	{
		return UserType.Manager;
	}

}

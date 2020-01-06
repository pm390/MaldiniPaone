package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;
/**
 * Extends {@link maldiniPaone.utilities.beans.users.User}.
 * Represents a Citizen
 **/
public class Citizen extends User{
	
	
	//================================================================================
    // userType
    //================================================================================
	@Override
	public UserType getUserType() 
	{
		return UserType.Citizen;
	}
	
}

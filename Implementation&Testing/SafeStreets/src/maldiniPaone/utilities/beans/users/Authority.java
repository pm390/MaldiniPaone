package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;
/**
 * Extends {@link maldiniPaone.utilities.beans.users.User}.
 * Represents an authority
 **/
public class Authority extends User 
{
	

	//================================================================================
    // userType
    //================================================================================
	@Override
	public UserType getUserType()
	{
		return UserType.Authority;
	}
}

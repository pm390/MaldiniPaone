package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;

public class Authority extends User 
{
	
	
	
	//TODO add code here
	//================================================================================
    // userType
    //================================================================================
	@Override
	public UserType getUserType()
	{
		return UserType.Authority;
	}
}

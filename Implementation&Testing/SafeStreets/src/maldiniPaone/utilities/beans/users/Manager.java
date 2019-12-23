package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;

public class Manager extends User 
{
//TODO add code here
	//================================================================================
    // userType
    //================================================================================
	@Override
	public UserType getUserType() 
	{
		return UserType.Manager;
	}

}

package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;

public class Citizen extends User{
	
	public Citizen()
	{}
	//================================================================================
    // userType
    //================================================================================
	@Override
	public UserType getUserType() 
	{
		
		return UserType.Citizen;
	}
	
}

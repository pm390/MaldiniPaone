package maldiniPaone.utilities.beans.users;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.utilities.UserType;

public class UserFactory 
{
	//TODO javadoc here
	/**
	 * 
	 * 
	 * 
	 * @throws IllegalParameterException if the userType is not null but has an unexpected value
	 **/
	public static User buildUserBase(String username,String password,UserType user) throws IllegalParameterException
	{
		User result=null;
		if(user==null)//if no usertype there is no user to build so return null
		{
			return result;
		}
		switch(user)//depending on user type build the corresponding Concrete object
		{
			case Citizen: result=new Citizen();break;
			case Authority:result=new Authority();break;
			case Municipality:result=new Municipality();break;
			case Manager:result=new Manager();break;
			default: throw new IllegalParameterException();
		}
		//set the basic needed informations needed for a user
		result.setUsername(username); 
		result.setPassword(password);
		return result;
	}
}

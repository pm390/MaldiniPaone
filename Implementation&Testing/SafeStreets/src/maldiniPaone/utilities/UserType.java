package maldiniPaone.utilities;
/**
 * this enumeration contains the different type of user that can 
 * interact with the system
 * */
public enum UserType {
	Citizen,
	Authority,
	Municipality,
	Manager;
	
	
	//================================================================================
    // useful method
    //================================================================================
	/**
	 * Converts a string in a user type 
	 * @param type : user type in string form , it gets converted to lowerCase
	 * @return UserType: returns the user type corresponding to the string it receives as parameter
	 * 					 returns null if no matching user type exists
	 * @note this method doesn't check if string is null: use with caution
	 **/
	public UserType fromString(String type)
	{
		switch(type.toLowerCase())
		{
		case "citizen":return Citizen;
		case "authority": return Authority;
		case "municipality": return Municipality;
		case "manager" : return Manager;
		default: return null ;
		}
	}
	
}

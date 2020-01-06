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
    // useful methods
    //================================================================================
	/**
	 * Converts a string in a user type 
	 * This method doesn't check if string is null: use with caution
	 * @param type : user type in string form , it gets converted to lowerCase
	 * @return UserType: returns the user type corresponding to the string it receives as parameter
	 * 					 returns null if no matching user type exists
	 * 
	 **/
	public static UserType fromString(String type)
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
	/**
	 * Converts a user type to a string
	 * @return String: returns the string corresponding to the user type on which is called
	 **/
	public String toString()
	{
		switch(this)
		{
		case Citizen : return "citizen";
		case Authority : return "authority";
		case Municipality : return "municipality";
		case Manager : return "manager";
		default: return ""; // needed to avoid compilation error but can't be reached
		}
	}
	
}

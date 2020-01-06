package maldiniPaone.utilities;
/** 
 * Enumeration representing the possible types of violation which the system can use to generate
 * suggestions
 **/
public enum ViolationType 
{
	DoubleParking,
	ReservedParking,
	ParkingWithForbiddingSignals;
	//TODO add other type of violations
	

	//================================================================================
    // useful methods
    //================================================================================
	/**
	 * Converts a string in a ViolationType
	 * This method doesn't check if string is null: use with caution.
	 * @param state : violation type in string form , it gets converted to lowerCase
	 * @return State: returns the violation type corresponding to the string it receives as parameter
	 * 					 returns null if no matching State exists
	 **/
	public static ViolationType fromString(String state)
	{
		switch(state.toLowerCase())
		{

		case "double":return DoubleParking;
		case "reserved":return ReservedParking;
		case "forbidden":return ParkingWithForbiddingSignals;

		default:return null;
		}
	}
	
	/**
	 * Converts a Violationtype into a string
	 * @return String: returns the string corresponding to the ViolationType on which is called
	 **/
	public String toString()
	{
		switch(this)
		{
		case DoubleParking:return "double";
		case ReservedParking:return "reserved";
		case ParkingWithForbiddingSignals:return "forbidden";
		default:return null;
		}
	}
	
}

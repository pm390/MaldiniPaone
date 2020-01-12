package maldiniPaone.utilities;

/**
 * this enumeration contains the different possible states of assignments
 */
public enum State {
	Pending,
	Accepted,
	CorrectlyFinished,
	AlreadySolved,
	FalseReport;
	

	//================================================================================
    // useful methods
    //================================================================================
	/**
	 * Converts a string in a State
	 * This method doesn't check if string is null: use with caution
	 * @param state : state in string form , it gets converted to lowerCase
	 * @return State: returns the State corresponding to the string it receives as parameter
	 * 					 returns null if no matching State exists
	 * 
	 **/
	public static State fromString(String state)
	{
		switch(state.toLowerCase())
		{
		case "created":return Pending;
		case "accepted":return Accepted;
		case "finished":return CorrectlyFinished;
		case "solved":return AlreadySolved;
		case "false" :return FalseReport;
		default:return null;
		}
	}
	/**
	 * Converts a State into a string
	 * @return String: returns the string corresponding to the State on which is called
	 **/
	public String toString()
	{
		switch(this)
		{
		case Pending:return "created";
		case Accepted:return "accepted";
		case CorrectlyFinished:return "finished";
		case AlreadySolved:return "solved";
		case FalseReport:return "false";
		default:return null;
		}
	}
	
}

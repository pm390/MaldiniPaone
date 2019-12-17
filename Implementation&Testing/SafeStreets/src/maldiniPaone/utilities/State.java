package maldiniPaone.utilities;

/**
 *
 */
public enum State {
	Pending,
	Accepted,
	CorrectlyFinished,
	AlreadySolved,
	FalseReport;
	
	public static State fromString(String state)
	{
		switch(state.toLowerCase())
		{
		case "pending":return Pending;
		case "accepted":return Accepted;
		case "finished":return CorrectlyFinished;
		case "solved":return AlreadySolved;
		case "false" :return FalseReport;
		default:return null;
		}
	}
	
	public String toString()
	{
		switch(this)
		{
		case Pending:return "pending";
		case Accepted:return "accepted";
		case CorrectlyFinished:return "finished";
		case AlreadySolved:return "solved";
		case FalseReport:return "false";
		default:return null;
		}
	}
	
}

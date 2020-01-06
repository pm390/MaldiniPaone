package maldiniPaone.databaseConnection.databaseExceptions;

/**
 * Extends {@link Exception}. This exception is thrown when 
 * database connection methods are called using not valid parameters
 * */
public class IllegalParameterException extends Exception {

	//================================================================================
    // constructors
    //================================================================================
	public IllegalParameterException(Exception e) {
		super(e);
	}

	public IllegalParameterException() {
		super();
	}
	
	@Override
	public void printStackTrace()
	{
		//can add other debug functionalities here
		super.printStackTrace();
	}
	

	private static final long serialVersionUID = 558315124589746276L;
}

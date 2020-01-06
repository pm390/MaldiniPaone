package maldiniPaone.databaseConnection.databaseExceptions;

/**
 * Extends {@link Exception}. This exception is thrown when the Connection Pool is not able to
 * instantiate connections with the database
 * */

public class DatabaseNotFoundException extends Exception {
	
	//================================================================================
    // constructors
    //================================================================================
	public DatabaseNotFoundException(Exception e) {
		super(e);
	}

	public DatabaseNotFoundException() {
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

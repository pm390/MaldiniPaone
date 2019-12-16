package maldiniPaone.databaseConnection.databaseExceptions;

/**
 * Derived from Exception interface. This exception is thrown when the Connection Pool is not able to
 * instantiate connections with the database
 * */

public class DatabaseNotFoundException extends Exception {
	
	public DatabaseNotFoundException(Exception e) {
		super(e);
		
	}

	public DatabaseNotFoundException() {
		super();
	}

	

	private static final long serialVersionUID = 558315124589746276L;
	
}

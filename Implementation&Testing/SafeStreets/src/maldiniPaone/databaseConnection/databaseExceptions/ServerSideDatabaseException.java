package maldiniPaone.databaseConnection.databaseExceptions;

/**
 * Extends {@link Exception}. This exception is thrown when the class
 * {@link maldiniPaone.databaseConnection.ConnectionPool} is not able to
 * instantiate connections with the database
 */
public class ServerSideDatabaseException extends Exception {

	// ================================================================================
	// variables
	// ================================================================================
	private static final long serialVersionUID = 1L; // auto generated for serialization
	private String note = null;

	// ================================================================================
	// constructors
	// ================================================================================
	public ServerSideDatabaseException(Exception e) {
		super(e);
	}

	public ServerSideDatabaseException(Exception e, String message) {
		super(e);
		note = message;
	}

	public ServerSideDatabaseException() {
		super();
	}

	@Override
	public void printStackTrace() {
		System.err.println("can add additional information here");
		if (note != null)
			System.err.println(note);
		// can add other debug functionalities here
		super.printStackTrace();
	}

	/**
	 * @return the note added to the exception
	 **/
	public String getMessage() {
		return note;
	}
}

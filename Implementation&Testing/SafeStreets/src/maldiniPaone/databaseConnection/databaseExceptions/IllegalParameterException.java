package maldiniPaone.databaseConnection.databaseExceptions;


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
		System.err.println("can add additional information here");
		//can add other debug functionalities here
		super.printStackTrace();
	}
	

	private static final long serialVersionUID = 558315124589746276L;
}

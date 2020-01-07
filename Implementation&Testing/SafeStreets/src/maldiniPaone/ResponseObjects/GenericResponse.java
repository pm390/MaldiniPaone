package maldiniPaone.ResponseObjects;

public  class GenericResponse {
	// ================================================================================
	// Variables
	// ================================================================================
	private boolean error = false;
	private int errorCode;
	
	private String errorMessage;

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
		 * Unsuccessful action response creation
		 * 
		 * @param code error code
		 * @param errorMessage error message
		 **/
		public GenericResponse(int code,String errorMessage) {
			setError(true);
			setErrorCode(code);
			setErrorMessage(errorMessage);
		}

	/**
		 * Successful action response creation
		 *
		 **/
		public GenericResponse() 
		{
			
		}

	// ================================================================================
	// Getters
	// ================================================================================
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	

	// ================================================================================
	// Setters
	// ================================================================================
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	
}

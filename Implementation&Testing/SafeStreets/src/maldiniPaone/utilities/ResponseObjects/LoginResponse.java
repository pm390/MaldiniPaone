package maldiniPaone.utilities.ResponseObjects;

public class LoginResponse extends GenericResponse{

	private String userType;
	public LoginResponse(int code, String errorMessage) {
		super(code, errorMessage);
	}
	public LoginResponse(String type) {
		super();
		userType=type;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

}

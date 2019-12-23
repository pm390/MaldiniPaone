package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;

public abstract class User 
{
	//================================================================================
    // static variables
    //================================================================================
	private String username;
	private String password;
	private String email;
	
	//================================================================================
    // getters
    //================================================================================
	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}
	//================================================================================
    // setters
    //================================================================================
	/**
	 * @param username the username to set
	 */
	public final void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param email the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}
	//================================================================================
    // userType
    //================================================================================
	public abstract UserType getUserType();

}

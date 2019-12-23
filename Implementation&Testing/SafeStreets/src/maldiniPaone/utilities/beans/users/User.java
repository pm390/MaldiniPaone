package maldiniPaone.utilities.beans.users;

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
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	//================================================================================
    // setters
    //================================================================================
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}

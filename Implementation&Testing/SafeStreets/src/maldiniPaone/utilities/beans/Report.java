package maldiniPaone.utilities.beans;

import java.sql.Timestamp;
//TODO add some javadoc comment for bean
public class Report  {
	//================================================================================
    // Variables
    //================================================================================
	private Location location;
	private String username;
	private String[] photoNames;
	private Timestamp date;
	
	//================================================================================
    // Empty Constructor
    //================================================================================
	public Report() {}

	//================================================================================
    // Getters
    //================================================================================
	
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the user name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the photoNames
	 */
	public String[] getPhotoNames() {
		return photoNames;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}
	//================================================================================
    // Setters
    //================================================================================
	/**
	 * @param location : the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @param username : the user name to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param photoNames : the photo names to set
	 */
	public void setPhotoNames(String[] photoNames) {
		this.photoNames = photoNames;
	}

	/**
	 * @param date : the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
	
}

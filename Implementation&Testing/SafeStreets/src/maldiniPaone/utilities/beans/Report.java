package maldiniPaone.utilities.beans;

import java.sql.Timestamp;
import java.util.List;
//TODO add some javadoc comment for bean
public class Report  {
	//================================================================================
    // Variables
    //================================================================================
	private Location location;
	private String username;
	private List<Photo> photoNames;
	private String note;
	private String licensePlate;
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
	public List<Photo> getPhotoNames() {
		return photoNames;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}
	
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @return the licensePlate
	 */
	public String getLicensePlate() {
		return licensePlate;
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
	 * @param images : the photo names to set
	 */
	public void setPhotos(List<Photo> images) {
		this.photoNames = images;
	}

	/**
	 * @param date : the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param licensePlate the licensePlate to set
	 */
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	
	
	
}

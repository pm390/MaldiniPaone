package maldiniPaone.utilities.beans;

import java.sql.Timestamp;
/**
 * @deprecated 
 * Bean representing Accident data.
 * This provides the basic idea of how an accident should be represented
 **/
public class Accident {
	//================================================================================
    // Variables
    //================================================================================
	private Location location;
	private Timestamp date;
	
	
	//================================================================================
    // Empty Constructor
    //================================================================================
	public Accident() {}
	
	
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
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}
	
	//================================================================================
    // Setters
    //================================================================================

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
}

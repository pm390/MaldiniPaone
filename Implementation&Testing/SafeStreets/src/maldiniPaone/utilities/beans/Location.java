package maldiniPaone.utilities.beans;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;

/**
 * Class representing a couple of coordinates longitude and latitude
 */
public class Location {
	// ================================================================================
	// Variables
	// ================================================================================
	private Float longitude;
	private Float latitude;

	// ================================================================================
	// Empty Constructor
	// ================================================================================
	public Location() {
	}

	// ================================================================================
	// Getters
	// ================================================================================
	/**
	 * @return the longitude
	 */
	public Float getLongitude() {
		return longitude;
	}

	/**
	 * @return the latitude
	 */
	public Float getLatitude() {
		return latitude;
	}

	// ================================================================================
	// Setters
	// ================================================================================
	/**
	 * @param longitude : the longitude to set
	 * @throws IllegalParameterException  if the longitude value is not valid
	 */
	public void setLongitude(Float longitude) throws IllegalParameterException {
		if(Math.abs(longitude)<=180)
		{
			this.longitude = longitude;
		}
		else 
		{
			throw new IllegalParameterException();
		}
	}

	/**
	 * @param latitude : the latitude to set
	 * @throws IllegalParameterException if the values are not valid
	 */
	public void setLatitude(Float latitude) throws IllegalParameterException {
		if(Math.abs(latitude)<=180)
		{
			this.latitude = latitude;
		}
		else 
		{
			throw new IllegalParameterException();
		}
		
	}

}

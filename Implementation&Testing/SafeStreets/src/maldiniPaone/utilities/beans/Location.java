package maldiniPaone.utilities.beans;

//TODO add some javadoc comment for bean
/**
 * Class representing a couple of coordinates longitude and latitude 
 */
public class Location {
	//================================================================================
    // Variables
    //================================================================================
	private Float longitude;
	private Float latitude;

	//================================================================================
    // Empty Constructor
    //================================================================================
	public Location() {	}
	
	//================================================================================
    // Getters
    //================================================================================
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
	//================================================================================
    // Setters
    //================================================================================
	/**
	 * @param longitude :  the longitude to set
	 */
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param latitude : the latitude to set
	 */
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	
	
	
	

	
	
}

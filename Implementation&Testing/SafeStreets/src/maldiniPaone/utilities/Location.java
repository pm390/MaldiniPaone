package maldiniPaone.utilities;

/**
 * Class representing a couple of coordinates longitude and latitude.
 * @note After creation the object is read-only
 */
public class Location {
	//================================================================================
    // variables
    //================================================================================
	private Float longitude;
	private Float latitude;

	//================================================================================
    // Constructor
    //================================================================================
	public Location(Float lon,Float lat)
	{
		longitude=lon;
		latitude=lat;
	}
	//================================================================================
    // getters
    //================================================================================
	public Float getLongitude() {
		return longitude;
	}
	
	public Float getLatitude() {
		return latitude;
	}

	
	
}

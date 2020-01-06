package maldiniPaone.utilities.beans;

/**
 * Bean representing city hall data.
 **/
public class CityHall {

	// ================================================================================
	// Variables
	// ================================================================================
	private String name;
	private String province;
	private String region;
	private Location location;

	// ================================================================================
	// Empty Constructor
	// ================================================================================
	public CityHall() {
	}

	// ================================================================================
	// Getters
	// ================================================================================

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	// ================================================================================
	// Setters
	// ================================================================================
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

}

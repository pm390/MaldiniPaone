package maldiniPaone.utilities.beans;

public class District {
	//================================================================================
    // Variables
    //================================================================================
	private String name;
	private String province;
	private Location locationTopLeft;
	private Location locationBottomRight;
	//================================================================================
    // Empty Constructor
    //================================================================================
	public District()
	{}
	//================================================================================
    // Getters
    //================================================================================
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
	 * @return the locationTopLeft
	 */
	public Location getLocationTopLeft() {
		return locationTopLeft;
	}
	/**
	 * @return the locationBottomRight
	 */
	public Location getLocationBottomRight() {
		return locationBottomRight;
	}
	//================================================================================
    // Setters
    //================================================================================
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
	 * @param locationTopLeft the locationTopLeft to set
	 */
	public void setLocationTopLeft(Location locationTopLeft) {
		this.locationTopLeft = locationTopLeft;
	}
	/**
	 * @param locationBottomRight the locationBottomRight to set
	 */
	public void setLocationBottomRight(Location locationBottomRight) {
		this.locationBottomRight = locationBottomRight;
	}
}

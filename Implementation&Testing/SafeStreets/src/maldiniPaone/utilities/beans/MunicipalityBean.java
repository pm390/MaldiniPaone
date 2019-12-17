package maldiniPaone.utilities.beans;

//TODO add some javadoc comment for bean
public class MunicipalityBean {
	//================================================================================
    // Variables
    //================================================================================
	private String name;
	private Location location;
	
	
	//================================================================================
    // Empty Constructor
    //================================================================================
	public MunicipalityBean() {}

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
	 * @return the location
	 */
	public Location getLocation() {
		return location;
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
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
}

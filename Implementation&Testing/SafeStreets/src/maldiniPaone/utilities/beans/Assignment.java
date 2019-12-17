package maldiniPaone.utilities.beans;

import maldiniPaone.utilities.State;
//TODO add some javadoc comment for bean
public class Assignment{

	//================================================================================
    // Variables
    //================================================================================
	private Location location;
	private String[] photoNames;
	private String[] descriptions;
	private State state;
	
	//================================================================================
    // Empty Constructor
    //================================================================================
	public Assignment() {}
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
	 * @return the photoNames
	 */
	public String[] getPhotoNames() {
		return photoNames;
	}

	/**
	 * @return the descriptions
	 */
	public String[] getDescriptions() {
		return descriptions;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
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
	 * @param photoNames the photoNames to set
	 */
	public void setPhotoNames(String[] photoNames) {
		this.photoNames = photoNames;
	}

	/**
	 * @param descriptions the descriptions to set
	 */
	public void setDescriptions(String[] descriptions) {
		this.descriptions = descriptions;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}
}

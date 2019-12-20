package maldiniPaone.utilities.beans;

import java.util.List;

import maldiniPaone.utilities.State;

public class CityHall {

	//================================================================================
    // Variables
    //================================================================================
	private String name;
	private String province;

	
	
	//================================================================================
    // Empty Constructor
    //================================================================================
	public CityHall() {}
	
	
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
}

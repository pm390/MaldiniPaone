package maldiniPaone.utilities.beans.users;

import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;

public class Municipality extends User{
	//================================================================================
    // Variables
    //================================================================================
	private Location location;
	private CityHall cityHall;
	

	//================================================================================
    // Empty Constructor
    //================================================================================
	public Municipality() {}

	//================================================================================
    // Getters
    //================================================================================
	

	/**
	 * @return the cityhall
	 */
	public CityHall getCityhall() {
		return cityHall;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() 
	{
		return location;
	}

	//================================================================================
    // Setters
    //================================================================================
	

	/**
	 * @param cityhall the cityhall to set
	 */
	public void setCityhall(CityHall cityhall) 
	{
		this.cityHall = cityhall;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) 
	{
		this.location = location;
	}
	//================================================================================
    // userType
    //================================================================================
	@Override
	public UserType getUserType() 
	{
		return UserType.Municipality;
	}	
}

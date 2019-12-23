package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;

public interface ManageRegistration 
{
	/**
	 *registers Citizen. 
	 * @param username : the username of the citizen to be added
	 * @param password : the password of the citizen to be added
	 * @param email : the email of the citizen to be added
	 * @return true if the creation is successful
	 * 			false otherwise
	 * @throws ServerSideDatabaseException  when the database can't be found
	 * @throws IllegalParameterException  when parameters are not valid(empty or null) 
	 **/
	public boolean registerCitizen(String username,String password,String email)
			throws ServerSideDatabaseException, IllegalParameterException;
	/**
	 *registers Municipality by system manager. 
	 * @param username : the username of the municipality to be added
	 * @param password : the password of the municipality to be added
	 * @param email : the email of the municipality to be added
	 * @param cityHallName : the name of the cityHall where the municipality works
	 * @param cityHallProvince : the province in which the cityhall is located
	 * @param region : the name of the region in which the cityhall is located
	 * @param location : the coordinates of the cityhall
	 * @return true if the creation is successful
	 * 			false otherwise
	 * @throws ServerSideDatabaseException  when the database can't be found
	 * @throws IllegalParameterException  when parameters are not valid(empty or null) 
	 **/
	public boolean registerMunicipalityByManager(String username,String password,
			String email,String cityHallName,String cityHallProvince,String region,Location location)
					throws ServerSideDatabaseException, IllegalParameterException;
}

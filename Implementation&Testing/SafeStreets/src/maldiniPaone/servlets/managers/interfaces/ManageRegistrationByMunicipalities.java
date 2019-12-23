package maldiniPaone.servlets.managers.interfaces;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;

public interface ManageRegistrationByMunicipalities {
	/**
	 *registers Municipality by another municipality in the same cityHall. 
	 * @param username : the username of the municipality to be added
	 * @param password : the password of the municipality to be added
	 * @param email : the email of the municipality to be added
	 * @param creator : the username of the municipality creating the other municipality
	 * @param cityHallName : the name of the city hall of the creator
	 * @param cityHallProvince : the province of the city hall of the creator
	 * @return true if the creation is successful
	 * 			false otherwise
	 * @throws ServerSideDatabaseException  when the database can't be found
	 * @throws IllegalParameterException  when parameters are not valid(empty or null) 
	 **/
	public boolean registerMunicipalityByMunicipality(String username,String password,
	String email,String creator,String cityHallName,String cityHallProvince)
			throws ServerSideDatabaseException, IllegalParameterException;
	
	/**
	 *registers Authority by municipality in the same cityHall. 
	 * @param username : the username of the authority to be added
	 * @param password : the password of the authority to be added
	 * @param email : the email of the authority to be added
	 * @param creator : the username of the municipality creating the authority
	 * @param cityHallName : the name of the city hall of the creator
	 * @param cityHallProvince : the province of the city hall of the creator
	 * @param topLeftDistrictBound : the coordinates of the top left bound of the district where the authority works
	 * @param bottomRightDistrictBound : the coordinates of the bottom right bound of the district where the authority works
	 * @return true if the creation is successful
	 * 			false otherwise
	 * @throws ServerSideDatabaseException  when the database can't be found
	 * @throws IllegalParameterException  when parameters are not valid(empty or null) 
	 **/
	public boolean registerAuthority(String username,String password,
			String email,String creator,String cityHallName,String cityHallProvince,
				Location topLeftDistrictBound,Location bottomRightDistrictBound)
					throws ServerSideDatabaseException, IllegalParameterException;
}

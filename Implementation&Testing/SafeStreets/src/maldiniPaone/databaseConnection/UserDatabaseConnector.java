package maldiniPaone.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.utilities.Location;


public class UserDatabaseConnector {
	private static boolean verbose=true;//TODO set to false on release
	
	
	/**
	 * Adds citizen to the database , gets a connection to the database from the connection pool and executes the insertion
	 * @param username : the user name of the citizen to be added 
	 * @param password : the password of the citizen to be added
	 * @param email : the email address of the citizen to be added
	 * @return boolean: true if the creation is successful, false if there is already a user with the given user name or email
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static boolean AddCitizen(String username,String password,String email) throws DatabaseNotFoundException
	{
		boolean res=false;
		try {
			Connection c=ConnectionPool.getIstance().getConnection();
			PreparedStatement ps = c.prepareStatement("insert into citizen (`username`,`password`,`email`) values (?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.execute();
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(verbose)e.printStackTrace();
			return res;
		}
		return true;
	}
	
	
	/**
	 * Adds municipality to the database, gets a connection to the database from the connection pool and executes the insertion.
	 * Use this method when municipality is created by another municipality
	 * @param username : the user name of the municipality to be added 
	 * @param password : the password of the municipality to be added
	 * @param email : the email address of the municipality to be added
	 * @param creator :the user name of the user who asked the creation of the municipality
	 * @param cityName : the name of the city hall where the municipality works
	 * @param cityProvince : the name of the city hall's province where the municipality works
	 * @return boolean: true if the creation is successful, false if there is already a user with the given user name or email
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static boolean addMunicipality(String username,String password,String email,String creator,String cityName,String cityProvince) throws DatabaseNotFoundException
	{
		boolean res=false;
		try {
			Connection c=ConnectionPool.getIstance().getConnection();
			PreparedStatement ps = c.prepareStatement("insert into municipality (`username`,`password`,`email`,`employee`,`cityhall_name`,`cityhall_province`) values (?,?,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, creator);
			ps.setString(5, cityName);
			ps.setString(6, cityProvince);
			ps.execute();
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(verbose)e.printStackTrace();
			return res;
		}
		return true;
	}
	
	/**
	 * Adds municipality to the database , gets a connection to the database from the connection pool and executes the insertion.
	 * Use this method when the creation is done by a system manager.
	 * @param username : the user name of the municipality to be added 
	 * @param password : the password of the municipality to be added
	 * @param email : the email address of the municipality to be added
	 * @param creator :the user name of the user who asked the creation of the municipality
	 * @param cityName : the name of the city hall where the municipality works
	 * @param cityProvince : the name of the city hall's province where the municipality works
	 * @return boolean: true if the creation is successful, false if there is already a user with the given user name or email
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static boolean addMunicipality(String username,String password,String email,String cityName,String cityProvince) throws DatabaseNotFoundException
	{
		boolean res=false;
		try {
			Connection c=ConnectionPool.getIstance().getConnection();
			PreparedStatement ps = c.prepareStatement("insert into municipality (`username`,`password`,`email`,`cityhall_name`,`cityhall_province`) values (?,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, cityName);
			ps.setString(5, cityProvince);
			ps.execute();
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(verbose)e.printStackTrace();
			return res;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Adds city hall to the database , gets a connection to the database from the connection pool and executes the insertion.
	 * @param name : the name of the city hall that must be added 
	 * @param province : the province name in which the city hall is located
	 * @param region : the region name in which the city hall is located
	 * @param location :the user name of the user who asked the creation of the municipality
	 * @return boolean: true if the creation is successful, false if there is already a city hall with the same name and province
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static boolean addCityhall(String name,String province,String region,Location location) throws DatabaseNotFoundException
	{
		boolean res=false;
		try {
			Connection c=ConnectionPool.getIstance().getConnection();
			PreparedStatement ps = c.prepareStatement("insert into cityhall (`cityhall_name`,`cityhall_province`,`region`,`latitude`,`longitude`) values (?,?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, province);
			ps.setString(3, region);
			ps.setFloat(4, location.getLatitude());
			ps.setFloat(5, location.getLongitude());
			ps.execute();
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(verbose)e.printStackTrace();
			return res;
		}
		return true;
	}
	
	/**
	 * Adds district to the database , gets a connection to the database from the connection pool and executes the insertion.
	 * @param name : the name of the city hall in which the district is located
	 * @param province : the name of the province in which the district is located
	 * @param locationTopLeft : the coordinates of the top-left corner of the district area
	 * @param locationBottomRight : the coordinates of the bottom-right corner of the district area
	 * @return Integer: returns the index of the newly created district or -1 if the creation is not successful
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static Integer addDistrict(String name,String province,Location locationTopLeft,Location locationBottomRight) throws DatabaseNotFoundException
	{
		Integer res=-1;
		try {
			Connection c=ConnectionPool.getIstance().getConnection();
			PreparedStatement ps = c.prepareStatement("insert into district (`cityhall_name`,`cityhall_province`,`tllatitude`,`tllongitude`,`brlatitude`,`brlongitude`) values (?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, name);
			ps.setString(2, province);
			ps.setFloat(3, locationTopLeft.getLatitude());
			ps.setFloat(4, locationTopLeft.getLongitude());
			ps.setFloat(5, locationBottomRight.getLatitude());
			ps.setFloat(6, locationBottomRight.getLongitude());
			ps.executeUpdate();
			ResultSet temp = ps.getGeneratedKeys();
			// get the list of indexes created by the query, so the index of the new district
			temp.next();//move to the first element of the result set
			res=temp.getInt(1);//index of the created row
			
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(verbose)e.printStackTrace();
			return res;
		}
		return res;
	}
	
	public static void main(String[] args) throws Exception
	{
		//UserDatabaseConnector.addCityhall("alserio", "como", "lombardia", new Location(10.0f, 20.0f));
		//UserDatabaseConnector.addMunicipality("angelo", "paons", "pp", "alserio", "como");
		//System.out.print(UserDatabaseConnector.addDistrict("alserio", "como" , new Location(10.0f, 20.0f),new Location(10.0f, 20.0f)));
	}
}


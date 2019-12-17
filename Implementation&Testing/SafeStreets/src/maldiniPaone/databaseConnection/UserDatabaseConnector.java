package maldiniPaone.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.Location;

//TODO fix closing of statements and result sets
public class UserDatabaseConnector {
	
	//================================================================================
    // Static variables
    //================================================================================	
	private static boolean verbose=true;//TODO set to false on release
	
	//================================================================================
    // User Adders
    //================================================================================

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
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("insert into citizen "
					+ "(`username`,`password`,`email`) "
					+ "values (?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.execute();
			ps.close();
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
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
	protected static boolean addMunicipalityByMunicipality(String username,String password,String email,String creator,String cityName,String cityProvince) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("insert into municipality "
					+ "(`username`,`password`,`email`,`employee`,`cityhall_name`,`cityhall_province`)"
					+ " values (?,?,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, creator);
			ps.setString(5, cityName);
			ps.setString(6, cityProvince);
			ps.execute();
			ps.close();
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
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
	protected static boolean addMunicipalityByManager(String username,String password,String email,String cityName,String cityProvince) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("insert into municipality "
					+ "(`username`,`password`,`email`,`cityhall_name`,`cityhall_province`) "
					+ "values (?,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, cityName);
			ps.setString(5, cityProvince);
			ps.execute();
			ps.close();
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			if(verbose)e.printStackTrace();
			return res;
		}
		return true;
	}
	
	/**
	 * Adds authority to the database, gets a connection to the database from the connection pool and executes the insertion.
	 * @param username : the user name of the authority to be added 
	 * @param password : the password of the authority to be added
	 * @param email : the email address of the authority to be added
	 * @param creator :the user name of the municipality who asked the creation of the authority
	 * @param districtId : the id of the district in which the authority works
	 * @return boolean: true if the creation is successful, false if there is already a user with the given user name or email
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static boolean addAuthority(String username,String password,String email,String creator,Integer districtId) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("insert into authority "
					+ "(`username`,`password`,`email`,`employee`,`district_id`)"
					+ " values (?,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, creator);
			ps.setInt(5, districtId);
			ps.execute();
			ps.close();
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			if(verbose)e.printStackTrace();
			return res;
		}
		return true;
	}
	
	/**
	 * Adds system manager to the database, gets a connection to the database from the connection pool and executes the insertion.
	 * @param username : the user name of the manager to be added 
	 * @param password : the password of the manager to be added
	 * @param email : the email address of the manager to be added
	 * @param venueName : the name of the venue where the system manager works
	 * @return boolean: true if the creation is successful, false if there is already a user with the given user name or email
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static boolean addManager(String username,String password,String email,String venueName) throws DatabaseNotFoundException
	{
		Connection c=null;
		boolean res=false;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("insert into manager "
					+ "(`username`,`password`,`email`,`venue`)"
					+ " values (?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, venueName);
			ps.execute();
			ps.close();
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			if(verbose)e.printStackTrace();
			return res;
		}
		return true;
	}
	
	//================================================================================
    // city hall and district adders
    //================================================================================

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
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("insert into cityhall "
					+ "(`cityhall_name`,`cityhall_province`,`region`,`latitude`,`longitude`) "
					+ "values (?,?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, province);
			ps.setString(3, region);
			ps.setFloat(4, location.getLatitude());
			ps.setFloat(5, location.getLongitude());
			ps.execute();
			ps.close();
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
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
		Connection c=null;
		Integer res=-1;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("insert into district "
					+ "(`cityhall_name`,`cityhall_province`,`tllatitude`,`tllongitude`,`brlatitude`,`brlongitude`) "
					+ "values (?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, name);
			ps.setString(2, province);
			ps.setFloat(3, locationTopLeft.getLatitude());
			ps.setFloat(4, locationTopLeft.getLongitude());
			ps.setFloat(5, locationBottomRight.getLatitude());
			ps.setFloat(6, locationBottomRight.getLongitude());
			ps.executeUpdate();
			ResultSet temp = ps.getGeneratedKeys();
			// get the list of indexes created by the query, so the index of the new district
			if(temp.next())//move to the first element of the result set
				res=temp.getInt(1);//index of the created row
			temp.close();
			ps.close();
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			if(verbose) e.printStackTrace();
			return res;
		}
		return res;
	}
	//================================================================================
    // check user type and credentials
    //================================================================================
	/**
	 * This method checks if a user credentials are correct and at the same time it returns its user type
	 * @param username : the user name of the user to be checked
	 * @param password : the password of the user to be checked
	 * @return UserType : returns the user type of the user if the credentials are correct,
	 * 					  null if no correspondence is found
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static UserType checkUserCredentials(String username,String password) throws DatabaseNotFoundException
	{
		UserType res=null;
		Connection c=null;
		PreparedStatement ps=null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement(" select usertype from user "
					+ " where username=? and password=? ");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if(rs.next())
				res=UserType.fromString(rs.getString(1));
			rs.close();
			ps.close();
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			if(verbose)e.printStackTrace();
			return res;
		}
		return res;
	}
	
	//================================================================================
    // update user
    //================================================================================
	/**
	 * Modifies the user name , password and email of a user using the given String parameters
	 * @param oldUsername : the user name of the user before being modified
	 * @param oldPassword : the password of the user before being modified
	 * @param user : the user type of the user to be modified
	 * @param newEmail : the new email address of the user
	 * @param newUsername : the new user name of the user
	 * @param newPassword : the new password of the user 
	 * @return boolean  : returns true if the modification is successful false otherwise
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static boolean ModifyUser(String oldUsername,String oldPassword,UserType user,String newEmail,String newUsername, String newPassword) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps=null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("update "+user.toString()
					+ " set username=?, password= ?, email=? "
					+ " where username=? and password=? ");
			ps.setString(1, newUsername);
			ps.setString(2, newPassword);
			ps.setString(3, newEmail);
			ps.setString(4, oldUsername);
			ps.setString(5, oldPassword);
			ps.executeUpdate();
			ps.close();
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			if(verbose)e.printStackTrace();
			return res;
		}	
		return res;
	}
	
	/**
	 * Modifies the user name and password of a user using the given String parameters
	 * @param oldUsername : the user name of the user before being modified
	 * @param oldPassword : the password of the user before being modified
	 * @param user : the user type of the user to be modified
	 * @param newUsername : the new user name of the user
	 * @param newPassword : the new password of the user 
	 * @return boolean  : returns true if the modification is successful false otherwise
	 * @throws DatabaseNotFoundException: connection to database could not be instantiated
	 **/
	protected static boolean ModifyUser(String oldUsername,String oldPassword,UserType user,String newUsername, String newPassword) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps=null;
		try {
			c=ConnectionPool.getInstance().getConnection();
			ps = c.prepareStatement("update "+user.toString()
					+ " set username=?, password=? "
					+ " where username=? and password=? ");
			ps.setString(1, newUsername);
			ps.setString(2, newPassword);
			ps.setString(3, oldUsername);
			ps.setString(4, oldPassword);
			ps.executeUpdate();
			ps.close();
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			if(verbose)e.printStackTrace();
			return res;
		}	
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//================================================================================
    // Dummy main method
    //================================================================================

	public static void main(String[] args) throws Exception
	{
		//UserDatabaseConnector.addCityhall("alserio", "como", "lombardia", new Location(10.0f, 20.0f));
		//UserDatabaseConnector.addMunicipality("angelo", "paons", "pp", "alserio", "como");
		//System.out.print(UserDatabaseConnector.addDistrict("alserio", "como" , new Location(10.0f, 20.0f),new Location(10.0f, 20.0f)));
		//UserDatabaseConnector.AddCitizen("pietro", "maldini", "p@p");
		//UserDatabaseConnector.ModifyUser("pietro", "maldini", UserType.Citizen, "pm390", "newone");
	}
}


package maldiniPaone.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.users.User;

//TODO fix closing of statements and result sets
public class UserDatabaseConnector {
	

	
	
	
	//================================================================================
    // User Adders
    //================================================================================

	/**
	 * Adds citizen to the database , gets a connection to the database from the connection pool and executes the insertion
	 * @param username : the user name of the citizen to be added 
	 * @param password : the password of the citizen to be added
	 * @param email : the email address of the citizen to be added
	 * @return boolean: true if the creation is successful, false if there is already a user with the given user name or email
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean AddCitizen(String username,String password,String email) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("insert into citizen "// add user 
					+ "(`username`,`password`,`email`) " 
					+ "values (?,?,?)");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			//execute query
			ps.executeUpdate();
			//if query fails for duplicate username or email exception is thrown
			res=true; // if code reaches this line the insert is successfull
			//close statement
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
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
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean addMunicipalityByMunicipality(String username,String password,String email,String creator,String cityName,String cityProvince) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("insert into municipality "//add municipality
					+ "(`username`,`password`,`email`,`employee`,`cityhall_name`,`cityhall_province`)"
					+ " values (?,?,?,?,?,?)");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, creator);
			ps.setString(5, cityName);
			ps.setString(6, cityProvince);
			//executes insertion
			ps.executeUpdate();
			// throws exception if fail
			res=true; // line reached only when insertion is successful
			//close statement
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
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
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean addMunicipalityByManager(String username,String password,String email,String cityName,String cityProvince) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("insert into municipality " // add municipality
					+ "(`username`,`password`,`email`,`cityhall_name`,`cityhall_province`) "
					+ "values (?,?,?,?,?)");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, cityName);
			ps.setString(5, cityProvince);
			//execute query
			ps.executeUpdate();
			//if fail throws an exception
			res=true;//reached only on success
			// close statement
			ps.close();
			//release database connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}
	
	/**
	 * Adds authority to the database, gets a connection to the database from the connection pool and executes the insertion.
	 * @param username : the user name of the authority to be added 
	 * @param password : the password of the authority to be added
	 * @param email : the email address of the authority to be added
	 * @param creator :the user name of the municipality who asked the creation of the authority
	 * @param districtId : the id of the district in which the authority works
	 * @return boolean: true if the creation is successful, false if there is already a user with the given user name or email
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean addAuthority(String username,String password,String email,String creator,Integer districtId) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("insert into authority " //add authority
					+ "(`username`,`password`,`email`,`employee`,`district_id`)"
					+ " values (?,?,?,?,?)");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, creator);
			ps.setInt(5, districtId);
			//execute query
			ps.executeUpdate();
			//if fails throws an exception
			res=true;//line reached only on success
			//close statement
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}
	
	/**
	 * Adds system manager to the database, gets a connection to the database from the connection pool and executes the insertion.
	 * @param username : the user name of the manager to be added 
	 * @param password : the password of the manager to be added
	 * @param email : the email address of the manager to be added
	 * @param venueName : the name of the venue where the system manager works
	 * @return boolean: true if the creation is successful, false if there is already a user with the given user name or email
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean addManager(String username,String password,String email,String venueName) throws DatabaseNotFoundException
	{
		Connection c=null;
		boolean res=false;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("insert into manager "//add manager
					+ "(`username`,`password`,`email`,`venue`)"
					+ " values (?,?,?,?)");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, venueName);
			//execute query
			ps.executeUpdate();
			//if fails throws an exception
			res=true;//line reached only if successful
			//close statement
			ps.close();
			//releases connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)	e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
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
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean addCityhall(String name,String province,String region,Location location) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("insert into cityhall " // add cityhall
					+ "(`cityhall_name`,`cityhall_province`,`region`,`latitude`,`longitude`) "
					+ "values (?,?,?,?,?)");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, name);
			ps.setString(2, province);
			ps.setString(3, region);
			ps.setFloat(4, location.getLatitude());
			ps.setFloat(5, location.getLongitude());
			//ececute the inseriton
			ps.executeUpdate();
			// if fails throws an  exception
			res=true; //reached only on success
			//close statement
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}
	
	/**
	 * Adds district to the database , gets a connection to the database from the connection pool and executes the insertion.
	 * @param name : the name of the city hall in which the district is located
	 * @param province : the name of the province in which the district is located
	 * @param locationTopLeft : the coordinates of the top-left corner of the district area
	 * @param locationBottomRight : the coordinates of the bottom-right corner of the district area
	 * @return Integer: returns the index of the newly created district or -1 if the creation is not successful
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static Integer addDistrict(String name,String province,Location locationTopLeft,Location locationBottomRight) throws DatabaseNotFoundException
	{
		Connection c=null;
		Integer res=-1;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("insert into district " // add district
					+ "(`cityhall_name`,`cityhall_province`,`tllatitude`,`tllongitude`,`brlatitude`,`brlongitude`) "
					+ "values (?,?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);//ask the statement to return the generated keys
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, name);
			ps.setString(2, province);
			ps.setFloat(3, locationTopLeft.getLatitude());
			ps.setFloat(4, locationTopLeft.getLongitude());
			ps.setFloat(5, locationBottomRight.getLatitude());
			ps.setFloat(6, locationBottomRight.getLongitude());
			//exectute insertion
			ps.executeUpdate();
			// get result set of the query
			ResultSet temp = ps.getGeneratedKeys();//reached only on query success
			// get the list of indexes created by the query, so the index of the new district
			if(temp.next())//move to the first element of the result set
			{
				res=temp.getInt(1);//index of the created row
			}
			//close result set and statement
			temp.close();
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE) e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}
	
	//================================================================================
    // city hall getter
    //================================================================================
	/**
	 * Gets the cityhall where a municipality works
	 * @param username: the username of the municipality whose cityhall is being searched 
	 * @return CityHall
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static CityHall getCityHall(String username) throws DatabaseNotFoundException
	{
		CityHall res=null;
		Connection c=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("select ch.cityhall_name, ch.cityhall_province ,ch.region"
					+ ", ch.longitude,ch.latitude" // get cityhall
					+ " from muncipality as mujoin cityhall as ch "
					+ " on mu.cityhall_name=ch.cityhall_name and mu.cityhall_province=ch.cityhall_province"
					+ " where mu.username=?");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);

			//ececute the inseriton
			ps.execute();
			// if fails throws an  exception
			ResultSet rs=ps.getResultSet();
			if(rs.next())
			{
				res=new CityHall();
				Location location=new Location();
				
				res.setName(rs.getString(1));
				res.setProvince(rs.getString(2));
				res.setRegion(rs.getString(3));
				res.setLocation(location);
				location.setLongitude(rs.getFloat(4));
				location.setLatitude(rs.getFloat(5));
			}
			//close statement
			rs.close();
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
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
	 * @return UserType : returns if the user type of the user the credentials are correct,
	 * 					  null if no correspondence is found
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static UserType checkUserCredentials(String username,String password) throws DatabaseNotFoundException
	{
		UserType res=null;
		Connection c=null;
		PreparedStatement ps=null;
		try {
			c=ConnectionPool.getInstance().getConnection(); // get connection
			ps = c.prepareStatement(" select usertype from user " //get user type of the user 
																  //with the given credentials
					+ " where username=? and password=? ");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);
			ps.setString(2, password);
			//execute query
			ps.execute();
			// get result set
			ResultSet rs = ps.getResultSet();
			if(rs.next())
			{
				res=UserType.fromString(rs.getString(1)); // convert the string from result set to the user type 
			}
			//close result set and prepared statement
			rs.close();
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
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
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean modifyUser(String oldUsername,String oldPassword,UserType user,String newEmail,String newUsername, String newPassword) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps=null;
		try {
			c=ConnectionPool.getInstance().getConnection(); // get connection
			ps = c.prepareStatement("update "+user.toString() // update the given user type
					+ " set username=?, password= ?, email=? "
					+ " where username=? and password=? ");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, newUsername);
			ps.setString(2, newPassword);
			ps.setString(3, newEmail);
			ps.setString(4, oldUsername);
			ps.setString(5, oldPassword);
			//execute update
			ps.executeUpdate();
			//if fails throws exception
			res=true; // reached only on success
			//close prepared statement
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
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
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean modifyUser(String oldUsername,String oldPassword,UserType user,String newUsername, String newPassword) throws DatabaseNotFoundException
	{
		boolean res=false;
		Connection c=null;
		PreparedStatement ps=null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("update "+user.toString() // update given user type
					+ " set username=?, password=? "
					+ " where username=? and password=? ");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, newUsername);
			ps.setString(2, newPassword);
			ps.setString(3, oldUsername);
			ps.setString(4, oldPassword);
			//execute query
			ps.executeUpdate();
			//if fails throws an exception
			res=true;//reached only on success
			//close prepared statement
			ps.close();
			//release connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e){
			if(Constants.VERBOSE)e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}	
		return res;
	}
	
	
	
	//================================================================================
    // remove user
    //================================================================================
	/**
	 * Removes a user given its username and password
	 * @param username : the user name of the user to be deleted
	 * @param password : the password of the user to be deleted
	 * @param user : the user type of the user to be deleted
	 * @return boolean: true if the deletion is successful, false if fails
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static boolean removeUser(String username,String password,UserType user) throws DatabaseNotFoundException
	{
		Connection c=null;
		boolean res=false;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("delete from "+user.toString() //delete from the right table
					+ "where username=? and password=?");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);
			ps.setString(2, password);
			//execute query
			ps.executeUpdate();
			//if fails throws an exception
			res=true;//line reached only if successful
			//close statement
			ps.close();
			//releases connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)	e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}
	
	
	//================================================================================
    // get User data
    //================================================================================
	//================================================================================
    // get email
    //================================================================================
	/**
	 * Finds the email address of a user
	 * @param username : the user name of the user whose email must be retrieved
	 * @return String : the email of the user . null if no user exists with the given username
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static String findEmailByUsername(String username) throws DatabaseNotFoundException
	{
		Connection c=null;
		String res=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("select email from user " //delete from the right table
					+ "where username=?");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, username);

			//execute query
			ps.execute();
			//if fails throws an exception
			//line reached only if successful
			ResultSet rs=ps.getResultSet();
			if(rs.next())
			{
				res=rs.getString(1);
			}
			//close statement
			rs.close();
			ps.close();
			//releases connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)	e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}
	//================================================================================
    // get username by email
    //================================================================================
	/**
	 * Finds the username of a user
	 * @param email : the email address of the user whose username must be retrieved
	 * @return String : the username of the user . null if no user exists with the given email
	 * @throws DatabaseNotFoundException the connection to the database could not be instantiated
	 **/
	protected static String findUsernameByEmail(String email) throws DatabaseNotFoundException
	{
		Connection c=null;
		String res=null;
		PreparedStatement ps =null;
		try {
			c=ConnectionPool.getInstance().getConnection();//get connection
			ps = c.prepareStatement("select email from user " //delete from the right table
					+ "where email=?");
			//set the values in the prepared statements avoid sql injection
			ps.setString(1, email);

			//execute query
			ps.execute();
			//if fails throws an exception
			//line reached only if successful
			ResultSet rs=ps.getResultSet();
			if(rs.next())
			{
				res=rs.getString(1);
			}
			//close statement
			rs.close();
			ps.close();
			//releases connection
			ConnectionPool.getInstance().releaseConnection(c);
		}
		catch(DatabaseNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)	e.printStackTrace();
			if(ps!=null) try{ps.close();}catch(Exception ex){/*database didn't close the statement*/}
			if(c!=null) ConnectionPool.getInstance().releaseConnection(c);
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


package maldiniPaone.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimeZone;

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
/**this class manages the creation of the connections with the database, every connection gotten using 
 * GetConnection method must be released using Release Connection method. 
 * To modify internal database , initial size and user name and password needed
 * the initialization file used here must be modified(TODO insert name here)
 **/
public class ConnectionPool {
	//================================================================================
    // static variables
    //================================================================================


	/**instance of the connection pool*/
	private static ConnectionPool instance;
	


	//================================================================================
    // instance values
    //================================================================================

	//TODO put in initialization file
	/**driver to connect to database*/
	private String driver;
	/**user name to access database*/
	private String username;
	/**password to access database*/
	private String password;
	/**URL of the database*/
	private String databaseURL;
	/**stored connections*/
	private ArrayList<Connection> availableConnections; 

	//================================================================================
    // constructor
    //================================================================================

	/**
	 * This constructor is private to allow the creation of a singleton. 
	 * In this method user name,password and database URL are read from initialization file
	 * (TODO insert here name)
	 * It calls the InstantiateConnection method one time for the Initial size of the pool.
	 * @throws DatabaseNotFoundException  no connection to the database could be instantiated
	 **/
	private ConnectionPool() throws DatabaseNotFoundException
	{
		driver=Constants.DB_DRIVER;
		username=Constants.DB_USERNAME;
		password=Constants.DB_PASSWORD;
		databaseURL=Constants.DB_URL;
		availableConnections=new ArrayList<Connection>(Constants.INITIALSIZE);
		try
		{
			Class.forName(driver);
		}
		catch(Exception e)
		{
			if(Constants.VERBOSE)e.printStackTrace(); 
		}
		for(int i=0;i<Constants.INITIALSIZE;++i)
		{
			try {
				availableConnections.add(this.instantiateConnection());
			}
			catch(Exception e)
			{
				if(Constants.VERBOSE)e.printStackTrace();
			}
		}
		if (availableConnections.size()==0) throw new DatabaseNotFoundException();
	}
	
	//================================================================================
    // Instantiator
    //================================================================================
	/**
	 * instantiates a ConnectionPoolObject if it is not available, otherwise returns the already existing object
	 * @return ConnectionPool
	 * @throws DatabaseNotFoundException
	 * @note Singleton pattern.
	 **/
	protected static ConnectionPool getInstance() throws DatabaseNotFoundException
	{
		return (instance==null)?instance=new ConnectionPool():instance;
	}

	//================================================================================
    // methods to get and return connection to the pool
    //================================================================================

	/**
	 * this function gets an available connection or instantiates it if no more connections are available
	 * @return Connection: get an available connection or if not connection is available calls the instantiate 
	 * connection method 
	 * @throws DatabaseNotFoundException: when instantiating new connection database could not be reached
	 **/
	protected synchronized Connection getConnection() throws DatabaseNotFoundException
	{
		int size=availableConnections.size();
		return (size>0)?availableConnections.remove(size-1):instantiateConnection();
	}


	/**
	 * This function reads the connection to the available Connections
	 * @param c : connection which is returned to the pool
	 **/
	protected synchronized void releaseConnection(Connection c)
	{
		availableConnections.add(c);
	}	


	
	//================================================================================
    // connection creator
    //================================================================================

	/**
	 * Creates the actual connections using Connection Pool's parameters
	 * @throws SQLException database connection could not be instantiated 
	 **/
	private Connection instantiateConnection() throws DatabaseNotFoundException
	{
		try
		{
			return DriverManager.getConnection(databaseURL, username, password);
		}catch(SQLException s)
		{
			if(Constants.VERBOSE) s.printStackTrace();
			throw new DatabaseNotFoundException(s);
		} 
	}

	
	//================================================================================
    // dummy main
    //================================================================================

	public static void main(String[] args) throws Exception
	{
		ConnectionPool.getInstance();
	}
}

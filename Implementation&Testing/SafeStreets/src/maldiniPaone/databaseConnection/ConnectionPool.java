package maldiniPaone.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimeZone;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
/**this class manages the creation of the connections with the database, every connection gotten using 
 * GetConnection method must be released using Release Connection method. 
 * To modify internal database , initial size and user name and password needed
 * the initialization file used here must be modified(TODO insert name here)
 **/
public class ConnectionPool {
	//TODO set verbose to false for release
	private static Boolean verbose=true;
	private static ConnectionPool istance;
	private static Integer INITIALSIZE=5;//TODO remember to change to bigger number for testing and actual release.
	//low number for functionality testing	

	//TODO put in initialization file
	private String driver;
	private String username;
	private String password;
	private String databaseURL;
	private ArrayList<Connection> availableConnections; 



	/**
	 * instantiates a ConnectionPoolObject if it is not available, otherwise returns the already existing object
	 * @return ConnectionPool
	 * @exception DatabaseNotFoundException
	 * @note Singleton pattern.
	 **/
	protected static ConnectionPool getIstance() throws DatabaseNotFoundException
	{
		return (istance==null)?istance=new ConnectionPool():istance;
	}

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


	/**
	 * This constructor is private to allow the creation of a singleton. 
	 * In this method user name,password and database URL are read from initialization file
	 * (TODO insert here name)
	 * It calls the InstantiateConnection method one time for the Initial size of the pool.
	 * @exception DatabaseNotFoundException: no connection to the database could be instantiated
	 **/
	private ConnectionPool() throws DatabaseNotFoundException
	{
		driver="com.mysql.cj.jdbc.Driver";//TODO set parameters from file
		username="SafeStreets";
		password="Safestreets1886_Server";
		databaseURL="jdbc:mysql://localhost:3306/safestreets"+"?serverTimezone="+ TimeZone.getDefault().getID();
		availableConnections=new ArrayList<Connection>(INITIALSIZE);
		try
		{
			Class.forName(driver);
		}
		catch(Exception e)
		{
			if(verbose)e.printStackTrace(); 
		}
		for(int i=0;i<INITIALSIZE;++i)
		{
			try {
				availableConnections.add(this.instantiateConnection());
			}
			catch(Exception e)
			{
				if(verbose)e.printStackTrace();
			}
		}
		if (availableConnections.size()==0) throw new DatabaseNotFoundException();
	}

	/**
	 * Creates the actual connections using Connection Pool's parameters
	 * @exception SQLException: database connection could not be instantiated 
	 **/
	private Connection instantiateConnection() throws DatabaseNotFoundException
	{
		try
		{
			return DriverManager.getConnection(databaseURL, username, password);
		}catch(SQLException s)
		{
			if(verbose) s.printStackTrace();
			throw new DatabaseNotFoundException(s);
		} 
	}

	public static void main(String[] args) throws Exception
	{
		ConnectionPool.getIstance();
	}
}

package maldiniPaone.databaseConnection;

import java.sql.Connection;
import java.util.ArrayList;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFound;
/**this class manages the creation of the connections with the database, every connection gotten using 
 * GetConnection method must be released using Release Connection method. 
 * To modify internal database , initial size and user name and password needed
 * the initialization file used here must be modified(TODO insert name here)
 * */
public class ConnectionPool {
	//TODO put in initialization file
	private static Integer INITIALSIZE=5;//TODO remember to change to bigger number for testing and actual release.
	//low number for functionality testing	
	private String username;
	private String password;
	private String databaseURL;
	private static ConnectionPool istance;
	private ArrayList<Connection> availableConnections; 
	
		
	
	/**
	 * instantiates a ConnectionPoolObject if it is not available, otherwise returns the already existing object
	 * Singleton pattern.
	 * @return ConnectionPool
	 * @exception DatabaseNotFound
	 * */
	protected ConnectionPool getIstance() throws DatabaseNotFound
	{
		return (istance==null)?istance=new ConnectionPool():istance;
	}
	
	/**
	 * this function gets an available connection or instantiates it if no more connections are available
	 * @return Connection: get an available connection or if not connection is available calls the instantiate 
	 * connection method 
	 * @throws DatabaseNotFound: when instantiating new connection database could not be reached
	*/
	protected synchronized Connection GetConnection() throws DatabaseNotFound
	{
		int size=availableConnections.size();
		return (size>0)?availableConnections.remove(size-1):InstantiateConnection();
	}
	
	
	/**
	 *@param Connection c:  connection which is returned to the pool
	 * This function reads the connection to the available Connections
	*/
	protected synchronized void ReleaseConnection(Connection c)
	{
		availableConnections.add(c);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * This constructor is private to allow the creation of a singleton. 
	 * In this method user name,password and database URL are read from initialization file
	 * (TODO insert here name)
	 * It calls the InstantiateConnection method one time for the Initial size of the pool.
	 * @exception DatabaseNotFound: no connection to the database could be instantiated
	 * */
	private ConnectionPool() throws DatabaseNotFound
	{
		username="temp";//TODO set parameters
		password="temp";
		databaseURL="temp";
		availableConnections=new ArrayList<Connection>(INITIALSIZE);
		for(int i=0;i<INITIALSIZE;++i)
		{
			availableConnections.add(this.InstantiateConnection());
		}
	}
	
	/**
	 * Creates the actual connections using Connection Pool's parameters
	 * @exception DatabaseNotFound: database connection could not be instantiated
	 * */
	//TODO 
	private Connection InstantiateConnection() throws DatabaseNotFound
	{
		return null;
	}
	
}

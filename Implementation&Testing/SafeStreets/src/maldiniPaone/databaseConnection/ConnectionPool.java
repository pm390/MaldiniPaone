package maldiniPaone.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.utilities.constants.Constants;

/**
 * this class manages the creation of the connections with the database, every
 * connection gotten using GetConnection method must be released using Release
 * Connection method. To modify internal database , initial size and user name
 * and password needed the constants in {@link Constants} must be modified
 **/
public class ConnectionPool {
	// ================================================================================
	// static variables
	// ================================================================================

	/** instance of the connection pool */
	private static ConnectionPool instance;

	// ================================================================================
	// instance values
	// ================================================================================

	/** driver to connect to database */
	private String driver;
	/** user name to access database */
	private String username;
	/** password to access database */
	private String password;
	/** URL of the database */
	private String databaseURL;
	/** stored connections */
	private ArrayList<Connection> availableConnections;

	// ================================================================================
	// constructor
	// ================================================================================

	/**
	 * This constructor is private to allow the creation of a singleton. In this
	 * method user name,password and database URL are read from {@link Constants}
	 * 
	 * @throws DatabaseNotFoundException no connection to the database could be
	 *                                   instantiated
	 **/
	private ConnectionPool() throws DatabaseNotFoundException {
		driver = Constants.DB_DRIVER;
		username = Constants.DB_USERNAME;
		password = Constants.DB_PASSWORD;
		databaseURL = Constants.DB_URL;
		availableConnections = new ArrayList<Connection>(Constants.INITIALSIZE);
		if(Constants.VERBOSE)
		{
			System.out.println(driver+"\n"+username+"\n"+password+"\n"+databaseURL);
		}
		try {
			Class.forName(driver);
		} catch (Exception e) {
			if (Constants.VERBOSE)
				e.printStackTrace();
		}
		for (int i = 0; i < Constants.INITIALSIZE; ++i) {
			try {
				availableConnections.add(this.instantiateConnection());
			} catch (Exception e) {
				if (Constants.VERBOSE)
					e.printStackTrace();
			}
		}
		if (availableConnections.size() == 0)
			throw new DatabaseNotFoundException();
	}

	// ================================================================================
	// Instantiator
	// ================================================================================
	/**
	 * instantiates a ConnectionPoolObject if it is not available, otherwise returns
	 * the already existing object Singleton design pattern.
	 * 
	 * @return ConnectionPool
	 * @throws DatabaseNotFoundException if no connection could be initialized
	 * 
	 **/
	protected static ConnectionPool getInstance() throws DatabaseNotFoundException {
		return (instance == null) ? instance = new ConnectionPool() : instance;
	}

	// ================================================================================
	// methods to get and return connection to the pool
	// ================================================================================

	/**
	 * this function gets an available connection or instantiates it if no more
	 * connections are available
	 * 
	 * @return Connection: get an available connection or if not connection is
	 *         available calls the instantiate connection method
	 * @throws DatabaseNotFoundException  when instantiating new connection database
	 *                                    could not be reached or when the
	 *                                    connection got from the pool was closed by
	 *                                    the database because is no longer
	 *                                    available or for internal problems
	 **/
	protected synchronized Connection getConnection() throws DatabaseNotFoundException {
		int size = availableConnections.size();
		Connection result;
		if (size > 0) {
			result = availableConnections.remove(size - 1);
		} else {
			result = instantiateConnection();
		}
		try {
			if (result.isValid(0)) {// check if the connection returned is valid if not valid then the database
									// closed it
				return result;
			}
		} catch (SQLException e) {
			if (Constants.VERBOSE) {
				e.printStackTrace();
			}
		}
		throw new DatabaseNotFoundException();
	}

	/**
	 * This function reads the connection to the available Connections
	 * 
	 * @param c : connection which is returned to the pool
	 **/
	protected synchronized void releaseConnection(Connection c) {
		availableConnections.add(c);
	}

	// ================================================================================
	// connection creator
	// ================================================================================

	/**
	 * Creates the actual connections using Connection Pool's parameters
	 * @return the instantiated connection
	 * @throws DatabaseNotFoundException if database connection could not be instantiated
	 **/
	private Connection instantiateConnection() throws DatabaseNotFoundException {
		try {
			return DriverManager.getConnection(databaseURL, username, password);
		} catch (SQLException s) {
			if (Constants.VERBOSE)
				s.printStackTrace();
			throw new DatabaseNotFoundException(s);
		}
	}


	protected void finalize() throws Exception {
		for (Connection c : availableConnections) {
			try {
				System.out.println("closing connections");
				c.close();
			} catch (Exception e) {

			}
		}
	}
	
	
	
	
	// ================================================================================
	// dummy main
	// ================================================================================

	public static void main(String[] args) throws Exception {
		ConnectionPool.getInstance();
	}
}

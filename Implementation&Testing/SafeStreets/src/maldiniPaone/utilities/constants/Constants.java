package maldiniPaone.utilities.constants;

import java.util.TimeZone;

/**
 * This class contains the various constants used by safeStreets back end
 **/
public class Constants {
	// ================================================================================
	// initialization
	// ================================================================================
	static {
		// TODO set parameters from file
	}
	// ================================================================================
	// database constants
	// ================================================================================
	/**
	 * username to access database
	 */
	public static final String DB_USERNAME = "SafeStreets";
	/**
	 * password to access database
	 */
	public static final String DB_PASSWORD = "Safestreets1886_Server";
	/**
	 * driver to access database
	 */
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	/**
	 * database url
	 */
	public static final String DB_URL = "jdbc:mysql://localhost:3306/safestreets" + "?serverTimezone="
			+ TimeZone.getDefault().getID();;

	// ================================================================================
	// query constants
	// ================================================================================
	/**
	 * radius used for statistics retrieve
	 */
	public static final float STATISTICS_RADIUS = 0.01f;
	

	/**
	 *	square root of limit of statistics size 
	 **/
	public static final int STATISTICS_LIMIT = 30;
	/**
	 * radius used to consider 2 locations close to each other
	 **/
	public static final Float EUCLIDEAN_CLOSE_DISTANCE = 1f;
	/**
	 * radius used to consider when finding the closest city hall
	 **/
	public static final Float CLOSE_CITYHALL = null;
	/**
	 * standard query limit
	 **/
	public static final Integer STANDARD_QUERY_LIMIT = 20;
	/**
	 * initial size of the connection pool
	 **/
	public static final Integer INITIALSIZE = 5;// low number for functionality testing
	// TODO remember to change to bigger number for testing and actual release.
	/**
	 * max number of static suggestions to show to a municipality
	 **/
	public static final Integer SUGGESTION_MAX_SIZE = 40;

	// ================================================================================
	// temporary password constants
	// ================================================================================
	/**
	 * number of digits to be included in a temporary password
	 **/
	public static final Integer TEMPORARY_PASSWORD_DIGIT_LENGHT = 3;
	/**
	 * number of lower case characters to be included in a temporary password
	 **/
	public static final Integer TEMPORARY_PASSWORD_LOWERCASE_LENGHT = 5;
	/**
	 * number of upper case characters digits to be included in a temporary password
	 **/
	public static final Integer TEMPORARY_PASSWORD_UPPERCASE_LENGHT = 8;

	// ================================================================================
	// mail manager
	// ================================================================================
	/**
	 * mail address of the service
	 **/
	public static final String MAIL_USERNAME = "Safestreetssweng19.20@gmail.com";
	/**
	 * password of the mail of the service
	 **/
	public static final String MAIL_PASSWORD = "MaldiniPaone9897";

	// ================================================================================
	// server address
	// ================================================================================
	/**
	 * Server address
	 **/
	public static final String SERVER_ADDRESS = "http://localhost:8081/SafeStreets";

	// ================================================================================
	// photos constants
	// ================================================================================
	/**
	 * location where photos are stored
	 **/
	public static final String PHOTO_PATH = "C:\\SafeStreetsPhotos";

	// ================================================================================
	// debugging constants
	// ================================================================================
	/**
	 * if true prints errors in the console
	 **/
	public static final boolean VERBOSE = true;// set to false on release
	
	
}

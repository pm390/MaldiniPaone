package maldiniPaone.utilities.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TimeZone;

/**
 * This class contains the various constants used by safeStreets back end
 **/
public class Constants {
	static {
		System.err.println("init");
		String root = System.getProperty("catalina.home");

		System.err.println("reading init parameters from: "+ root + File.separator + "configuration.txt");
		
		File file = new File(root + File.separator + "configuration.txt");

		BufferedReader br;
		boolean finished = false;
		try {
			br = new BufferedReader(new FileReader(file));
			initFromBufferedReader(br);
			finished = true;
			// finished initialization
		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.err.println("config file is corrupted or not found load with defaul configuration");
		}
		if (!finished) {
			DB_USERNAME = "SafeStreets";
			DB_PASSWORD = "Safestreets1886_Server";
			DB_URL = "jdbc:mysql://localhost:3306/safestreets" + "?serverTimezone=" + TimeZone.getDefault().getID();
			STATISTICS_RADIUS = 0.01f;
			STATISTICS_LIMIT = 30;
			EUCLIDEAN_CLOSE_DISTANCE = 1f;
			CLOSE_CITYHALL = 1f;
			STANDARD_QUERY_LIMIT = 20;
			INITIALSIZE = 5;
			SUGGESTION_MAX_SIZE = 40;
			TEMPORARY_PASSWORD_DIGIT_LENGHT = 3;
			TEMPORARY_PASSWORD_LOWERCASE_LENGHT = 5;
			TEMPORARY_PASSWORD_UPPERCASE_LENGHT = 8;
			MAIL_USERNAME = "Safestreetssweng19.20@gmail.com";
			MAIL_PASSWORD = "MaldiniPaone9897";

			SERVER_ADDRESS = "http://localhost:8081/SafeStreets";

			PHOTO_PATH = "C:\\SafeStreetsPhotos";

			VERBOSE = true;// set to false on release

			AUTHORITY_LOCATION_GRANULARITY = 50;

			VIOLATION_SEVERITY_LIMIT = 5;
		}
	}

	// ================================================================================
	// database constants
	// ================================================================================
	/**
	 * username to access database
	 */
	public static String DB_USERNAME ;
	/**
	 * password to access database
	 */
	public static String DB_PASSWORD ;
	/**
	 * driver to access database
	 */
	public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; ;
	/**
	 * database url
	 */
	public static String DB_URL;

	// ================================================================================
	// query constants
	// ================================================================================
	/**
	 * radius used for statistics retrieve
	 */
	public static float STATISTICS_RADIUS ;

	/**
	 * square root of limit of statistics size
	 **/
	public static int STATISTICS_LIMIT ;
	/**
	 * radius used to consider 2 locations close to each other
	 **/
	public static Float EUCLIDEAN_CLOSE_DISTANCE ;
	/**
	 * radius used to consider when finding the closest city hall
	 **/
	public static Float CLOSE_CITYHALL;
	/**
	 * standard query limit
	 **/
	public static Integer STANDARD_QUERY_LIMIT ;
	/**
	 * initial size of the connection pool
	 **/
	public static Integer INITIALSIZE ;// low number for functionality testing
	// TODO ON RELEASE remember to change to bigger number for testing and actual
	// release.
	/**
	 * max number of static suggestions to show to a municipality
	 **/
	public static Integer SUGGESTION_MAX_SIZE ;

	// ================================================================================
	// temporary password constants
	// ================================================================================
	/**
	 * number of digits to be included in a temporary password
	 **/
	public static Integer TEMPORARY_PASSWORD_DIGIT_LENGHT ;
	/**
	 * number of lower case characters to be included in a temporary password
	 **/
	public static Integer TEMPORARY_PASSWORD_LOWERCASE_LENGHT;
	/**
	 * number of upper case characters digits to be included in a temporary password
	 **/
	public static Integer TEMPORARY_PASSWORD_UPPERCASE_LENGHT ;

	// ================================================================================
	// mail manager
	// ================================================================================
	/**
	 * mail address of the service
	 **/
	public static String MAIL_USERNAME ;
	/**
	 * password of the mail of the service
	 **/
	public static String MAIL_PASSWORD ;

	// ================================================================================
	// server address
	// ================================================================================
	/**
	 * Server address
	 **/
	public static String SERVER_ADDRESS ;

	// ================================================================================
	// photos constants
	// ================================================================================
	/**
	 * location where photos are stored
	 **/
	public static String PHOTO_PATH ;

	// ================================================================================
	// debugging constants
	// ================================================================================
	/**
	 * if true prints errors in the console
	 **/
	public static boolean VERBOSE ;// set to false on release

	/**
	 * The number of edge on the map border created to save authority location
	 **/
	public static int AUTHORITY_LOCATION_GRANULARITY;

	/**
	 * The number of violations after which a violation is considered severe // TODO
	 * ON RELEASE increase
	 **/
	public static Integer VIOLATION_SEVERITY_LIMIT ;

	// ================================================================================
	// initializer
	// ================================================================================
	/**
	 * Uses the provided buffered reader to set the constants
	 * 
	 */
	private static void initFromBufferedReader(BufferedReader br) throws IOException {
		boolean valid=true;
		String temp;
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			DB_USERNAME = temp.split("\\s+")[1];
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			DB_PASSWORD = temp.split("\\s+")[1];
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			DB_URL =  temp.split("\\s+")[1]+ "?serverTimezone=" + TimeZone.getDefault().getID();;
		}
		else
		{
			valid=false;
		}
		
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			STATISTICS_RADIUS = Float.parseFloat(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			STATISTICS_LIMIT =Integer.parseInt(temp.split("\\s+")[1]);
			System.out.println(temp);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			EUCLIDEAN_CLOSE_DISTANCE =Float.parseFloat(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			CLOSE_CITYHALL =Float.parseFloat(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			STANDARD_QUERY_LIMIT =Integer.parseInt(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			INITIALSIZE=Integer.parseInt(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			SUGGESTION_MAX_SIZE=Integer.parseInt(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			TEMPORARY_PASSWORD_DIGIT_LENGHT=Integer.parseInt(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			TEMPORARY_PASSWORD_LOWERCASE_LENGHT=Integer.parseInt(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{

			System.out.println(temp);
			TEMPORARY_PASSWORD_UPPERCASE_LENGHT=Integer.parseInt(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			MAIL_USERNAME=temp.split("\\s+")[1];
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			MAIL_PASSWORD =temp.split("\\s+")[1];
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			SERVER_ADDRESS =temp.split("\\s+")[1];
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			PHOTO_PATH =temp.split("\\s+")[1];
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			VERBOSE =Boolean.parseBoolean(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			AUTHORITY_LOCATION_GRANULARITY=Integer.parseInt(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		if(valid&&((temp = br.readLine()) != null))
		{
			System.out.println(temp);
			VIOLATION_SEVERITY_LIMIT=Integer.parseInt(temp.split("\\s+")[1]);
		}
		else
		{
			valid=false;
		}
		
		if(!valid)
		{
			throw new IOException ();
		}
		return;
	}

}

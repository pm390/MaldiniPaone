package maldiniPaone.constants;

import java.util.TimeZone;

public class Constants {

	//TODO set parameters from file
	public static final String DB_USERNAME="SafeStreets";
	
	public static final String DB_PASSWORD="Safestreets1886_Server";
	
	public static final String DB_DRIVER="com.mysql.cj.jdbc.Driver";
	
	public static final String DB_URL="jdbc:mysql://localhost:3306/safestreets"+"?serverTimezone="+ TimeZone.getDefault().getID();;
	
	public static final float STATISTICS_RADIUS=0.5f;
	/**if true prints errors in the console*/
	public static final boolean VERBOSE=true;//set to true on release
	/**radius used to consider 2 locations close to each other*/
	public static final Float EUCLIDEAN_CLOSE_DISTANCE=1f;//TODO think about the value
	/**standard query limit*/
	public static final Integer STANDARD_QUERY_LIMIT=20;
	/**initial size of the connection pool*/
	public static final Integer INITIALSIZE=5;//low number for functionality testing	
	//TODO remember to change to bigger number for testing and actual release.
	public static final Integer SUGGESTION_MAX_SIZE=40;
	
	public static final Integer TEMPORARY_PASSWORD_DIGIT_LENGHT=3;
	
	public static final Integer TEMPORARY_PASSWORD_LOWERCASE_LENGHT=5;
	
	public static final Integer TEMPORARY_PASSWORD_UPPERCASE_LENGHT=8;
	
	public static final String MAIL_USERNAME="Safestreetssweng19.20@gmail.com";
	
	public static final String MAIL_PASSWORD="MaldiniPaone9897";
	
	public static final String SERVER_ADDRESS="http://localhost:8081/SafeStreets";

	public static final int MAX_ATTEMPTS=5;

	public static final String PHOTO_PATH = "C:\\SafeStreetsPhotos";
}

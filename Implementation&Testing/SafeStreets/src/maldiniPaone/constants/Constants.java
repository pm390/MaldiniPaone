package maldiniPaone.constants;

public class Constants {

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
	
	public static final Integer TEMPORARY_PASSWORD_DIGIT_LENGHT=8;
	
	public static final Integer TEMPORARY_PASSWORD_LOWERCASE_LENGHT=12;
	
	public static final Integer TEMPORARY_PASSWORD_UPPERCASE_LENGHT=16;
}

package maldiniPaone.databaseConnection;


import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;

/**
 * This class contains protected static methods which are used to get and update
 * data about users. Those methods checks parameters values to check data
 * validity and avoid inconsistent data. All methods may throw exceptions if
 * database can't be reached or parameters are not valid. The actual
 * communication with the database happens in {@link UserDatabaseConnector}.
 * 
 * @see UserDatabaseConnector
 **/
public class UserDataChecker {
	
	// ================================================================================
	// User Adders
	// ================================================================================

	/**
	 * Calls the function to add citizen inside UserDatabaseConnector this function
	 * checks the validity of the parameters
	 * 
	 * @param username : the user name of the citizen to be added
	 * @param password : the password of the citizen to be added
	 * @param email    : the email address of the citizen to be added
	 * @return boolean : true if the creation is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean AddCitizen(String username, String password, String email)
			throws IllegalParameterException, ServerSideDatabaseException {
		boolean res = false;
		if (username != null && password != null && email != null && // check null values
				username != "" && password != "" && email != "") // check empty strings
		{
			try {
				res = UserDatabaseConnector.addCitizen(username, password, email);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	/**
	 * Calls the function to add Municipality inside UserDatabaseConnector this
	 * function checks the validity of the parameters and calls the appropriate
	 * function
	 * 
	 * @param username     : the user name of the Municipality to be added
	 * @param password     : the password of the Municipality to be added
	 * @param email        : the email address of the Municipality to be added
	 * @param creator      : the municipality that is adding the municipality(null
	 *                     if it is added by a system manager)
	 * @param cityName     : the name of the city hall where the municipality works
	 * @param cityProvince : the province where the city hall is located
	 * @return boolean : true if the creation is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean addMunicipality(String username, String password, String email, String creator,
			String cityName, String cityProvince) throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (username != null && password != null && email != null && cityName != null && cityProvince != null && // check
																													// null
																													// values
				username != "" && password != "" && email != "" && cityName != "" && cityProvince != "") // check empty
																											// strings
		{
			try {
				if (creator != null && creator != "") {
					res = UserDatabaseConnector.addMunicipalityByMunicipality(username, password, email, creator,
							cityName, cityProvince);
				} else {
					res = UserDatabaseConnector.addMunicipalityByManager(username, password, email, cityName,
							cityProvince);
				}
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	/**
	 * Calls the function to add Authority inside UserDatabaseConnector this
	 * function checks the validity of the parameters and calls the appropriate
	 * function
	 * 
	 * @param username   : the user name of the Authority to be added
	 * @param password   : the password of the Authority to be added
	 * @param email      : the email address of the Authority to be added
	 * @param creator    : the municipality that is adding the Authority
	 * @param districtId : the district where the Authority works
	 * @return boolean : true if the creation is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean addAuthority(String username, String password, String email, String creator,
			Integer districtId) throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (username != null && password != null && email != null && creator != null && districtId != null && // check
																												// null
																												// values
				username != "" && password != "" && email != "" && creator != "" && districtId >= 0) // check empty
																										// strings(or
																										// invalid
																										// values)
		{
			try {
				res = UserDatabaseConnector.addAuthority(username, password, email, creator, districtId);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	/**
	 * Calls the function to add Manager inside UserDatabaseConnector this function
	 * checks the validity of the parameters and calls the appropriate function
	 * 
	 * @param username  : the user name of the Manager to be added
	 * @param password  : the password of the Manager to be added
	 * @param email     : the email address of the Manager to be added
	 * @param venueName : the venue name of the Manager
	 * @return boolean : true if the creation is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean addManager(String username, String password, String email, String venueName)
			throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (username != null && password != null && email != null && venueName != null && // check null values
				username != "" && password != "" && email != "" && venueName != "") // check empty strings(or invalid
																					// values)
		{
			try {
				res = UserDatabaseConnector.addManager(username, password, email, venueName);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	// ================================================================================
	// city hall and district adders
	// ================================================================================

	/**
	 * Calls the function to add Manager inside UserDatabaseConnector this function
	 * checks the validity of the parameters and calls the appropriate function
	 * 
	 * @param name     : the name of the cityhall to be added
	 * @param province : the name of the province of the cityhall
	 * @param region   : the name of the region where the cityhall is located
	 * @param location : the coordinates of the cityhall
	 * @return boolean : true if the creation is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean addCityhall(String name, String province, String region, Location location)
			throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (name != null && province != null && region != null && location != null && location.getLatitude() != null
				&& location.getLongitude() != null && // check null values
				name != "" && province != "" && region != "") // check empty strings(or invalid values)
		{
			try {
				res = UserDatabaseConnector.addCityhall(name, province, region, location);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding cityhall");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	/**
	 * Calls the function to add District inside UserDatabaseConnector this function
	 * checks the validity of the parameters and calls the appropriate function
	 * 
	 * @param name                : the name of the cityhall in which the district
	 *                            is located
	 * @param province            : the name of the province of the cityhall
	 * @param locationTopLeft     : the coordinates of the topleft corner of the
	 *                            district
	 * @param locationBottomRight :the coordinates of the bottomright corner of the
	 *                            district
	 * @return Integer : the id of the created district, -1 if failed
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static Integer addDistrict(String name, String province, Location locationTopLeft,
			Location locationBottomRight) throws ServerSideDatabaseException, IllegalParameterException {
		Integer res = -1;
		if (name != null && province != null && locationTopLeft != null && locationBottomRight != null
				&& locationTopLeft.getLatitude() != null && locationTopLeft.getLongitude() != null
				&& locationBottomRight.getLatitude() != null && locationBottomRight.getLongitude() != null && // check
																												// null
																												// values
				name != "" && province != "") // check empty strings(or invalid values)
		{
			try {
				res = UserDatabaseConnector.addDistrict(name, province, locationTopLeft, locationBottomRight);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding district");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}
	// ================================================================================
	// city hall getter
	// ================================================================================

	/**
	 * Gets the city hall where a municipality works
	 * 
	 * @param username : the user name of the municipality whose city hall is being
	 *                 searched
	 * @return CityHall
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static CityHall getCityHall(String username)
			throws ServerSideDatabaseException, IllegalParameterException {
		CityHall res = null;
		if (username != null && // check null values
				username != "") // check empty strings(or invalid values)
		{
			try {
				res = UserDatabaseConnector.getCityHall(username);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding cityhall");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	// ================================================================================
	// check user type and credentials
	// ================================================================================

	/**
	 * Calls the function to check user type inside UserDatabaseConnector this
	 * function checks the validity of the parameters and calls the appropriate
	 * function
	 * 
	 * @param username : the user name of the user to be found
	 * @param password : the password of the user
	 * @return UserType : the user type of the user, null if no user with those
	 *         credentials exists
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static UserType checkUserCredentials(String username, String password)
			throws ServerSideDatabaseException, IllegalParameterException {
		UserType res = null;
		if (username != null && password != null && // check null values
				username != "" && password != "") // check empty strings(or invalid values)
		{
			try {
				res = UserDatabaseConnector.checkUserCredentials(username, password);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when checking user type");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	// ================================================================================
	// update user
	// ================================================================================
	/**
	 * Calls the function to update user data inside UserDatabaseConnector this
	 * function checks the validity of the parameters and calls the appropriate
	 * function
	 * 
	 * @param oldUsername : the old user name of the user to be modified
	 * @param oldPassword : the old password of the user
	 * @param user        : the type of the user that must be modified
	 * @param newEmail    : the new email to be set(set to null if no modification
	 *                    needed)
	 * @param newUsername : the new user name to be set
	 * @param newPassword : the new password to be set
	 * @return boolean : true if the update is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean modifyUser(String oldUsername, String oldPassword, UserType user, String newEmail,
			String newUsername, String newPassword) throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (oldUsername != null && oldPassword != null && user != null && newUsername != null && newPassword != null && // check
																														// null
																														// values
				oldUsername != "" && oldPassword != "" && newUsername != "" && newPassword != "") // check empty
																									// strings(or
																									// invalid values)
		{
			try {
				if (newEmail != null && newEmail != "") {
					res = UserDatabaseConnector.modifyUser(oldUsername, oldPassword, user, newEmail, newUsername,
							newPassword);
				} else {
					res = UserDatabaseConnector.modifyUser(oldUsername, oldPassword, user, newUsername, newPassword);
				}
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when modifying user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	/**
	 * Calls the function to update user data inside UserDatabaseConnector this
	 * function checks the validity of the parameters and calls the appropriate
	 * function
	 * 
	 * @param oldUsername : the old user name of the user to be modified
	 * @param user        : the type of the user that must be modified
	 * @param newPassword : the new password to be set
	 * @return boolean : true if the update is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean forgotPassword(String oldUsername, UserType user, String newPassword)
			throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (oldUsername != null && user != null && newPassword != null && // check null values
				oldUsername != "" && newPassword != "") // check empty strings(or invalid values)
		{
			try {

				res = UserDatabaseConnector.forgotPasswordModification(oldUsername,  user,
						newPassword);

			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when modifying user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	// ================================================================================
	// remove user
	// ================================================================================
	/**
	 * Removes a user given its user name and password
	 * 
	 * @param username : the user name of the user to be deleted
	 * @param password : the password of the user to be deleted
	 * @param user     : the user type of the user to be deleted
	 * @return boolean: true if the deletion is successful, false if fails
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean removeUser(String username, String password, UserType user)
			throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (username != null && password != null && user != null && // check null values
				username != "" && password != "") // check empty strings(or invalid values)
		{
			try {
				res = UserDatabaseConnector.removeUser(username, password, user);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when modifying user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	// ================================================================================
	// get User data
	// ================================================================================
	// ================================================================================
	// get email
	// ================================================================================
	/**
	 * Finds the email address of a user
	 * 
	 * @param username : the user name of the user whose email must be retrieved
	 * @return String : the email of the user . null if no user exists with the
	 *         given username
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static String findEmailByUsername(String username)
			throws ServerSideDatabaseException, IllegalParameterException {
		String res = null;
		if (username != null && // check null values
				username != "") // check empty strings(or invalid values)
		{
			try {
				res = UserDatabaseConnector.findEmailByUsername(username);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when modifying user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	// ================================================================================
	// get user name by email
	// ================================================================================
	/**
	 * Finds the user name of a user
	 * 
	 * @param email : the email address of the user whose user name must be
	 *              retrieved
	 * @return String : the user name of the user . null if no user exists with the
	 *         given email
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static String findUsernameByEmail(String email)
			throws ServerSideDatabaseException, IllegalParameterException {
		String res = null;
		if (email != null && // check null values
				email != "") // check empty strings(or invalid values)
		{
			try {
				res = UserDatabaseConnector.findUsernameByEmail(email);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when modifying user");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

}

package maldiniPaone.databaseConnection.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.ViolationType;
import maldiniPaone.utilities.beans.Accident;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.District;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Report;
import maldiniPaone.utilities.beans.Violation;

/**
 * This interface provides all the methods to retrieve, save, modify and delete
 * data provided by the {@link databaseConnection} package
 */
public interface ManageDataAccess {
	// ================================================================================
	// retrieve data
	// ================================================================================
	/**
	 * Gets reports made by a user
	 * 
	 * @param username : user name of the user whose reports are being retrieved
	 * @return List of Reports : the list of reports made by the user
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 */
	public List<Report> getReportsMadeBy(String username) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the list of accidents close to a given location
	 * 
	 * @param location the location where accidents must be searched
	 * @return List of Accidents the list of accidents found in the area
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 */
	public List<Accident> getAccidents(Location location) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the amount of reports made in the last week near a given location
	 * 
	 * @param location the location to which respect the reports are searched
	 * @return Integer the amount of reports
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 */
	public Integer getReportCountInLastWeek(Location location)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the amount of assignments made in the last week near a given location
	 * 
	 * @param location the location to which respect the assignments are searched
	 * @return Integer the amount of assignments
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 */
	public Integer getAssignmentCountInLastWeek(Location lcation)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the list of assignments close to a given location
	 * 
	 * @param location the location where assignments must be searched
	 * @return List of Assignments the list of accidents found in the area
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 */
	public List<Assignment> getAssignments(Location location)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Checks the user credentials
	 * 
	 * @param username the user name of the user to be checked
	 * @param password the password of the user
	 * @return the UserType of the user
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 **/
	public UserType checkUserCredentials(String username, String password)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Retrieves the suggestions made by citizens and authorities in a given
	 * location
	 * 
	 * @param location the location to which respect the suggestions are searched
	 * @return a list of String containing the suggestions found
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 **/
	public List<String> getStaticSuggestions(Location location)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Retrieves the suggestions made by citizens and authorities close to a given
	 * CityHall
	 * 
	 * @param location the location to which respect the suggestions are searched
	 * @return a list of String containing the suggestions found
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 **/
	public List<String> getStaticSuggestions(CityHall cityHall)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Retrieves the closest CityHall with respect to a given location
	 * 
	 * @param location the location to which respect the CityHall is searched
	 * @return a list of String containing the suggestions found
	 * @throws ServerSideDatabaseException if database connection is unsuccessful
	 * @throws IllegalParameterException   if the methods are called on not valid
	 *                                     methods
	 **/
	public CityHall getClosestCityHall(Location location) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the CityHall where a municipality works
	 * 
	 * @param username the user name of the municipality whose CityHall is being
	 *                 searched
	 * @return the closest CityHall if any close enough , null otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public CityHall getCityHall(String username) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the user name of a User given his/her email
	 * 
	 * @param email the email address of the searched user
	 * @return String containing the user name of the searched user, empty string if
	 *         no user was found
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public String findUsernameByEmail(String email) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Gets the email address of a user given his/her user name
	 * 
	 * @param username the username of the searched user
	 * @return String containing the email of the searched user, empty string if no
	 *         user was found
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public String findEmailByUsername(String username) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * gets the list of the violation associated to a given city hall
	 * 
	 * @param name     the name of the city hall
	 * @param province the province in which the city hall is located
	 * @return List of violations associated to the cityhall
	 * @throws IllegalParameterException   when violation type is not supported
	 * @throws ServerSideDatabaseException when database can't be found
	 * @implNote ArrayList is used
	 **/
	public List<Violation> getViolations(CityHall cityHall)
			throws IllegalParameterException, ServerSideDatabaseException;

	// ================================================================================
	// Adding new data
	// ================================================================================
	/**
	 * Adds a report to the database
	 * 
	 * @param report the report to be added
	 * @return the {@link Assignment} created when the Report is added
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public Assignment addNewReport(Report report) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Adds a citizen to the service
	 * 
	 * @param username the username of the citizen to be added
	 * @param password the password of the citizen to be added
	 * @param email    the email of the citizen to be added
	 * @return true if the creation is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean addCitizen(String username, String password, String email)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Adds a Manager to the service
	 * 
	 * @param username  the user name of the Manager to be added
	 * @param password  the password of the Manager to be added
	 * @param email     the email of the Manager to be added
	 * @param vanueName the name of the venue of the Manager
	 * @return true if the creation is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean addManager(String username, String password, String email, String venueName)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Adds a Authority to the service
	 * 
	 * @param username        the user name of the Authority to be added
	 * @param password        the password of the Authority to be added
	 * @param email           the email of the Authority to be added
	 * @param creatorUsername the username of the municipality who adds the
	 *                        authority
	 * @param district        the district where the authority works
	 * @return true if the creation is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean addAuthority(String username, String password, String email, String creatorUsername,
			District district) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Adds a Municipality to the service
	 * 
	 * @param username        the user name of the Municipality to be added
	 * @param password        the password of the Municipality to be added
	 * @param email           the email of the Municipality to be added
	 * @param creatorUsername the username of the municipality who adds the
	 *                        municipality
	 * @param cityHall        the CityHall to which the municipality is associated
	 * @return true if the creation is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean addMunicipality(String username, String password, String email, String creatorUsername,
			CityHall cityHall) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Adds a Municipality to the service
	 * 
	 * @param username        the user name of the Municipality to be added
	 * @param password        the password of the Municipality to be added
	 * @param email           the email of the Municipality to be added
	 * @param creatorUsername the username of the manager who adds the municipality
	 * @param cityHall        the CityHall to which the municipality is associated
	 *                        and must be created
	 * @return true if the creation is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean addMunicipalityAndCityHall(String username, String password, String email, String creatorUsername,
			CityHall CityHall) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Adds a suggestion to a CityHall
	 * 
	 * @param suggestion the suggestion content
	 * @param cityHall   the CityHall to which the suggestion must be associated
	 * @return true if the creation is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean addSuggestion(String suggestion, CityHall cityHall)
			throws ServerSideDatabaseException, IllegalParameterException;

	// ================================================================================
	// Modify already available data
	// ================================================================================

	/**
	 * Modify User credentials
	 * 
	 * @param username    the old username of the user
	 * @param password    the old password of the user
	 * @param user        the UserType of the user
	 * @param email       the new email address of the user
	 * @param newUsername the new username of the User
	 * @param newPassword the new password of the user
	 * @return true if the update is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean modifyUser(String username, String password, UserType user, String email, String newUsername,
			String newPassword) throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Modify Assignment state
	 * 
	 * @param assignmentId the id of the assignment to be modified
	 * @param state        the new state of the assignment
	 * @param username     the username of the user who is modifying the state
	 * @param type         the ViolationType of the assignment
	 * @return true if the update is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean updateAssignment(Integer assignmentId, State state, String username, ViolationType type)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Modify Account when forgot old password
	 * 
	 * @param oldUsername : the old user name of the user to be modified
	 * @param user        : the type of the user that must be modified
	 * @param newPassword : the new password to be set
	 * @return boolean : true if the update is successful, false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean forgotPassword(String oldUsername, UserType user, String newPassword)
			throws ServerSideDatabaseException, IllegalParameterException;

	// ================================================================================
	// Delete data
	// ================================================================================
	/**
	 * Delete a user
	 * 
	 * @param username the old username of the user
	 * @param password the old password of the user
	 * @param user     the UserType of the user
	 * @return true if the deletion is successful , false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	public boolean removeUser(String username, String password, UserType user)
			throws ServerSideDatabaseException, IllegalParameterException;

}

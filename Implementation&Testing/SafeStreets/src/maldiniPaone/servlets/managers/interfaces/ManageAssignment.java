package maldiniPaone.servlets.managers.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.ViolationType;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.Location;

/**
 * This interface defines the signatures of the methods to retrieve the
 * assignments and to update assignment state
 **/
public interface ManageAssignment {
	/**
	 * Gets the list of assignment close to a given location
	 * 
	 * @param location the location to which respect the assignment are searched
	 * @return the list of Assignment found close to the given location
	 * @throws ServerSideDatabaseException when database can't be accessed
	 * @throws IllegalParameterException   when provided parameters aren't valid
	 **/
	public List<Assignment> getAssignment(Location location)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Allows a User to refuse an assignment
	 * 
	 * @param id       the id of the assignment to be refused
	 * @param username the username of user who refuses the assignment
	 * @return true if it succeeds to refuse, false otherwise
	 * @throws ServerSideDatabaseException when database can't be accessed
	 * @throws IllegalParameterException   when provided parameters aren't valid
	 **/
	public boolean refuseAssignment(Integer id, String username)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Allows a User to accept an assignment
	 * 
	 * @param id       the id of the assignment to be accepted
	 * @param username the username of user who accepts the assignment
	 * @return true if it succeeds to refuse, false otherwise
	 * @throws ServerSideDatabaseException when database can't be accessed
	 * @throws IllegalParameterException   when provided parameters aren't valid
	 **/
	public boolean acceptAssignment(Integer id, String username)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Allows a User to terminate an assignment
	 * 
	 * @param id          the id of the assignment to be terminated
	 * @param username    the username of user who terminates the assignment
	 * @param finishState the status with which the assignment terminates
	 * @param type        the type of violation which is terminated
	 * @return true if it succeeds to terminate, false otherwise
	 * @throws ServerSideDatabaseException when database can't be accessed
	 * @throws IllegalParameterException   when provided parameters aren't valid
	 **/
	public boolean terminateAssignment(Integer id, String username, State finishState, ViolationType type)
			throws ServerSideDatabaseException, IllegalParameterException;

	/**
	 * Check if there are active assignment for the authority
	 * 
	 * @param username : the user name of the authority
	 * @return list of the ids of active assignments
	 * @throws ServerSideDatabaseException when database can't be found
	 * @throws IllegalParameterException   if parameters are not valid
	 */
	public Assignment checkActive(String username) throws ServerSideDatabaseException, IllegalParameterException;

}

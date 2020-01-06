package maldiniPaone.databaseConnection;

import java.sql.Timestamp;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.ViolationType;
import maldiniPaone.utilities.beans.Location;

/**
 * This class contains protected static methods which are used to update data
 * about reports and assignments. Those methods checks parameters values to
 * check data validity and avoid inconsistent data. All methods may throw
 * exceptions if database can't be reached or parameters are not valid. The
 * actual communication with the database happens in
 * {@link ReportAndAssignmentDatabaseConnector}.
 * 
 * @see ReportAndAssignmentDatabaseConnector
 **/
public class ReportAndAssignmentUpdater {

	// ================================================================================
	// ReportCreation Adder
	// ================================================================================
	/**
	 * Calls the method inside ReportAndAssignmentDatabaseConnector which adds a
	 * report to the database Checks the parameter validity to avoid meaningless
	 * connections to the database.
	 * 
	 * @param username     : the user name of the user who makes the report
	 * @param time         : the timestamp corresponding to the report
	 * @param location     : the location of the report
	 * @param note         : possible notes added by user
	 * @param licencePlate : the license plate in the report in String form
	 * @return boolean : true if insertion is successful false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static Integer addReport(String username, Timestamp time, Location location, String note,
			String licensePlate) throws ServerSideDatabaseException, IllegalParameterException {
		Integer res = -1;
		if (username != null && time != null && location != null && licensePlate != null
				&& location.getLatitude() != null && location.getLongitude() != null && // check null values
				username != "") // check empty strings
		{
			if (note == null)
				note = "";
			try {
				res = ReportAndAssignmentDatabaseConnector.addReport(username, time, location, note, licensePlate);
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding report");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	// ================================================================================
	// AssignmentServlet modifier
	// ================================================================================
	/**
	 * Calls the method inside ReportAndAssignmentDatabaseConnector which updates a
	 * report's state Checks the parameter validity to avoid meaningless connections
	 * to the database.
	 * 
	 * @param username : the user name of the user who is modifying the state
	 * @param id       : the id of the assignment to be modified
	 * @param newState : the new state of the assignment
	 * @return boolean : true if update is successful false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean updateAssignment(Integer id, String username, ViolationType type, State newState)
			throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (username != null && id != null && newState != null && // check null values
				id != -1 && username != "") // check empty strings
		{
			try {
				switch (newState) {
				case Accepted:
					res = ReportAndAssignmentDatabaseConnector.takeAssignment(id, username);
					break;
				case Pending:
					res = ReportAndAssignmentDatabaseConnector.refuseAssignment(id, username);
					break;
				default:
					res = ReportAndAssignmentDatabaseConnector.finishAssignment(id, username,
							(type != null) ? type.toString() : "", newState);
					break;// if no type than set empty type
				}
			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when modifying assignment state");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

	// ================================================================================
	// Suggestion adder
	// ================================================================================
	/**
	 * Calls the method inside ReportAndAssignmentDatabaseConnector which adds a
	 * suggestion Checks the parameter validity to avoid meaningless connections to
	 * the database.
	 * 
	 * @param suggestion : String containing the text of the suggestion
	 * @param name       : the name of the cityhall to which the suggestion is
	 *                   related
	 * @param province   : the new state of the assignment
	 * @return boolean : true if insertion is successful false otherwise
	 * @throws ServerSideDatabaseException when the database can't be found
	 * @throws IllegalParameterException   when parameters are not valid(empty or
	 *                                     null)
	 **/
	protected static boolean AddSuggestions(String suggestion, String name, String province)
			throws ServerSideDatabaseException, IllegalParameterException {
		boolean res = false;
		if (suggestion != null && name != null && province != null && // check null values
				suggestion != "" && name != "" && province != "") // check empty strings
		{
			try {
				ReportAndAssignmentDatabaseConnector.AddSuggestions(suggestion, name, province);

			} catch (DatabaseNotFoundException e) {
				throw new ServerSideDatabaseException(e, "database not found when adding a suggestion");
			}
		} else {
			throw new IllegalParameterException();
		}
		return res;
	}

}

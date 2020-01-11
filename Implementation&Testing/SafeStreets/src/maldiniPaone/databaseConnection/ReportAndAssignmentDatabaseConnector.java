package maldiniPaone.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.ViolationType;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Photo;
import maldiniPaone.utilities.beans.Report;
import maldiniPaone.utilities.beans.Violation;
import maldiniPaone.utilities.constants.Constants;

/**
 * This class connects with the database using connections generated by
 * {@link ConnectionPool}. This class's methods gets and saves useful data for
 * report and assignment. All methods uses prepared statements to avoid
 * possibility of SQL injections. Results are encapsulated in objects defined in
 * {@link maldiniPaone.utilities}
 **/
public class ReportAndAssignmentDatabaseConnector {

	// ================================================================================
	// ReportCreation creation and retrieve
	// ================================================================================
	/**
	 * Adds report to the database. Gets connection from connection pool and uses it
	 * to execute the insertion.
	 * 
	 * @param username     : the user name of the user who makes the report
	 * @param time         : the time stamp corresponding to the report
	 * @param location     : the location of the report
	 * @param note         : possible notes added by user
	 * @param licensePlate : the license plate in the report in String form
	 * @return Integer: the primary key of the bridge table between assignment and
	 *         report, -1 if the insertion is not possible
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 */
	protected static Integer[] addReport(String username, Timestamp time, Location location, String note,
			String licensePlate) throws DatabaseNotFoundException {
		Integer[] res = { -1, -1, -1 };
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("insert into report " // insert new report
					+ "(`maker`,`datetime`,`latitude`,`longitude`,`note`,`car`) " + "values (?,?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);// return generated keys
			// set the values in the prepared statements avoid SQL injection
			ps.setString(1, username);
			ps.setTimestamp(2, time);
			ps.setFloat(3, location.getLatitude());
			ps.setFloat(4, location.getLongitude());
			ps.setString(5, note);
			ps.setString(6, licensePlate);
			// execute update
			ps.executeUpdate();
			// get id of the last row inserted if fails throws an exception
			ResultSet rs = ps.getGeneratedKeys();
			int i = 0;
			while (rs.next())// get all the generated keys
			{
				res[i++] = rs.getInt(1);
			}
			// close result set and prepared statement
			rs.close();
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (Constants.VERBOSE)
				e.printStackTrace();
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}

	/**
	 * Gets a List of reports done by the user. Gets connection from connection pool
	 * and uses it to execute the insertion.
	 * 
	 * @param username : the user name of the user who made the reports
	 * @return List of Reports : list of reports null if not reports found
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 * @throws IllegalParameterException if not valid coordinates are saved
	 */
	protected static List<Report> getReportsMadeBy(String username)
			throws DatabaseNotFoundException, IllegalParameterException {
		List<Report> res = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("select datetime,latitude,longitude,note,car " // get reports data
					+ "from report " + "where maker=? " // made by the given user
					+ "order by datetime desc" // ordered by date
					+ " LIMIT " + Constants.STANDARD_QUERY_LIMIT); // limited to the last ones
			// set maker of the reports
			ps.setString(1, username);
			// execute selection
			ps.execute();
			// get result set
			rs = ps.getResultSet();
			res = new ArrayList<Report>();
			while (rs.next()) // for each row of the result set build a report and add to the return value
			{
				Report temp = buildReportFromResultSet(rs);// uses the current line of the
				res.add(temp);
			}
			// close statement
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (IllegalParameterException e) {
			try {
				ConnectionPool.getInstance().releaseConnection(c);
				ps.close();
			} catch (Exception ex) {
				/* database didn't close the statement */
			}
			if (Constants.VERBOSE)
				e.printStackTrace();
			throw e;
		} catch (Exception e) {
			if (Constants.VERBOSE)
				e.printStackTrace();
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}

	/**
	 * Gets the number of reports done close to a given location. Gets connection
	 * from connection pool and uses it to execute the insertion.
	 * 
	 * @param location : the location to which respect the reports are searched
	 * @return Integer : the number of reports (0 if no match)
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	public static Integer getReportCountInLastWeek(Location location) throws DatabaseNotFoundException {
		Integer res = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("select count(*) " + " from report as rep" // count reports
					+ " where TimestampDiff(DAY,rep.datetime,current_timestamp())<=7"// within the last 7 days
					+ " and " + squareAreaCloseTo("rep", location, Constants.STATISTICS_RADIUS) // within a certain
																								// radius
					+ " order by rep.datetime");// ordered by the date the reports were made
			// execute the query
			ps.execute();
			// get result set
			rs = ps.getResultSet();
			while (rs.next()) {
				res = rs.getInt(1);// get the number of reports
			}
			// close result statement and prepared statement
			rs.close();
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (Constants.VERBOSE)
				e.printStackTrace();
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}

	// ================================================================================
	// Assignments
	// ================================================================================
	// ================================================================================
	// AssignmentServlet modifiers(take,finish,refuse)
	// ================================================================================
	/**
	 * Assigns an AssignmentServlet to an authority
	 * 
	 * @param id       : the id of the assignment to be taken
	 * @param username : the user name of the authority taking the assignment
	 * @return boolean : true if the action is successful; false otherwise
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	protected static boolean takeAssignment(Integer id, String username) throws DatabaseNotFoundException {
		boolean res = false;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("update assignment as assign join "
					+ " assignmentreportbridge as bridge" // update assignment
					+ " set assign.state=? , assign.appointee=? " 
					+ " where assign.id=bridge.idassignment "
					+ " and bridge.id=?");
			// set the values in the prepared statements avoid SQL injection
			ps.setString(1, State.Accepted.toString());
			ps.setString(2, username);
			ps.setInt(3, id);
			// execute update
			ps.executeUpdate();
			// if fails throws an
			res = true;
			// close statement
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (Constants.VERBOSE)
				e.printStackTrace();
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}

	/**
	 * Terminate an AssignmentServlet
	 * 
	 * @param id          : the id of the assignment to be terminated
	 * @param username    : the user name of the authority who terminated the
	 *                    assignment
	 * @param type        : the type of the violation
	 * @param finishState : the state in which the user
	 * @return boolean : true if the action is successful; false otherwise
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	protected static boolean finishAssignment(Integer id, String username, String type, State finishState)
			throws DatabaseNotFoundException {
		boolean res = false;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().getConnection(); // get connection
			ps = c.prepareStatement("update assignment as assign join "
					+ " assignmentreportbridge as bridge" // update assignment
					+ " set assign.state=? ,assign.typeofviolation=?" 
					+ " where assign.id=bridge.idassignment "
					+ "	and assign.appointee=?"
					+ " and bridge.id=?");
			// set the values in the prepared statements avoid SQL injection
			ps.setString(1, finishState.toString());
			ps.setString(2, type);
			ps.setString(3, username);
			ps.setInt(4, id);

			// execute update
			ps.executeUpdate();
			// if fails throws an exception
			res = true;
			// close statement
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			if (Constants.VERBOSE)
				e.printStackTrace();
			return res;
		}
		return res;
	}

	/**
	 * Refuse an AssignmentServlet
	 * 
	 * @param id       : the id of the assignment to be refused
	 * @param username : the user name of the authority who is refusing
	 * @return boolean : true if the action is successful; false otherwise
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	protected static boolean refuseAssignment(Integer id, String username) throws DatabaseNotFoundException {
		boolean res = false;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("update assignment as assign join "
					+ " assignmentreportbridge as bridge" // update assignment
					+ " set assign.state=? , assign.appointee=? " 
					+ " where assign.id=bridge.idassignment "
					+ "	and assign.state=?"
					+ " and bridge.id=?");
			// set the values in the prepared statements avoid sql injection
			ps.setString(1, State.Pending.toString());
			ps.setString(2, username);
			ps.setString(3, State.Accepted.toString());
			ps.setInt(4, id);
		
			// execute update
			ps.executeUpdate();
			// if fails throws an exception
			res = true;
			// close statement
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			if (Constants.VERBOSE)
				e.printStackTrace();
			return res;
		}
		return res;
	}

	// ================================================================================
	// AssignmentServlet list getter
	// ================================================================================
	/**
	 * gets the list of the assignment associated with the last 100 photos submitted
	 * for assignments close to the location given as parameter
	 * 
	 * @param location : the location close to which the assignments are searched
	 * @return List of Assignments : the assignment found are returned in a List
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 * @throws IllegalParameterException if not consistent values are in the
	 *                                   database
	 **/
	protected static List<Assignment> getAssignments(Location location)
			throws DatabaseNotFoundException, IllegalParameterException {
		List<Assignment> res = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("select assign.id,ph.image,rep.note,rep.latitude,rep.longitude "
					// join of report assignment bridge table and photos
					+ " from (((assignment as assign join assignmentreportbridge as arb on assign.id=arb.idassignment)"
					+ " join report as rep on rep.id=arb.idreport)" + " join photo as ph on ph.idreport=rep.id)"
					+ " where " + closeTo("rep", location) + " and assign.state=?" // close to the location
					+ " order by assign.id DESC, arb.timestamp DESC " // ordered by assignment id and timestamp
					+ " LIMIT " + Constants.STANDARD_QUERY_LIMIT * 3// consider an average of 3 photos for report
			// limit result to last 3*standard limit results
			);
			// set the values in the prepared statements avoid sql injection
			ps.setString(1, State.Pending.toString());
			// execute qurey
			ps.execute();
			rs = ps.getResultSet();// get result set
			res = new ArrayList<Assignment>();
			res = buildAssignmentFromResultSet(rs);// builds actual result set
			// close statement
			ps.close();
			// release the connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (IllegalParameterException e) {
			try {
				ConnectionPool.getInstance().releaseConnection(c);
				ps.close();
			} catch (Exception ex) {
				/* database didn't close the statement */}
			if (Constants.VERBOSE)
				e.printStackTrace();
			throw e;
		} catch (Exception e) {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			if (Constants.VERBOSE)
				e.printStackTrace();
			return res;
		}
		return res;
	}

	// ================================================================================
	// AssignmentServlet count
	// ================================================================================
	/**
	 * Gets the number of assignments done close to a given location. Gets
	 * connection from connection pool and uses it to execute the insertion.
	 * 
	 * @param location : the location to which respect the assignments are searched
	 * @return Integer : the number of reports (0 if no match)
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	protected static Integer getAssignmentCountInLastWeek(Location location) throws DatabaseNotFoundException {
		Integer res = 0;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("select COUNT(DISTINCT(arb.idassignment))" // count assignment
					+ " from assignment as assign join assignmentreportbridge as arb "
					+ " on arb.idassignment=assign.id" + " join report as re " + " on re.id=arb.idreport"
					+ " where TimestampDiff(DAY,arb.timestamp,current_timestamp())<=7" + " and "
					+ squareAreaCloseTo("re", location, Constants.STATISTICS_RADIUS));
			// execute query
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) // get the count of the assignment
			{
				res = rs.getInt(1);
			}
			// close statement
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (Constants.VERBOSE)
				e.printStackTrace();
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}
	// ================================================================================
	// Check Active State
	// ================================================================================

	/**
	 * Check if there are active assignment for the authority
	 * 
	 * @param username : the user name of the authority
	 * @return list of active assignments id
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 * @throws IllegalParameterException if not valid coordinates are saved
	 */
	protected static List<Integer> checkActive(String username)
			throws DatabaseNotFoundException, IllegalParameterException {
		List<Integer> res = new ArrayList<Integer>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("select id " //
					+ "from assignment " + "where appointee=? and state=?");
			// set maker of the reports
			ps.setString(1, username);
			ps.setString(2, State.Accepted.toString());
			// execute selection
			ps.execute();
			// get result set
			rs = ps.getResultSet();
			while (rs.next()) {
				// if no result than it is not active
				res.add(rs.getInt(1));
			}
			// close statement
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (Constants.VERBOSE)
				e.printStackTrace();
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			return res;
		}
		return res;
	}

	// ================================================================================
	// Get Suggestions
	// ================================================================================
	/**
	 * gets the list of the last suggestions for a given city hall
	 * 
	 * @param name     : the name of the city hall
	 * @param province : the province in which the city hall is located
	 * @return List of Strings : the suggestions given by Citizens and Authorities
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	protected static List<String> getSuggestions(String name, String province) throws DatabaseNotFoundException {
		List<String> res = new ArrayList<String>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("select note " // get notes from suggestion table
					+ " from suggestion" + " where name=? and province=? "
					+ " and TimestampDiff(DAY,timestamp,current_timestamp())<=30" // last month
					+ " order by id DESC LIMIT " + Constants.STANDARD_QUERY_LIMIT);
			// set the values in the prepared statements avoid sql injection
			ps.setString(1, name);
			ps.setString(2, province);
			// execute query
			ps.execute();
			rs = ps.getResultSet();// get result
			while (rs.next()) {
				res.add(rs.getString(1));// for each suggestion adds it to the result
			}
			// close result set and statement
			rs.close();
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			if (Constants.VERBOSE)
				e.printStackTrace();
			return res;
		}
		return res;
	}

	// ================================================================================
	// Add Suggestions
	// ================================================================================
	/**
	 * Adds a suggestion to a given city hall
	 * 
	 * @param suggestion : the text of the suggestion to be added
	 * @param name       : the name of the city hall
	 * @param province   : the province in which the city hall is located
	 * @return boolean : true if the insertion is successful false otherwise
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	protected static boolean AddSuggestions(String suggestion, String name, String province)
			throws DatabaseNotFoundException {
		boolean res = false;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("insert into suggestion" // add suggestion
					+ "(name,province,note) values (?,?,?)");
			// set the values in the prepared statements avoid sql injection
			ps.setString(1, name);
			ps.setString(2, province);
			ps.setString(3, suggestion);
			// execute update
			ps.executeUpdate();
			/// close statement
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			if (Constants.VERBOSE)
				e.printStackTrace();
			return res;
		}
		return res;
	}

	// ================================================================================
	// Get Violation Type Count
	// ================================================================================
	/**
	 * gets the list of the violation associated to a given city hall
	 * 
	 * @param name     the name of the city hall
	 * @param province the province in which the city hall is located
	 * @return List of violations associated to the cityhall
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 * @throws IllegalParameterException when violation type is not supported
	 **/
	protected static List<Violation> getViolations(String name, String province)
			throws DatabaseNotFoundException, IllegalParameterException {
		List<Violation> res = new ArrayList<Violation>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("select violationtype , count  " // get notes from suggestion table
					+ " from violation where cityhall_name=?  " + " and cityhall_province=?" + "order by count DESC ");
			// set the values in the prepared statements avoid sql injection
			ps.setString(1, name);
			ps.setString(2, province);
			// execute query
			ps.execute();
			rs = ps.getResultSet();// get result
			while (rs.next()) {
				Violation temp = new Violation();
				ViolationType vType = ViolationType.fromString(rs.getString(1));
				if (vType == null) {
					throw new IllegalParameterException();
				}
				temp.setViolationType(vType);
				temp.setCount(rs.getInt(2));
				res.add(temp);
			}
			// close result set and statement
			rs.close();
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (IllegalParameterException e) {
			try {
				ConnectionPool.getInstance().releaseConnection(c);
				ps.close();
			} catch (Exception ex) {
				/* database didn't close the statement */}
			if (Constants.VERBOSE)
				e.printStackTrace();
			throw e;
		} catch (Exception e) {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			if (Constants.VERBOSE)
				e.printStackTrace();
			return res;
		}
		return res;
	}
	// ================================================================================
	// photo adder
	// ================================================================================

	/**
	 * Saves the photo name on the database
	 * 
	 * @param name     : the name of the photo
	 * @param reportId : the id of the report to which the photo must be associated
	 * 
	 * @return boolean : true if insertion is successful false otherwise
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	public static boolean addNewPhoto(String name, int reportId) throws DatabaseNotFoundException {
		boolean res = false;
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("insert into photo" // add photo
					+ "(idreport,image) values (?,?)");
			// set the values in the prepared statements avoid sql injection
			ps.setInt(1,reportId);
			ps.setString(2, name);
			// execute update
			ps.executeUpdate();
			/// close statement
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			if (Constants.VERBOSE)
				e.printStackTrace();
			return res;
		}
		return res;
	}

	// ================================================================================
	// Support functions
	// ================================================================================
	// ================================================================================
	// Closest city hall
	// ================================================================================
	/**
	 * gets the closest city hall to the given location
	 * 
	 * @param location : the location with respect to the search must be done
	 * @return CityHall : the closest city hall, null if no city hall close was
	 *         found
	 * @throws DatabaseNotFoundException the connection to the database could not be
	 *                                   instantiated
	 **/
	protected static CityHall getClosestCityhall(Location location) throws DatabaseNotFoundException {
		CityHall res = null;
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().getConnection();// get connection
			ps = c.prepareStatement("select cityhall_name, cityhall_province" // get city hall
					+ "from cityhall" + "where " + closeTo("cityhall", location, Constants.CLOSE_CITYHALL) + "order by "
					+ SquareDistance("cityhall", location) + " DESC LIMIT 1");// get the one with less
																				// distance
			// execute query
			ps.execute();
			rs = ps.getResultSet();
			if (rs.next())// if a city hall is in the result set create an object to contain it
			{
				res = new CityHall();
				res.setName(rs.getString(1));
				res.setProvince(rs.getString(2));
			}
			// close result set and statement
			rs.close();
			ps.close();
			// release connection
			ConnectionPool.getInstance().releaseConnection(c);
		} catch (DatabaseNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception ex) {
					/* database didn't close the statement */}
			if (c != null)
				ConnectionPool.getInstance().releaseConnection(c);
			if (Constants.VERBOSE)
				e.printStackTrace();
			return res;
		}
		return res;
	}

	// ================================================================================
	// Distance functions
	// ================================================================================

	/**
	 * defines a String in for an SQL condition for indicating a place near to the
	 * specified location
	 * 
	 * @param base     : this variable is the name of the table in which a location
	 *                 is stored. it must contain a longitude and a latitude column
	 * @param location : the location with which the distance must be computed
	 * @return String : represents the condition in String usable in a mySql query
	 */
	private static String closeTo(String base, Location location) {
		return closeTo(base, location, Constants.EUCLIDEAN_CLOSE_DISTANCE);
	}

	/**
	 * defines a String in for an MySql condition for indicating a place near to the
	 * specified location
	 * 
	 * @param base     : this variable is the name of the table in which a location
	 *                 is stored. it must contain a longitude and a latitude column
	 * @param location : the location with which the distance must be computed
	 * @param radius   : the "radius" in which a place is considered close
	 * @return String : represents the condition in String usable in a MySql query
	 */
	private static String closeTo(String base, Location location, Float radius) {

		return SquareDistance(base, location) + "<=" + (radius * radius) + " ";
		// compare square of distance with square the radius
		// we don't use the root operation on the result for performance.
	}

	/**
	 * defines a String in for an MySql condition for indicating a place near to the
	 * specified location
	 * 
	 * @param base     : this variable is the name of the table in which a location
	 *                 is stored. it must contain a longitude and a latitude column
	 * @param location : the location with which the distance must be computed
	 * @param edge     : the "radius" in which a place is considered close
	 * @return String : represents the condition in String usable in a MySql query
	 */
	private static String squareAreaCloseTo(String base, Location location, Float edge) {
		return "ABS(" + base + ".latitude-" + location.getLatitude() + ")<" + edge + " and " + "ABS(" + base
				+ ".longitude-" + location.getLongitude() + ")<" + edge;
	}

	/**
	 * defines a String for representing the square of distance in MySQL condition
	 * 
	 * @param base     : this variable is the name of the table in which a location
	 *                 is stored. it must contain a longitude and a latitude column
	 * @param location : the location with which the distance must be computed
	 * @return String : represents the square distance usable in MySQL condition
	 **/
	private static String SquareDistance(String base, Location location) {
		return "power(" + base + ".latitude-" + location.getLatitude() + ",2)+" + "power(" + base + ".longitude-"
				+ location.getLongitude() + ",2)";
	}

	// ================================================================================
	// Use Result Sets to build useful data
	// ================================================================================

	/**
	 * builds report from a row of the result set
	 * 
	 * @param rs : the result set set on the correct row
	 * @return ReportCreation : the report corresponding to the row of the result
	 *         set
	 * @throws SQLException              specified field are not available
	 * @throws IllegalParameterException if the location in a report has invalid
	 *                                   values
	 */
	private static Report buildReportFromResultSet(ResultSet rs) throws SQLException, IllegalParameterException {
		// initializations
		Report res = new Report();
		Location loc = new Location();
		// setting bean states
		res.setDate(rs.getTimestamp(1));
		loc.setLatitude(rs.getFloat(2));
		loc.setLongitude(rs.getFloat(3));
		res.setLocation(loc);
		res.setNote(rs.getString(4));
		res.setLicensePlate(rs.getString(5));
		return res;
	}

	/**
	 * builds list of Assignments from a result rest
	 * 
	 * @param rs : the result set from which the list is created
	 * @return List of AssignmentServlet : list of assignment in the result set,
	 *         empty list if the result contains nothing
	 * @throws SQLException              specified field are not available
	 * @throws IllegalParameterException if location in an assignment is not valid
	 **/
	private static List<Assignment> buildAssignmentFromResultSet(ResultSet rs)
			throws SQLException, IllegalParameterException {

		// initialize return value+ check exit condition
		List<Assignment> res = new ArrayList<Assignment>(); // initialize empty list
		if (!rs.next())
			return res;// if result set is empty returns

		// variable initialization
		Assignment assignment = new Assignment(); // creates an assignment
		List<Report> reports = new ArrayList<Report>();
		Report report = new Report();
		List<Photo> images = new ArrayList<Photo>();
		Location lastLocation = new Location();
		Photo photo = new Photo();

		// get values from first result set row
		Integer id = rs.getInt(1);
		String lastNote = rs.getString(3);
		Float latitude = rs.getFloat(4);
		Float longitude = rs.getFloat(5);
		photo.setName(rs.getString(2));

		// adding elements to the lists
		res.add(assignment);// adds the assignment into the result set
							// stores id to control later when the id changes to understand
							// when a new assignment begins
		reports.add(report);
		images.add(photo);

		// setting state of beans
		assignment.setState(State.Pending);
		assignment.setId(id);
		assignment.setReports(reports);
		report.setPhotos(images);// set the reference to the list
		report.setNote(lastNote);
		lastLocation.setLatitude(latitude);
		lastLocation.setLongitude(longitude);
		report.setLocation(lastLocation);
		while (rs.next()) {
			Integer newId = rs.getInt(1);
			if ((newId != id) || (latitude != rs.getFloat(4) || longitude != rs.getFloat(5)))
			// new assignment or new report
			{
				if (newId != id)// new assignment so it is also a new report
				{
					// modifying values
					assignment = new Assignment();
					reports = new ArrayList<Report>();
					id = newId;
					// setting beans state
					assignment.setState(State.Pending);
					assignment.setId(id);
					assignment.setReports(reports);
					// adding values to lists
					res.add(assignment);
				}
				// modifying values
				report = new Report(); // section in common for both new assignment and new report
				images = new ArrayList<Photo>();
				lastLocation = new Location();
				// get values from first result set row
				lastNote = rs.getString(3);
				latitude = rs.getFloat(4);
				longitude = rs.getFloat(5);
				// setting beans state
				report.setNote(lastNote);
				report.setPhotos(images);
				lastLocation.setLatitude(latitude);
				lastLocation.setLongitude(longitude);
				report.setLocation(lastLocation);
				// adding values to lists
				reports.add(report);
			}
			photo = new Photo();
			photo.setName(rs.getString(2));
			// adding element to List
			images.add(photo);// if an already inserted report it must only add the photo
								// part in common for all the possible situations
		}
		return res;
	}

}

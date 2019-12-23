package maldiniPaone.databaseConnection;
import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.databaseConnection.databaseExceptions.InvalidParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Report;

public class DataCollector {
	
	//================================================================================
    // Static variables
    //================================================================================	
	//Uncomment if you find a useful point to debug code here
	//private static boolean verbose=true;//TODO set to false on release
	
	
	//================================================================================
    // Get Report count  and list of reports
    //================================================================================
	/**
	 *  Gets the number of reports done close to a given location.Calls the appropriate method in
	 *  ReportAndAssignmentDatabaseConnector and checks the validity of the parameters
	 *  @param location : the location to which respect the reports are searched
	 *  @return Integer : the number of reports (0 if no match)
	 *  @throws ServerSideDatabaseException when the database can't be found
	 *  @throws InvalidParameterException when parameters are not valid(empty or null) 
	 **/
	protected static Integer getReportCountInLastWeek(Location location) throws ServerSideDatabaseException, InvalidParameterException
	{
		Integer res = 0;
		if(location!=null&&location.getLatitude()!=null&&location.getLongitude()!=null)//check null values 
			{
				try
				{
					res=ReportAndAssignmentDatabaseConnector.getReportCountInLastWeek(location);
				}
				catch(DatabaseNotFoundException e)
				{
					throw new ServerSideDatabaseException(e,"database not found when counting reports");
				}
			}	
			else
			{
				throw new InvalidParameterException();
			}	
		return res;
	}
	
	/**
	 *  Gets the List of reports done by a user.Calls the appropriate method in
	 *  ReportAndAssignmentDatabaseConnector and checks the validity of the parameters
	 *  @param Username : the username whose reports are being searched
	 *  @return List of Reports : the List of reports of the user 
	 *  						  empty List if none is found
	 *  @throws ServerSideDatabaseException when the database can't be found
	 *  @throws InvalidParameterException when parameters are not valid(empty or null) 
	 **/
	protected static List<Report> getReportsMadeBy(String Username) throws ServerSideDatabaseException, InvalidParameterException
	{
		List<Report> res = null;
		if(Username!=null&&Username!="")
			{
				try
				{
					res=ReportAndAssignmentDatabaseConnector.getReportsMadeBy(Username);
				}
				catch(DatabaseNotFoundException e)
				{
					throw new ServerSideDatabaseException(e,"database not found when finding reports");
				}
			}	
			else
			{
				throw new InvalidParameterException();
			}	
		return res;
	}
	//================================================================================
    // Get Assignment count  and list of Assignment
    //================================================================================
	/**
	 *  Gets the number of Assignments done close to a given location.Calls the appropriate method in
	 *  ReportAndAssignmentDatabaseConnector and checks the validity of the parameters
	 *  @param location : the location to which respect the assignments are searched
	 *  @return Integer : the number of assignments (0 if no match)
	 *  @throws ServerSideDatabaseException when the database can't be found
	 *  @throws InvalidParameterException when parameters are not valid(empty or null) 
	 **/
	protected static Integer getAssignmentsCountInLastWeek(Location location) throws ServerSideDatabaseException, InvalidParameterException
	{
		Integer res = 0;
		if(location!=null&&location.getLatitude()!=null&&location.getLongitude()!=null)//check null values 
			{
				try
				{
					res=ReportAndAssignmentDatabaseConnector.getAssignmentCountInLastWeek(location);
				}
				catch(DatabaseNotFoundException e)
				{
					throw new ServerSideDatabaseException(e,"database not found when counting reports");
				}
			}	
			else
			{
				throw new InvalidParameterException();
			}	
		return res;
	}
	
	/**
	 *  Gets the List of pending Assignments in a given location.Calls the appropriate method in
	 *  ReportAndAssignmentDatabaseConnector and checks the validity of the parameters
	 *  @param location : the location to which respect the assignments are being searched
	 *  @return List of Assignment : the List of assignment in the location 
	 *  						  empty List if none is found
	 *  @throws ServerSideDatabaseException when the database can't be found
	 *  @throws InvalidParameterException when parameters are not valid(empty or null) 
	 **/
	protected static List<Assignment> getAssignment(Location location) throws ServerSideDatabaseException, InvalidParameterException
	{
		List<Assignment> res = null;
		if(location!=null&&location.getLatitude()!=null&&location.getLongitude()!=null)
			{
				try
				{
					res=ReportAndAssignmentDatabaseConnector.getAssignments(location);
				}
				catch(DatabaseNotFoundException e)
				{
					throw new ServerSideDatabaseException(e,"database not found when finding assignments");
				}
			}	
			else
			{
				throw new InvalidParameterException();
			}	
		return res;
	}
	//================================================================================
    // Get Suggestions
    //================================================================================
	
	/**
	 * Gets the List of static suggestions made by user to a cityhall
	 *  @param name : the name of the cityhall
	 *  @param province : the name of the province where the cityhall is located
	 *  @return List of Assignment : the List of static suggestions for the cityhall
	 *  						  empty List if none is found
	 *  @throws ServerSideDatabaseException when the database can't be found
	 *  @throws InvalidParameterException when parameters are not valid(empty or null) 
	 **/
	
	protected static List<String> getSuggestion(String name,String province) throws ServerSideDatabaseException, InvalidParameterException
	{
		List<String> res = null;
		if(name!=null&&province!=null&&
				name!=""&&province!="")
			{
				try
				{
					res=ReportAndAssignmentDatabaseConnector.getSuggestions(name, province);
				}
				catch(DatabaseNotFoundException e)
				{
					throw new ServerSideDatabaseException(e,"database not found when finding assignments");
				}
			}	
			else
			{
				throw new InvalidParameterException();
			}	
		return res;
	}
	
	//================================================================================
    // Get ClosestCityHall
    //================================================================================
	//TODO javadoc
	protected static CityHall getClosestCityHall(Location location) throws ServerSideDatabaseException, InvalidParameterException
	{
		CityHall res = null;
		if(location!=null&&location.getLatitude()!=null&&location.getLongitude()!=null)
			{
				try
				{
					res=ReportAndAssignmentDatabaseConnector.getClosestCityhall(location);
				}
				catch(DatabaseNotFoundException e)
				{
					throw new ServerSideDatabaseException(e,"database not found when finding closest cityhall");
				}
			}	
			else
			{
				throw new InvalidParameterException();
			}	
		return res;
	}
	
}

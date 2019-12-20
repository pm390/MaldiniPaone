package maldiniPaone.databaseConnection;

import java.sql.Timestamp;
import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.databaseConnection.databaseExceptions.InvalidParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.databaseConnection.interfaces.ManageDataAccess;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.Accident;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.District;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Report;
import maldiniPaone.utilities.beans.Statistic;

public class DataAccessFacade implements ManageDataAccess{
	//================================================================================
    // static variables
    //================================================================================
	private static boolean verbose=true;//TODO set to false on relese
	private static DataAccessFacade instance=null;
	//================================================================================
    // Empty constructor singleton design pattern
    //================================================================================
	private DataAccessFacade(){}
	//================================================================================
    // Instantiator
    //================================================================================
	public static DataAccessFacade getInstance()
	{
		 return(instance==null) ?  instance=new DataAccessFacade() : instance ;
	}
	//================================================================================
    // Interface implementation
    //================================================================================
	//================================================================================
    // Data retrieve(GET)
    //================================================================================
	
	/**
	 * Gets reports made by a user
	 * @param username : username of the user whose reports are being retrieved
	 * @returns List of Reports : the list of reports made by the user
	 * @throws ServerSideDatabaseException 
	 * @throws InvalidParameterExcpetion
	 * */
	@Override
	public List<Report> getReportsMadeBy(String username) throws ServerSideDatabaseException, InvalidParameterException 
	{
		return DataCollector.GetReportsMadeBy(username);
	}

	
	
	@Override
	public List<Accident> getAccidents(Location location) {
		//return DataCollector.getAccidents(location);
		//TODO uncomment
		return null;
	}

	
	
	
	@Override
	public Integer getReports(Location location) throws ServerSideDatabaseException, InvalidParameterException 
	{
		return DataCollector.GetReports(location);
	}

	
	
	
	@Override
	public List<Statistic> getStatistics(Location location) 
	{
		// TODO build statistics here????????????????????
		return null;
	}

	
	
	
	@Override
	public List<Assignment> getAssignments(Location location) throws ServerSideDatabaseException, InvalidParameterException 
	{
		return DataCollector.GetAssignment(location);
	}

	
	
	
	@Override
	public UserType checkUserType(String username, String password) throws ServerSideDatabaseException, InvalidParameterException 
	{
		return UserDataChecker.checkUserCredentials(username, password);
	}

	
	
	
	@Override
	public List<String> GetStaticSuggestions(Location location) throws ServerSideDatabaseException, InvalidParameterException {
		CityHall ch;
		try {
			ch = ReportAndAssignmentDatabaseConnector.getClosestCityhall(location);
			return DataCollector.GetSuggestion(ch.getName(),ch.getProvince());
		} catch (DatabaseNotFoundException e) {
			if(verbose)e.printStackTrace();
			throw new ServerSideDatabaseException(e, "server side error when searching closest cityhall");
		}
	}
	
	
	
	@Override
	public List<String> GetStaticSuggestions(CityHall cityHall) throws ServerSideDatabaseException, InvalidParameterException
	{
		return DataCollector.GetSuggestion(cityHall.getName(), cityHall.getProvince());
	}
	
	
	//================================================================================
    // New data creation(POST)
    //================================================================================
	
	
	@Override
	public Assignment addNewReport(String username, Timestamp date, Report report) throws ServerSideDatabaseException, InvalidParameterException 
	{
		//Integer newAssignmentid=ReportAndAssignmentUpdater.addReport(username, date, report.getLocation(), report.getNote(), report.getLicensePlate());
		return null; //FIXME add search newly created assignment
	}
	
	
	
	@Override
	public boolean addCitizen(String username, String password, String email) throws InvalidParameterException, ServerSideDatabaseException
	{
		return UserDataChecker.AddCitizen(username, password, email);
	}

	
	
	@Override
	public boolean addManager(String username, String password, String email, String venueName) throws ServerSideDatabaseException, InvalidParameterException
	{
		return UserDataChecker.addManager(username, password, email, venueName);
	}

	
	
	@Override
	public boolean addAuthority(String username, String password, String email,String creator, District district) throws ServerSideDatabaseException, InvalidParameterException
	{
		Integer districtId=UserDataChecker.addDistrict(district.getName(), district.getProvince(), district.getLocationTopLeft(), district.getLocationBottomRight());
		return UserDataChecker.addAuthority(username, password, email, creator, districtId);
	}

	
	
	@Override
	public boolean addMunicipality(String username, String password, String email, String creatorUsername,
			CityHall cityHall) throws ServerSideDatabaseException, InvalidParameterException 
	{
		try 
		{
			UserDataChecker.addCityhall(cityHall.getName(), cityHall.getProvince(), cityHall.getRegion(), cityHall.getLocation());
		}
		catch(ServerSideDatabaseException | InvalidParameterException e)
		{
			if(verbose)e.printStackTrace();
			throw e;
		}
		return UserDataChecker.addMunicipality(username, password, email, creatorUsername, cityHall.getName(), cityHall.getProvince());
	}
	
	
	
	//================================================================================
    // Data Modification(PUT)
    //================================================================================
	
	
	
	@Override
	public boolean ModifyUser(String username, String password,UserType user, String email, String newUsername, String newPassword) 
			throws ServerSideDatabaseException, InvalidParameterException 
	{
		return UserDataChecker.ModifyUser(username, password, user, email, newUsername, newPassword);
	}

	
	
	@Override
	public boolean updateAssignment(Assignment assign, State state, String username) throws ServerSideDatabaseException, InvalidParameterException
	{
		return ReportAndAssignmentUpdater.UpdateAssignment(assign.getId(), username, state);
	}
}

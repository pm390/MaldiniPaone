package maldiniPaone.databaseConnection;


import java.util.List;

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.DatabaseNotFoundException;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
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

public class DataAccessFacade implements ManageDataAccess{
	//================================================================================
    // static variables
    //================================================================================
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
	public List<Report> getReportsMadeBy(String username)
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		return DataCollector.getReportsMadeBy(username);
	}

	
	
	@Override
	public List<Accident> getAccidents(Location location) {
		//return DataCollector.getAccidents(location);
		//TODO uncomment
		return null;
	}

	
	
	
	@Override
	public Integer getReportCountInLastWeek(Location location) 
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		return DataCollector.getReportCountInLastWeek(location);
	}

	
	
	@Override
	public Integer getAssignmentCountInLastWeek(Location location) 
			throws ServerSideDatabaseException, IllegalParameterException
	{
		return DataCollector.getAssignmentsCountInLastWeek(location);
	}
	
	
	
	@Override
	public List<Assignment> getAssignments(Location location)
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		return DataCollector.getAssignment(location);
	}

	
	
	
	@Override
	public UserType checkUserCredentials(String username, String password) 
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		return UserDataChecker.checkUserCredentials(username, password);
	}

	
	
	
	@Override
	public List<String> getStaticSuggestions(Location location)
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		CityHall ch;
		try {
			ch = ReportAndAssignmentDatabaseConnector.getClosestCityhall(location);
			return DataCollector.getSuggestion(ch.getName(),ch.getProvince());
		} catch (DatabaseNotFoundException e) {
			if(Constants.VERBOSE)e.printStackTrace();
			throw new ServerSideDatabaseException(e, "server side error when searching closest cityhall");
		}
	}
	
	
	
	@Override
	public List<String> getStaticSuggestions(CityHall cityHall)
			throws ServerSideDatabaseException, IllegalParameterException
	{
		return DataCollector.getSuggestion(cityHall.getName(), cityHall.getProvince());
	}
	
	@Override
	public CityHall getClosestCityHall(Location location)
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		return DataCollector.getClosestCityHall(location);
	}
	
	@Override
	public CityHall getCityHall(String username)
			throws ServerSideDatabaseException, IllegalParameterException
	{
	return	UserDataChecker.getCityHall(username);
	}
	@Override
	public String findUsernameByEmail(String email)
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		return UserDataChecker.findUsernameByEmail(email);
	}
	@Override
	public String findEmailByUsername(String username) 
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		return UserDataChecker.findEmailByUsername(username);
	}
	
	
	//================================================================================
    // New data creation(POST)
    //================================================================================
	
	
	@Override
	public Assignment addNewReport(Report report) 
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		//Integer newAssignmentid=ReportAndAssignmentUpdater.addReport(report.getUsername(), report.getDate(), report.getLocation(), report.getNote(), report.getLicensePlate());
		return null; //FIXME add search newly created assignment
	}
	
	
	
	@Override
	public boolean addCitizen(String username, String password, String email) throws IllegalParameterException, ServerSideDatabaseException
	{
		return UserDataChecker.AddCitizen(username, password, email);
	}

	
	
	@Override
	public boolean addManager(String username, String password, String email, String venueName) throws ServerSideDatabaseException, IllegalParameterException
	{
		return UserDataChecker.addManager(username, password, email, venueName);
	}

	
	
	@Override
	public boolean addAuthority(String username, String password, String email,String creator, District district) throws ServerSideDatabaseException, IllegalParameterException
	{
		Integer districtId=UserDataChecker.addDistrict(district.getName(), district.getProvince(), district.getLocationTopLeft(), district.getLocationBottomRight());
		return UserDataChecker.addAuthority(username, password, email, creator, districtId);
	}

	
	
	@Override
	public boolean addMunicipalityAndCityHall(String username, String password, String email, String creatorUsername,
			CityHall cityHall) throws ServerSideDatabaseException, IllegalParameterException 
	{
		try 
		{
			UserDataChecker.addCityhall(cityHall.getName(), cityHall.getProvince(),
					cityHall.getRegion(), cityHall.getLocation());
		}
		catch(ServerSideDatabaseException | IllegalParameterException e)
		{
			if(Constants.VERBOSE)e.printStackTrace();
			throw e;
		}
		return UserDataChecker.addMunicipality(username, password, email, creatorUsername, 
				cityHall.getName(), cityHall.getProvince());
	}
	
	@Override
	public boolean addMunicipality(String username, String password, String email, String creatorUsername,
			CityHall cityHall) throws ServerSideDatabaseException, IllegalParameterException 
	{
		return UserDataChecker.addMunicipality(username, password, email, creatorUsername,
				cityHall.getName(), cityHall.getProvince());
	}
	
	@Override
	public boolean addSuggestion(String suggestion, CityHall cityHall)
			throws ServerSideDatabaseException, IllegalParameterException {
		return ReportAndAssignmentUpdater.AddSuggestions(suggestion,
				cityHall.getName(), cityHall.getProvince());
	}
	
	//================================================================================
    // Data Modification(PUT)
    //================================================================================
	
	
	
	@Override
	public boolean modifyUser(String username, String password,UserType user, String email, String newUsername, String newPassword) 
			throws ServerSideDatabaseException, IllegalParameterException 
	{
		return UserDataChecker.modifyUser(username, password, user, email, newUsername, newPassword);
	}

	
	
	@Override
	public boolean updateAssignment(Integer assignmentId, State state, String username) throws ServerSideDatabaseException, IllegalParameterException
	{
		return ReportAndAssignmentUpdater.updateAssignment(assignmentId, username, state);
	}
	
	//================================================================================
    // Delete data (Delete requests)
    //================================================================================
	
	public boolean removeUser(String username,String password,UserType user) throws ServerSideDatabaseException, IllegalParameterException
	{
		return UserDataChecker.removeUser(username, password, user);
	}
	
	
}

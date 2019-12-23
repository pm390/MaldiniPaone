package maldiniPaone.databaseConnection.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.InvalidParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.Accident;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.District;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Report;
import maldiniPaone.utilities.beans.Statistic;
//TODO put all javadocs to methods
public interface ManageDataAccess {
	//================================================================================
    // retrieve data (GET requests)
    //================================================================================
	public List<Report> getReportsMadeBy(String username) throws ServerSideDatabaseException, InvalidParameterException;
	
	public List<Accident> getAccidents(Location location) throws ServerSideDatabaseException, InvalidParameterException;
	
	//useful to give? maybe use by municipalities to see how many reports are made in certain location? 
	//maybe add a radius for selecting an area???
	public Integer getReportCountInLastWeek(Location location) throws ServerSideDatabaseException, InvalidParameterException;
	
	public Integer getAssignmentCountInLastWeek(Location lcation) throws ServerSideDatabaseException, InvalidParameterException;
	
	public List<Assignment> getAssignments(Location location) throws ServerSideDatabaseException, InvalidParameterException;
	
	public UserType checkUserCredentials(String username,String password) throws ServerSideDatabaseException, InvalidParameterException;
	
	public List<String> getStaticSuggestions(Location location) throws ServerSideDatabaseException, InvalidParameterException;
	
	public List<String> getStaticSuggestions(CityHall cityHall) throws ServerSideDatabaseException, InvalidParameterException;
	
	public CityHall getClosestCityHall(Location location) throws ServerSideDatabaseException, InvalidParameterException; 
	 
	//================================================================================
    // Adding new data (POST requests)
    //================================================================================
	
	public Assignment addNewReport (Report report) throws ServerSideDatabaseException, InvalidParameterException;
	
	public boolean addCitizen(String username,String password,String email) throws ServerSideDatabaseException, InvalidParameterException;
	
	public boolean addManager(String username,String password,String email,String venueName) throws ServerSideDatabaseException, InvalidParameterException;

	public boolean addAuthority(String username,String password,String email,String creatorUsername,District district) throws ServerSideDatabaseException, InvalidParameterException;
	
	public boolean addMunicipality(String username,String password,String email,String creatorUsername,CityHall cityHall) throws ServerSideDatabaseException, InvalidParameterException;

	public boolean addMunicipalityAndCityHall(String username, String password, String email, String creatorUsername,
			CityHall cityHall) throws ServerSideDatabaseException, InvalidParameterException;
	
	public boolean addSuggestion(String suggestion,CityHall cityHall)
			throws ServerSideDatabaseException, InvalidParameterException;
	
	//================================================================================
    // Modify already available data (PUT requests)
    //================================================================================
	
	public boolean modifyUser(String username,String password,UserType user,String email,String newUsername,String newPassword) throws ServerSideDatabaseException, InvalidParameterException;
	
	public boolean updateAssignment(Integer assignmentId,State state,String username) throws ServerSideDatabaseException, InvalidParameterException;

	
	

}


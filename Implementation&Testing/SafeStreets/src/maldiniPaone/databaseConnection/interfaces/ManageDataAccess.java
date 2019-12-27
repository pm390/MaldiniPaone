package maldiniPaone.databaseConnection.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.UserType;
import maldiniPaone.utilities.beans.Accident;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.CityHall;
import maldiniPaone.utilities.beans.District;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Report;
//TODO put all javadocs to methods
public interface ManageDataAccess {
	//================================================================================
    // retrieve data (GET requests)
    //================================================================================
	public List<Report> getReportsMadeBy(String username) throws ServerSideDatabaseException, IllegalParameterException;
	
	public List<Accident> getAccidents(Location location) throws ServerSideDatabaseException, IllegalParameterException;
	
	//useful to give? maybe use by municipalities to see how many reports are made in certain location? 
	//maybe add a radius for selecting an area???
	public Integer getReportCountInLastWeek(Location location) throws ServerSideDatabaseException, IllegalParameterException;
	
	public Integer getAssignmentCountInLastWeek(Location lcation) throws ServerSideDatabaseException, IllegalParameterException;
	
	public List<Assignment> getAssignments(Location location) throws ServerSideDatabaseException, IllegalParameterException;
	
	public UserType checkUserCredentials(String username,String password) throws ServerSideDatabaseException, IllegalParameterException;
	
	public List<String> getStaticSuggestions(Location location) throws ServerSideDatabaseException, IllegalParameterException;
	
	public List<String> getStaticSuggestions(CityHall cityHall) throws ServerSideDatabaseException, IllegalParameterException;
	
	public CityHall getClosestCityHall(Location location) throws ServerSideDatabaseException, IllegalParameterException; 
	 
	//================================================================================
    // Adding new data (POST requests)
    //================================================================================
	
	public Assignment addNewReport (Report report) throws ServerSideDatabaseException, IllegalParameterException;
	
	public boolean addCitizen(String username,String password,String email) throws ServerSideDatabaseException, IllegalParameterException;
	
	public boolean addManager(String username,String password,String email,String venueName) throws ServerSideDatabaseException, IllegalParameterException;

	public boolean addAuthority(String username,String password,String email,String creatorUsername,District district) throws ServerSideDatabaseException, IllegalParameterException;
	
	public boolean addMunicipality(String username,String password,String email,String creatorUsername,CityHall cityHall) throws ServerSideDatabaseException, IllegalParameterException;

	public boolean addMunicipalityAndCityHall(String username, String password, String email, String creatorUsername,
			CityHall cityHall) throws ServerSideDatabaseException, IllegalParameterException;
	
	public boolean addSuggestion(String suggestion,CityHall cityHall)
			throws ServerSideDatabaseException, IllegalParameterException;
	
	//================================================================================
    // Modify already available data (PUT requests)
    //================================================================================
	
	public boolean modifyUser(String username,String password,UserType user,String email,String newUsername,String newPassword) throws ServerSideDatabaseException, IllegalParameterException;
	
	public boolean updateAssignment(Integer assignmentId,State state,String username) throws ServerSideDatabaseException, IllegalParameterException;

	
	

}


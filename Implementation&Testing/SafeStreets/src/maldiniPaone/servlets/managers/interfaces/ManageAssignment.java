package maldiniPaone.servlets.managers.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.InvalidParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.State;
import maldiniPaone.utilities.beans.Assignment;
import maldiniPaone.utilities.beans.Location;

public interface ManageAssignment 
{
	//TODO javadoc here
	public List<Assignment> getAssignment(Location location)throws ServerSideDatabaseException, InvalidParameterException;

	//TODO javadoc here
	public boolean refuseAssignment(Integer id,String username) throws ServerSideDatabaseException, InvalidParameterException;
	
	//TODO javadoc here
	public boolean acceptAssignment(Integer id,String username)throws ServerSideDatabaseException, InvalidParameterException;
	
	//TODO javadoc here
	public boolean terminateAssignment(Integer id,String username,State finishState)throws ServerSideDatabaseException, InvalidParameterException;
}

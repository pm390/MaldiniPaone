package maldiniPaone.servlets.managers.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.InvalidParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;

public interface ManageReportCreation
{
	//TODO javadoc here
	public boolean addReport(String username, Location location, List<String> photos,String licensePlate,String note) 
			throws ServerSideDatabaseException, InvalidParameterException;
}

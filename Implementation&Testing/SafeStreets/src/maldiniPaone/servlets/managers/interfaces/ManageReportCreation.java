package maldiniPaone.servlets.managers.interfaces;

import java.io.IOException;
import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Photo;

public interface ManageReportCreation
{
	//TODO javadoc here
	public boolean addReport(String username, Location location, List<Photo> photos,String licensePlate,String note) 
			throws ServerSideDatabaseException, IllegalParameterException, IOException;


}

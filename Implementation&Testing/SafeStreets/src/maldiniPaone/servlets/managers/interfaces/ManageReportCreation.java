package maldiniPaone.servlets.managers.interfaces;

import java.io.IOException;
import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Photo;

/**
 * This interface defines the signature of the method to create reports
 **/
public interface ManageReportCreation {
	/**
	 * Adds a new Report to the System.
	 * 
	 * @param username     the username of the citizen making the report
	 * @param location     the location where the violation being reported happened
	 * @param photos       the list of photos associated with the report
	 * @param licensePlate String containing the license plate of the car
	 * @param note         additional notes provided by the user
	 * @return true if the creation is successful , false otherwise
	 * @throws IllegalParameterException   if the provided parameters are invalid
	 * @throws ServerSideDatabaseException if the database can't be found
	 * @throws IOException                 if the photos couldn't be saved
	 **/
	public boolean addReport(String username, Location location, List<Photo> photos, String licensePlate, String note)
			throws ServerSideDatabaseException, IllegalParameterException, IOException;

}

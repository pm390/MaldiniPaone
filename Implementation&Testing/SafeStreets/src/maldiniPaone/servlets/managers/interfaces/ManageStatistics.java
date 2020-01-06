/**
 * 
 */
package maldiniPaone.servlets.managers.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Statistic;

/**
 * This interface defines the signatures of the methods to retrieve the
 * statistics
 **/
public interface ManageStatistics {

	/**
	 * Gets the list of statistics associated to a given area
	 * 
	 * @param location the location to which respect the statistics are searched
	 * @param edge     the edge of the square within which the statistics are
	 *                 searched
	 * @return the list of statistics in the given location
	 * @throws ServerSideDatabaseException when database can't be found
	 * @throws IllegalParameterException   when the provided parameters are not
	 *                                     valid
	 **/
	public List<Statistic> getStatistics(Location location, Float edge)
			throws ServerSideDatabaseException, IllegalParameterException;
}

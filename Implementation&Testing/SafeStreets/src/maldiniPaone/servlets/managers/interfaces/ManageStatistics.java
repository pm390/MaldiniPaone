/**
 * 
 */
package maldiniPaone.servlets.managers.interfaces;

import java.util.List;

import maldiniPaone.databaseConnection.databaseExceptions.InvalidParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Statistic;

/**
 *
 *
 */
//TODO javadoc
public interface ManageStatistics {

	public List<Statistic> getStatistics(Location location,Float edge) throws ServerSideDatabaseException, InvalidParameterException;
}

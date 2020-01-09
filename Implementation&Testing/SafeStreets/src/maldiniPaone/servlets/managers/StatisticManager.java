/**
 * 
 */
package maldiniPaone.servlets.managers;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.interfaces.ManageStatistics;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Statistic;

/**
 * Implements {@link ManageStatistics} Singleton design pattern
 **/
public class StatisticManager implements ManageStatistics {
	// ================================================================================
	// instance
	// ================================================================================

	private static StatisticManager instance;

	private StatisticManager() {
	}
	// ================================================================================
	// Instantiator
	// ================================================================================

	/**
	 * Gets the instance of the Suggestion manager. Singleton design pattern
	 * 
	 * @return the instance
	 **/
	public static StatisticManager getInstance() {
		return (instance == null) ? instance = new StatisticManager() : instance;
	}

	@Override
	//TODO add some implementation notes here
	public List<Statistic> getStatistics(Location location, Float edge)
			throws ServerSideDatabaseException, IllegalParameterException {
		int subEdgeNumber = (int) (edge / (2 * Constants.STATISTICS_RADIUS));
		List<Statistic> result = new ArrayList<Statistic>(subEdgeNumber * subEdgeNumber);
		
		//drop some useless decimal for statistics
		DecimalFormat df = new DecimalFormat("#.##");
	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setDecimalSeparator('.');
	    df.setDecimalFormatSymbols(dfs);
	    
		df.setRoundingMode(RoundingMode.FLOOR);
		location.setLatitude(Float.parseFloat(df.format(location.getLatitude())));

		location.setLongitude(Float.parseFloat(df.format(location.getLongitude())));
		
		if(subEdgeNumber>Constants.STATISTICS_LIMIT)
		{
			return result;
		}
		if (subEdgeNumber == 0) {
			result.add(StatisticBuilder.buildStatistic(location));
			return result;
		}
		for (int i = 0; i < subEdgeNumber; ++i) {
			for (int j = 0; j < subEdgeNumber; ++j) {
				Location centerOfStatistic = new Location();
				try
				{	
					centerOfStatistic.setLatitude(location.getLatitude() + (1 + 2 * i) * Constants.STATISTICS_RADIUS);
					centerOfStatistic.setLongitude(location.getLongitude() + (1 + 2 * j) * Constants.STATISTICS_RADIUS);
					result.add(StatisticBuilder.buildStatistic(centerOfStatistic));
				}
				catch(IllegalParameterException e)
				{
					/*just a not valid coordinate to search*/
				}
			}
		}
		return result;
	}

}

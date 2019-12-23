/**
 * 
 */
package maldiniPaone.servlets.managers;

import java.util.ArrayList;
import java.util.List;

import maldiniPaone.constants.Constants;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.servlets.managers.interfaces.ManageStatistics;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Statistic;

/**
 * @author MaurizioMaldini
 *
 */
public class StatisticManager implements ManageStatistics
{

	@Override
	public List<Statistic> getStatistics(Location location,Float edge) throws ServerSideDatabaseException, IllegalParameterException {
		int subEdgeNumber=(int) (edge/(2*Constants.STATISTICS_RADIUS));
		List<Statistic> result=new ArrayList<Statistic>(subEdgeNumber*subEdgeNumber);
		if(subEdgeNumber==0)
		{
			result.add(StatisticBuilder.buildStatistic(location));
			return result;
		}	
		for(int i=0;i<subEdgeNumber;++i)
		{
			for(int j=0;j<subEdgeNumber;++i)
			{
				Location centerOfStatistic=new Location();
				centerOfStatistic.setLatitude(location.getLatitude()+
						(1+2*i)*Constants.STATISTICS_RADIUS);
				centerOfStatistic.setLongitude(location.getLongitude()+
						(1+2*j)*Constants.STATISTICS_RADIUS);
				result.add(StatisticBuilder.buildStatistic(centerOfStatistic));
			}
		}
		return null;
	}

}

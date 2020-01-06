package maldiniPaone.servlets.managers;

import maldiniPaone.databaseConnection.DataAccessFacade;
import maldiniPaone.databaseConnection.databaseExceptions.IllegalParameterException;
import maldiniPaone.databaseConnection.databaseExceptions.ServerSideDatabaseException;
import maldiniPaone.utilities.beans.Location;
import maldiniPaone.utilities.beans.Statistic;

/**
 * Builds statistics from data retrieve from {@link DataAccessFacade} 
 **/
public class StatisticBuilder
{
	/**
	 * Builds the statistic object 
	 * @param location the location to which respect the statistics are searched
	 * @return the statistic object
	 * @throws ServerSideDatabaseException when database is not found
	 * @throws IllegalParameterException when the provided parameters are not valid
	 * */
	public static Statistic buildStatistic(Location location) throws ServerSideDatabaseException, IllegalParameterException
	{
		Statistic result=new Statistic();
		result.setLocation(location);
		int repLastWeek=DataAccessFacade.getInstance().getReportCountInLastWeek(location);
		result.setReportCountLastWeek(repLastWeek);
		result.setDailyReportCountLastWeek((float)repLastWeek/7);
		int assignmentLastWeek=DataAccessFacade.getInstance().getAssignmentCountInLastWeek(location);
		result.setAssignmentCountLastWeek(assignmentLastWeek);
		if(assignmentLastWeek!=0)
		{
			result.setReportForAssignmentCountLastWeek((float)repLastWeek/assignmentLastWeek);
		}
		else
		{
			result.setReportForAssignmentCountLastWeek(-1f);
		}
		return result;
	}
}

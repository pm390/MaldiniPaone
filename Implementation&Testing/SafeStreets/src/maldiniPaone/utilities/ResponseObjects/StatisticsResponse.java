package maldiniPaone.utilities.ResponseObjects;

import java.util.List;

import maldiniPaone.utilities.beans.Statistic;

/**
 * Object containing the response for a request for getting statistics
 **/
public class StatisticsResponse extends GenericResponse {
	private List<Statistic> statistics;

	public StatisticsResponse(int code, String message) {
		super(code, message);
	}

	public StatisticsResponse(List<Statistic> statistics) {
		super();
		setStatistics(statistics);
	}

	/**
	 * @return the statistics
	 */
	public List<Statistic> getStatistics() {
		return statistics;
	}

	/**
	 * @param statistics the statistics to set
	 */
	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}
}

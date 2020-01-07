package maldiniPaone.ResponseObjects;

import java.util.List;

import maldiniPaone.utilities.beans.Statistic;

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
	 * @return the assignments
	 */
	public List<Statistic> getStatistics() {
		return statistics;
	}

	/**
	 * @param assignments the assignments to set
	 */
	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}
}

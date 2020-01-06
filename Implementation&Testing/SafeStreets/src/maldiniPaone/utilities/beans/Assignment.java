package maldiniPaone.utilities.beans;

import java.util.List;

import maldiniPaone.utilities.State;

/**
 * Bean representing assignment data.
 **/
public class Assignment {

	// ================================================================================
	// Variables
	// ================================================================================
	private Integer id;
	private List<Report> reports;
	private State state;

	// ================================================================================
	// Empty Constructor
	// ================================================================================
	public Assignment() {
	}

	// ================================================================================
	// Getters
	// ================================================================================
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the reports
	 */
	public List<Report> getReports() {
		return reports;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	// ================================================================================
	// Setters
	// ================================================================================

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

}

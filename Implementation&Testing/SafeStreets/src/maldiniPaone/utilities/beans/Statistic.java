package maldiniPaone.utilities.beans;

//TODO add some javadoc comment for bean
public class Statistic {
	//================================================================================
    // Variables
    //================================================================================
	private Location location;
	private MunicipalityBean closestMuniciaplity;
	private Float reportCount;
	private Float assignmentCount;
	private Float reportForAssignmentCount;
	private Float dailyReportCount;
	//================================================================================
    // Empty Constructor
    //================================================================================
	public Statistic() {}
	//================================================================================
    // Getters
    //================================================================================
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @return the closestMuniciaplity
	 */
	public MunicipalityBean getClosestMuniciaplity() {
		return closestMuniciaplity;
	}
	/**
	 * @return the reportCount
	 */
	public Float getReportCount() {
		return reportCount;
	}
	/**
	 * @return the assignmentCount
	 */
	public Float getAssignmentCount() {
		return assignmentCount;
	}
	/**
	 * @return the reportForAssignmentCount
	 */
	public Float getReportForAssignmentCount() {
		return reportForAssignmentCount;
	}
	/**
	 * @return the dailyReportCount
	 */
	public Float getDailyReportCount() {
		return dailyReportCount;
	}
	
	//================================================================================
    // Setters
    //================================================================================
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @param closestMuniciaplity the closestMuniciaplity to set
	 */
	public void setClosestMuniciaplity(MunicipalityBean closestMuniciaplity) {
		this.closestMuniciaplity = closestMuniciaplity;
	}
	/**
	 * @param reportCount the reportCount to set
	 */
	public void setReportCount(Float reportCount) {
		this.reportCount = reportCount;
	}
	/**
	 * @param assignmentCount the assignmentCount to set
	 */
	public void setAssignmentCount(Float assignmentCount) {
		this.assignmentCount = assignmentCount;
	}
	/**
	 * @param reportForAssignmentCount the reportForAssignmentCount to set
	 */
	public void setReportForAssignmentCount(Float reportForAssignmentCount) {
		this.reportForAssignmentCount = reportForAssignmentCount;
	}
	/**
	 * @param dailyReportCount the dailyReportCount to set
	 */
	public void setDailyReportCount(Float dailyReportCount) {
		this.dailyReportCount = dailyReportCount;
	}
}

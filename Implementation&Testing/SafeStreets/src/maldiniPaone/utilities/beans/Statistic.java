package maldiniPaone.utilities.beans;

//TODO add some javadoc comment for bean
public class Statistic {
	//================================================================================
    // Variables
    //================================================================================
	private Location location;
	private MunicipalityBean closestMuniciaplity;
	private int reportCountLastWeek;
	private int assignmentCountLastWeek;
	private Float reportForAssignmentCountLastWeek;
	private Float dailyReportCountLastWeek;
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
	public int getReportCountLastWeek() {
		return reportCountLastWeek;
	}
	/**
	 * @return the assignmentCount
	 */
	public int getAssignmentCountLastWeek() {
		return assignmentCountLastWeek;
	}
	/**
	 * @return the reportForAssignmentCount
	 */
	public Float getReportForAssignmentCountLastWeek() {
		return reportForAssignmentCountLastWeek;
	}
	/**
	 * @return the dailyReportCount
	 */
	public Float getDailyReportCountLastWeek() {
		return dailyReportCountLastWeek;
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
	public void setReportCountLastWeek(int reportCount) {
		this.reportCountLastWeek = reportCount;
	}
	/**
	 * @param assignmentCount the assignmentCount to set
	 */
	public void setAssignmentCountLastWeek(int assignmentCount) {
		this.assignmentCountLastWeek = assignmentCount;
	}
	/**
	 * @param reportForAssignmentCount the reportForAssignmentCount to set
	 */
	public void setReportForAssignmentCountLastWeek(Float reportForAssignmentCount) {
		this.reportForAssignmentCountLastWeek = reportForAssignmentCount;
	}
	/**
	 * @param dailyReportCount the dailyReportCount to set
	 */
	public void setDailyReportCountLastWeek(float dailyReportCount) {
		this.dailyReportCountLastWeek = dailyReportCount;
	}
}

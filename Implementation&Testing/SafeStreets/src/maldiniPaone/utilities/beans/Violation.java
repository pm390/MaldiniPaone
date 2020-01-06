package maldiniPaone.utilities.beans;

import maldiniPaone.utilities.ViolationType;

/**
 * Bean containing a violation type and the frequency of its occurrence
 **/
public class Violation {
	// ================================================================================
	// DATA
	// ================================================================================

	private ViolationType violationType;
	private Integer count;

	// ================================================================================
	// Getters
	// ================================================================================

	/**
	 * @return the violationType
	 */
	public ViolationType getViolationType() {
		return violationType;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	// ================================================================================
	// Setters
	// ================================================================================

	/**
	 * @param violationType the violationType to set
	 */
	public void setViolationType(ViolationType violationType) {
		this.violationType = violationType;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
}

package maldiniPaone.utilities.ResponseObjects;

import java.util.List;

import maldiniPaone.utilities.beans.Assignment;
/**
 * Object containing the response for a request for getting assignments 
 **/
public class AssignmentResponse extends GenericResponse {
	private List<Assignment> assignments;

	public AssignmentResponse(int code, String message) {
		super(code, message);
	}

	public AssignmentResponse(List<Assignment> assign) {
		super();
		assignments = assign;
	}

	/**
	 * @return the assignments
	 */
	public List<Assignment> getAssignments() {
		return assignments;
	}

	/**
	 * @param assignments the assignments to set
	 */
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

}

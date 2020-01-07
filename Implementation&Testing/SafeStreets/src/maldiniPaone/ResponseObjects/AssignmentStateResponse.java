package maldiniPaone.ResponseObjects;

import java.util.List;

public class AssignmentStateResponse extends GenericResponse {
	private List<Integer> activeIDs;

	public AssignmentStateResponse(int code, String message) {
		super(code, message);
	}


	public AssignmentStateResponse(List<Integer> ids) {
		super();
		activeIDs=ids;
	}

	/**
	 * @return true if active
	 */
	public List<Integer> getState() {
		return activeIDs;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(List<Integer> ids) {
		this.activeIDs = ids;
	}
}

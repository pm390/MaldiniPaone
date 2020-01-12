package maldiniPaone.utilities.ResponseObjects;


import maldiniPaone.utilities.beans.Assignment;

/**
 * Object containing the response for a request for getting assignment state 
 **/
public class AssignmentStateResponse extends GenericResponse {
	private Assignment activeIDs;

	public AssignmentStateResponse(int code, String message) {
		super(code, message);
	}


	public AssignmentStateResponse(Assignment ids) {
		super();
		activeIDs=ids;
	}

	/**
	 * @return true if active
	 */
	public Assignment getState() {
		return activeIDs;
	}

	/**
	 * @param ids the id list  to set
	 */
	public void setState(Assignment ids) {
		this.activeIDs = ids;
	}
}

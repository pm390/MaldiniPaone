package maldiniPaone.ResponseObjects;

import java.util.List;


public class SuggestionResponse extends GenericResponse {
	private List<String> suggestion;

	public SuggestionResponse(int code, String message) {
		super(code, message);
	}

	public SuggestionResponse(List<String> suggestion) {
		super();
		setSuggestion(suggestion);
	}

	/**
	 * @return the assignments
	 */
	public List<String> getsuggestion() {
		return suggestion;
	}

	/**
	 * @param assignments the assignments to set
	 */
	public void setSuggestion(List<String> suggestion) {
		this.suggestion = suggestion;
	}
}

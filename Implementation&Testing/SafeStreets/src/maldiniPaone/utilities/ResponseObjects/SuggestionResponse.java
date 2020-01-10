package maldiniPaone.utilities.ResponseObjects;

import java.util.List;


public class SuggestionResponse extends GenericResponse {
	private List<String> suggestions;

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
		return suggestions;
	}

	/**
	 * @param suggestion the suggestion to set
	 */
	public void setSuggestion(List<String> suggestion) {
		this.suggestions = suggestion;
	}
}

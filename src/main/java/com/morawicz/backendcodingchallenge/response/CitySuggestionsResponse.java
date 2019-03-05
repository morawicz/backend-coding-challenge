package com.morawicz.backendcodingchallenge.response;

import java.util.List;

public class CitySuggestionsResponse {

	private List<CitySuggestionResponse> suggestions;

	public CitySuggestionsResponse() {
		super();
	}

	public CitySuggestionsResponse(List<CitySuggestionResponse> suggestions) {
		super();
		this.suggestions = suggestions;
	}

	public List<CitySuggestionResponse> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<CitySuggestionResponse> suggestions) {
		this.suggestions = suggestions;
	}

}

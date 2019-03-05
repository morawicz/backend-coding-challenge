package com.morawicz.backendcodingchallenge.response;

import java.util.List;

import com.morawicz.backendcodingchallenge.dtos.CitySuggestion;

public class CitySuggestionsResponse {

	private List<CitySuggestion> suggestions;

	public CitySuggestionsResponse() {
		super();
	}

	public CitySuggestionsResponse(List<CitySuggestion> suggestions) {
		super();
		this.suggestions = suggestions;
	}

	public List<CitySuggestion> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<CitySuggestion> suggestions) {
		this.suggestions = suggestions;
	}

}

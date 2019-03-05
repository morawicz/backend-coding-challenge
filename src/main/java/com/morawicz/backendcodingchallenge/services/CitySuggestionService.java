package com.morawicz.backendcodingchallenge.services;

import java.util.List;

import com.morawicz.backendcodingchallenge.dtos.CitySuggestion;

public interface CitySuggestionService {

	List<CitySuggestion> findSuggestions(String name, Double latitude, Double longitude);

}

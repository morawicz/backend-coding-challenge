package com.morawicz.backendcodingchallenge.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.morawicz.backendcodingchallenge.dtos.CitySuggestion;
import com.morawicz.backendcodingchallenge.exceptions.InvalidRequestValidationRuntimeException;
import com.morawicz.backendcodingchallenge.response.CitySuggestionsResponse;
import com.morawicz.backendcodingchallenge.services.CitySuggestionService;

@RestController
public class CitySuggestionController {

	private CitySuggestionService citySuggestionService;

	public CitySuggestionController(CitySuggestionService citySuggestionService) {
		super();
		this.citySuggestionService = citySuggestionService;
	}

	@RequestMapping(path = "/suggestions", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CitySuggestionsResponse suggestions(@RequestParam("q") String queryStr,
			@RequestParam(value = "latitude", required = false) Double latitude,
			@RequestParam(value = "longitude", required = false) Double longitude) {
		validateRequest(queryStr);
		List<CitySuggestion> suggestions = citySuggestionService.findSuggestions(queryStr, latitude, longitude);
		return new CitySuggestionsResponse(suggestions);
	}

	private void validateRequest(String queryStr) {
		if (!StringUtils.hasText(queryStr)) {
			throw new InvalidRequestValidationRuntimeException("Query string cannot be empty.");
		} else if (queryStr.strip().length() < 2) {
			throw new InvalidRequestValidationRuntimeException("Query string must be minimum 2 characters.");
		}
	}
}

package com.morawicz.backendcodingchallenge.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.morawicz.backendcodingchallenge.response.CitySuggestionsResponse;

@RestController
public class CitySearchController {

	@RequestMapping(path = "/suggestions", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CitySuggestionsResponse search(@RequestParam("q") String queryStr,
			@RequestParam(value = "latitude", required = false) Double latitude,
			@RequestParam(value = "longitude", required = false) Double longitude) {
		return null;
	}
}

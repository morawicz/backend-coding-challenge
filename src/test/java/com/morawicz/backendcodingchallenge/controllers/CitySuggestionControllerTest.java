package com.morawicz.backendcodingchallenge.controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morawicz.backendcodingchallenge.BackendCodingChallengeApplication;
import com.morawicz.backendcodingchallenge.response.CitySuggestionsResponse;
import com.morawicz.backendcodingchallenge.response.ErrorResponse;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendCodingChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CitySuggestionControllerTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private String createURLWithPort(String queryParams) {
		return "http://localhost:" + port + "/suggestions?q=" + queryParams;
	}

	private ResponseEntity<String> callSuggestionsAPI(String queryStr) {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort(queryStr),
				HttpMethod.GET, entity, String.class);
		return response;
	}

	@Test
	public void invalidQueryStr_empty_badRequestResponse() throws JsonParseException, JsonMappingException, IOException {
		String queryStr = "";

		ResponseEntity<String> response = callSuggestionsAPI(queryStr);

		Assert.assertEquals(400, response.getStatusCodeValue());

		ErrorResponse error = new ObjectMapper().readValue(response.getBody(), ErrorResponse.class);

		Assert.assertEquals("Query string cannot be empty.", error.getMessage());
	}

	@Test
	public void invalidQueryStr_tooShort_badRequestResponse() throws JsonParseException, JsonMappingException, IOException {
		String queryStr = "L";

		ResponseEntity<String> response = callSuggestionsAPI(queryStr);

		Assert.assertEquals(400, response.getStatusCodeValue());

		ErrorResponse error = new ObjectMapper().readValue(response.getBody(), ErrorResponse.class);

		Assert.assertEquals("Query string must be minimum 2 characters.", error.getMessage());
	}

	@Test
	public void validQueryStr_SomeRandomCityInTheMiddleOfNowhere_returnEmptySuggestions() throws JsonParseException, JsonMappingException, IOException {
		String queryStr = "SomeRandomCityInTheMiddleOfNowhere";

		ResponseEntity<String> response = callSuggestionsAPI(queryStr);

		Assert.assertEquals(200, response.getStatusCodeValue());

		CitySuggestionsResponse responseBody = new ObjectMapper().readValue(response.getBody(), CitySuggestionsResponse.class);

		Assert.assertEquals(true, responseBody.getSuggestions().isEmpty());
	}

	@Test
	public void validQueryStr_Londo_returnSuggestions() throws JsonParseException, JsonMappingException, IOException {
		String queryStr = "Londo";

		ResponseEntity<String> response = callSuggestionsAPI(queryStr);

		Assert.assertEquals(200, response.getStatusCodeValue());

		CitySuggestionsResponse responseBody = new ObjectMapper().readValue(response.getBody(), CitySuggestionsResponse.class);

		Assert.assertEquals(false, responseBody.getSuggestions().isEmpty());
	}

	@Test
	public void invalidQueryParams_missingLongitude_badRequestResponse() throws JsonParseException, JsonMappingException, IOException {
		String queryStr = "Londo&latitude=43.70011";

		ResponseEntity<String> response = callSuggestionsAPI(queryStr);

		Assert.assertEquals(400, response.getStatusCodeValue());

		ErrorResponse error = new ObjectMapper().readValue(response.getBody(), ErrorResponse.class);

		Assert.assertEquals("Latitude and longitude must both be provided at the same time.", error.getMessage());
	}

	@Test
	public void invalidQueryParams_missingLatitude_badRequestResponse() throws JsonParseException, JsonMappingException, IOException {
		String queryStr = "Londo&longitude=-79.4163";

		ResponseEntity<String> response = callSuggestionsAPI(queryStr);

		Assert.assertEquals(400, response.getStatusCodeValue());

		ErrorResponse error = new ObjectMapper().readValue(response.getBody(), ErrorResponse.class);

		Assert.assertEquals("Latitude and longitude must both be provided at the same time.", error.getMessage());
	}

	@Test
	public void validQueryParams_q_latitude_longitude_returnSuggestions() throws JsonParseException, JsonMappingException, IOException {
		String queryStr = "Londo&latitude=43.70011&longitude=-79.4163";

		ResponseEntity<String> response = callSuggestionsAPI(queryStr);

		Assert.assertEquals(200, response.getStatusCodeValue());

		CitySuggestionsResponse responseBody = new ObjectMapper().readValue(response.getBody(), CitySuggestionsResponse.class);

		Assert.assertEquals(false, responseBody.getSuggestions().isEmpty());
	}
}

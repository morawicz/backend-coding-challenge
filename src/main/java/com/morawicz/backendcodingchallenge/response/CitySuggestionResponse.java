package com.morawicz.backendcodingchallenge.response;

public class CitySuggestionResponse {

	private String name;
	private String latitude;
	private String longitude;
	private Double score;

	public void setName(String name) {
		this.name = name;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}

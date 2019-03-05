package com.morawicz.backendcodingchallenge.dtos;

public class CitySuggestion {

	// Name format: "City name, State/Province abbreviation, Country name"
	private String name;
	private String latitude;
	private String longitude;
	private Double score;

	public void setName(String name) {
		this.name = name;
	}

	public void setName(String cityName, String stateCode, String countryName) {
		this.name = String.format("%s, %s, %s", cityName, stateCode, countryName);
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = String.format("%.5f", latitude);
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = String.format("%.5f", longitude);
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public Double getScore() {
		return score;
	}

}

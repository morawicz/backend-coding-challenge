package com.morawicz.backendcodingchallenge.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.morawicz.backendcodingchallenge.dtos.CitySuggestion;
import com.morawicz.backendcodingchallenge.entities.CityEntity;
import com.morawicz.backendcodingchallenge.repositories.CityRepository;

@Service
public class CitySuggestionServiceImpl implements CitySuggestionService {

	private CityRepository cityRepository;

	public CitySuggestionServiceImpl(CityRepository cityRepository) {
		super();
		this.cityRepository = cityRepository;
	}

	@Override
	public List<CitySuggestion> findSuggestions(String name, Double latitude, Double longitude) {
		List<CityEntity> cityMatches = cityRepository.findByNameContainingIgnoreCase(name);
		return convertToCitySuggestions(cityMatches);
	}

	private List<CitySuggestion> convertToCitySuggestions(List<CityEntity> cityEntities) {

		return cityEntities.stream().map(cityEntity -> {
			CitySuggestion suggestion = new CitySuggestion();
			suggestion.setName(cityEntity.getName(), cityEntity.getStateCode(), cityEntity.getCountry());
			suggestion.setLatitude(cityEntity.getLatitude());
			suggestion.setLongitude(cityEntity.getLongitude());
			suggestion.setScore(0d);
			return suggestion;
		}).collect(Collectors.toList());
	}

}

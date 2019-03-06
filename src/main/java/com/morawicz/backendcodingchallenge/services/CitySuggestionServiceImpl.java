package com.morawicz.backendcodingchallenge.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.morawicz.backendcodingchallenge.dtos.CitySuggestion;
import com.morawicz.backendcodingchallenge.entities.CityEntity;
import com.morawicz.backendcodingchallenge.repositories.CityRepository;
import com.morawicz.backendcodingchallenge.utils.ScoreCalculator;

@Service
public class CitySuggestionServiceImpl implements CitySuggestionService {

	private CityRepository cityRepository;

	public CitySuggestionServiceImpl(CityRepository cityRepository) {
		super();
		this.cityRepository = cityRepository;
	}

	@Override
	public List<CitySuggestion> findSuggestions(String queryName, Double targetLatitude, Double targetLongitude) {
		List<CityEntity> cityMatches = cityRepository.findByNameContaining(queryName);
		List<CitySuggestion> suggestions = convertToCitySuggestions(queryName, targetLatitude, targetLongitude, cityMatches);
		Collections.sort(suggestions, new Comparator<CitySuggestion>() {

			@Override
			public int compare(CitySuggestion o1, CitySuggestion o2) {
				return Double.compare(o2.getScore(), o1.getScore());
			}

		});
		return suggestions;
	}

	private List<CitySuggestion> convertToCitySuggestions(String queryName, Double targetLatitude, Double targetLongitude, List<CityEntity> cityEntities) {
		Integer largestPopulation = cityEntities.stream().mapToInt(cityEntity -> cityEntity.getPopulation()).max().orElse(0);

		return cityEntities.stream().map(cityEntity -> {
			CitySuggestion suggestion = new CitySuggestion();
			suggestion.setName(cityEntity.getName(), cityEntity.getStateCode(), cityEntity.getCountry());
			suggestion.setLatitude(cityEntity.getLatitude());
			suggestion.setLongitude(cityEntity.getLongitude());
			suggestion.setScore(ScoreCalculator.calculateScore(queryName, targetLatitude, targetLongitude, largestPopulation, cityEntity));
			return suggestion;
		}).collect(Collectors.toList());
	}

}

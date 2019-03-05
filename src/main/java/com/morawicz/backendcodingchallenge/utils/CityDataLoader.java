package com.morawicz.backendcodingchallenge.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.morawicz.backendcodingchallenge.entities.CityEntity;
import com.morawicz.backendcodingchallenge.repositories.CityRepository;

@Component
public class CityDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private static final String TSV_FILE_PATH = "/cities_canada-usa.tsv";

	private CityRepository cityRepository;

	public CityDataLoader(CityRepository cityRepository) {
		super();
		this.cityRepository = cityRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initData();
	}

	private void initData() {
		BufferedReader reader = null;
		try {
			InputStream inputStream = this.getClass().getResourceAsStream(TSV_FILE_PATH);
			InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			reader = new BufferedReader(streamReader);

			// Discard tsv headers
			reader.readLine();

			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split("\t");

				CityEntity cityEntity = new CityEntity();
				cityEntity.setName(values[1]);
				cityEntity.setStateCode(parseProvince(values[10]));
				cityEntity.setCountry(parseCountry(values[8]));
				cityEntity.setLatitude(Double.parseDouble(values[4]));
				cityEntity.setLongitude(Double.parseDouble(values[5]));
				cityEntity.setPopulation(Integer.parseInt(values[14]));

				cityRepository.save(cityEntity);
			}

			reader.close();
		} catch (Exception e) {
			System.out.println("CityDataLoader Error: " + e.getMessage());
		}
	}

	private String parseCountry(String countryCode) {
		switch (countryCode.toUpperCase()) {
		case "CA":
			return "Canada";
		case "US":
			return "USA";
		default:
			return countryCode;
		}
	}

	private String parseProvince(String stateCode) {
		if (!stateCode.matches("\\d{2}")) {
			return stateCode;
		}

		// Ref: http://www.geonames.org/CA/administrative-division-canada.html
		switch (Integer.parseInt(stateCode)) {
		case 1:
			return "AB"; // Alberta
		case 2:
			return "BC"; // British Columbia
		case 3:
			return "MB"; // Manitoba
		case 4:
			return "NB"; // New Brunswick
		case 5:
			return "NL"; // Newfoundland and Labrador
		case 7:
			return "NS"; // Nova Scotia
		case 8:
			return "ON"; // Ontario
		case 9:
			return "PE"; // Prince Edward Island
		case 10:
			return "QC"; // Quebec
		case 11:
			return "SK"; // Saskatchewan
		case 12:
			return "YT"; // Yukon Territory
		case 13:
			return "NT"; // Northwest Territories
		case 14:
			return "NU"; // Nunavut
		default:
			return stateCode;
		}
	}

}

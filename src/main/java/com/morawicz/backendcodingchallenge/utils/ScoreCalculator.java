package com.morawicz.backendcodingchallenge.utils;

import com.morawicz.backendcodingchallenge.entities.CityEntity;

public class ScoreCalculator {

	private final static double KM = 1000.0;

	public static Double calculateScore(String queryName, Double targetLatitude, Double targetLongitude, Integer largestPopulation, CityEntity cityEntity) {
		double levenshteinScore = calculateLevenshteinScore(queryName, cityEntity.getName());
		double distanceScore = calculateDistanceScore(targetLatitude, targetLongitude, cityEntity.getLatitude(), cityEntity.getLongitude());
		double populationScore = calculatePopulationScore(largestPopulation, cityEntity.getPopulation());

		double score = 0.0;
		if (targetLatitude != null && targetLongitude != null) {
			score = 0.4 * levenshteinScore + 0.3 * distanceScore + 0.3 * populationScore;
		} else {
			score = 0.6 * levenshteinScore + 0.4 * populationScore;
		}
		return toOneDecimalPlace(score);
	}

	private static Double toOneDecimalPlace(double value) {
		return Math.round(value * 10.0) / 10.0;
	}

	private static double calculateLevenshteinScore(String queryName, String cityName) {
		int levenshteinDistance = levenshteinDistance(queryName, cityName);
		double score = 1.0 - levenshteinDistance * 0.1;
		return score < 0.0 ? 0.0 : score;
	}

	private static double calculateDistanceScore(Double targetLatitude, Double targetLongitude, Double cityLatitude, Double cityLongitude) {
		if (targetLatitude != null && targetLongitude != null) {
			double distanceInMeters = distance(targetLatitude, cityLatitude, targetLongitude, cityLongitude, 0, 0);

			if (distanceInMeters >= 0.0 && distanceInMeters < 50.0 * KM) { // less than 50km away
				return 1.0;
			} else if (distanceInMeters >= 50.0 * KM && distanceInMeters < 100.0 * KM) {
				return 0.9;
			} else if (distanceInMeters >= 100.0 * KM && distanceInMeters < 150.0 * KM) {
				return 0.8;
			} else if (distanceInMeters >= 150.0 * KM && distanceInMeters < 200.0 * KM) {
				return 0.7;
			} else if (distanceInMeters >= 200.0 * KM && distanceInMeters < 250.0 * KM) {
				return 0.6;
			} else if (distanceInMeters >= 250.0 * KM && distanceInMeters < 300.0 * KM) {
				return 0.5;
			} else if (distanceInMeters >= 300.0 * KM && distanceInMeters < 350.0 * KM) {
				return 0.4;
			} else if (distanceInMeters >= 350.0 * KM && distanceInMeters < 400.0 * KM) {
				return 0.3;
			} else if (distanceInMeters >= 400.0 * KM && distanceInMeters < 450.0 * KM) {
				return 0.2;
			} else if (distanceInMeters >= 450.0 * KM && distanceInMeters < 500.0 * KM) {
				return 0.1;
			}
		}
		return 0.0;
	}

	private static double calculatePopulationScore(Integer largestPopulation, Integer cityPopulation) {
		if (largestPopulation > 0) {
			return cityPopulation / largestPopulation;
		}
		return 0.0;
	}

	// Reference: https://rosettacode.org/wiki/Levenshtein_distance#Java
	public static int levenshteinDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 *
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	// Reference: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
	private static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
}

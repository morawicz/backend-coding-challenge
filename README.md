# Documentation

## H2 Database
On startup, the application parses the provided cities_canada-usa.tsv file and inserts specific data into an a cities table:
- city name
- state/province abbreviation
- country ('USA' or 'Canada')
- latitude
- longitude
- population

## Calling the CitySuggestions API
The application is hosted on Heroku at https://protected-sea-10929.herokuapp.com

API Query Param Restrictions:
- Query param "q" cannot be empty or less than 2 characters
- Query params latitude and longitude are optional
- Query params latitude and longitude if provided must both be present

Sample API Call:

    GET https://protected-sea-10929.herokuapp.com/suggestions?q=Londo&latitude=43.70011&longitude=-79.4163

Sample Response:

```
{
    "suggestions": [
        {
            "name": "London, ON, Canada",
            "latitude": "42.98339",
            "longitude": "-81.23304",
            "score": 0.9
        },
        {
            "name": "London, KY, USA",
            "latitude": "37.12898",
            "longitude": "-84.08326",
            "score": 0.4
        },
        {
            "name": "London, OH, USA",
            "latitude": "39.88645",
            "longitude": "-83.44825",
            "score": 0.4
        },
        {
            "name": "Londontowne, MD, USA",
            "latitude": "38.93345",
            "longitude": "-76.54941",
            "score": 0.2
        },
        {
            "name": "New London, CT, USA",
            "latitude": "41.35565",
            "longitude": "-72.09952",
            "score": 0.2
        },
        {
            "name": "Londonderry, NH, USA",
            "latitude": "42.86509",
            "longitude": "-71.37395",
            "score": 0.2
        },
        {
            "name": "New London, WI, USA",
            "latitude": "44.39276",
            "longitude": "-88.73983",
            "score": 0.2
        }
    ]
}
```

## Score Calculation
The score (ranging from 0.0 to 1.0) is determined by the weighted average of three separate scores:
- The Levenshtein Distance (the close similarity of the query input to a city name gives a higher score)
- The distance of the user to a city (proximity of user to a city gives a higher score)
- The population (a larger population gives a higher score)

When the user's latitude and longitude are provided, the weights are:

    0.4 * levenshtein_score + 0.3 * distance_score + 0.3 * population_score;

Without the user's location, the weights are:

    0.6 * levenshtein_score + 0.4 * population_score 

## Algorithm credit goes to:

The algorithm to calculate the Levenshtein Distance is taken from:
    
    https://rosettacode.org/wiki/Levenshtein_distance#Java

The algorithm to calculate the distance of a user from a city is taken from:

    https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude


# Coveo Backend Coding Challenge
(inspired by https://github.com/busbud/coding-challenge-backend-c)

## Requirements

Design an API endpoint that provides auto-complete suggestions for large cities.

- The endpoint is exposed at `/suggestions`
- The partial (or complete) search term is passed as a querystring parameter `q`
- The caller's location can optionally be supplied via querystring parameters `latitude` and `longitude` to help improve relative scores
- The endpoint returns a JSON response with an array of scored suggested matches
    - The suggestions are sorted by descending score
    - Each suggestion has a score between 0 and 1 (inclusive) indicating confidence in the suggestion (1 is most confident)
    - Each suggestion has a name which can be used to disambiguate between similarly named locations
    - Each suggestion has a latitude and longitude

## "The rules"

- *You can use the language and technology of your choosing.* It's OK to try something new (tell us if you do), but feel free to use something you're comfortable with. We don't care if you use something we don't; the goal here is not to validate your knowledge of a particular technology.
- End result should be deployed on a public Cloud (Heroku, AWS etc. all have free tiers you can use).

## Advices

- **Try to design and implement your solution as you would do for real production code**. Show us how you create clean, maintainable code that does awesome stuff. Build something that we'd be happy to contribute to. This is not a programming contest where dirty hacks win the game.
- Feel free to add more features! Really, we're curious about what you can think of. We'd expect the same if you worked with us.
- Documentation and maintainability is a plus.
- Don't you forget those unit tests.
- We don’t want to know if you can do exactly as asked (or everybody would have the same result). We want to know what **you** bring to the table when working on a project, what is your secret sauce. More features? Best solution? Thinking outside the box?

## Sample responses

These responses are meant to provide guidance. The exact values can vary based on the data source and scoring algorithm

**Near match**

    GET /suggestions?q=Londo&latitude=43.70011&longitude=-79.4163

```json
{
  "suggestions": [
    {
      "name": "London, ON, Canada",
      "latitude": "42.98339",
      "longitude": "-81.23304",
      "score": 0.9
    },
    {
      "name": "London, OH, USA",
      "latitude": "39.88645",
      "longitude": "-83.44825",
      "score": 0.5
    },
    {
      "name": "London, KY, USA",
      "latitude": "37.12898",
      "longitude": "-84.08326",
      "score": 0.5
    },
    {
      "name": "Londontowne, MD, USA",
      "latitude": "38.93345",
      "longitude": "-76.54941",
      "score": 0.3
    }
  ]
}
```

**No match**

    GET /suggestions?q=SomeRandomCityInTheMiddleOfNowhere

```json
{
  "suggestions": []
}
```

## References

- Geonames provides city lists Canada and the USA http://download.geonames.org/export/dump/readme.txt

## Getting Started

Begin by forking this repo and cloning your fork. GitHub has apps for [Mac](http://mac.github.com/) and
[Windows](http://windows.github.com/) that make this easier.

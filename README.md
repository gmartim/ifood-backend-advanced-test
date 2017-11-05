# iFood Backend Advanced Test

Create a micro-service able to accept RESTful requests receiving as parameter either city name or lat long coordinates and returns a playlist (only track names is fine) suggestion according to the current temperature.

## Business rules

* If temperature (celcius) is above 30 degrees, suggest tracks for party
* In case temperature is above 15 and below 30 degrees, suggest pop music tracks
* If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
* Otherwise, if it's freezing outside, suggests classical music tracks 

## Hints

You can make usage of OpenWeatherMaps API (https://openweathermap.org) to fetch temperature data and Spotify (https://developer.spotify.com) to suggest the tracks as part of the playlist.

## Non functional requirements

As this service will be a worldwide success,it must be prepared to be fault tolerant,responsive and resilient.

Use whatever language, tools and frameworks you feel comfortable to. 

Also, briefly elaborate on your solution, architecture details, choice of patterns and frameworks.

Fork this repository and submit your code.

## Development environment 

* Java (1.7)
* Maven (3.5.0)
* Eclipse (or any IDE or text editor you want)

## How to run it local

```bash
git clone 'git@github.com:gmartim/ifood-backend-advanced-test.git'
cd ifood-backend-advanced-test
mvn clean package
java -jar target/ifood-backend-advanced-test-0.0.1-SNAPSHOT.jar

```

You can validate its health running:

```bash
curl 'http://localhost:8080/health'
```

If everything is running fine, you shall see a response like:

```javascript
{"status":"UP"}
```

## CI

Continuous integration is set, using Travis CI for building and Bluemix for deploying.

If you want to validate the application's health on Bluemix you can run:

```bash
curl 'https://ifood-suggestion.mybluemix.net/health'
```

If everything is running fine, you shall see a response like:

```javascript
{"status":"UP"}
```

## Bluemix

It is a Platform as a Service (PaaS), besides others implementations, that offers a high availability and high performance environment, if can be easily scalable, horizontally and vertically. 

## API

API's documentation is automatically generated using Swagger. You can check it opening [Swagger UI](http://localhost:8080/swagger-ui.html) on your browser.

## API's request examples

[Using piracicaba as city name and br as country code](https://ifood-suggestion.mybluemix.net/api/playlist/weather/city/piracicaba?countryCode=br)

[Using piracicaba as city name](https://ifood-suggestion.mybluemix.net/api/playlist/weather/city/piracicaba)

[Using moscow as city name and ru as country code](https://ifood-suggestion.mybluemix.net/api/playlist/weather/city/moscow?countryCode=ru)

[Using moscow as city name](https://ifood-suggestion.mybluemix.net/api/playlist/weather/city/moscow)

[Using Piracicaba's latitude and longitude](https://ifood-suggestion.mybluemix.net/api/playlist/weather/lat/-22.73/lon/-47.65)
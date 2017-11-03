package br.eti.gm.ifood.suggestion.openweather;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.eti.gm.ifood.suggestion.Constant;
import br.eti.gm.ifood.suggestion.SuggestionException;

@Component
public class OpenWeatherWeatherRepository {

	private final Logger logger;

	@Value("${openWeather.api.weather.service.cityName}")
	private String serviceCityName;

	@Value("${openWeather.api.weather.service.latAndLon}")
	private String serviceLatAndLon;

	@Autowired
	private RestTemplate restTemplate;

	public OpenWeatherWeatherRepository() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public OpenWeatherWeatherResponse findByCityName(String cityName) throws SuggestionException {
		logger.debug("Started findByCityName {}", cityName);

		String url;

		url = String.format(serviceCityName, cityName);

		logger.debug("url: {}", url);

		ResponseEntity<OpenWeatherWeatherResponse> responseEntity;

		try {
			responseEntity = restTemplate.getForEntity(url, OpenWeatherWeatherResponse.class);
		} catch (Exception exception) {
			throw new SuggestionException("Error getting entity from OpenWeather", exception);
		}

		logger.debug("Finished findByCityNameAndCountryCode");

		return responseEntity.getBody();
	}

	public OpenWeatherWeatherResponse findByCityNameAndCountryCode(String cityName, String countryCode)
			throws SuggestionException {
		logger.debug("Started findByCityNameAndCountryCode {} {}", cityName, countryCode);

		String cityNameAndCountryCode;

		cityNameAndCountryCode = StringUtils.join(cityName, Constant.COMMA, countryCode);

		return findByCityName(cityNameAndCountryCode);
	}

	public OpenWeatherWeatherResponse findByLatAndLog(Double lat, Double lon) throws SuggestionException {
		logger.debug("Started findByLatAndLon {}", lat, lon);

		String url;

		url = String.format(Locale.ENGLISH, serviceLatAndLon, lat, lon);

		logger.debug("url: {}", url);

		ResponseEntity<OpenWeatherWeatherResponse> responseEntity;

		try {
			responseEntity = restTemplate.getForEntity(url, OpenWeatherWeatherResponse.class);
		} catch (Exception exception) {
			throw new SuggestionException("Error getting entity from OpenWeather", exception);
		}

		logger.debug("Finished findByLatAndLon");

		return responseEntity.getBody();
	}

}

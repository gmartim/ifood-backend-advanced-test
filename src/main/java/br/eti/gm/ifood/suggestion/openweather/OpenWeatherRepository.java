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
public class OpenWeatherRepository {

	private final Logger logger;

	@Value("${openWeather.api.service.cityName}")
	private String serviceCityName;

	@Value("${openWeather.api.service.latAndLon}")
	private String serviceLatAndLon;

	@Autowired
	private RestTemplate restTemplate;

	public OpenWeatherRepository() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public OpenWeatherResponse findByCityName(String cityName) throws SuggestionException {
		logger.debug("Started findByCityName {}", cityName);

		String url;

		url = String.format(serviceCityName, cityName);

		logger.debug("url: {}", url);

		ResponseEntity<OpenWeatherResponse> responseEntity;

		try {
			responseEntity = restTemplate.getForEntity(url, OpenWeatherResponse.class);
		} catch (Exception exception) {
			throw new SuggestionException("Error getting entity from OpenWeather", exception);
		}

		logger.debug("Finished findByCityNameAndCountryCode");

		return responseEntity.getBody();
	}

	public OpenWeatherResponse findByCityNameAndCountryCode(String cityName, String countryCode)
			throws SuggestionException {
		logger.debug("Started findByCityNameAndCountryCode {} {}", cityName, countryCode);

		String cityNameAndCountryCode;

		cityNameAndCountryCode = StringUtils.join(cityName, Constant.COMMA, countryCode);

		return findByCityName(cityNameAndCountryCode);
	}

	public OpenWeatherResponse findByLatAndLog(Double lat, Double lon) throws SuggestionException {
		logger.debug("Started findByLatAndLon {}", lat, lon);

		String url;

		url = String.format(Locale.ENGLISH, serviceLatAndLon, lat, lon);

		logger.debug("url: {}", url);

		ResponseEntity<OpenWeatherResponse> responseEntity;

		try {
			responseEntity = restTemplate.getForEntity(url, OpenWeatherResponse.class);
		} catch (Exception exception) {
			throw new SuggestionException("Error getting entity from OpenWeather", exception);
		}

		logger.debug("Finished findByLatAndLon");

		return responseEntity.getBody();
	}

}

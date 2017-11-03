package br.eti.gm.ifood.suggestion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.eti.gm.ifood.suggestion.entity.weather.Scale;

@Service
public class WeatherService {

	private final Logger logger;

	public WeatherService() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public Scale checkWeather(String cityName, String countryCode) {
		logger.debug("Started checkWeather {} {}", cityName, countryCode);

		Scale scale;

		scale = Scale.BELOW_10;

		logger.debug("Finished checkWeather {}", scale);

		return scale;
	}

}

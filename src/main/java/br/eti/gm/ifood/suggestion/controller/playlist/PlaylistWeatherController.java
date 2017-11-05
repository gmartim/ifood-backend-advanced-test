package br.eti.gm.ifood.suggestion.controller.playlist;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.eti.gm.ifood.suggestion.SuggestionException;
import br.eti.gm.ifood.suggestion.controller.AbstractController;
import br.eti.gm.ifood.suggestion.entity.Suggestion;
import br.eti.gm.ifood.suggestion.error.entity.Error;
import br.eti.gm.ifood.suggestion.service.PlaylistWeatherService;

@RestController
@RequestMapping("/api/playlist/weather")
public class PlaylistWeatherController extends AbstractController {

	@Autowired
	private PlaylistWeatherService playlistWeatherService;

	// added the :.+ in order to avoid missing anything after dot (.)
	@GetMapping("/city/{cityName:.+}")
	public ResponseEntity<?> mappingGetAndCity(@PathVariable(required = true, value = "cityName") String cityName,
			@RequestParam(required = false, value = "countryCode") String countryCode) {
		long startedAt;

		startedAt = System.currentTimeMillis();

		logger.info("Request /city/{}?countryCode={} received", cityName, countryCode);

		if (StringUtils.isBlank(cityName)) {
			// just for demonstration, it will never get true because validation on path
			// variable
			Error error;

			error = Error.invalidField().fieldBlank("cityName").build();

			return ResponseEntity.badRequest().body(error);
		}

		// adding a validation just to check if countryCode is 2 for length
		if (StringUtils.isNotBlank(countryCode)
				&& (countryCode.trim().length() < 2 || countryCode.trim().length() > 2)) {
			Error error;

			error = Error.invalidField().fieldSize("countryCode", 2, 2).build();

			return ResponseEntity.badRequest().body(error);
		}

		Suggestion suggestion;

		try {
			suggestion = playlistWeatherService.createSuggestionByCityNameAndCountryCode(cityName, countryCode);
		} catch (SuggestionException exception) {
			logger.error("Error creating suggestion", exception);

			Error error;

			error = Error.serviceFailed().message(exception.getMessage()).build();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}

		logger.info("Finished mappingGetAndCity in {} ms", (System.currentTimeMillis() - startedAt));

		return ResponseEntity.ok().body(suggestion);
	}

	// added the :.+ in order to avoid missing anything after dot (.)
	@GetMapping("/lat/{lat:.+}/lon/{lon:.+}")
	public ResponseEntity<?> mappingLatAndLon(@PathVariable(required = true, value = "lat") Double lat,
			@PathVariable(required = true, value = "lon") Double lon) {
		long startedAt;

		startedAt = System.currentTimeMillis();

		logger.info("Request /lat/{}/lon/{} received", lat, lon);

		Suggestion suggestion;

		try {
			suggestion = playlistWeatherService.createSuggestionByLatAndLon(lat, lon);
		} catch (SuggestionException exception) {
			logger.error("Error creating suggestion", exception);

			Error error;

			error = Error.serviceFailed().message(exception.getMessage()).build();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}

		logger.info("Finished mappingLatAndLon in {} ms", (System.currentTimeMillis() - startedAt));

		return ResponseEntity.ok().body(suggestion);
	}

}

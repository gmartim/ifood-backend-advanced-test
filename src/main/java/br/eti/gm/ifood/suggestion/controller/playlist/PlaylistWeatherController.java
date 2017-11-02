package br.eti.gm.ifood.suggestion.controller.playlist;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.eti.gm.ifood.suggestion.controller.AbstractController;
import br.eti.gm.ifood.suggestion.entity.Suggestion;
import br.eti.gm.ifood.suggestion.entity.playlist.Playlist;
import br.eti.gm.ifood.suggestion.entity.weather.Scale;
import br.eti.gm.ifood.suggestion.error.entity.Error;
import br.eti.gm.ifood.suggestion.service.PlaylistService;

@RestController
@RequestMapping("/api/playlist/weather")
public class PlaylistWeatherController extends AbstractController {

	@Autowired
	private PlaylistService playlistService;

	@GetMapping("/city/{cityName:.+}")
	public ResponseEntity<?> mappingGetAndCity(@PathVariable(required = true, value = "cityName") String cityName,
			@RequestParam(required = false, value = "countryCode") String countryCode) {
		logger.debug("Request /city {} {} received", cityName, countryCode);

		if (StringUtils.isBlank(cityName)) {
			// just for demonstration, it will never get true because pattern validation on
			// path variable
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

		Playlist playlist;

		playlist = playlistService.loadPlaylist(Scale.BELOW_10);

		Suggestion suggestion;

		suggestion = new Suggestion(playlist);

		return ResponseEntity.ok().body(suggestion);
	}

}

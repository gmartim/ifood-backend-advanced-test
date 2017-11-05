package br.eti.gm.ifood.suggestion.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.eti.gm.ifood.suggestion.SuggestionException;
import br.eti.gm.ifood.suggestion.entity.Suggestion;
import br.eti.gm.ifood.suggestion.entity.playlist.Playlist;
import br.eti.gm.ifood.suggestion.entity.playlist.Track;
import br.eti.gm.ifood.suggestion.openweather.OpenWeatherWeatherRepository;
import br.eti.gm.ifood.suggestion.openweather.OpenWeatherWeatherResponse;
import br.eti.gm.ifood.suggestion.openweather.response.Coord;
import br.eti.gm.ifood.suggestion.spotify.SpotifyRecommendationRepository;
import br.eti.gm.ifood.suggestion.spotify.SpotifyRecommendationResponse;

@Service
public class PlaylistWeatherService {

	private static final String GENRE_PARTY = "party";

	private static final String GENRE_POP = "pop";

	private static final String GENRE_ROCK = "rock";

	private static final String GENRE_CLASSICAL = "classical";

	private final Logger logger;

	@Autowired
	private OpenWeatherWeatherRepository openWeatherWeatherRepository;

	@Autowired
	private SpotifyRecommendationRepository spotifyRecommendationRepository;

	public PlaylistWeatherService() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public Suggestion createSuggestionByCityNameAndCountryCode(String cityName, String countryCode)
			throws SuggestionException {
		logger.debug("Started createSuggestionByCityNameAndCountryCode {} {}", cityName, countryCode);

		long startedAt;

		OpenWeatherWeatherResponse openWeatherWeatherResponse;

		startedAt = System.currentTimeMillis();

		if (StringUtils.isBlank(countryCode)) {
			openWeatherWeatherResponse = openWeatherWeatherRepository.findByCityName(cityName);
		} else {
			openWeatherWeatherResponse = openWeatherWeatherRepository.findByCityNameAndCountryCode(cityName,
					countryCode);
		}

		logger.info("openWeatherWeatherRepository.findByCityName (or findByCityNameAndCountryCode) executed in {} ms",
				(System.currentTimeMillis() - startedAt));

		String genre;

		genre = parseTempToGenre(openWeatherWeatherResponse.getMain().getTemp());

		logger.debug("genre: {}", genre);

		SpotifyRecommendationResponse spotifyRecommendationResponse;

		spotifyRecommendationResponse = getSpotifyRecommendationResponse(genre);

		Suggestion suggestion;

		suggestion = suggestionMapper(openWeatherWeatherResponse, spotifyRecommendationResponse, genre);

		logger.debug("Finished createSuggestionByCityNameAndCountryCode");

		return suggestion;
	}

	public Suggestion createSuggestionByLatAndLon(Double lat, Double lon) throws SuggestionException {
		logger.debug("Started createSuggestionByLatAndLon {} {}", lat, lon);

		long startedAt;

		OpenWeatherWeatherResponse openWeatherWeatherResponse;

		startedAt = System.currentTimeMillis();

		openWeatherWeatherResponse = openWeatherWeatherRepository.findByLatAndLog(lat, lon);

		logger.info("openWeatherWeatherRepository.findByLatAndLog executed in {} ms",
				(System.currentTimeMillis() - startedAt));

		String genre;

		genre = parseTempToGenre(openWeatherWeatherResponse.getMain().getTemp());

		logger.debug("genre: {}", genre);

		SpotifyRecommendationResponse spotifyRecommendationResponse;

		spotifyRecommendationResponse = getSpotifyRecommendationResponse(genre);

		Suggestion suggestion;

		suggestion = suggestionMapper(openWeatherWeatherResponse, spotifyRecommendationResponse, genre);

		logger.debug("Finished createSuggestionByCityNameAndCountryCode");

		return suggestion;
	}

	protected Suggestion suggestionMapper(OpenWeatherWeatherResponse openWeatherWeatherResponse,
			SpotifyRecommendationResponse spotifyRecommendationResponse, String genre) {
		List<Track> tracks;

		tracks = new ArrayList<>();

		Track parsedTrack;

		for (br.eti.gm.ifood.suggestion.spotify.response.Track track : spotifyRecommendationResponse.getTracks()) {
			parsedTrack = new Track(track.getName());

			tracks.add(parsedTrack);
		}

		Playlist playlist;

		playlist = new Playlist(tracks);

		Coord coord;

		coord = openWeatherWeatherResponse.getCoord();

		return new Suggestion(openWeatherWeatherResponse.getName(), openWeatherWeatherResponse.getMain().getTemp(),
				coord.getLat(), coord.getLon(), genre, playlist);
	}

	protected SpotifyRecommendationResponse getSpotifyRecommendationResponse(String genre) throws SuggestionException {
		long startedAt;

		SpotifyRecommendationResponse spotifyRecommendationResponse;

		startedAt = System.currentTimeMillis();

		spotifyRecommendationResponse = spotifyRecommendationRepository.findByGenre(genre);

		logger.info("spotifyRecommendationRepository.findByGenre executed in {} ms",
				(System.currentTimeMillis() - startedAt));

		return spotifyRecommendationResponse;
	}

	protected String parseTempToGenre(Double temp) {
		if (temp < 10) {
			return GENRE_CLASSICAL;
		} else if (temp < 15) {
			return GENRE_ROCK;
		} else if (temp < 30) {
			return GENRE_POP;
		} else {
			return GENRE_PARTY;
		}
	}

}

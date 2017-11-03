package br.eti.gm.ifood.suggestion.spotify;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.eti.gm.ifood.suggestion.Constant;
import br.eti.gm.ifood.suggestion.SuggestionException;

@Component
public class SpotifyRecommendationRepository extends AbstractSpotifyRepository {

	@Value("${spotify.api.recommendation.service.genre}")
	private String serviceGenre;

	@Autowired
	private SpotifyTokenRepository spotifyTokenRepository;

	public SpotifyRecommendationResponse findByGenre(String genre) throws SuggestionException {
		logger.debug("Started findByGenre {}", genre);

		SpotifyTokenResponse spotifyTokenResponse;

		spotifyTokenResponse = spotifyTokenRepository.getSpotifyTokenResponse();

		MultiValueMap<String, String> headers;

		headers = new LinkedMultiValueMap<String, String>();
		headers.add(Constant.AUTHORIZATION,
				StringUtils.join(Constant.BEARER, Constant.SPACE, spotifyTokenResponse.getAccessToken()));

		HttpEntity<MultiValueMap<String, String>> requestEntity;

		requestEntity = new HttpEntity<MultiValueMap<String, String>>(null, headers);

		String url;

		url = String.format(serviceGenre, genre);

		logger.debug("url: {}", url);

		ResponseEntity<SpotifyRecommendationResponse> responseEntity;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
					SpotifyRecommendationResponse.class);
		} catch (Exception exception) {
			throw new SuggestionException("Error exchanging to Spotify", exception);
		}

		logger.debug("Finished findByGenre");

		return responseEntity.getBody();
	}

}

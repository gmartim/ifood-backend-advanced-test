package br.eti.gm.ifood.suggestion.spotify;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
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
public class SpotifyTokenRepository extends AbstractSpotifyRepository {

	@Value("${spotify.api.token.service}")
	private String service;

	private SpotifyTokenResponse spotifyTokenResponse;

	private long itExpiresAt;

	@PostConstruct
	public void postConstruct() {
		logger.debug("Started postContruct");

		request();

		logger.debug("Finished postContruct");
	}

	public SpotifyTokenResponse getSpotifyTokenResponse() throws SuggestionException {
		if (System.currentTimeMillis() > this.itExpiresAt) {
			request();
		}

		if (this.spotifyTokenResponse == null) {
			throw new SuggestionException("Error getting token from Spotify");
		}

		return this.spotifyTokenResponse;
	}

	protected void request() {
		logger.debug("Started request");

		MultiValueMap<String, String>

		body = new LinkedMultiValueMap<String, String>();
		body.add(Constant.GRANT_TYPE, Constant.CLIENT_CREDENTIALS);

		String encodedAuthorizationValue;

		encodedAuthorizationValue = getEncodedAuthorizationValue();

		logger.debug("encodedAuthorizationValue: {}", encodedAuthorizationValue);

		MultiValueMap<String, String> headers;

		headers = new LinkedMultiValueMap<String, String>();
		headers.add(Constant.AUTHORIZATION,
				StringUtils.join(Constant.BASIC, Constant.SPACE, encodedAuthorizationValue));

		HttpEntity<MultiValueMap<String, String>> requestEntity;

		requestEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);

		String url;

		url = service;

		logger.debug("url: {}", url);

		ResponseEntity<SpotifyTokenResponse> responseEntity;

		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SpotifyTokenResponse.class);
		} catch (Exception exception) {
			logger.error("Error exchanging from Spotify", exception);

			this.spotifyTokenResponse = null;

			return;
		}

		this.spotifyTokenResponse = responseEntity.getBody();
		this.itExpiresAt = System.currentTimeMillis() + (this.spotifyTokenResponse.getExpiresIn() * 1000);
		// subtracting a delta of 5 minutes to have a safety window of expiring
		this.itExpiresAt = this.itExpiresAt - (5 * 60 * 1000);

		logger.debug("Finished request");
	}

}

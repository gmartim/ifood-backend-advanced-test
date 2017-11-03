package br.eti.gm.ifood.suggestion.spotify;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import br.eti.gm.ifood.suggestion.Constant;

public abstract class AbstractSpotifyRepository {

	protected final Logger logger;

	@Value("${spotify.api.clientId}")
	private String clientId;

	@Value("${spotify.api.clientSecret}")
	private String clientSecret;

	@Autowired
	protected RestTemplate restTemplate;

	public AbstractSpotifyRepository() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	protected String getAuthorizationValue() {
		return StringUtils.join(clientId, Constant.COLON, clientSecret);
	}

	protected String getEncodedAuthorizationValue() {
		return encodeBase64(getAuthorizationValue().getBytes());
	}

	protected String encodeBase64(byte[] value) {
		return Base64.encodeBase64String(value);
	}

}

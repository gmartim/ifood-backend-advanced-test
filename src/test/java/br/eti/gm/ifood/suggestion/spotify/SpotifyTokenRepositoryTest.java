package br.eti.gm.ifood.suggestion.spotify;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@Import({ SpotifyTokenRepository.class, RestTemplate.class })
@PropertySource("classpath:application.properties")
public class SpotifyTokenRepositoryTest {

	@Autowired
	private SpotifyTokenRepository spotifyTokenRepository;

	@Before
	public void before() {
		Assert.assertNotNull("spotifyTokenRepository is null", spotifyTokenRepository);
	}

	@Test
	public void testEncodedAuthorizationValue() {
		String encodedAuthorizationValue;

		encodedAuthorizationValue = spotifyTokenRepository.getEncodedAuthorizationValue();

		Assert.assertNotNull("encodedAuthorizationValue is null", encodedAuthorizationValue);
		Assert.assertEquals("encodedAuthorizationValue is not equals",
				"NzA4NmVlZWEwNmYwNGViYmE2ODc3MmIwYzkxYzkyN2M6NWFjY2Q3NGQ5MDcwNDY1M2JiNjRkMDEzZDdlZTY5NjA=",
				encodedAuthorizationValue);
	}

	// @Test
	// disbaled this test because it makes build on Travis CI fails due to the
	// sleeping
	public void testGetSpotifyTokenResponse() {
		SpotifyTokenResponse spotifyTokenResponse;

		try {
			spotifyTokenResponse = spotifyTokenRepository.getSpotifyTokenResponse();
		} catch (Exception exception) {
			Assert.fail("Error getting Spotify token response");

			return;
		}

		Assert.assertNotNull("spotifyTokenResponse is null", spotifyTokenResponse);

		Assert.assertNotNull("spotifyTokenResponse.accessToken is null", spotifyTokenResponse.getAccessToken());
		Assert.assertFalse("spotifyTokenResponse.accessToken is null",
				StringUtils.isBlank(spotifyTokenResponse.getAccessToken()));

		Assert.assertNotNull("spotifyTokenResponse.tokenType is null", spotifyTokenResponse.getTokenType());
		Assert.assertFalse("spotifyTokenResponse.tokenType is null",
				StringUtils.isBlank(spotifyTokenResponse.getTokenType()));

		Assert.assertNotNull("spotifyTokenResponse.expiresIn is null", spotifyTokenResponse.getExpiresIn());
		Assert.assertEquals("spotifyTokenResponse.expiresIn is not equals", new Integer("3600"),
				spotifyTokenResponse.getExpiresIn());

		String accessToken;

		accessToken = spotifyTokenResponse.getAccessToken();

		try {
			spotifyTokenResponse = spotifyTokenRepository.getSpotifyTokenResponse();
		} catch (Exception exception) {
			Assert.fail("Error getting Spotify token response");

			return;
		}

		Assert.assertNotNull("spotifyTokenResponse is null", spotifyTokenResponse);

		Assert.assertNotNull("spotifyTokenResponse.accessToken is null", spotifyTokenResponse.getAccessToken());
		Assert.assertFalse("spotifyTokenResponse.accessToken is null",
				StringUtils.isBlank(spotifyTokenResponse.getAccessToken()));
		Assert.assertEquals("accessToken is not equals", accessToken, spotifyTokenResponse.getAccessToken());

		// sleeping for 1 hour to test refresh
		try {
			Thread.sleep(3600 * 1000);
		} catch (InterruptedException exception) {
			Assert.fail("Error sleeping");

			return;
		}

		try {
			spotifyTokenResponse = spotifyTokenRepository.getSpotifyTokenResponse();
		} catch (Exception exception) {
			Assert.fail("Error getting Spotify token response");

			return;
		}

		Assert.assertNotNull("spotifyTokenResponse is null", spotifyTokenResponse);

		Assert.assertNotNull("spotifyTokenResponse.accessToken is null", spotifyTokenResponse.getAccessToken());
		Assert.assertFalse("spotifyTokenResponse.accessToken is null",
				StringUtils.isBlank(spotifyTokenResponse.getAccessToken()));
		Assert.assertNotEquals("accessToken is equals", accessToken, spotifyTokenResponse.getAccessToken());
	}

}

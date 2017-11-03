package br.eti.gm.ifood.suggestion.spotify;

import java.util.List;

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

import br.eti.gm.ifood.suggestion.SuggestionException;
import br.eti.gm.ifood.suggestion.spotify.response.Track;

@RunWith(SpringRunner.class)
@Import({ SpotifyRecommendationRepository.class, SpotifyTokenRepository.class, RestTemplate.class })
@PropertySource("classpath:application.properties")
public class SpotifyRecommendationRepositoryTest {

	@Autowired
	private SpotifyRecommendationRepository spotifyRecommendationRepository;

	@Before
	public void before() {
		Assert.assertNotNull("spotifyRecommendationRepository is null", spotifyRecommendationRepository);
	}

	@Test
	public void testFindByGenre() {
		SpotifyRecommendationResponse spotifyRecommendationResponse;

		List<Track> tracks;

		Track track;

		try {
			spotifyRecommendationResponse = spotifyRecommendationRepository.findByGenre("rock");
		} catch (SuggestionException exception) {
			Assert.fail("Error finding by genre");

			return;
		}

		Assert.assertNotNull("spotifyRecommendationResponse is null", spotifyRecommendationResponse);

		tracks = spotifyRecommendationResponse.getTracks();

		Assert.assertNotNull("tracks is null", tracks);
		Assert.assertFalse("tracks is empty", tracks.isEmpty());

		track = tracks.get(0);

		Assert.assertNotNull("track is null", track);
		Assert.assertFalse("track.name is empty", StringUtils.isBlank(track.getName()));

		try {
			spotifyRecommendationResponse = spotifyRecommendationRepository.findByGenre("party");
		} catch (SuggestionException exception) {
			Assert.fail("Error finding by genre");

			return;
		}

		Assert.assertNotNull("spotifyRecommendationResponse is null", spotifyRecommendationResponse);

		tracks = spotifyRecommendationResponse.getTracks();

		Assert.assertNotNull("tracks is null", tracks);
		Assert.assertFalse("tracks is empty", tracks.isEmpty());

		track = tracks.get(0);

		Assert.assertNotNull("track is null", track);
		Assert.assertFalse("track.name is empty", StringUtils.isBlank(track.getName()));

		try {
			spotifyRecommendationResponse = spotifyRecommendationRepository.findByGenre("pop");
		} catch (SuggestionException exception) {
			Assert.fail("Error finding by genre");

			return;
		}

		Assert.assertNotNull("spotifyRecommendationResponse is null", spotifyRecommendationResponse);

		tracks = spotifyRecommendationResponse.getTracks();

		Assert.assertNotNull("tracks is null", tracks);
		Assert.assertFalse("tracks is empty", tracks.isEmpty());

		track = tracks.get(0);

		Assert.assertNotNull("track is null", track);
		Assert.assertFalse("track.name is empty", StringUtils.isBlank(track.getName()));

		try {
			spotifyRecommendationResponse = spotifyRecommendationRepository.findByGenre("classical");
		} catch (SuggestionException exception) {
			Assert.fail("Error finding by genre");

			return;
		}

		Assert.assertNotNull("spotifyRecommendationResponse is null", spotifyRecommendationResponse);

		tracks = spotifyRecommendationResponse.getTracks();

		Assert.assertNotNull("tracks is null", tracks);
		Assert.assertFalse("tracks is empty", tracks.isEmpty());

		track = tracks.get(0);

		Assert.assertNotNull("track is null", track);
		Assert.assertFalse("track.name is empty", StringUtils.isBlank(track.getName()));
	}

}

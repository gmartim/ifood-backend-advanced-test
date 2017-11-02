package br.eti.gm.ifood.suggestion.controller.playlist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.eti.gm.ifood.suggestion.entity.playlist.Playlist;
import br.eti.gm.ifood.suggestion.entity.playlist.Track;
import br.eti.gm.ifood.suggestion.entity.weather.Scale;
import br.eti.gm.ifood.suggestion.service.PlaylistService;

@RunWith(SpringRunner.class)
@WebMvcTest(PlaylistWeatherController.class)
public class PlaylistWeatherControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected PlaylistService playlistService;

	@Before
	public void before() {
		Track track1, track2, track3;

		track1 = new Track("Track 1");
		track2 = new Track("Track 2");
		track3 = new Track("Track 3");

		Playlist playlist;

		playlist = new Playlist("Playlist", track1, track2, track3);

		Mockito.when(playlistService.loadPlaylist(Mockito.any(Scale.class))).thenReturn(playlist);
	}

	@Test
	public void testMappingGetAndCity() throws Exception {
		mockMvc.perform(get("/api/playlist/weather/city/Piracicaba?countryCode=bra")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code", Is.is("001"))).andExpect(jsonPath("$.reason", Is.is("001")))
				.andExpect(jsonPath("$.details.countryCode", Is.is("size must be between 2 and 2")));

		mockMvc.perform(get("/api/playlist/weather/city/Piracicaba?countryCode=br")).andExpect(status().isOk())
				.andExpect(jsonPath("$.playlist.name", Is.is("Playlist")));

		mockMvc.perform(get("/api/playlist/weather/city/Piracicaba")).andExpect(status().isOk())
				.andExpect(jsonPath("$.playlist.tracks[0].name", Is.is("Track 1")));
	}

	@Test
	public void testMappingGetAndPing() throws Exception {
		mockMvc.perform(get("/api/playlist/weather/ping")).andExpect(status().isOk());
	}

}

package br.eti.gm.ifood.suggestion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.eti.gm.ifood.suggestion.entity.playlist.Playlist;
import br.eti.gm.ifood.suggestion.entity.playlist.Track;
import br.eti.gm.ifood.suggestion.entity.weather.Scale;

@Service
public class PlaylistService {

	private final Logger logger;

	public PlaylistService() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public Playlist loadPlaylist(Scale scale) {
		logger.debug("Started loadPlaylist {}", scale);

		Track track1, track2, track3;

		track1 = new Track("Track 1");
		track2 = new Track("Track 2");
		track3 = new Track("Track 3");

		Playlist playlist;

		playlist = new Playlist("Playlist", track1, track2, track3);

		logger.debug("Finished loadPlaylist {}", scale);

		return playlist;
	}

}

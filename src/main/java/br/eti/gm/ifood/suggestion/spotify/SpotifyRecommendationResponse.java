package br.eti.gm.ifood.suggestion.spotify;

import java.util.List;

import br.eti.gm.ifood.suggestion.spotify.response.Track;

public class SpotifyRecommendationResponse {

	private List<Track> tracks;

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

}

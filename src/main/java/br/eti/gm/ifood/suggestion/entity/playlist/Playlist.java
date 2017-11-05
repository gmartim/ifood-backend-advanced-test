package br.eti.gm.ifood.suggestion.entity.playlist;

import java.util.List;

public class Playlist {

	private List<Track> tracks;

	public Playlist(List<Track> tracks) {
		this.tracks = tracks;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

}

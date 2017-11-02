package br.eti.gm.ifood.suggestion.entity.playlist;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

	private String name;

	private List<Track> tracks;

	public Playlist(String name, Track... tracks) {
		this.name = name;
		this.tracks = new ArrayList<>();

		for (Track track : tracks) {
			this.tracks.add(track);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

}

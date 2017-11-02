package br.eti.gm.ifood.suggestion.entity;

import br.eti.gm.ifood.suggestion.entity.playlist.Playlist;

public class Suggestion {

	private Playlist playlist;

	public Suggestion(Playlist playlist) {
		this.playlist = playlist;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

}

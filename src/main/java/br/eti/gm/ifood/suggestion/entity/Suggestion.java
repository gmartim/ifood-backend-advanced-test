package br.eti.gm.ifood.suggestion.entity;

import br.eti.gm.ifood.suggestion.entity.playlist.Playlist;

public class Suggestion {

	private String cityName;

	private Double temp;

	private Double lat;

	private Double lon;

	private String genre;

	private Playlist playlist;

	public Suggestion(String cityName, Double temp, Double lat, Double lon, String genre, Playlist playlist) {
		this.cityName = cityName;
		this.temp = temp;
		this.lat = lat;
		this.lon = lon;
		this.genre = genre;
		this.playlist = playlist;
	}

	public String getCityName() {
		return cityName;
	}

	public Double getTemp() {
		return temp;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}

	public String getGenre() {
		return genre;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

}

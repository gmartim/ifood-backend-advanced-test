package br.eti.gm.ifood.suggestion.openweather;

import br.eti.gm.ifood.suggestion.openweather.response.Coord;
import br.eti.gm.ifood.suggestion.openweather.response.Main;

public class OpenWeatherResponse {

	private Coord coord;

	private Main main;

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

}

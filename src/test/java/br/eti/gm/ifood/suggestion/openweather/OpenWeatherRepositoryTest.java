package br.eti.gm.ifood.suggestion.openweather;

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
import br.eti.gm.ifood.suggestion.openweather.response.Coord;
import br.eti.gm.ifood.suggestion.openweather.response.Main;

@RunWith(SpringRunner.class)
@Import({ OpenWeatherRepository.class, RestTemplate.class })
@PropertySource("classpath:application.properties")
public class OpenWeatherRepositoryTest {

	@Autowired
	private OpenWeatherRepository openWeatherRepository;

	@Before
	public void before() {
		Assert.assertNotNull("openWeatherRepository is null", openWeatherRepository);
	}

	@Test
	public void testFindByCityNameAndCountryCode() {
		OpenWeatherResponse openWeatherResponse;

		Coord coord;

		Main main;

		try {
			openWeatherResponse = openWeatherRepository.findByCityNameAndCountryCode("Piracicaba", "br");
		} catch (SuggestionException exception) {
			Assert.fail("Error finding by city name and country code");

			return;
		}

		Assert.assertNotNull("openWeatherResponse is null", openWeatherResponse);

		coord = openWeatherResponse.getCoord();

		Assert.assertNotNull("coord is null", coord);
		Assert.assertNotNull("coor.lat is null", coord.getLat());
		Assert.assertNotNull("coor.lon is null", coord.getLon());
		Assert.assertEquals("coord.lat is not equals", new Double("-22.73"), coord.getLat());
		Assert.assertEquals("coord.lon is not equals", new Double("-47.65"), coord.getLon());

		main = openWeatherResponse.getMain();

		Assert.assertNotNull("main is null", main);
		Assert.assertNotNull("main.temp is null", main.getTemp());
		// I could use even > 20 to Piracicaba :(
		Assert.assertTrue("main.temp is not greater than zero", (main.getTemp() > 1));

		try {
			openWeatherResponse = openWeatherRepository.findByCityName("Piracicaba");
		} catch (SuggestionException exception) {
			Assert.fail("Error finding by city name");

			return;
		}

		Assert.assertNotNull("openWeatherResponse is null", openWeatherResponse);

		coord = openWeatherResponse.getCoord();

		Assert.assertNotNull("coord is null", coord);
		Assert.assertNotNull("coord.lat is null", coord.getLat());
		Assert.assertNotNull("coord.lon is null", coord.getLon());
		Assert.assertEquals("coord.lat is not equals", new Double("-22.73"), coord.getLat());
		Assert.assertEquals("coord.lon is not equals", new Double("-47.65"), coord.getLon());
	}

	@Test
	public void testFindByLatAndLog() {
		OpenWeatherResponse openWeatherResponse;

		Coord coord;

		Main main;

		try {
			openWeatherResponse = openWeatherRepository.findByLatAndLog((double) -22.73, (double) -47.65);
		} catch (SuggestionException exception) {
			Assert.fail("Error finding by city name and country code");

			return;
		}

		Assert.assertNotNull("openWeatherResponse is null", openWeatherResponse);

		coord = openWeatherResponse.getCoord();

		Assert.assertNotNull("coord is null", coord);
		Assert.assertNotNull("coor.lat is null", coord.getLat());
		Assert.assertNotNull("coor.lon is null", coord.getLon());
		Assert.assertEquals("coord.lat is not equals", new Double("-22.73"), coord.getLat());
		Assert.assertEquals("coord.lon is not equals", new Double("-47.65"), coord.getLon());

		main = openWeatherResponse.getMain();

		Assert.assertNotNull("main is null", main);
		Assert.assertNotNull("main.temp is null", main.getTemp());
		// I could use even > 20 to Piracicaba :(
		Assert.assertTrue("main.temp is not greater than zero", (main.getTemp() > 1));
	}

}

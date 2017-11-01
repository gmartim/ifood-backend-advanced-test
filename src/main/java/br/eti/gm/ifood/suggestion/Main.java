package br.eti.gm.ifood.suggestion;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String... arguments) {
		logger.info("Started main {}", Arrays.asList(arguments));

		SpringApplication.run(Main.class, arguments);
	}

}

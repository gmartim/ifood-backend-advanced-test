package br.eti.gm.ifood.suggestion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class AbstractController {

	protected final Logger logger;

	public AbstractController() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@GetMapping("/ping")
	public ResponseEntity<Void> mappingGetAndPing() {
		logger.debug("Request /ping received");

		return ResponseEntity.ok().build();
	}

}

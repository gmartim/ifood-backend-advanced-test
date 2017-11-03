package br.eti.gm.ifood.suggestion;

public class SuggestionException extends Exception {

	private static final long serialVersionUID = 1L;

	public SuggestionException(String message) {
		super(message);
	}

	public SuggestionException(String message, Throwable cause) {
		super(message, cause);
	}

}

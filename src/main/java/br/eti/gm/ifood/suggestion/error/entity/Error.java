package br.eti.gm.ifood.suggestion.error.entity;

import java.util.HashMap;
import java.util.Map;

import br.eti.gm.ifood.suggestion.Constant;

public class Error {

	private String code;

	private String reason;

	private Map<String, String> details;

	public Error(String code, String reason, Map<String, String> details) {
		this.code = code;
		this.reason = reason;
		this.details = details;
	}

	public String getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public static ErrorBuilder invalidField() {
		return new DefaultErrorBuilder(Constant.ERROR_CODE_BAD_REQUEST, Constant.ERROR_REASON_INVALID_FIELD);
	}

	public static ErrorBuilder serviceFailed() {
		return new DefaultErrorBuilder(Constant.ERROR_CODE_INTERNAL_SERVER_ERROR, Constant.ERROR_REASON_SERVICE_FAILED);
	}

	public interface ErrorBuilder {

		ErrorBuilder fieldBlank(String fieldName);

		ErrorBuilder fieldSize(String fieldName, int min, int max);

		ErrorBuilder message(String message);

		Error build();

	}

	private static class DefaultErrorBuilder implements ErrorBuilder {

		private String code;

		private String reason;

		private Map<String, String> details;

		private DefaultErrorBuilder(String code, String reason) {
			this.code = code;
			this.reason = reason;
			this.details = new HashMap<>();
		}

		@Override
		public ErrorBuilder fieldBlank(String fieldName) {
			details.put(fieldName, Constant.ERROR_FIELD_BLANK);

			return this;
		}

		@Override
		public ErrorBuilder fieldSize(String fieldName, int min, int max) {
			details.put(fieldName, String.format(Constant.ERROR_FIELD_SIZE, min, max));

			return this;
		}

		@Override
		public Error build() {
			return new Error(code, reason, details);
		}

		@Override
		public ErrorBuilder message(String message) {
			details.put(Constant.MESSAGE, message);

			return this;
		}

	}

}

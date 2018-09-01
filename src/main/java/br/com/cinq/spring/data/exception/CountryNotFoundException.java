package br.com.cinq.spring.data.exception;

public class CountryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public static final String MESSAGE = "Country with id %s not found.";
	
	private final Long countryId;
	
	public CountryNotFoundException(final Long countryId) {
		this.countryId = countryId;
	}
	
	@Override
	public String getMessage() {
		return String.format(MESSAGE, countryId);
	}
}

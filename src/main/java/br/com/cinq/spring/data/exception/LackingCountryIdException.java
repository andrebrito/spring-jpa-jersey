package br.com.cinq.spring.data.exception;

public class LackingCountryIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public static final String MESSAGE = "Lacking country's id.";
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}

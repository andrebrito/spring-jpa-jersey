package br.com.cinq.spring.data.exception;

public class LackingCityNameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public static final String MESSAGE = "Lacking city name.";
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}

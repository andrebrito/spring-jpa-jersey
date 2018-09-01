package br.com.cinq.spring.data.builder;

import java.util.function.Consumer;

import br.com.cinq.spring.data.entity.Country;

public class CountryBuilder {
	
	private String name;
	
	public CountryBuilder with(final Consumer<CountryBuilder> builderFunction) {
		builderFunction.accept(this);
		return this;
	}
	
	public Country createCountry() {
		final Country country = new Country();
		country.setName(name);
		
		return country;
	}
	
	public CountryBuilder setName(final String name) {
		this.name = name;
		return this;
	}

}

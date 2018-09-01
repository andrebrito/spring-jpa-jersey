package br.com.cinq.spring.data.builder;

import java.util.function.Consumer;

import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.entity.Country;

public class CityBuilder {
	
	private String name;
	private Country country;
	
	public CityBuilder with(final Consumer<CityBuilder> builderFunction) {
		builderFunction.accept(this);
		return this;
	}
	
	public City createCity() {
		final City city = new City();
		city.setName(name);
		city.setCountry(country);
		
		return city;
	}
	
	public CityBuilder setName(final String name) {
		this.name = name;
		return this;
	}
	
	public CityBuilder setCountry(final Country country) {
		this.country = country;
		return this;
	}

}

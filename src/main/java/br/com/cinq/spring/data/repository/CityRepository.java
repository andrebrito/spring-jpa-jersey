package br.com.cinq.spring.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.entity.Country;

public interface CityRepository extends CrudRepository<City, Long> {
	
	public List<City> findAllByCountry(final Country country);
	
	public List<City> findByName(final String name);

	public List<City> findByNameContainingIgnoreCase(final String name);

	public List<City> findAllByCountryName(final String string);

}

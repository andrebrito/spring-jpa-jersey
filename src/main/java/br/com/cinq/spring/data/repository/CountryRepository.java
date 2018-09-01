package br.com.cinq.spring.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cinq.spring.data.entity.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {
	
	public List<Country> findByNameContainingIgnoreCase(final String name);

	public Country findByNameIgnoreCase(final String country);

}

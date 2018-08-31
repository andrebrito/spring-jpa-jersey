package br.com.cinq.spring.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cinq.spring.data.entity.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {
	
	@Query("from Country where name like :name")
	public List<Country> findLikeName(@Param("name") final String name);

}

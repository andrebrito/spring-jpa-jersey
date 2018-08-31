package br.com.cinq.spring.data.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.cinq.spring.data.entity.City;

public interface CityRepository extends CrudRepository<City, Long> {

}

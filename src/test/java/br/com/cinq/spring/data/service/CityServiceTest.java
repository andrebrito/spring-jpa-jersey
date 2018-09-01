package br.com.cinq.spring.data.service;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cinq.spring.data.application.Application;
import br.com.cinq.spring.data.dto.CityDTO;
import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.exception.CountryNotFoundException;
import br.com.cinq.spring.data.exception.LackingCityNameException;
import br.com.cinq.spring.data.exception.LackingCountryIdException;
import br.com.cinq.spring.data.repository.CityRepository;

/**
 * This test aims to validate the error messages that ocurr when we try 
 * to create an invalid city. It means that when we try to create a:
 * <ul><li>city without a name</li>
 * <li>city with a country that does not exists</li>
 * <li>city with a country without id</li></ul>
 * an IllegalArgumentException will be thrown.
 * 
 * In JUnit 5 there's a better way to use these assertions. 
 * Something like assertThrows(Exception.class, () -> service.create()).
 * But there's a bug with Eclipse integration that it does not allow 
 * to run the code in Eclipse. Because of that, the execution of 
 * Coverage wouldn't be possible, so I'm using the old fashion Rule.
 * 
 * @author Andre Brito Fonseca
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestConfiguration(value = "test")
public class CityServiceTest {
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void shouldOccurrExceptionWhenCreatingCityWithoutName() {
		expectedException.expect(LackingCityNameException.class);
		cityService.createCity(new CityDTO(null, 1L));
	}
	
	@Test
	public void shouldOccurrExceptionWhenCreatingCityWithUnexistingCountry() {
		expectedException.expect(CountryNotFoundException.class);
		cityService.createCity(new CityDTO("Town", 10000L));
	}
	
	@Test
	public void shouldOccurrExceptionWhenCreatingCityWithoutCountry() {
		expectedException.expect(LackingCountryIdException.class);
		cityService.createCity(new CityDTO("Town", null));
	}
	
	@Test
	public void shoudCreateCityWhenAllParametersAreValid() {
		final List<City> citiesBefore = cityRepository.findByName("Town");
		assertThat(citiesBefore, empty());
		
		cityService.createCity(new CityDTO("Town", 1L));
		
		final List<City> citiesAfter = cityRepository.findByName("Town");
		assertThat(citiesAfter, hasSize(1));
	}

}

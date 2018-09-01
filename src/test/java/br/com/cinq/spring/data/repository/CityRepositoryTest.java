package br.com.cinq.spring.data.repository;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cinq.spring.data.application.Application;
import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.entity.Country;
import br.com.cinq.spring.matcher.CityMatcher;

/**
 * Testing CountryRepository using application-test config file.
 * @author Andre Brito Fonseca
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestConfiguration(value = "test")
public class CityRepositoryTest {

    @Autowired
    private CityRepository dao;

    @Test
    public void testFindCityByCountry() {
        assertNotNull(dao);
        assertThat(dao.count(), greaterThan(0L));

        final Country country = new Country();
        country.setId(3L);

        final List<City> cities = dao.findAllByCountry(country);
        assertThat(cities, hasSize(2));
        assertThat(cities, CityMatcher.hasCityWithName("Paris"));
        assertThat(cities, CityMatcher.hasCityWithName("Lyon"));
    }
    
    @Test
    public void testFindCityByCountryName() {
        assertNotNull(dao);
        assertThat(dao.count(), greaterThan(0L));

        final List<City> cities = dao.findAllByCountryName("France");
        assertThat(cities, hasSize(2));
        assertThat(cities, CityMatcher.hasCityWithName("Paris"));
        assertThat(cities, CityMatcher.hasCityWithName("Lyon"));
    }
    
    /**
     * Test the find the name criteria.
     * Using CityMatcher. 
     */
    @Test
    public void testFindByName() {
        assertNotNull(dao);

        final List<City> cities = dao.findByName("Curitiba");
        assertThat(cities, hasSize(1));
        
        assertThat(cities, CityMatcher.hasCityWithName("Curitiba"));
    }
    
    /**
     * Test the find using like by name, ignoring the capitalization.
     * Using CityMatcher. 
     */
    @Test
    public void testFindMultipleCitiesByName() {
        assertNotNull(dao);

        final List<City> cities = dao.findByNameContainingIgnoreCase("y");
        assertThat(cities, hasSize(2));
        
        assertThat(cities, CityMatcher.hasCityWithName("Lyon"));
        assertThat(cities, CityMatcher.hasCityWithName("New York"));
    }
}

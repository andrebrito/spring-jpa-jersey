package br.com.cinq.spring.data.repository;

import static org.hamcrest.Matchers.equalTo;
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
import br.com.cinq.spring.data.entity.Country;
import br.com.cinq.spring.matcher.CountryMatcher;

/**
 * Testing CountryRepository using application-test config file.
 * @author Andre Brito Fonseca
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestConfiguration(value = "test")
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository dao;

    /**
     * Test the findAll method.
     */
    @Test
    public void testFindAllCountries() {
        assertNotNull(dao);
        
        final long count = dao.count();
        assertThat(count, greaterThan(0L));

        final Iterable<Country> countries = dao.findAll();
        assertThat(count, equalTo(countries.spliterator().getExactSizeIfKnown()));
    }

    /**
     * Test the find using like by name, ignoring the capitalization.
     */
    @Test
    public void testFindOneCountryUsingLike() {
        assertNotNull(dao);
        
        final List<Country> countries = dao.findByNameContainingIgnoreCase("FRA");
        assertThat(countries, hasSize(1));
        assertThat(countries, CountryMatcher.hasCountryWithName("France"));
    }
    
    /**
     * Test the find using like by name, ignoring the capitalization.
     * Using CountryMatcher. 
     */
    @Test
    public void testFindMultipleCountriesByName() {
        assertNotNull(dao);
        
        final List<Country> countries = dao.findByNameContainingIgnoreCase("r");
        assertThat(countries, hasSize(2));
        
        assertThat(countries, CountryMatcher.hasCountryWithName("Brazil"));
        assertThat(countries, CountryMatcher.hasCountryWithName("France"));
    }

}

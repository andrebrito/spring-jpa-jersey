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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Iterables;

import br.com.cinq.spring.data.application.Application;
import br.com.cinq.spring.data.entity.Country;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = Application.class)
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository dao;

    @Test
    public void testGetAllCountries() {
        assertNotNull(dao);
        
        final long count = dao.count();
        assertThat(count, greaterThan(0L));

        final Iterable<Country> countries = dao.findAll();
        assertThat(count, equalTo(countries.spliterator().getExactSizeIfKnown()));
    }

    @Test
    public void testFindOneCountry() {
        assertNotNull(dao);
        
        List<Country> countries = dao.findLikeName("Fra");
        assertThat(countries, hasSize(1));
        
        final Country country = Iterables.getLast(countries);
        assertThat(country.getName(), equalTo("Fra"));
    }

}

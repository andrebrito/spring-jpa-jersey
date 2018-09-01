package br.com.cinq.spring.data.sample;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cinq.spring.data.application.Application;
import br.com.cinq.spring.data.entity.Country;
import br.com.cinq.spring.data.repository.CountryRepository;
import br.com.cinq.spring.matcher.CountryMatcher;

/**
 * This is the class that will contain all tests related to the Country resources.
 * We could left this code at EndpointTest class, but I believe that test classes 
 * should me the smallest possible. And if I left this code @ EndpointTest, it would
 * get larger and larger, so I decided to code this tests in this class.
 * 
 * @author Andre Brito Fonseca
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
@TestConfiguration(value = "test")
public class CountryResourceTest {
	
	private final String localhost = "http://localhost:";

    @LocalServerPort
    private int port;
	
	@Autowired
    private CountryRepository countryRepository;
	
	private final TestRestTemplate restTemplate = new TestRestTemplate();
	
	/**
     * This test checks the service that retrieves all countries. 
     * 
     * @throws InterruptedException
     */
    @Test
    public void findAllCountries() throws InterruptedException {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/countries");

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final ResponseEntity<Country[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), 
        		HttpMethod.GET,
                entity, 
                Country[].class);

        assertThat(response.getStatusCode(), equalTo(OK));
        
        final List<Country> countries = Arrays.asList(response.getBody());
        assertThat(countries, hasSize(3));
        assertThat(countryRepository.count(), equalTo(3L));
        assertThat(countries, CountryMatcher.hasCountryWithName("Brazil"));
        assertThat(countries, CountryMatcher.hasCountryWithName("United States"));
        assertThat(countries, CountryMatcher.hasCountryWithName("France"));
    }
    
    /**
     * This test checks the service that retrieves all countries. 
     * 
     * @throws InterruptedException
     */
    @Test
    public void findCountryByName() throws InterruptedException {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        final String countryName = "Brazil";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/countries")
        		.queryParam("country", countryName);

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final ResponseEntity<Country> response = this.restTemplate.exchange(builder.build().encode().toUri(), 
        		HttpMethod.GET,
                entity, 
                Country.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        
        final Country country = response.getBody();
        assertThat(country, notNullValue());
        assertThat(country.getName(), equalTo(countryName));
    }
    
    /**
     * Tests the creation of country using the POST service. 
     * 
     * @throws InterruptedException
     */
    @Test
    public void createCountry() throws InterruptedException {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        final String countryName = "Sweden";
        final UriComponentsBuilder builder = UriComponentsBuilder
        		.fromHttpUrl(this.localhost + this.port + "/rest/countries/" + countryName);

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final ResponseEntity<Country> response = this.restTemplate.exchange(builder.build().encode().toUri(), 
        		HttpMethod.POST,
                entity, 
                Country.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        
        final Country country = response.getBody();
        assertThat(country, notNullValue());
        assertThat(country.getName(), equalTo(countryName));
        assertThat(country.getId(), greaterThan(0L));
    }
}


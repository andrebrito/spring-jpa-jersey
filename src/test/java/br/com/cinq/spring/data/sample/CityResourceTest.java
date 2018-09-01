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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cinq.spring.data.application.Application;
import br.com.cinq.spring.data.dto.CityDTO;
import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.repository.CityRepository;

/**
 * This is the (old) EndpointTest.
 * It will test the resources related to cities entitites.
 * I didn't like how the name of the test class was (EndpointTest), 
 * so I changed. I hope that it's not a problem.  
 * 
 * @author Andre Brito Fonseca
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
@TestConfiguration(value = "test")
public class CityResourceTest {
	
	private final String localhost = "http://localhost:";

    @LocalServerPort
    private int port;
    
    @Autowired
    private CityRepository cityRepository;
    
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    /**
     * This tests the behavior of the GET method of cities resource.
     * In this test, we are sending a query parameter. Since the @QueryParam 
     * won't define a new path for the GET method, we will have to use the same GET
     * method to retrieve cities by country and if we do not send the query parameter, 
     * it will bring all cities (retrieveAll kind of).
     * 
     * If we were to have 2 GET annotated methods, it would probably be using 
     * some sort of @GET @Path(/{country}), sending the parameter as the path, 
     * and not as a query parameter. Too bad that I couldn't find an approach 
     * better for this.
     *  
     * @throws InterruptedException
     */
    @Test
    public void findCitiesByCountry() throws InterruptedException {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities")
                .queryParam("country", "France");

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final ResponseEntity<City[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), 
        		HttpMethod.GET,
                entity, 
                City[].class);

        assertThat(response.getStatusCode(), equalTo(OK));
        
        final List<City> cities = Arrays.asList(response.getBody());
        assertThat(cities, hasSize(2));
    }
    
    /**
     * This test consumes the service that searches cities by country, 
     * using the like clause. The test will pass 'fr' and the service
     * must return all the cities that have a country with 'fr' on its name.
     *  
     * @throws InterruptedException
     */
    @Test
    public void findCitiesByCountryWithLikeClause() throws InterruptedException {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities")
                .queryParam("country", "fr");

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final ResponseEntity<City[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), 
        		HttpMethod.GET,
                entity, 
                City[].class);

        assertThat(response.getStatusCode(), equalTo(OK));
        
        final List<City> cities = Arrays.asList(response.getBody());
        assertThat(cities, hasSize(2));
    }
    
    /**
     * This is the test that will check what happens when we do not send 
     * any query parameter to the GET method.
     * 
     * @throws InterruptedException
     */
    @Test
    public void findAllCities() throws InterruptedException {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities");

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final ResponseEntity<City[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), 
        		HttpMethod.GET,
                entity, 
                City[].class);

        assertThat(response.getStatusCode(), equalTo(OK));
        
        final List<City> cities = Arrays.asList(response.getBody());
        assertThat(cities, hasSize(9));
        assertThat(cityRepository.count(), equalTo(9L));
    }
    
    /**
     * Tests the creation of city using the POST service. 
     * 
     * @throws InterruptedException
     */
    @Test
    public void createCity() throws InterruptedException {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        
        final String cityName = "Maringa";
        final MultiValueMap<String, CityDTO> params = new LinkedMultiValueMap<>();
        params.add("cityDTO", new CityDTO(cityName, 1L));
        
        final HttpEntity<MultiValueMap<String, CityDTO>> request = new HttpEntity<>(params, headers);
        final ResponseEntity<City> response = restTemplate.postForEntity(this.localhost + this.port + "/rest/cities", 
        		request, 
        		City.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        
        final City city = response.getBody();
        assertThat(city, notNullValue());
        assertThat(city.getName(), equalTo(cityName));
        assertThat(city.getId(), greaterThan(0L));
        assertThat(city.getCountry().getName(), equalTo("Brazil"));
    }
}
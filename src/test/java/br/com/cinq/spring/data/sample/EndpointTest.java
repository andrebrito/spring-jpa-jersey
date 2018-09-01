package br.com.cinq.spring.data.sample;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.repository.CityRepository;

/**
 * This is the EndpointTest.
 * It will test the Resources created by Spring.
 * I actually believe that this should be CityResourceTest, 
 * and that we would have to create anoter class, 
 * the CountryResourceTest. Since I don't know that we can do that, 
 * the tests related to the CountryResource will be here aswell.
 * 
 * @author Andre Brito Fonseca
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
@TestConfiguration(value = "test")
public class EndpointTest {
	
	private final String localhost = "http://localhost:";

    @LocalServerPort
    private int port;
    
    @Autowired
    private CityRepository cityRepository;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    /**
     * This tests the behavior of the GET method of cities service.
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
     * This is the test that will check what happens when we do not send 
     * any query parameter to the GET method.
     * 
     * @throws InterruptedException
     */
    @Test
    public void findCitiesWithoutCountryParameter() throws InterruptedException {
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
}

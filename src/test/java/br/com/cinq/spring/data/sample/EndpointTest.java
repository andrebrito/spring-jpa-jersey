package br.com.cinq.spring.data.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cinq.spring.data.application.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("unit")
@TestConfiguration(value = "test")
public class EndpointTest {
	
    Logger logger = LoggerFactory.getLogger(EndpointTest.class);

    private final String localhost = "http://localhost:";

    @Value("${server.port}")
    private int port;

//    private final RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testGetCities() throws InterruptedException {
        final String country = "France";

//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/")
//                .queryParam("country", country);

//        HttpEntity<?> entity = new HttpEntity<>(headers);

//        ResponseEntity<City[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
//                entity, City[].class);

//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

//        Thread.sleep(2000L);

//        City[] cities = response.getBody();

//        Assert.assertEquals(2, cities.length);

    }
}

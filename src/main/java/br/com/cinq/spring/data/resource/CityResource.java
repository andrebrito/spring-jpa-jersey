package br.com.cinq.spring.data.resource;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.cinq.spring.data.dto.CityDTO;
import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.repository.CityRepository;
import br.com.cinq.spring.data.service.CityService;

/**
 * Resource methods for City objects.
 * All methods produce JSON objects.
 * @author Andre Brito Fonseca
 *
 */
@Component
@Path("/cities")
@Produces("application/json")
public class CityResource {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CityService cityService;
	
	@GET
	public Response findCities(final @QueryParam("country") String country) {
		if (isNullOrEmpty(country))  {
			return Response.ok(cityRepository.findAll()).build();
		}
		
		final List<City> cities = cityRepository.findAllByCountryNameContainingIgnoreCase(country);
		return Response.ok(cities).build();
	}
	
	@POST
	@Consumes("application/json")
	public ResponseEntity<?> createCity(final CityDTO cityDTO) {
		try {
			final City city = cityService.createCity(cityDTO);
			return ResponseEntity.ok().body("City with id " + city.getId() + " created.");
		} catch (final IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}

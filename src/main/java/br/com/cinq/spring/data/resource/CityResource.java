package br.com.cinq.spring.data.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import br.com.cinq.spring.data.dto.CityDTO;
import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.repository.CityRepository;

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
	
	@GET
	public Response findCities(final @QueryParam("country") String country) {
		if (Strings.isNullOrEmpty(country))  {
			return Response.ok(cityRepository.findAll()).build();
		}
		
		final List<City> cities = cityRepository.findAllByCountryNameContainingIgnoreCase(country);
		return Response.ok(cities).build();
	}
	
	@POST
	@Consumes("application/json")
	public Response createCity(final CityDTO cityDTO) {
		System.out.println("countryId: " + cityDTO);
		return null;
	}
	

}

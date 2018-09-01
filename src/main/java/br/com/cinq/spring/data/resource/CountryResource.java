package br.com.cinq.spring.data.resource;

import static java.util.Objects.nonNull;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import br.com.cinq.spring.data.builder.CountryBuilder;
import br.com.cinq.spring.data.entity.Country;
import br.com.cinq.spring.data.repository.CountryRepository;

/**
 * Resource methods for Country objects.
 * All methods produce JSON objects.
 * @author Andre Brito Fonseca
 *
 */
@Component
@Path("/countries")
@Produces("application/json")
public class CountryResource {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@GET
	public Response findCountries(final @QueryParam("country") String countryName) {
		if (Strings.isNullOrEmpty(countryName))  {
			return Response.ok(countryRepository.findAll()).build();
		}
		
		final Country country = countryRepository.findByNameIgnoreCase(countryName);
		return Response.ok(country).build();
	}
	
	@POST
	@Path("/{countryName}")
	public Response createCountry(final @PathParam("countryName") String countryName) {
		Country country = countryRepository.findByNameIgnoreCase(countryName);
		if (nonNull(country)) {
			return Response.ok(country).build();
		}
		
		country = new CountryBuilder().with(cb -> cb.setName(countryName)).createCountry();
		country = countryRepository.save(country);
		
		return Response.ok(country).build();
	}

}

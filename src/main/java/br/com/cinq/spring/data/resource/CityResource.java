package br.com.cinq.spring.data.resource;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

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
	
	@GET
	public Response listAllCities() {
		return Response.ok(Arrays.asList("Maringa", "Guarapuava")).build();
	}

}

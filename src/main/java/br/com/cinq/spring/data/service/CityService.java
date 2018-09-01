package br.com.cinq.spring.data.service;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cinq.spring.data.builder.CityBuilder;
import br.com.cinq.spring.data.dto.CityDTO;
import br.com.cinq.spring.data.entity.City;
import br.com.cinq.spring.data.entity.Country;
import br.com.cinq.spring.data.exception.CountryNotFoundException;
import br.com.cinq.spring.data.exception.LackingCityNameException;
import br.com.cinq.spring.data.exception.LackingCountryIdException;
import br.com.cinq.spring.data.repository.CityRepository;
import br.com.cinq.spring.data.repository.CountryRepository;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	public City createCity(final CityDTO cityDTO) {
		validateDTO(cityDTO);
		
		final String cityName = cityDTO.getCityName();
		final Country country = countryRepository.findById(cityDTO.getCountryId()).get();

		final City city = new CityBuilder().with($ -> {
			$.setName(cityName);
			$.setCountry(country);
		}).createCity();
		
		cityRepository.save(city);
		
		return city;
	}

	private void validateDTO(final CityDTO cityDTO) {
		validateCountry(cityDTO);
		validateCity(cityDTO);
	}

	private void validateCity(final CityDTO cityDTO) {
		final String cityName = cityDTO.getCityName();
		if (isNullOrEmpty(cityName)) {
			throw new LackingCityNameException();
		}
	}

	private void validateCountry(final CityDTO cityDTO) {
		final Long countryId = cityDTO.getCountryId();
		if (isNull(countryId)) {
			throw new LackingCountryIdException();
		}
		
		final Optional<Country> optCountry = countryRepository.findById(countryId);
		if (!optCountry.isPresent()) {
			throw new CountryNotFoundException(countryId);
		}
	}

}

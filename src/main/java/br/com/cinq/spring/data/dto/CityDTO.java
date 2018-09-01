package br.com.cinq.spring.data.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CityDTO {

	private String cityName;
	private Long countryId;

	public CityDTO() {
	}
	
	public CityDTO(final String cityName, final Long countryId) {
		this.cityName = cityName;
		this.countryId = countryId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(final Long countryId) {
		this.countryId = countryId;
	}

}

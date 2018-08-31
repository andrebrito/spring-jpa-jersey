package br.com.cinq.spring.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CITY")
public class City {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Size(max = 40)
	@Column(name = "NAME")
	private String name;

	@JoinColumn(name = "COUNTRY_ID")
	@ManyToOne(targetEntity = Country.class)
	private Country country;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(final Country country) {
		this.country = country;
	}

}

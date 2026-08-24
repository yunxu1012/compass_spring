package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;

@Entity
public class City {
	@Id
	private Integer cityId;
	private String name;
	private String county;
	private String state;


	public City() {
		super();
	}


	public City(Integer cityId, String name, String county, String state) {
		super();
		this.cityId = cityId;
		this.name = name;
		this.county = county;
		this.state = state;
	}


	public Integer getCityId() {
		return cityId;
	}


	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCounty() {
		return county;
	}


	public void setCounty(String county) {
		this.county = county;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

	

}

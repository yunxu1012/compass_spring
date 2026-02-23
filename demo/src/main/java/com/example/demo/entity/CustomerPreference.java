package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;

@Entity
public class CustomerPreference {
	@Id
    private Integer customerId;
	private int minSquareFeet;
	private int maxSquareFeet;
	private int minPrice;
	private int maxPrice;
	@ColumnTransformer(write = "?::bedcount")
	@Convert(converter = BedCountConverter.class)
	private BedCount minBed;
	@ColumnTransformer(write = "?::bedcount")
	@Convert(converter = BedCountConverter.class)
	private BedCount maxBed;
	@ColumnTransformer(write = "?::bathcount")
	@Convert(converter = BathCountConverter.class)
	private BathCount minBath;
	@Transient
	private String cityStr;
	
	public CustomerPreference() {
		super();
	}
	
	public CustomerPreference(Integer customerId, int minSquareFeet, int maxSquareFeet,
			int minPrice, int maxPrice, BedCount minBed, BedCount maxBed, BathCount minBath) {
		super();
		this.customerId = customerId;
		this.minSquareFeet = minSquareFeet;
		this.maxSquareFeet = maxSquareFeet;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.minBed = minBed;
		this.maxBed = maxBed;
		this.minBath = minBath;
	} 

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public int getMinSquareFeet() {
		return minSquareFeet;
	}
	public void setMinSquareFeet(int minSquareFeet) {
		this.minSquareFeet = minSquareFeet;
	}
	public int getMaxSquareFeet() {
		return maxSquareFeet;
	}
	public void setMaxSquareFeet(int maxSquareFeet) {
		this.maxSquareFeet = maxSquareFeet;
	}
	
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public BedCount getMinBed() {
		return minBed;
	}
	public void setMinBed(BedCount minBed) {
		this.minBed = minBed;
	}
	public BedCount getMaxBed() {
		return maxBed;
	}
	public void setMaxBed(BedCount maxBed) {
		this.maxBed = maxBed;
	}
	public BathCount getMinBath() {
		return minBath;
	}
	public void setMinBath(BathCount minBath) {
		this.minBath = minBath;
	} 
	
	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	public String toString() {
		return "Home Type: "+", Min Price: "+minPrice
				+", Max Price: "+maxPrice+", Min Bed: "+minBed.name()
				+", Max Bed: "+maxBed.name()+", Min Bath: "
				+minBath.name()+", Min Square feet: "+minSquareFeet
				+", Max Square Feet: "+maxSquareFeet;
	}
	
	//@ManyToMany(fetch = FetchType.EAGER)
	//@JoinTable(name = "customer_city", joinColumns = @JoinColumn(name = "customerId"), 
	//inverseJoinColumns = @JoinColumn(name = "cityId"))
	@Transient
	private Set<City> cities = new HashSet<>();

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

}

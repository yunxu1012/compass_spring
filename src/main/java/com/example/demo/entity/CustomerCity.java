package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class CustomerCity {
	
	@SequenceGenerator(initialValue = 1, name = "cust_city_seq", sequenceName = "customer_city_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_city_seq")
	@Id
	private Long id;
	
	private Integer customerId;
	
    private Integer cityId;

   
    public CustomerCity() {
    	super();
    }


	public CustomerCity( Integer customerId, Integer cityId) {
		super();
		this.customerId = customerId;
		this.cityId = cityId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public Integer getCityId() {
		return cityId;
	}


	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}


	
  
  
}
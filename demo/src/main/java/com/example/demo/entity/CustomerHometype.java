package com.example.demo.entity;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class CustomerHometype {
	
	@SequenceGenerator(initialValue = 1, name = "cust_hometype_seq", sequenceName = "customer_hometype_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_hometype_seq")
	@Id
	private Long id;
	
	private Integer customerId;

	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "?::hometype")
	private Hometype hometype;

   
    public CustomerHometype() {
    	super();
    }


	public CustomerHometype(Integer customerId, Hometype homeType) {
		super();
		this.customerId = customerId;
		this.hometype = homeType;
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

	public Hometype getHometype() {
		return hometype;
	}


	public void setHometype(Hometype hometype) {
		this.hometype = hometype;
	}

  
  
}
package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class CustomerRole {
	
	@SequenceGenerator(initialValue = 1, name = "cust_role_seq", sequenceName = "customer_role_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_role_seq")
	@Id
	private Integer id;
	
	private Integer customerId;
	
    private Integer roleId;

   
    public CustomerRole() {
    	super();
    }


	public CustomerRole( Integer customerId, Integer roleId) {
		super();
		this.customerId = customerId;
		this.roleId = roleId;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
    
    
  
  
}
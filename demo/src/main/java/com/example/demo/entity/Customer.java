package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Customer {
	@Id
	@SequenceGenerator(initialValue = 1, name = "cust_seq", sequenceName = "customer_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_seq")
	private Integer customerId;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	@OneToOne
	@PrimaryKeyJoinColumn
	private CustomerPreference perference;

	public Customer() {
		super();
	}

	public Customer(Integer customerId, String password, String firstName, String lastName, String phoneNumber) {
		super();
		this.customerId = customerId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public CustomerPreference getPerference() {
		return perference;
	}

	public void setPerference(CustomerPreference perference) {
		this.perference = perference;
	}

}

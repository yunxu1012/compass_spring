package com.example.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerPreference;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CustomerPreferenceRepository;
import com.example.demo.repository.CustomerRepository;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping(path = "/api")
public class CustomerPreferenceController {
	@Autowired
    CustomerPreferenceRepository customerPreferenceRepository;
	@Autowired
    CustomerRepository customerRepository;
	Logger logger = LoggerFactory.getLogger(CustomerPreferenceController.class);
	
	@PostMapping("/customersPreferences/{email}")
    public CustomerPreference createCustomerPreference(@PathVariable String email,
    		@RequestBody CustomerPreference customerPreference) {
		logger.info("Create customer preference!!!");
		   logger.info("Customer Prference: "+customerPreference.toString());
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
        	Customer customer =  optionalCustomer.get();
        	if(customer.getPerference()!=null) {
        		throw new AlreadyExistsException("Customer Preference already exists for: "
     			        +email);
        	}
        	customerPreference.setCustomerId(customer.getCustomerId());
        	return customerPreferenceRepository.save(customerPreference);
        }else {
      	   throw new NotFoundException("Customer not found for: "
      		        +email);
        }
    }
	
	@GetMapping("/customersPreferences/{email}")
    public CustomerPreference getCustoemr(@PathVariable String email) {
		logger.info("Select customer!!!");
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
     	   Customer customer =  optionalCustomer.get();
     	   CustomerPreference preference = customer.getPerference();
     	   if(preference!=null) {
     		   return preference;
     	   }else {
     		  throw new NotFoundException("Customer Preference not found for: "
     			        +email);
     	   }
        }else {
     	   throw new NotFoundException("Customer not found for: "
        +email);
        }
    }
	

	@PutMapping(path = "customersPreferences/{email}")
    public CustomerPreference updateCustomerPreference(@PathVariable String email,
    		@RequestBody CustomerPreference customerPreference) {
		   logger.info("Update customer!!!");
		   logger.info("Customer Prference: "+customerPreference.toString());
           Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
           if(optionalCustomer.isPresent()) {
        	   Customer existingCustomer = optionalCustomer.get();
        	   CustomerPreference existingPreference = existingCustomer.getPerference();
        	   if(existingPreference==null) {
        		   throw new NotFoundException("Customer Preference not found for: "
        		           + email); 
        	   }
        	   existingPreference.setMinBed(customerPreference.getMinBed());
        	   existingPreference.setMaxBed(customerPreference.getMaxBed());
        	   existingPreference.setMinBath(customerPreference.getMinBath());
        	   existingPreference.setMinPrice(customerPreference.getMinPrice());
        	   existingPreference.setMaxPrice(customerPreference.getMaxPrice());
        	   existingPreference.setMinSquareFeet(customerPreference.getMinSquareFeet()); 
        	   existingPreference.setMaxSquareFeet(customerPreference.getMaxSquareFeet()); 
        	   return customerPreferenceRepository.save(existingPreference);
           }else {
        	   throw new NotFoundException("Customer not found for: "
           + email);
           }
    }
}

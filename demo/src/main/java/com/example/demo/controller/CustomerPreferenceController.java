package com.example.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerPreference;
import com.example.demo.exception.CustomerNotFoundException;
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
	
	@PostMapping("/customersPreferences")
    public CustomerPreference createCustoemr(@RequestBody CustomerPreference customerPreference) {
		return customerPreferenceRepository.save(customerPreference);
    }
	
	@GetMapping("/customersPreferences/{email}")
    public CustomerPreference getCustoemr(@PathVariable String email) {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
     	   Customer customer =  optionalCustomer.get();
     	   return customer.getPerference();
        }else {
     	   throw new CustomerNotFoundException("Customer not found for: "
        +email);
        }
    }
	
}

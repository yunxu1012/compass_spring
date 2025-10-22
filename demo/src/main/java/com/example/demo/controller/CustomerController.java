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
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.PasswordNotCorrectException;
import com.example.demo.repository.CustomerRepository;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping(path = "/api")
public class CustomerController {
	@Autowired
    CustomerRepository customerRepository;
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@PostMapping("/customers")
    public Customer createCustoemr(@RequestBody Customer customer) {
        
		return customerRepository.save(customer);
    }
	
	@PostMapping(path = "customers/authenticate")
    public Customer authenticate(@RequestBody Customer customer) {
           Optional<Customer> optionalCustomer = customerRepository.findByEmail(customer.getEmail());
           if(optionalCustomer.isPresent()) {
        	   Customer existingCustomer = optionalCustomer.get();
        	   if(existingCustomer.getPassword().equals(customer.getPassword())) {
        		   return existingCustomer;
        	   }else {
        		   throw new PasswordNotCorrectException("Password not correct");
        	   }
           }else {
        	   throw new CustomerNotFoundException("Customer not found for: "
           +customer.getEmail());
           }
    }

	@GetMapping("/customers/{email}")
    public Customer getCustoemr(@PathVariable String email) {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
     	   return optionalCustomer.get();
        }else {
     	   throw new CustomerNotFoundException("Customer not found for: "
        +email);
        }
    }
	
	@PutMapping(path = "customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
		   logger.info("Update customer");
           Optional<Customer> optionalCustomer = customerRepository.findByEmail(customer.getEmail());
           if(optionalCustomer.isPresent()) {
        	   Customer existingCustomer = optionalCustomer.get();
        	   existingCustomer.setLastName(customer.getLastName());
        	   existingCustomer.setFirstName(customer.getFirstName());
        	   existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        	   return customerRepository.save(existingCustomer);
           }else {
        	   throw new CustomerNotFoundException("Customer not found for: "
           +customer.getEmail());
           }
    }

}

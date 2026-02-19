package com.example.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.security.AuthenticationService;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping(path = "/api")
public class CustomerController {
	@Autowired
    CustomerRepository customerRepository;
	 @Autowired
     private AuthenticationService authService;
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	

	@GetMapping("/customers/{email}")
    public Customer getCustomer(@PathVariable String email) {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
     	   return optionalCustomer.get();
        }else {
     	   throw new NotFoundException("Customer not found for: "
        +email);
        }
    }
	
	@PutMapping(path = "customers/{email}")
    public Customer updateCustomer(@PathVariable String email, @RequestBody Customer customer) {
		   logger.info("Update customer");
           Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
           if(optionalCustomer.isPresent()) {
        	   Customer existingCustomer = optionalCustomer.get();
        	   existingCustomer.setLastName(customer.getLastName());
        	   existingCustomer.setFirstName(customer.getFirstName());
        	   existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        	   return customerRepository.save(existingCustomer);
           }else {
        	   throw new NotFoundException("Customer not found for: "
           + email);
           }
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path = "customers/admin")
	public ResponseEntity<String> getAdminTest() {

		return new ResponseEntity<>("ADMIN", HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping(path = "customers/customer")
	public ResponseEntity<String> getCustomerTest() {

		return new ResponseEntity<>("CUSTOMER", HttpStatus.OK);
	}
	
	
	@GetMapping(path = "customers/read")
	public ResponseEntity<String> getRead() {

		return new ResponseEntity<>("READ", HttpStatus.OK);
	}

}

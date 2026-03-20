package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerSearch;
import com.example.demo.entity.RoleType;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.CustomerSearchRepositoryImpl;
import com.example.demo.security.AuthenticationService;
import com.example.demo.service.CustomerService;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping(path = "/api")
public class AdminController {
	 @Autowired
     private AuthenticationService authService;
	 @Autowired
	 private CustomerSearchRepositoryImpl searchImpl;
	 @Autowired
	 CustomerRepository customerRepository;
	 @Autowired
		CustomerService customerService;
	Logger logger = LoggerFactory.getLogger(AdminController.class);
	

	
	@PostMapping(path = "/admin/search")
    public ResponseEntity<List<Customer>> searchCustomer(@RequestBody CustomerSearch search) {
		   logger.info("search: "+search.toString());
           List<Customer> customers = searchImpl.searchCustomer(search);
           return new ResponseEntity<>(customers, HttpStatus.OK);
    }
	
	@GetMapping(path = "/admin/customers")
	public ResponseEntity<List<Customer>> getCustomerList() {
        List<Customer> customers = customerRepository.findAll();
        List<Customer> realCustomers = new ArrayList<Customer>();
        for(Customer customer:customers) {
        	if (customerService.checkCustomerType(customer, RoleType.CUSTOMER.name())) {
        		realCustomers.add(customer);
        	}
        }
		return new ResponseEntity<>(realCustomers, HttpStatus.OK);
	}


}

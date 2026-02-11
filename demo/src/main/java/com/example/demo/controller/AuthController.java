package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.security.AuthenticationService;
import com.example.demo.security.JwtService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api")
public class AuthController {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	private AuthenticationService authService;
	@Autowired
	private JwtService jwtService;

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/auth/signup")
	public Customer createCustomer(@RequestBody Customer customer) {
		// return customerRepository.save(customer);
		Customer created = authService.signup(customer);
		created.setPassword("******");
		return created;
	}

	@PostMapping(path = "auth/login")
	public ResponseEntity<String> authenticate(@RequestBody Customer customer) {
		Customer authenticatedUser = authService.authenticate(customer);

		String jwtToken = jwtService.generateToken(authenticatedUser);
		return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
	}

}

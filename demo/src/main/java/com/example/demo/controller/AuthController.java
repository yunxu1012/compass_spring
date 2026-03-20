package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

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
import com.example.demo.entity.CustomerRole;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleType;
import com.example.demo.exception.UserTypeErrorException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.CustomerRoleRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.security.AuthenticationService;
import com.example.demo.security.JwtService;
import com.example.demo.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api")
public class AuthController {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	CustomerRoleRepository customerRoleRepository;
	@Autowired
	private AuthenticationService authService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	CustomerService customerService;
	
	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/auth/customer/signup")
	public Customer createCustomer(@RequestBody Customer customer) {
		// return customerRepository.save(customer);
		Customer created = authService.signup(customer);
		logger.info("customer created");
		Role role = roleRepository.findByName(RoleType.CUSTOMER.name());
		logger.info("role: " + role.getRoleId());
		CustomerRole cr = new CustomerRole(customer.getCustomerId(), role.getRoleId());
		customerRoleRepository.save(cr);
		created.setPassword("******");
		return created;
	}

	@PostMapping("/auth/admin/signup")
	public Customer createAdmin(@RequestBody Customer customer) {
		// return customerRepository.save(customer);
		Customer created = authService.signup(customer);
		Role role = roleRepository.findByName(RoleType.ADMIN.name());
		CustomerRole cr = new CustomerRole(customer.getCustomerId(), role.getRoleId());
		customerRoleRepository.save(cr);
		created.setPassword("******");
		return created;
	}

	@PostMapping(path = "auth/admin/login")
	public ResponseEntity<String> authenticateAdmin(@RequestBody Customer input) {
		Customer authenticatedUser = authService.authenticate(input);
		if (!customerService.checkCustomerType(authenticatedUser, RoleType.ADMIN.name())) {
			throw new UserTypeErrorException("User is not admin. Please use customer login");
		}
		String jwtToken = jwtService.generateToken(authenticatedUser);
		return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
	}

	@PostMapping(path = "auth/customer/login")
	public ResponseEntity<String> authenticateCustomer(@RequestBody Customer input) {
		Customer authenticatedUser = authService.authenticate(input);
		if (!customerService.checkCustomerType(authenticatedUser, RoleType.CUSTOMER.name())) {
			throw new UserTypeErrorException("User is not customer. Please use admin login");
		}

		String jwtToken = jwtService.generateToken(authenticatedUser);
		return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
	}

	

}

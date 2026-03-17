package com.example.demo.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CustomerRepository;

@Service
public class AuthenticationService {
	private final CustomerRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	public AuthenticationService(CustomerRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Customer signup(Customer customer) {
		Optional<Customer> ex = userRepository.findByEmail(customer.getEmail());
		if (ex.isPresent()) {
			throw new AlreadyExistsException("Email already used.");
		}

		String passEncode = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(passEncode);
		return userRepository.save(customer);
	}

	public Customer authenticate(Customer input) {

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
		} catch (BadCredentialsException e) {
			throw new UserNotFoundException("Username or password not correct");
		}

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}

}
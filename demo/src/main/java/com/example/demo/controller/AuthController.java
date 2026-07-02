package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CodeTime;
import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerRole;
import com.example.demo.entity.EmailCode;
import com.example.demo.entity.JwtInfo;
import com.example.demo.entity.ResetPassword;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleType;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.EmailCodeException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserTypeErrorException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.CustomerRoleRepository;
import com.example.demo.repository.EmailCodeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.security.AuthenticationService;
import com.example.demo.security.JwtService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.MailService;

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
	private static int CODE_EXPIRE_MINUTES = 30;

	@Autowired
	MailService mailService;
	@Autowired
	EmailCodeRepository emailCodeRepository;

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/auth/customer/signup")
	public Customer createCustomer(@RequestBody Customer customer) {
		// return customerRepository.save(customer);
		checkEmailCode(customer.getEmail().toLowerCase(), customer.getCode());
		customer.setEmail(customer.getEmail().toLowerCase());
		Customer created = authService.signup(customer);
		Role role = roleRepository.findByName(RoleType.CUSTOMER.name());
		CustomerRole cr = new CustomerRole(customer.getCustomerId(), role.getRoleId());
		customerRoleRepository.save(cr);
		created.setPassword("******");
		return created;
	}

	@PostMapping("/auth/admin/signup")
	public Customer createAdmin(@RequestBody Customer customer) {
		// return customerRepository.save(customer);
		customer.setEmail(customer.getEmail().toLowerCase());
		Customer created = authService.signup(customer);
		Role role = roleRepository.findByName(RoleType.ADMIN.name());
		CustomerRole cr = new CustomerRole(customer.getCustomerId(), role.getRoleId());
		customerRoleRepository.save(cr);
		created.setPassword("******");
		return created;
	}

	@PostMapping(path = "/auth/admin/login")
	public ResponseEntity<JwtInfo> authenticateAdmin(@RequestBody Customer input) {
		Customer authenticatedUser = authService.authenticate(input);
		if (!customerService.checkCustomerType(authenticatedUser, RoleType.ADMIN.name())) {
			throw new UserTypeErrorException("User is not admin. Please use customer login");
		}
		String jwtToken = jwtService.generateToken(authenticatedUser);
		String name = authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName();
		JwtInfo info = new JwtInfo(jwtToken, name);
		return new ResponseEntity<>(info, HttpStatus.CREATED);
	}

	@PostMapping(path = "/auth/customer/login")
	public ResponseEntity<JwtInfo> authenticateCustomer(@RequestBody Customer input) {
		Customer authenticatedUser = authService.authenticate(input);
		if (!customerService.checkCustomerType(authenticatedUser, RoleType.CUSTOMER.name())) {
			throw new UserTypeErrorException("User is not customer. Please use admin login");
		}

		String jwtToken = jwtService.generateToken(authenticatedUser);
		String name = authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName();
		JwtInfo info = new JwtInfo(jwtToken, name);
		return new ResponseEntity<>(info, HttpStatus.CREATED);
	}

	@GetMapping(path = "/auth/registerCode")
	public ResponseEntity<String> sendRegisterMail(@RequestParam("email") String email,
			@RequestParam("firstTime") boolean firstTime) {
		if (firstTime) {
			Optional<Customer> optionalCustomer = customerRepository.findByEmail(email.toLowerCase());
			if (optionalCustomer.isPresent()) {
				throw new AlreadyExistsException("Email already used.");
			}
		}
		sendCodeEmail(email);
		return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
	}

	@GetMapping(path = "/auth/sendCode")
	public ResponseEntity<CodeTime> sendMail(@RequestParam("email") String email,
			@RequestParam("firstTime") boolean firstTime) {
		if (firstTime) {
			Optional<Customer> optionalCustomer = customerRepository.findByEmail(email.toLowerCase());
			if (optionalCustomer.isEmpty()) {
				throw new UserNotFoundException("Email not found.");
			}
		}
		sendCodeEmail(email.toLowerCase());
		LocalDateTime time = LocalDateTime.now();
		CodeTime codeTime = new CodeTime(time);
		return new ResponseEntity<>(codeTime, HttpStatus.OK);
	}

	private void sendCodeEmail(String email) {
		Random rnd = new Random();
		// Generates a number from 0 to 899,999, then adds 100,000
		int number = 100000 + rnd.nextInt(900000);
		String body = "Your security code is: " + number;
		mailService.sendPlainText(email, "Your Security Code", body);
		LocalDateTime time = LocalDateTime.now();
		EmailCode code = new EmailCode(email.toLowerCase(), String.valueOf(number), time);
		emailCodeRepository.save(code);
	}

	@PostMapping("/auth/customer/resetPassword")
	public Customer resetPassword(@RequestBody ResetPassword reset) {
		logger.info("reset password");
		checkEmailCode(reset.getEmail().toLowerCase(), reset.getCode());
		String email = reset.getEmail();
		if (email != null) {
			Optional opt = customerRepository.findByEmail(email.toLowerCase());
			if (opt.isEmpty()) {
				throw new UserNotFoundException("Customer Not Found");
			} else {
				Customer customer = (Customer) opt.get();
				customer.setPassword(reset.getPassword());
				authService.resetPassword(customer);
				customer.setPassword("*****");
				return customer;
			}
		}else {
			throw new UserNotFoundException("Customer Not Found");
		}

	}

	private void checkEmailCode(String email, String code) {
		Optional<EmailCode> op = emailCodeRepository.findById(email.toLowerCase());
		if (op.isEmpty()) {
			throw new EmailCodeException("Code not found for email: " + email);
		} else {
			EmailCode eCode = op.get();
			if (!eCode.getCode().equals(code)) {
				throw new EmailCodeException("Code not correct.");
			}
			LocalDateTime now = LocalDateTime.now();
			if (now.isAfter(eCode.getUpdateTime().plusMinutes(CODE_EXPIRE_MINUTES))) {
				throw new EmailCodeException("Code is expired.");
			}
		}
	}

}

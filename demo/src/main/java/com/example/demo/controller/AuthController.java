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
	private static int CODE_EXPIRE_MINUTES=10;
	
	@Autowired
	MailService mailService;
	@Autowired
	EmailCodeRepository emailCodeRepository;
	
	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/auth/customer/signup")
	public Customer createCustomer(@RequestBody Customer customer) {
		// return customerRepository.save(customer);
		Optional<EmailCode> op = emailCodeRepository.findById(customer.getEmail());
        if(op.isEmpty()) {
        	throw new EmailCodeException("Code Not Found");
        }else {
        	EmailCode eCode = op.get();
        	if(!eCode.getCode().equals(customer.getCode())) {
        		throw new EmailCodeException("Code Not Correct");
        	}
        	LocalDateTime now = LocalDateTime.now();
        	if(now.isAfter(eCode.getUpdateTime().plusMinutes(1))){
        		throw new EmailCodeException("Code Is Expired");
        	}
        }
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

	@PostMapping(path = "/auth/admin/login")
	public ResponseEntity<JwtInfo> authenticateAdmin(@RequestBody Customer input) {
		Customer authenticatedUser = authService.authenticate(input);
		if (!customerService.checkCustomerType(authenticatedUser, RoleType.ADMIN.name())) {
			throw new UserTypeErrorException("User is not admin. Please use customer login");
		}
		String jwtToken = jwtService.generateToken(authenticatedUser);
		JwtInfo info = new JwtInfo(jwtToken);
		return new ResponseEntity<>(info, HttpStatus.CREATED);
	}

	@PostMapping(path = "/auth/customer/login")
	public ResponseEntity<JwtInfo> authenticateCustomer(@RequestBody Customer input) {
		Customer authenticatedUser = authService.authenticate(input);
		if (!customerService.checkCustomerType(authenticatedUser, RoleType.CUSTOMER.name())) {
			throw new UserTypeErrorException("User is not customer. Please use admin login");
		}

		String jwtToken = jwtService.generateToken(authenticatedUser);
		JwtInfo info = new JwtInfo(jwtToken);
		return new ResponseEntity<>(info, HttpStatus.CREATED);
	}

	@GetMapping(path = "/auth/registerCode")
	public ResponseEntity<String> sendRegisterMail(@RequestParam("email") String email, @RequestParam("firstTime") boolean firstTime) {
        if(firstTime) {
        	Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
            if(optionalCustomer.isPresent()) {
         	   throw new AlreadyExistsException("Email already used.");
            }
        }
        Random rnd = new Random();
        // Generates a number from 0 to 899,999, then adds 100,000
        int number = 100000 + rnd.nextInt(900000);
        String body = "Your security code is: "+ number;
        mailService.sendPlainText(email,"Your Security Code", body);
        LocalDateTime time = LocalDateTime.now();
        EmailCode code = new EmailCode(email, String.valueOf(number), time);
        emailCodeRepository.save(code);
		return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
	}
	
	@GetMapping(path = "/auth/sendCode")
	public ResponseEntity<String> sendMail(@RequestParam("email") String email, @RequestParam("firstTime") boolean firstTime) {
        if(firstTime) {
        	Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
            if(optionalCustomer.isEmpty()) {
         	   throw new UserNotFoundException("Email not found.");
            }
        }
        Random rnd = new Random();
        // Generates a number from 0 to 899,999, then adds 100,000
        int number = 100000 + rnd.nextInt(900000);
        String body = "Your security code is: "+ number;
        mailService.sendPlainText(email,"Your Security Code", body);
        LocalDateTime time = LocalDateTime.now();
        EmailCode code = new EmailCode(email, String.valueOf(number), time);
        emailCodeRepository.save(code);
		return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
	}
	
	@GetMapping(path = "/auth/checkCode")
	public ResponseEntity<String> checkCode(@RequestParam("email") String email, @RequestParam("code") String code) {     
        checkEmailCode(email, code);
		return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
	}
	
	@PostMapping("/auth/customer/resetPassword")
	public Customer resetPassword(@RequestBody ResetPassword reset) {
		logger.info("reset password");
        checkEmailCode(reset.getEmail(), reset.getCode());
		Optional opt = customerRepository.findByEmail(reset.getEmail());
	
		if(opt.isEmpty()) {
			throw new UserNotFoundException("Customer Not Found");
		}else {
			Customer customer =(Customer) opt.get();
			customer.setPassword(reset.getPassword());
			customerRepository.save(customer);
			return customer;
		}
	}
	
	private void checkEmailCode(String email, String code) {
		 Optional<EmailCode> op = emailCodeRepository.findById(email);
	        if(op.isEmpty()) {
	        	throw new EmailCodeException("Code not found for email: "+email);
	        }else {
	        	EmailCode eCode = op.get();
	        	if(!eCode.getCode().equals(code)) {
	        		throw new EmailCodeException("Code not correct.");
	        	}
	        	LocalDateTime now = LocalDateTime.now();
	        	if(now.isAfter(eCode.getUpdateTime().plusMinutes(CODE_EXPIRE_MINUTES))){
	        		throw new EmailCodeException("Code is expired.");
	        	}
	        }
	}

}

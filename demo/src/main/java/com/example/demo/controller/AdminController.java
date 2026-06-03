package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.City;
import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerSearch;
import com.example.demo.entity.RoleType;
import com.example.demo.entity.ScheduledTask;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.CustomerSearchRepositoryImpl;
import com.example.demo.repository.ScheduledTaskRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.service.TaskService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api")
public class AdminController {
	@Autowired
	private CustomerSearchRepositoryImpl searchImpl;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerService customerService;
	@Autowired
    TaskService taskService;

	@Autowired
	ScheduledTaskRepository scheduledTaskRepository;

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@PostMapping(path = "/admin/search")
	public ResponseEntity<List<Customer>> searchCustomer(@RequestBody CustomerSearch search) {
		logger.info("search: " + search.toString());
		List<Customer> customers = searchImpl.searchCustomer(search);
		for (Customer customer : customers) {
			customerService.hideCustomer(customer);
		}
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@GetMapping(path = "/admin/customers")
	public ResponseEntity<List<Customer>> getCustomerList() {
		List<Customer> customers = customerRepository.findAll();
		List<Customer> realCustomers = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if (customerService.checkCustomerType(customer, RoleType.CUSTOMER.name())) {
				realCustomers.add(customer);
			}
		}
		for (Customer customer : realCustomers) {
			customerService.hideCustomer(customer);
		}
		return new ResponseEntity<>(realCustomers, HttpStatus.OK);
	}

	@GetMapping("/admin/customers/{email}")
	public Customer getCustomer(@PathVariable String email) {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			if (customer.getPreference() != null) {
				Set<City> cities = customerService.findCities(customer);
				customer.getPreference().setCities(cities);
				Set<String> homeTypes = customerService.findHomeTypes(customer);
				customer.getPreference().setHometypes(homeTypes);
			}
			customerService.hideCustomer(customer);
			return customer;
		} else {
			throw new UserNotFoundException("Customer not found for: " + email);
		}
	}

	@GetMapping("/admin/tasks")
	public ResponseEntity<List<ScheduledTask>> getTasks() {
		LocalDate today = LocalDate.now();
		List<ScheduledTask> tasks = scheduledTaskRepository.findTasks(today);
		return new ResponseEntity<>(tasks, HttpStatus.OK);

	}

	@GetMapping("/admin/tasks/{taskId}")
	public ResponseEntity<ScheduledTask> getTaskForCustomer(@PathVariable Long taskId) {
		logger.info("cancel task here!!\n\n");
		ScheduledTask task = taskService.findTaskForAdmin(taskId);
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@PutMapping(path = "/admin/tasks")
    public ScheduledTask updateTask(@RequestBody ScheduledTask task) {
		logger.info("change task here");
		try {
           scheduledTaskRepository.save(task);
		}catch(Exception e) {
			logger.info("error: "+e.getMessage());
		}
           return task;
    }
}

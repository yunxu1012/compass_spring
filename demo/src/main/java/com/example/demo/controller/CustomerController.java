package com.example.demo.controller;

import java.time.LocalTime;
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
import com.example.demo.entity.Schedule;
import com.example.demo.entity.ScheduledTask;
import com.example.demo.entity.StatusType;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ScheduledTaskRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.service.TaskService;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping(path = "/api")
public class CustomerController {
	@Autowired
    CustomerRepository customerRepository;
	@Autowired
    CustomerService customerService;
	@Autowired
    TaskService taskService;
	@Autowired
    ScheduledTaskRepository scheduledTaskRepository;
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	

	@GetMapping("/customers/{email}")
    public Customer getCustomer(@PathVariable String email) {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
     	   Customer customer =  optionalCustomer.get();
     	   if(customer.getPreference()!=null) {
     		   Set<City> cities = customerService.findCities(customer);
     		  customer.getPreference().setCities(cities);
     		  Set<String> homeTypes = customerService.findHomeTypes(customer);
     		 customer.getPreference().setHometypes(homeTypes);
     	   }
     	   return customer;
        }else {
     	   throw new UserNotFoundException("Customer not found for: "
        +email);
        }
    }
	
	@PutMapping(path = "customers/{email}")
    public Customer updateCustomer(@PathVariable String email, @RequestBody Customer customer) {
           Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
           if(optionalCustomer.isPresent()) {
        	   Customer existingCustomer = optionalCustomer.get();
        	   existingCustomer.setLastName(customer.getLastName());
        	   existingCustomer.setFirstName(customer.getFirstName());
        	   existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        	   return customerRepository.save(existingCustomer);
           }else {
        	   throw new UserNotFoundException("Customer not found for: "
           + email);
           }
    }
	

	@GetMapping("/customers/{email}/schedules")
    public ResponseEntity<List<Schedule>> getTasks(@PathVariable String email) {
		logger.info("get schedules here!!\n\n");
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
     	   Customer customer =  optionalCustomer.get();
     	  List<Schedule> list =taskService.getSchedules(customer.getCustomerId());
     	  logger.info("list size: "+list.size());
     	   return new ResponseEntity<>(list, HttpStatus.OK);
        }else {
     	   throw new UserNotFoundException("Customer not found for: "
        +email);
        }
    }
	
	@GetMapping("/customers/{email}/tasks")
    public ResponseEntity<List<ScheduledTask>> getSchedules(@PathVariable String email) {
		logger.info("get tasks here!!\n\n");
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
     	   Customer customer =  optionalCustomer.get();
     	   List<ScheduledTask> tasks = taskService.getTasks(customer);
     	  logger.info("task get!!\n\n"+tasks.size());
     	   return new ResponseEntity<>(tasks, HttpStatus.OK);
        }else {
     	   throw new UserNotFoundException("Customer not found for: "
        +email);
        }
    }
	
	@PostMapping("/customers/{email}/tasks")
    public ResponseEntity createTask(@PathVariable String email, @RequestBody ScheduledTask task) {
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if(optionalCustomer.isPresent()) {
     	   Customer customer =  optionalCustomer.get();
     	   task.setCustomerId(customer.getCustomerId());
     	   String taskTime = task.getTaskTime();
     	   if(taskTime!=null) {
     		   String[] arr = taskTime.split(":");
     		  
     		   LocalTime startTime = LocalTime.of(Integer.parseInt(arr[0]), 0);
     		   task.setStartTime(startTime);
     	   }
     	  task.setStatus(StatusType.PENDING);
     	 taskService.createTask(task);
     	   return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
        }else {
     	   throw new UserNotFoundException("Customer not found for: "
        +email);
        }
    }
	
	@PutMapping("/customers/{email}/tasks/{taskId}/cancel")
    public ResponseEntity cancelTask(@PathVariable String email, @PathVariable Long taskId) {
		logger.info("cancel task here!!\n\n");
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
		Optional<ScheduledTask> op = scheduledTaskRepository.findById(taskId);
        if(optionalCustomer.isPresent()) {
     	   Customer customer =  optionalCustomer.get();
     	   if(op.isPresent()) {
     	   ScheduledTask task = op.get();
     	   if(!task.getCustomerId().equals(customer.getCustomerId())){
     		  throw new UserNotFoundException("User have no rights to change the task: ");
     	   }
     	  task.setStatus(StatusType.CANCELED);
     	  
     	   scheduledTaskRepository.save(task);
     	   return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
     	   }else {
     		  throw new UserNotFoundException("Task Not found: ");
     	   }
        }else {
     	   throw new UserNotFoundException("Customer not found for: "
        +email);
        }
    }
	
	

	@GetMapping("/customers/{email}/tasks/{taskId}")
    public ResponseEntity<ScheduledTask> getTaskForCustomer(@PathVariable String email, @PathVariable Long taskId) {
		logger.info("cancel task here!!\n\n");
		Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
		Optional<ScheduledTask> op = scheduledTaskRepository.findById(taskId);
        if(optionalCustomer.isPresent()) {
     	   Customer customer =  optionalCustomer.get();
     	   if(op.isPresent()) {
     	   ScheduledTask task = op.get();
     	   if(!task.getCustomerId().equals(customer.getCustomerId())){
     		  throw new UserNotFoundException("User have no rights to see the appoitment: ");
     	   }
     	   return new ResponseEntity<>(task, HttpStatus.OK);
     	   }else {
     		  throw new UserNotFoundException("Task Not found: ");
     	   }
        }else {
     	   throw new UserNotFoundException("Customer not found for: "
        +email);
        }
    }

}

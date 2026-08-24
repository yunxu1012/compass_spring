package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Optional<Customer>findByEmail(String email);
	
	@Query(value = "SELECT * FROM customer c order by lower(c.first_name)", nativeQuery = true)
	public List<Customer> findAll();
	
}

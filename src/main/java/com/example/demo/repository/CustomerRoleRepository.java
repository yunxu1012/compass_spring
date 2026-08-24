package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.CustomerRole;

public interface CustomerRoleRepository extends JpaRepository<CustomerRole, Integer> {
	@Query(value = "SELECT * FROM customer_role  where customer_id = ?", nativeQuery = true)
	public List<CustomerRole> findByCustomerId(Integer customerId);

}

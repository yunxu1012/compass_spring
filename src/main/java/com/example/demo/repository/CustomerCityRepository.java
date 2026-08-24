package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.CustomerCity;

import jakarta.transaction.Transactional;

public interface CustomerCityRepository extends JpaRepository<CustomerCity, Integer> {
   List<CustomerCity> findByCustomerId(Integer customerId);	
   @Modifying
   @Transactional
   @Query(value = "delete FROM customer_city  where customer_id = ?", nativeQuery = true)
	public void deleteByCustomerId(Integer customerId);

}

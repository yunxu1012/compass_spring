package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.CustomerHometype;

import jakarta.transaction.Transactional;

public interface CustomerHometypeRepository extends JpaRepository<CustomerHometype, Integer> {
   List<CustomerHometype> findByCustomerId(Integer customerId);	
   @Modifying
   @Transactional
   @Query(value = "delete FROM customer_hometype  where customer_id = ?", nativeQuery = true)
	public void deleteByCustomerId(Integer customerId);

}

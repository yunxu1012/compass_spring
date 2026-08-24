package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CustomerPreference;

public interface CustomerPreferenceRepository extends JpaRepository<CustomerPreference, Integer> {
}

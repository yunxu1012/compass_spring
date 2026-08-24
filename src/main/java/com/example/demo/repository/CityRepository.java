package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> {
	@Query(value = "SELECT * FROM city  order by name", nativeQuery = true)
	public List<City> getCitiesOrdered();

}

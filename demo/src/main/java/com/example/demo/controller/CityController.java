package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.City;
import com.example.demo.repository.CityRepository;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping(path = "/api")
public class CityController {
	@Autowired
    CityRepository cityRepository;
	Logger logger = LoggerFactory.getLogger(CityController.class);
	
	
	@GetMapping("/cities")
	public ResponseEntity<List<City>> getCities() {
    try {
            List<City> list = cityRepository.getCitiesOrdered();
            return new ResponseEntity<>(list, HttpStatus.OK);
    } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

	
	
}

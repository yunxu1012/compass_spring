package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.City;
import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerCity;
import com.example.demo.entity.CustomerHometype;
import com.example.demo.entity.CustomerRole;
import com.example.demo.entity.Hometype;
import com.example.demo.entity.Role;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.CustomerCityRepository;
import com.example.demo.repository.CustomerHometypeRepository;
import com.example.demo.repository.CustomerRoleRepository;
import com.example.demo.repository.RoleRepository;
@Service
public class CustomerService {
	@Autowired
	CustomerHometypeRepository customerHometypeRepository;
	@Autowired
	CustomerCityRepository customerCityRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	CustomerRoleRepository customerRoleRepository;
	@Autowired
	RoleRepository roleRepository;
	
	public Set<String> findHomeTypes(Customer customer) {
		Set<String> types = new HashSet<>();
		List<CustomerHometype> homeTypes = customerHometypeRepository.findByCustomerId(
				customer.getCustomerId());
		for (CustomerHometype ch : homeTypes) {
			Hometype type = ch.getHometype();
			if(type!=null) {
				types.add(type.getType());
			}
		}
		return types;
	}
	
	public Set<City> findCities(Customer customer){
		Set<City> cities = new HashSet<>();
		List<CustomerCity> list = customerCityRepository.findByCustomerId(customer.getCustomerId());
		for (CustomerCity cc : list) {
			City city = cityRepository.getById(cc.getCityId());
			if (city != null) {
				cities.add(city);
			}
		}
		return cities;
	}
	
	public boolean checkCustomerType(Customer customer, String type) {
		boolean isType = false;
		List<CustomerRole> list = customerRoleRepository.findByCustomerId(customer.getCustomerId());
		for (CustomerRole cr : list) {
			Optional<Role> op = roleRepository.findById(cr.getRoleId());
			if (op.isPresent()) {
				Role role = op.get();
				if (role.getName().equals(type)) {
					isType = true;
				}
			}

		}
		return isType;

	}
	
	public void hideCustomer(Customer customer) {
		//customer.setRealEmail(customer.getEmail());
		customer.setFirstName(hideName(customer.getFirstName()));
		customer.setLastName(hideName(customer.getLastName()));
		customer.setEmail(hideEmail(customer.getEmail()));
		customer.setPhoneNumber(hidePhoneNumber(customer.getPhoneNumber()));
	}
	
	private String hideName(String name) {
		if(name.length()<=2) {
			return name;
		}
		int number = name.length()-2;
		String replace = "*".repeat(number);
		StringBuilder bu = new StringBuilder(name);
		return bu.replace(2,name.length(),replace).toString();
	}
	
	private String hideEmail(String email) {
		String[] arr = email.split("@");
		for(int i=0; i<arr.length; i++) {
			arr[i] = hideName(arr[i]);
		}
		return arr[0]+"@"+arr[1];
	}
	
	private String hidePhoneNumber(String phone) {
		if(phone.length()<=6) {
			return phone;
		}
		int num = phone.length()-6;
		String replace = "*".repeat(num);
		StringBuilder bu = new StringBuilder(phone);
		return bu.replace(3,phone.length()-3,replace).toString();
	}


}

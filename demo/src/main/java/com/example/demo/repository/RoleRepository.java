package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	@Query(value = "SELECT * FROM role where name=?::roletype", nativeQuery = true)
	public Role findByName(String name);

}

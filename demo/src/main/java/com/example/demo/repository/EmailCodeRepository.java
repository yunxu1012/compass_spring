package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EmailCode;

public interface EmailCodeRepository extends JpaRepository<EmailCode, String> {

}

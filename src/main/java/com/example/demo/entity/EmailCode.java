package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EmailCode {
	
	 @Id
	private String email;
	
    private String code;
    
    @Column(columnDefinition = "update_time")
    private LocalDateTime updateTime;

   
    

	public EmailCode(String email, String code, LocalDateTime updateTime) {
		super();
		this.email = email;
		this.code = code;
		this.updateTime = updateTime;
	}


	public EmailCode() {
    	super();
    }


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public LocalDateTime getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}


	
	
  
}
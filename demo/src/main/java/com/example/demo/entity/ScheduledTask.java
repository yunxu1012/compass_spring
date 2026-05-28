package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;

@Entity
public class ScheduledTask {
	
	@SequenceGenerator(initialValue = 1, name = "scheduled_task_seq", sequenceName = "scheduled_task_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheduled_task_seq")
	@Id
	private Long id;
	
	private Integer customerId;

	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "?::statustype")
	private StatusType status;
	
	@Column(columnDefinition = "task_date")
    private LocalDate taskDate;
	
	private LocalTime startTime;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String zipcode;
	
	private String comment;
	
	@Transient
	private String taskTime;

   
	
    public ScheduledTask(Integer customerId, StatusType status, LocalDate taskDate, LocalTime startTime, String address,
			String comment) {
		super();
		this.customerId = customerId;
		this.status = status;
		this.taskDate = taskDate;
		this.startTime = startTime;
		this.address = address;
		this.comment = comment;
	}


	public ScheduledTask() {
    	super();
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}



	public StatusType getStatus() {
		return status;
	}


	public void setStatus(StatusType status) {
		this.status = status;
	}


	public LocalDate getTaskDate() {
		return taskDate;
	}


	public void setTaskDate(LocalDate taskDate) {
		this.taskDate = taskDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	

	public String getTaskTime() {
		return taskTime;
	}


	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	

	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getZipcode() {
		return zipcode;
	}


	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}

	
  
  
}
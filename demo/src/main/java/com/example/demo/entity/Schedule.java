package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

public class Schedule {
   private LocalDate taskDate;
   private List<String> taskTime;
   public Schedule() {
	   super();
   }
   public Schedule(LocalDate taskDate, List<String> taskTime) {
	super();
	this.taskDate = taskDate;
	this.taskTime = taskTime;
   }
   public LocalDate getTaskDate() {
	return taskDate;
   }
   public void setTaskDate(LocalDate taskDate) {
	this.taskDate = taskDate;
   }
   public List<String> getTaskTime() {
	return taskTime;
   }
   public void setTaskTime(List<String> taskTime) {
	this.taskTime = taskTime;
   }
   
}

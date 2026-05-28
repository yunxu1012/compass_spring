package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.ScheduledTask;
import com.example.demo.repository.ScheduledTaskRepository;

@Service
public class TaskService {
	@Autowired
	ScheduledTaskRepository scheduleTaskRepository;
	public  List<LocalTime> timeList = new ArrayList<LocalTime>();
	Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	public TaskService() {
		timeList.add(LocalTime.of(9, 0));
		timeList.add(LocalTime.of(10, 0));
		timeList.add(LocalTime.of(11, 0));
		timeList.add(LocalTime.of(12, 0));
		timeList.add(LocalTime.of(14, 0));
		timeList.add(LocalTime.of(15, 0));
		timeList.add(LocalTime.of(16, 0));
		timeList.add(LocalTime.of(17, 0));
	}
	
	public List<Schedule> getSchedules() {
		LocalDate now = LocalDate.now();
		LocalTime time = LocalTime.now();
		LocalDate endDate = now.plusDays(9);
		List<ScheduledTask> tasks= scheduleTaskRepository.findTasks(now, LocalTime.of(9, 0), endDate,
				LocalTime.of(17, 0));
		
		logger.info("task size()"+tasks.size());
		List<Schedule> schs = new ArrayList<Schedule>();
		for(int i=0; i<=9; i++) {
			Schedule sch = new Schedule();
			LocalDate date = now.plusDays(i);
			sch.setTaskDate(date);
			List<String> list = new ArrayList<String>();
			for(int m=0; m<timeList.size();m++) {
				LocalTime start = (LocalTime)timeList.get(m);
				if(timeScheduled(tasks,date,start)) {
					continue;
				}
				LocalTime end = start.plusHours(1);
				String str = start.toString()+"-"+end.toString();
				list.add(str);
			}
			sch.setTaskTime(list);
			schs.add(sch);
		}
		return schs;
	}
	
	public  List<ScheduledTask> getTasks(Customer customer) {
		LocalDate now = LocalDate.now();
		List<ScheduledTask> tasks= scheduleTaskRepository.findTasksForCustomer(now,
				customer.getCustomerId());
		
		
		return tasks;
	}
	
	private boolean timeScheduled(List<ScheduledTask> list, LocalDate date, LocalTime time) {
		for(ScheduledTask task:list) {
			if(task.getTaskDate().equals(date)&&task.getStartTime().equals(time)) {
				return true;
			}
		}
		return false;
	}

}

package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.ScheduledTask;

public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, Long> {
	@Query(value = "SELECT * FROM Scheduled_Task s where "
			+ " s.task_date >= :startDate and status in "
			+ " ('PENDING', 'APPROVED') order by s.task_date, s.start_time ", nativeQuery = true)
	public List<ScheduledTask> findTasks(LocalDate startDate
			);
	

	@Query(value = "SELECT * FROM Scheduled_Task s where s.task_date >= :startDate "
			+ " and  customer_id =:customerId and status "
			+ "in ('PENDING', 'APPROVED', 'REJECTED') "
			+ " order by s.task_date, s.start_time", nativeQuery = true)
	public List<ScheduledTask> findTasksForCustomer(LocalDate startDate, 
		 Integer customerId);
	
	@Query(value = "SELECT s.agent FROM Scheduled_Task s where s.task_date = :startDate "
			+ " and s.start_time =:startTime and status in ('APPROVED')"
			+ " order by s.task_date, s.start_time ", nativeQuery = true)
	public List<String> findAgentsForDateTime(LocalDate startDate, LocalTime startTime);
}

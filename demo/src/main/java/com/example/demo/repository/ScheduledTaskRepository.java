package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.ScheduledTask;

public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, Long> {
	@Query(value = "SELECT * FROM Scheduled_Task s where "
			+ " s.task_date >= :startDate and status in ('PENDING', 'APPROVED')", nativeQuery = true)
	public List<ScheduledTask> findTasks(LocalDate startDate
			);
	

	@Query(value = "SELECT * FROM Scheduled_Task s where s.task_date >= :startDate "
			+ " and  customer_id =:customerId and status in ('PENDING', 'APPROVED', 'REJECTED')", nativeQuery = true)
	public List<ScheduledTask> findTasksForCustomer(LocalDate startDate, 
		 Integer customerId);
	
	@Query(value = "SELECT s.agent FROM Scheduled_Task s where s.task_date = :startDate "
			+ " and s.start_time =:startTime and status in ('APPROVED')", nativeQuery = true)
	public List<String> findAgentsForDateTime(LocalDate startDate, LocalTime startTime);
}

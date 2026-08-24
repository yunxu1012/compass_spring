package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.ScheduledTask;
import com.example.demo.entity.StatusType;
import com.example.demo.exception.TaskNotAvailableException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ScheduledTaskRepository;

@Service
public class TaskService {
	@Autowired
	ScheduledTaskRepository scheduledTaskRepository;
	@Autowired
	CustomerRepository customerRepository;
	public List<LocalTime> timeList = new ArrayList<LocalTime>();
	Logger logger = LoggerFactory.getLogger(TaskService.class);
	private static List<String> agentList = Arrays.asList("Agent A", "Agent B", "Agent C");

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

	public List<Schedule> getSchedules(Integer customerId) {
		LocalDate today = LocalDate.now();
		List<ScheduledTask> tasks = scheduledTaskRepository.findTasks(today);

		logger.info("task size()" + tasks.size());
		List<Schedule> schs = new ArrayList<Schedule>();
		for (int i = 1; i <= 14; i++) {
			Schedule sch = new Schedule();
			LocalDate date = today.plusDays(i);
			if (date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
				continue;
			}
			sch.setTaskDate(date);
			List<String> list = new ArrayList<String>();
			for (int m = 0; m < timeList.size(); m++) {
				LocalTime start = (LocalTime) timeList.get(m);
				if (timeScheduled(tasks, date, start, customerId)) {
					continue;
				}
				List already = alreadyScheduledAgent(date, start);
				if (already.size() >= this.agentList.size()) {
					continue;
				}
				LocalTime end = start.plusHours(1);
				String str = start.toString() + "-" + end.toString();
				list.add(str);
			}
			sch.setTaskTime(list);
			schs.add(sch);
		}
		return schs;
	}

	public List<ScheduledTask> getTasks(Customer customer) {
		LocalDate now = LocalDate.now();
		List<ScheduledTask> tasks = scheduledTaskRepository.findTasksForCustomer(now, customer.getCustomerId());
		if (customer.isDemo()) {
			for (ScheduledTask task : tasks) {
				hideTask(task);
			}
		}
		return tasks;
	}

	private boolean timeScheduled(List<ScheduledTask> list, LocalDate date, LocalTime time, Integer customerId) {
		for (ScheduledTask task : list) {
			if (task.getTaskDate().equals(date) && task.getStartTime().equals(time)
					&& task.getCustomerId().equals(customerId)) {
				return true;
			}
		}
		return false;
	}

	public ScheduledTask findTaskForAdmin(Long taskId) {
		Optional<ScheduledTask> op = scheduledTaskRepository.findById(taskId);
		try {

			if (op.isPresent()) {
				ScheduledTask task = op.get();
				hideTask(task);
				Optional<Customer> custOp = customerRepository.findById(task.getCustomerId());
				if (custOp.isPresent()) {
					Customer cust = (Customer) custOp.get();
					task.setCustomerName(hideField(cust.getFirstName()) + " " + hideField(cust.getLastName()));
					task.setCustomerEmail(hideEmail(cust.getEmail()));
					if (task.getStatus().equals(StatusType.PENDING)) {
						List<String> agents = new ArrayList<String>();
						List<String> already = alreadyScheduledAgent(task.getTaskDate(), task.getStartTime());
						if (already != null) {
							for (String agent : agentList) {
								if (!already.contains(agent)) {
									agents.add(agent);
								}
							}
						}
						task.setAgentList(agents);
					}
				}
				return task;
			} else {
				throw new UserNotFoundException("Task Not found: ");
			}
		} catch (Exception e) {
			logger.info("Error!!!" + e.getMessage());
			return null;
		}
	}

	private List<String> alreadyScheduledAgent(LocalDate taskDate, LocalTime time) {
		return scheduledTaskRepository.findAgentsForDateTime(taskDate, time);
	}

	public synchronized void createTask(ScheduledTask task) {
		List already = alreadyScheduledAgent(task.getTaskDate(), task.getStartTime());
		if (already.size() >= this.agentList.size()) {
			throw new TaskNotAvailableException(
					"The time slot already occupied: " + task.getTaskDate() + " " + task.getStartTime());
		}
		scheduledTaskRepository.save(task);
	}

	public void hideTask(ScheduledTask task) {
		try {
			task.setAddress(hideField(task.getAddress()));
			task.setComment(hideField(task.getComment()));
			task.setNote(hideField(task.getNote()));
		} catch (Exception e) {
			logger.info("error!!!!: " + e.getMessage());
		}
	}

	public String hideField(String field) {
		if (field==null || field.length() <= 2) {
			return field;
		}
		int number = field.length() - 2;
		String replace = "*".repeat(number);
		StringBuilder bu = new StringBuilder(field);
		return bu.replace(2, field.length(), replace).toString();
	}

	private String hideEmail(String email) {
		String[] arr = email.split("@");
		for (int i = 0; i < arr.length; i++) {
			arr[i] = hideField(arr[i]);
		}
		return arr[0] + "@" + arr[1];
	}

}

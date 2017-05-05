package com.sv.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerControllerFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.services.TaskService;
import com.sv.vo.SearchCriteria;
import com.sv.vo.Task;

@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskController extends TrackerControllerFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TaskService taskService;
	
	@PostMapping("/search")
	public List<Task> searchTasks(@RequestBody List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return taskService.searchTask(searchCriteriaList);
		} catch(TrackerException e) {
			logger.error("TrackerException", e);
			throw e;
		} catch(Exception e) {
			logger.error("Exception", e);
			throw new TrackerException("9999", e.getMessage());
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
	}
	
	@GetMapping("/{id}")
	public Task getTask(@PathVariable int id) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return taskService.getTask(id);
		} catch(TrackerException e) {
			logger.error("TrackerException", e);
			throw e;
		} catch(Exception e) {
			logger.error("Exception", e);
			throw new TrackerException("9999", e.getMessage());
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
	}
	
	
	// @Secur({"Delivery Manager","Test Manager","Dev Lead","Test Lead"})
	@Secured( {"Delivery Manager","Test Manager","Dev Lead","Test Lead"})
	@PostMapping()
	public Task addTask(@RequestBody Task task) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return taskService.addTask(task);
		} catch(TrackerException e) {
			logger.error("TrackerException", e);
			throw e;
		} catch(Exception e) {
			logger.error("Exception", e);
			throw new TrackerException("9999", e.getMessage());
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}	
	}
	
	@PutMapping()
	public Task updateTask(@RequestBody Task task) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return taskService.updateTask(task);
		} catch(TrackerException e) {
			logger.error("TrackerException", e);
			throw e;
		} catch(Exception e) {
			logger.error("Exception", e);
			throw new TrackerException("9999", e.getMessage());
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}	
	}

}

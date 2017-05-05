package com.sv.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.sv.services.ReminderService;
import com.sv.vo.Reminder;
import com.sv.vo.SearchCriteria;

@CrossOrigin
@RestController
@RequestMapping("/reminder")
public class ReminderController extends TrackerControllerFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ReminderService reminderService;
	
	@PostMapping("/search")
	public List<Reminder> searchReminders(@RequestBody List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return reminderService.searchReminder(searchCriteriaList);
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
	public Reminder getReminder(@PathVariable int id) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return reminderService.getReminder(id);
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
	
	@PostMapping()
	public Reminder addReminder(@RequestBody Reminder reminder) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return reminderService.addReminder(reminder);
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
	public Reminder updateReminder(@RequestBody Reminder reminder) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return reminderService.updateReminder(reminder);
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
	
	@DeleteMapping("/{id}")
	public void deleteReminder(@PathVariable int id) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			reminderService.deleteReminder(id);
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

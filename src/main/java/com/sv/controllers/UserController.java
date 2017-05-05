package com.sv.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.sv.services.UserService;
import com.sv.vo.User;
import com.sv.vo.SearchCriteria;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends TrackerControllerFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	
	@PostMapping("/search")
	public List<User> searchUsers(@RequestBody List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return userService.searchUser(searchCriteriaList);
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
	public User getUser(@PathVariable int id) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return userService.getUser(id);
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
	
	@GetMapping("/name/{userName}")
	public User getUserByName(@PathVariable String userName) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return userService.getUserByName(userName);
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
	public User addUser(@RequestBody User user) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return userService.addUser(user);
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
	public User updateUser(@RequestBody User user) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			
			return userService.updateUser(user);
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

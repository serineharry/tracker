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
import com.sv.services.ProjectService;
import com.sv.vo.Project;
import com.sv.vo.SearchCriteria;

@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController extends TrackerControllerFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProjectService projService;
	
	@PostMapping("/search")
	public List<Project> searchProjects(@RequestBody List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return projService.searchProject(searchCriteriaList);
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
	public Project getProject(@PathVariable int id) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return projService.getProject(id);
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
	public Project addProject(@RequestBody Project proj) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return projService.addProject(proj);
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
	public Project updateProject(@RequestBody Project proj) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return projService.updateProject(proj);
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

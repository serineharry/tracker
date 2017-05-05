package com.sv.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerControllerFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.services.ResourceService;
import com.sv.vo.Resource;
import com.sv.vo.Schedule;

@CrossOrigin
@RestController
@RequestMapping("/resource")
public class ResourceController extends TrackerControllerFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ResourceService resService;
	
	@PostMapping()
	public List<Resource> getResourcesWithAvailablity(@RequestBody Schedule schedule) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return resService.getResourcesWithAvailablity(schedule);
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

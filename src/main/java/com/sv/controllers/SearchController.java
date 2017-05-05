package com.sv.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerControllerFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.services.SearchService;
import com.sv.vo.SearchConfig;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController extends TrackerControllerFactory{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SearchService searchSvc;
	

	@GetMapping("/config/{searchOn}")
	public List<SearchConfig> getSearchConfig(@PathVariable String searchOn) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			return searchSvc.getSearchConfig(searchOn);
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

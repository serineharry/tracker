package com.sv.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerServiceFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.repositories.SearchDao;
import com.sv.vo.SearchConfig;

@Service
@Transactional
public class SearchService extends TrackerServiceFactory{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SearchDao searchDao;

	public List<SearchConfig> getSearchConfig(String searchOn) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<SearchConfig> searchConfig = null;
		
		try {
		
			searchConfig = searchDao.getSearchCriterias(searchOn);
			
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return searchConfig;
				
	}

}

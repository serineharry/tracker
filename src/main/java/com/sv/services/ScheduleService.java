package com.sv.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerServiceFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.repositories.ScheduleDao;
import com.sv.vo.Schedule;
import com.sv.vo.SearchCriteria;

@Service
public class ScheduleService extends TrackerServiceFactory{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ScheduleDao schedDao;
	
	public List<Schedule> searchSchedules(List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Schedule> scheduleList = null;
		
		try {		
			scheduleList = schedDao.searchSchedules(searchCriteriaList);					
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return scheduleList;
				
	}

}
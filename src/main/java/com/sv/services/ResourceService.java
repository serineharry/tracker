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
import com.sv.repositories.ApplicationDao;
import com.sv.repositories.ResourceDao;
import com.sv.vo.Application;
import com.sv.vo.Resource;
import com.sv.vo.Schedule;

@Service
@Transactional
public class ResourceService extends TrackerServiceFactory{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ResourceDao resDao;
	
	@Autowired
	private ApplicationDao appDao;

	public List<Resource> getResourcesWithAvailablity(Schedule schedule) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		List<Resource> userList;
		try {		
			
			userList = resDao.getApplicationUser(schedule.getApplicationId());
			
			if(userList!= null)
			{
				Application app = appDao.getApplicationById(schedule.getApplicationId());
				Double appWorkhrs = 8.0;
				if(app != null && app.getWorkingHours() != null)
				{
					appWorkhrs = app.getWorkingHours();
				}
				
				Integer schedDays = schedule.getDays();
				if(schedule.getDays() == null)
				{
					if(schedule.getStartDate() != null && schedule.getEndDate() != null)
					{
						schedDays = resDao.getNumOfWorkingDays(schedule.getStartDate(), schedule.getEndDate());
					}
					
				}
				
				
				List<Resource> userAvailablity = resDao.getResourceAvailablity(schedule);
				
				
				for(Resource user : userList)
				{
					
					if(schedDays != null && schedDays > 0)
					{
						Double totalHrs =  schedDays * appWorkhrs;
						user.setTotalAvailableHrs(totalHrs);
						user.setPerDayAvailableHrs( appWorkhrs );
					
						for(Resource availablity : userAvailablity)
						{
							if(availablity.getUserId() == user.getUserId())
							{
								Double totalAvailHrs =  totalHrs - availablity.getTotalAvailableHrs();
								
								if(totalHrs < 0)
								{
									user.setTotalAvailableHrs(0.0);
									user.setPerDayAvailableHrs(0.0);
										
								}else{
									user.setTotalAvailableHrs(totalAvailHrs);
									user.setPerDayAvailableHrs(totalAvailHrs / schedDays);										
								}
								
								break;
							}
						}
					}
				}
			}		
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return userList;
				
	}

	

}

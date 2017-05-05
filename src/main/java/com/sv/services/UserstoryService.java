package com.sv.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerServiceFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.repositories.UserstoryDao;
import com.sv.repositories.ScheduleDao;
import com.sv.repositories.TaskDao;
import com.sv.vo.Resource;
import com.sv.vo.Schedule;
import com.sv.vo.Userstory;
import com.sv.vo.SearchCriteria;
import com.sv.vo.Task;

@Service
@Transactional
public class UserstoryService extends TrackerServiceFactory{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserstoryDao userStoryDao;
	
	@Autowired
	private ScheduleDao schedDao;
	
	@Autowired
	private TaskDao taskDao;

	
	public List<Userstory> searchUserstory(List<SearchCriteria> searchCriteriaList)  throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Userstory> schedules = null;
		
		try {		
			schedules = userStoryDao.getUserstories(searchCriteriaList);					
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return schedules;
		
	}
	
	public List<Userstory> getProjectUserstories(int projectId) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Userstory> userstories = null;
		
		try {		
			userstories = userStoryDao.getProjectUserstories(projectId);
			
			for(Userstory ustory : userstories)
			{
				
				List<Schedule> scheduleList = schedDao.getUserstorySchedules(ustory.getUserstoryId());
				
				for(Schedule sched : scheduleList)
				{
					List<Resource> resourceList = schedDao.getScheduleResource(sched.getScheduleId());
					sched.setResources(resourceList);
				}
				ustory.setSchedules(scheduleList);
			}
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return userstories;
		
	}

	
	public Userstory addUserstory(Userstory uStory) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			uStory = userStoryDao.addUserstory(uStory);
			
			if(uStory.getUserstoryId() > 0)
			{
				for(Schedule sched : uStory.getSchedules())
				{
					sched.setUserstoryId(uStory.getUserstoryId());
					schedDao.addSchedule(sched);
					
					
					for(Resource res : sched.getResources())
					{
						Task task = getTaskFromResource(res, sched, uStory);
						
						if(task != null)
						{
							task.setStatus("Active");
							taskDao.addTask(task);
							if(task.getTaskId() > 0)
							{
								res.setTaskId(task.getTaskId());
							}
						}
					}	
				}
				
			}			
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return uStory;
	}

	public Userstory updateUserstory(Userstory uStory) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			uStory = userStoryDao.updateUserstory(uStory);
			
			List<Resource> deletedResourceList = new ArrayList<Resource>(1);
			
			if(uStory.getUserstoryId() > 0)
			{
				for(Schedule sched : uStory.getSchedules())
				{
					sched.setUserstoryId(uStory.getUserstoryId());
					schedDao.updateSchedule(sched);					
				
					for(Resource res : sched.getResources())
					{
						Task task = getTaskFromResource(res, sched, uStory);
						
						if(task != null)
						{
							if(task.getTaskId() > 0)
							{
								if("DELETE".equalsIgnoreCase(res.getMode()))
								{
									taskDao.unAssignTask(task);
									deletedResourceList.add(res);
								}else{
									taskDao.updateTask(task);
								}
								
							}else{
								taskDao.addTask(task);
								if(task.getTaskId() > 0)
								{
									res.setTaskId(task.getTaskId());
								}
							}
						}
					}
					
					if(deletedResourceList.size() > 0)
					{
						sched.getResources().removeAll(deletedResourceList);
					}
					
				}
				
			}
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return uStory;
	}
	
	public int deleteUserstory(int scheduleId) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		int delCnt = 0;
		
		try {
			delCnt = userStoryDao.deleteUserstory(scheduleId);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return delCnt;
	}
	
	private Task getTaskFromResource(Resource res, Schedule sched, Userstory uStory) {
		
		Task task = null;
		if(res != null)
		{
			/*task_id, user_id, app_id, sched_id, start_date, end_date, hours*/
			task = new Task();
			if(res.getTaskId() != null)
			{
				task.setTaskId(res.getTaskId());
			}			
			task.setUserId(res.getUserId());
			task.setApplicationId(uStory.getApplicationId());
			task.setScheduleId(sched.getScheduleId());
			task.setTaskName(uStory.getUserstory());
			task.setTaskDesc(uStory.getUserstoryDesc());
			task.setStartDate(sched.getStartDate());
			task.setEndDate(sched.getEndDate());
			task.setHoursOfWork(res.getHoursAssigned());
			task.setProgress(res.getProgress());
			task.setReviewProgress(res.getReviewProgress());
			task.setOtherProgress(res.getOtherProgress());
		}
		return task;		
	}

}

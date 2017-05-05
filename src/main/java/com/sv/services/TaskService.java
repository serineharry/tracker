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
import com.sv.repositories.TaskDao;
import com.sv.vo.Task;
import com.sv.vo.SearchCriteria;

@Service
@Transactional
public class TaskService extends TrackerServiceFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskDao taskDao;

	public List<Task> searchTask(List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Task> taskectList = null;
		
		try {		
			taskectList = taskDao.searchTask(searchCriteriaList);					
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return taskectList;
				
	}

	public Task getTask(int taskId) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		Task taskect = null;
		
		try {		
			taskect = taskDao.getTask(taskId);					
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return taskect;
				
	}
	
	public Task addTask(Task task) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			task = taskDao.addTask(task);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return task;
	}

	public Task updateTask(Task task) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			taskDao.updateTask(task);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return task;
	}
	
	
	public List<Task> getUserTask(Task task) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		List<Task> userTasks = null;
		
		try {
			userTasks = taskDao.getUserTask(task);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return userTasks;
		
	}
	
	public List<Task> getUserMissedTask(Task task) throws TrackerException
	{		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		List<Task> userMissedTasks = null;
		
		try {
			userMissedTasks = taskDao.getUserMissedTask(task);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return userMissedTasks;
		
	}
	
	public List<Task> getApplicationTask(int applicationId) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		List<Task> applicationLevelTasks = null;
		
		try {
			applicationLevelTasks = taskDao.getApplicationTask(applicationId);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return applicationLevelTasks;
	
	}
	
	public List<Task> getApplicationMissedTask(int applicationId) throws TrackerException
	{
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		List<Task> applicationMissedTasks = null;
		
		try {
			applicationMissedTasks = taskDao.getApplicationMissedTask(applicationId);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return applicationMissedTasks;	
		
	}

		

}

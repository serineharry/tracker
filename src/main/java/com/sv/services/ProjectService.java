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
import com.sv.repositories.ProjectDao;
import com.sv.vo.Project;
import com.sv.vo.SearchCriteria;

@Service
@Transactional
public class ProjectService extends TrackerServiceFactory{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProjectDao projDao;

	public List<Project> searchProject(List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Project> projectList = null;
		
		try {		
			projectList = projDao.searchProject(searchCriteriaList);					
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return projectList;
				
	}

	public Project getProject(int projectId) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		Project project = null;
		
		try {		
			project = projDao.getProject(projectId);					
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return project;
				
	}
	
	public Project addProject(Project proj) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			proj = projDao.addProject(proj);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return proj;
	}

	public Project updateProject(Project proj) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			proj = projDao.updateProject(proj);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return proj;
	}

}

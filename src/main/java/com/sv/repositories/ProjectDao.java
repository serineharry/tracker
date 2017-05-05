package com.sv.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.DynamicQueryGenerator;
import com.sv.framework.TrackerDaoFactory;
import com.sv.framework.TrackerMapSqlParameterSource;
import com.sv.framework.exceptions.TrackerException;
import com.sv.vo.Project;
import com.sv.vo.SearchCriteria;

@Repository
public class ProjectDao extends TrackerDaoFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<Project> searchProject(List<SearchCriteria> searchCriteriaList) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Project> projList = new ArrayList<>(1);
		try {
			String sql = "select * from project ";
			
			sql = DynamicQueryGenerator.generateSearch(sql, getJoiner(), searchCriteriaList);
			
			SqlParameterSource param = TrackerMapSqlParameterSource.mapSearchCriteria(searchCriteriaList);
	        	        			
			projList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Project.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching SearchCriteria");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return projList;		
	
	}

	public Project getProject(int projectId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		Project proj = null;
		
		try {
			String sql = "select * from project where project_id=:projectId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("projectId", projectId);
	        	        			
			List<Project> projList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Project.class));
			
			if(projList != null && projList.size() > 0)
			{
				proj = projList.get(0);
			}
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Project");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return proj;		
	
	}
	
	public Project addProject(Project proj) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "insert into project(project_uid, application_id, project_name, start_date, end_date, release_info, project_type,"
					+ "project_status, effort, tier2, primary_se, primary_developer, primary_tester, manager, lead_manager)"
					+ "values (:projectUid, :applicationId, :projectName, :startDate, :endDate, :releaseInfo, :projectType,"
					+ ":projectStatus, :effort, :tier2, :primarySe, :primaryDeveloper, :primaryTester, :manager, :leadManager)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource param = new BeanPropertySqlParameterSource(proj);
	        			
			
			int insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
	        if(insertCnt > 0)
	        {
	        	proj.setProjectId(keyHolder.getKey().intValue());
	        }
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating Project");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return proj;		
	
	}

	public Project updateProject(Project proj) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "update project "
					+ " set project_uid = :projectUid, "
					+ " application_id = :applicationId, "
					+ " project_name = :projectName, "
					+ " start_date = :startDate, "
					+ " end_date = :endDate, "
					+ " release_info = :releaseInfo, "
					+ " project_type = :projectType, "
					+ " project_status = :projectStatus, "
					+ " effort = :effort, "
					+ " tier2 = :tier2, "
					+ " primary_se = :primarySe, "
					+ " primary_developer = :primaryDeveloper, "
					+ " primary_tester = :primaryTester, "
					+ " manager = :manager, "
					+ " lead_manager = :leadManager "					
					+ " where project_id = :projectId";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(proj);
			int updateCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
			logger.debug("Update cnt: " + updateCnt);
	        
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while updating Project");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return proj;		
	
	}
	
	private Map<String, String> getJoiner() {
		Map<String, String> joiner = new HashMap<String, String>();
		joiner.put("Project Name", "project.project_name");
		joiner.put("Project Type", "project.project_type");
		joiner.put("Release", "project.release");
		joiner.put("Start Date", "project.start_date");
		joiner.put("End Date", "project.end_date");
		joiner.put("Application Id", "project.application_id");
		joiner.put("User Id", "project.user_id");
		return joiner;
	}


}

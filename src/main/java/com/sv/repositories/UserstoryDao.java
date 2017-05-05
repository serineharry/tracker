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
import com.sv.vo.SearchCriteria;
import com.sv.vo.Userstory;

@Repository
public class UserstoryDao extends TrackerDaoFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<Userstory> getUserstories(List<SearchCriteria> searchCriteriaList) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Userstory> schedList = new ArrayList<>(1);
		try {
			String sql = "select schedule.*, project.project_name "
					+ " from schedule, project"
					+ " where schedule.project_id = project.project_id ";
					
			sql = DynamicQueryGenerator.generateSearch(sql, getJoiner(), searchCriteriaList);
			SqlParameterSource param = TrackerMapSqlParameterSource.mapSearchCriteria(searchCriteriaList);
	        schedList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Userstory.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching SearchCriteria");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return schedList;		
	
	}

	public List<Userstory> getProjectUserstories(int projectId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		List<Userstory> schedList = null;
		
		try {
			String sql = "select * from userstory where project_id=:projectId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("projectId", projectId);
	        	        			
			schedList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Userstory.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Userstories");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return schedList;		
	
	}
	
	
	
	public Userstory addUserstory(Userstory sched) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "insert into schedule(project_id, application_id, user_story, user_story_desc, interfaces, dependencies, external_date, comments)"
					+ "values (:projectId, :userStory, :userStoryDesc, :interfaces, :dependencies, :externalDate, :comments)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource param = new BeanPropertySqlParameterSource(sched);
	        			
			
			int insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
	        if(insertCnt > 0)
	        {
	        	sched.setUserstoryId(keyHolder.getKey().intValue());
	        }
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating Userstory");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return sched;		
	
	}

	public Userstory updateUserstory(Userstory sched) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "update user_story "
					+ " set project_id = :projectId, "
					+ " user_story = :userStory, "
					+ " user_story_desc = :userStoryDesc, "
					+ " interfaces = :interfaces, "
					+ " dependencies = :dependencies, "
					+ " external_date = :externalDate, "
					+ " comments = :comments "
					+ " where userstory_id = :userstoryId";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(sched);
			int updateCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
			logger.debug("Update cnt: " + updateCnt);
	        
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while updating Userstory");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return sched;
	
	}
	
	
	public int deleteUserstory(int userstoryId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int deleteCnt = 0;
		try {
			String sql = "delete from userstory "
					+ " where userstory_id = :userstoryId ";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("userstoryId",userstoryId);
	        			
			deleteCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while deleting Userstory");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return deleteCnt;		
	
	}

	private Map<String, String> getJoiner() {

		Map<String, String> joiner = new HashMap<String, String>();
		joiner.put("Project Name", "project.project_name");
		joiner.put("User Story", "schedule.user_story");
		joiner.put("Start Date", "schedule.start_date");
		joiner.put("End Date", "schedule.end_date");
		joiner.put("Phase", "schedule.phase");
		joiner.put("days", "schedule.days");
		joiner.put("Application Id", "project.application_id");
		return joiner;
	}


}

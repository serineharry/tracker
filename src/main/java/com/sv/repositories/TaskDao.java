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
import com.sv.vo.Task;

@Repository
public class TaskDao extends TrackerDaoFactory {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public List<Task> searchTask(List<SearchCriteria> searchCriteriaList) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Task> taskList = new ArrayList<>(1);
		try {
			String sql = "select task.*, user.username,user.first_name, user.last_name, schedule.user_story "
					+ " from task "
					+ " join user on task.user_id = user.user_id "
					+ " left join schedule on schedule.schedule_id = task.schedule_id ";
			
			sql = DynamicQueryGenerator.generateSearch(sql, getJoiner(), searchCriteriaList);
			
			SqlParameterSource param = TrackerMapSqlParameterSource.mapSearchCriteria(searchCriteriaList);
	        	        			
			taskList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Task.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching SearchCriteria");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return taskList;		
	
	}
	
	public Task getTask(int taskId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		Task task = null;
		
		try {
			String sql = "select task.* , schedule.schedule_id, schedule.user_story, schedule.phase, "
					+ " user.user_id, user.username, user.last_name, user.first_name "
					+ " from task left join schedule on task.schedule_id = schedule.schedule_id "
					+ " left join user on task.user_id = user.user_id "
					+ " where task.task_id = :taskId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("taskId", taskId);
	        	        			
			List<Task> taskList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Task.class));
			
			if(taskList != null && taskList.size() > 0)
			{
				task = taskList.get(0);
			}
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Task");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return task;		
	
	}
	
	public Task addTask(Task task) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);		
		
		try {
			String sql = "insert into task(user_id, application_id, schedule_id, task_name, task_desc, start_date, end_date, hours_of_work, status, comment) "
					+ " values (:userId, :applicationId, :scheduleId, :taskName, :taskDesc, :startDate, :endDate, :hoursOfWork, :status, :comment)";
			
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource param = new BeanPropertySqlParameterSource(task);
	        			
			
			int insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
	        if(insertCnt > 0)
	        {
	        	task.setTaskId(keyHolder.getKey().intValue());
	        }			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while Creating task assignment");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return task;		
	
	}
	
	public int updateTask(Task task) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int insertCnt = 0;
		try {
			String sql = "update task "
					+ " set user_id=:userId, application_id=:applicationId, schedule_id=:scheduleId, "
					+ " task_name=:taskName, task_desc=:taskDesc, start_date=:startDate, end_date=:endDate, "
					+ " hours_of_work=:hoursOfWork, status=:status, comment=:comment "
					+ " where task_id=:taskId";
			
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(task);
	        			
			
			insertCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while updating Task assignment");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return insertCnt;		
	
	}
	
	

	public int unAssignTask(Task task) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int insertCnt = 0;
		try {
			String sql = "update task "
					+ " set status='Inactive' "
					+ " where task_id=:taskId";
			
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(task);
	        			
			
			insertCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while UNassigning Task assignment");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return insertCnt;		
	
	}
	
	public List<Task> getUserTask(Task task) throws TrackerException
	{

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Task> taskList = new ArrayList<>(1);
		try {
			String sql = "select task.*, user.last_name, user.first_name "
					+ " from task left outer join user on task.user_id = user.user_id "
					+ " where task.user_id = :userId and task.application_id=:applicationId "
					+ " and (task.start_date <= curdate() and task.end_date >= curdate()) "
					+ " and task.status in ('Active','In progress')";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(task);
	        	        			
			taskList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Task.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Developer Task");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
		return taskList;	
	}
	
	public List<Task> getUserMissedTask(Task task) throws TrackerException
	{

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Task> taskList = new ArrayList<>(1);
		try {
			String sql = "select task.*, user.last_name, user.first_name "
					+ " from task left outer join user on task.user_id = user.user_id "
					+ " where task.user_id = :userId and task.application_id=:applicationId "
					+ " and task.end_date < curdate() "
					+ " and task.status in ('Active','In progress')";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(task);
	        	        			
			taskList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Task.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Developer Task");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
		return taskList;	
	}
	
	public List<Task> getApplicationTask(int applicationId) throws TrackerException
	{

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Task> taskList = new ArrayList<>(1);
		try {
			String sql = "select task.*, user.last_name, user.first_name "
					+ " from task left outer join user on task.user_id = user.user_id "
					+ " where task.application_id=:applicationId "
					+ " and (task.start_date <= curdate() and task.end_date >= curdate()) "
					+ " and task.status in ('Active','In progress')";
			
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("applicationId", applicationId);
	        	        			
			taskList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Task.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Developer Task");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
		return taskList;	
	}
	
	public List<Task> getApplicationMissedTask(int applicationId) throws TrackerException
	{

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Task> taskList = new ArrayList<>(1);
		try {
			String sql = "select task.*, user.last_name, user.first_name "
					+ " from task left outer join user on task.user_id = user.user_id "
					+ " where task.application_id=:applicationId "
					+ " and task.end_date < curdate() "
					+ " and task.status in ('Active','In progress')";
						
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("applicationId", applicationId);
	        	        			
			taskList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Task.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Developer Task");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
		return taskList;	
	}
	
	private Map<String, String> getJoiner() {
		
		Map<String, String> joiner = new HashMap<String, String>();
		joiner.put("Task Id", "task.task_id");
		joiner.put("Last Name", "user.last_name");
		joiner.put("User Story", "schedule.user_story ");
		joiner.put("Start Date", "task.start_date");
		joiner.put("End Date", "task.end_date");
		joiner.put("Hours Of Work", "task.hours_of_work");
		joiner.put("Application Id", "task.application_id");
		return joiner;
	}

	
}

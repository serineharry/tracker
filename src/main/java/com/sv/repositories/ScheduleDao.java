package com.sv.repositories;

import java.util.List;

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
import com.sv.framework.TrackerDaoFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.vo.Resource;
import com.sv.vo.Schedule;

@Repository
public class ScheduleDao extends TrackerDaoFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public List<Schedule> getUserstorySchedules(int userstoryId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		List<Schedule> schedList = null;
		
		try {
			String sql = "select * from schedule where userstory_id=:userstoryId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("userstoryId", userstoryId);
	        	        			
			schedList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Schedule.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Userstory schedules");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return schedList;		
	
	}
	
	public Schedule addSchedule(Schedule sched) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "insert into schedule(userstory_id, phase, days, start_date, end_date, implementation_status, "
					+ " review_status, testing_status, comments)"
					+ " values (:userstoryId, :phase, :days, :startDate, :endDate, :implementationStatus, "
					+ " :reviewStatus, :testingStatus, :comments)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource param = new BeanPropertySqlParameterSource(sched);	        			
			
			int insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
	        if(insertCnt > 0)
	        {
	        	sched.setScheduleId(keyHolder.getKey().intValue());
	        }
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating Schedule");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return sched;		
	
	}
	
	public Schedule updateSchedule(Schedule sched) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "update schedule"
					+ "set userstory_id = :userstoryId, "
					+ " phase = :phase, "
					+ " days = :days, "
					+ " start_date = :startDate, "
					+ " end_date = :endDate, "
					+ " implementation_status = :implementationStatus, "
					+ " review_status = :reviewStatus, "
					+ " testing_status = :testingStatus, "
					+ " comments = :comments "
					+ "where schedule_id = :scheduleId";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(sched);	        			
			
			int updateCnt = getNamedParameterJdbcTemplate().update(sql, param);
			logger.debug("Update cnt: " + updateCnt);
			
	     } catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while updating Schedule");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return sched;		
	
	}
	
	
	public int deleteUserstorySchedules(int userstoryId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int deleteCnt = 0;
		try {
			String sql = "delete from schedule "
					+ " where userstory_id = :userstoryId ";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("userstoryId",userstoryId);
	        			
			deleteCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while deleting Userstory schedules");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return deleteCnt;		
	
	}
	
	public int deleteSchedules(int scheduleId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int deleteCnt = 0;
		try {
			String sql = "delete from schedule "
					+ " where schedule_id = :scheduleId ";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("scheduleId",scheduleId);
	        			
			deleteCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while deleting schedules");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return deleteCnt;		
	
	}
	
	public List<Resource> getScheduleResource(int scheduleId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		List<Resource> schedList = null;
		
		try {
			String sql = "select user.username, user.last_name, user.first_name, task.task_id, task.user_id, "
					+ " task.hours_of_work as hoursAssigned "
					+ " from task, user "
					+ " where task.user_id = user.user_id "
					+ " and task.status = 'Active' "
					+ " and task.schedule_id = :scheduleId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("scheduleId", scheduleId);
	        	        			
			schedList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Resource.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Userstories");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return schedList;		
	
	}




	

}

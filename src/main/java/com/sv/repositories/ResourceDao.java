package com.sv.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerDaoFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.vo.Resource;
import com.sv.vo.Schedule;

@Repository
public class ResourceDao extends TrackerDaoFactory {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<Resource> getApplicationUser(int applicationId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Resource> appUserList = new ArrayList<>(1);
		try {
			String sql = "select usr.* "
					+ " from user usr, application_user appuser "
					+ " where appuser.user_id = usr.user_id "
					+ " and appuser.application_id = :applicationId "
					+ " and usr.enabled = 1 "
					+ " and usr.account_non_expired = 1;";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("applicationId", applicationId);
	        	        			
			appUserList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Resource.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching SearchCriteria");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return appUserList;		
	
	}
	
		
	public List<Resource> getResourceAvailablity(Schedule schedule) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Resource> appUserList = new ArrayList<>(1);
		try {
			String sql = "select task.user_id, COALESCE(sum(workday(task.start_date, task.end_date) * hours_of_work),0)  as totalAvailableHrs "
					+ " from task "
					+ " where task.application_id = :applicationId "
					+ " and (start_date between :startDate and :endDate "
					+ " or end_date  between :startDate and :endDate) "
					+ " and task.status in ('Active','In progress') "
					+ " and COALESCE(user_id,0) > 0 "
					+ " group by task.user_id";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(schedule);
	        	        			
			appUserList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Resource.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Resource Availablity");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
		return appUserList;	
	}


	public Integer getNumOfWorkingDays(Date startDate, Date endDate) throws TrackerException {


		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		Integer days = null;
		try {
			String sql = "select workday(:startDate, :endDate) as days from dual";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("startDate", startDate);
			param.addValue("endDate", endDate);
				        	        			
			days = getNamedParameterJdbcTemplate().queryForObject(sql, param, Integer.class);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Resource Availablity");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
		return days;	
	
	}
	
	

}

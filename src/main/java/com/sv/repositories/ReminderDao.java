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
import com.sv.vo.Reminder;
import com.sv.vo.SearchCriteria;
import com.sv.vo.User;

@Repository
public class ReminderDao extends TrackerDaoFactory {
	

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<Reminder> searchReminder(List<SearchCriteria> searchCriteriaList) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Reminder> projList = new ArrayList<>(1);
		try {
			String sql = "select * from reminder rem ";
			
			sql = DynamicQueryGenerator.generateSearch(sql, getJoiner(), searchCriteriaList);
			
			SqlParameterSource param = TrackerMapSqlParameterSource.mapSearchCriteria(searchCriteriaList);
	        	        			
			projList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Reminder.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching SearchCriteria");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return projList;		
	
	}

	public Reminder getReminder(int reminderId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		Reminder remind = null;
		
		try {
			String sql = "select * from reminder where reminder_id=:reminderId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
	        	        			
			List<Reminder> reminderList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Reminder.class));
			
			if(reminderList != null && reminderList.size() > 0)
			{
				remind = reminderList.get(0);
			}
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Reminder");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return remind;		
	
	}
	
	public Reminder addReminder(Reminder reminder) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "insert into reminder(reminder_type, is_before, is_after, major_release, minor_release, days, phase, notify_owner,content)"
					+ "values (:reminderType, :isBefore, :isAfter, :majorRelease, :minorRelease, :days, :phase, :notifyOwner, :content)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource param = new BeanPropertySqlParameterSource(reminder);
	        			
			
			int insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
	        if(insertCnt > 0)
	        {
	        	reminder.setReminderId(keyHolder.getKey().intValue());
	        }
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating Reminder");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminder;		
	
	}

	public Reminder updateReminder(Reminder reminder) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "update reminder"
					+ " set reminder_type = :reminderType, "
					+ " is_before = :isBefore,"
					+ " is_after = :isAfter, "
					+ " major_release = :majorRelease, "
					+ " minor_release = :minorRelease, "
					+ " days = :days, "
					+ " phase = :phase, "
					+ " notify_owner = :notifyOwner, "
					+ " content = :content "
					+ " where reminder_id = :reminderId";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(reminder);
			int updateCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
			logger.debug("Update cnt: " + updateCnt);
	        
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while updating Reminder");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminder;		
	
	}
	
	public int deleteReminder(int reminderId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		int delCnt = 0;
		try {
			String sql = "delete from reminder where reminder_id=:reminderId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
			delCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
			logger.debug("Update cnt: " + delCnt);
	        
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while deleting Reminder");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return delCnt;		
	
	}
	
	public List<String> getReminderWeekdays(int reminderId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<String> reminderWeekdayList = null;
		
		try {
			String sql = "select weekday from reminder_weekday where reminder_id=:reminderId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
	        	        			
			reminderWeekdayList = getNamedParameterJdbcTemplate().queryForList(sql, param, String.class);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Reminder weekdays");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminderWeekdayList;		
	
	}
	
	public int addReminderWeekday(int reminderId, String weekDay) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int insertCnt = 0;
		
		try {
			String sql = "insert into reminder_weekday(reminder_id, weekday) "
					+ "values (:reminderId, :weekDay)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
			param.addValue("weekDay", weekDay);
	        			
			insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating Reminder weekdays");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return insertCnt;		
	
	}
	
	public int deleteAllReminderWeekday(int reminderId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int delCnt = 0;
		
		try {
			String sql = "delete from reminder_weekday where reminder_id = :reminderId";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
						
			delCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while deleting All Reminder weekdays");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return delCnt;		
	
	}
	
	public List<Integer> getReminderMonthdays(int reminderId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Integer> reminderMonthdayList = null;
		
		try {
			String sql = "select monthday from reminder_monthday where reminder_id=:reminderId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
	        	        			
			reminderMonthdayList = getNamedParameterJdbcTemplate().queryForList(sql, param, Integer.class);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Reminder monthdays");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminderMonthdayList;		
	
	}
	
	public int addReminderMonthday(int reminderId, Integer monthDay) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int insertCnt = 0;
		
		try {
			String sql = "insert into reminder_monthday(reminder_id, monthday) "
					+ "values (:reminderId, :monthDay)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
			param.addValue("monthDay", monthDay);
	        			
			insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating Reminder monthday");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return insertCnt;		
	
	}
	
	public int deleteAllReminderMonthday(int reminderId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int delCnt = 0;
		
		try {
			String sql = "delete from reminder_monthday where reminder_id = :reminderId";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
						
			delCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while deleting All Reminder weekdays");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return delCnt;		
	
	}
	
	public List<User> getReminderUserId(int reminderId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<User> reminderUserList = null;
		
		try {
			String sql = "select user.user_id, user.username, user.last_name, user.first_name, user.email_address "
					+ " from reminder_user remuser, user user "
					+ " where remuser.reminder_id=:reminderId "
					+ " and remuser.user_id = user.user_id";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
	        	        			
			reminderUserList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(User.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Reminder userid");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminderUserList;		
	
	}
	
	public int addReminderUserId(int reminderId, int userId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int insertCnt = 0;
		
		try {
			String sql = "insert into reminder_user(reminder_id, user_id) "
					+ "values (:reminderId, :userId)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
			param.addValue("userId", userId);
	        			
			insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating Reminder user info");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return insertCnt;		
	
	}
	
	public int deleteAllReminderUser(int reminderId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int delCnt = 0;
		
		try {
			String sql = "delete from reminder_user where reminder_id = :reminderId";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("reminderId", reminderId);
						
			delCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while deleting All Reminder user");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return delCnt;		
	
	}
	
	private Map<String, String> getJoiner() {
		Map<String, String> joiner = new HashMap<String, String>();
		joiner.put("Application Id", "rem.application_id");
		joiner.put("Type", "rem.release_type");
		joiner.put("Before?", "rem.is_before");
		joiner.put("After?", "rem.is_after");
		joiner.put("Major Release?", "rem.major_release");
		joiner.put("Minor Release?", "rem.minor_release");
		joiner.put("Days", "rem.days");
		joiner.put("Phase", "rem.phase");
		
		return joiner;
	}




}

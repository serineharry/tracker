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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.DynamicQueryGenerator;
import com.sv.framework.TrackerDaoFactory;
import com.sv.framework.TrackerMapSqlParameterSource;
import com.sv.framework.exceptions.TrackerException;
import com.sv.vo.SearchCriteria;
import com.sv.vo.User;

@Repository
public class UserDao extends TrackerDaoFactory {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public User findByUsername(String login) throws UsernameNotFoundException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		User user = null;
		try {
			String sql = "select * from user where username=:login";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("login",login);
	        			
			List<User> userList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(User.class));
			
			if(userList != null && userList.size() == 1)
			{
				user = userList.get(0);
			}
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new UsernameNotFoundException("Exception while fetching Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return user;		
	
	}
	
	public List<User> searchUser(List<SearchCriteria> searchCriteriaList) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<User> projList = new ArrayList<>(1);
		try {
			String sql = "select user.user_id, user.username, '' as password, user.last_name, user.first_name, user.email_address, "
					+ " user.enabled, user.account_non_expired, user.global_admin from user";
			
			sql = DynamicQueryGenerator.generateSearch(sql, getJoiner(), searchCriteriaList);
			
			SqlParameterSource param = TrackerMapSqlParameterSource.mapSearchCriteria(searchCriteriaList);
	        	        			
			projList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(User.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching SearchCriteria");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return projList;		
	
	}

	public User getUser(int userId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		User user = null;
		
		try {
			String sql = "select user.user_id, user.username, '' as password, user.last_name, user.first_name, "
					+ " user.email_address, user.enabled, user.account_non_expired, user.global_admin "
					+ " from user where user_id=:userId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("userId", userId);
	        	        			
			List<User> userList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(User.class));
			
			if(userList != null && userList.size() > 0)
			{
				user = userList.get(0);
			}
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching User");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return user;		
	
	}
	
	public User addUser(User user) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			
			String sql = "insert into user(username, password, last_name, first_name, email_address, enabled, global_admin)"
					+ "values (:username, :password, :lastName, :firstName, :emailAddress, :enabled, :globalAdmin)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource param = new BeanPropertySqlParameterSource(user);
	        			
			
			int insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
	        if(insertCnt > 0)
	        {
	        	user.setUserId(keyHolder.getKey().intValue());
	        	user.setPassword(null);
	        	user.setNewpassword(null);
	        }
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating User");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
		return user;		
	
	}

	public User updateUser(User user) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "update user "
					+ " set username = :username, "
					+ " password = COALESCE(:password, password), "
					+ " last_name = :lastName, "
					+ " first_name = :firstName, "
					+ " email_address = :emailAddress, "
					+ " enabled = :enabled, "
					+ " global_admin = :globalAdmin "
					+ " where user_id = :userId";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(user);
			int updateCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
			logger.debug("Update cnt: " + updateCnt);
	        
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while updating User");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return user;		
	
	}
	
	private Map<String, String> getJoiner() {
		Map<String, String> joiner = new HashMap<String, String>();
		joiner.put("username", "user.username");
		joiner.put("Last Name", "user.last_name");
		joiner.put("First Name", "user.first_name");
		joiner.put("email", "user.email_address");
		joiner.put("enabled", "user.enabled");
		return joiner;
	}
		
}

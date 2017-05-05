package com.sv.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.sv.vo.Application;
import com.sv.vo.Role;
import com.sv.vo.User;

@Repository
public class ApplicationDao extends TrackerDaoFactory {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<Application> getApplications(int userId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Application> applicationList = new ArrayList<>(1);
		try {
			String sql = "select * from application_user usr, application app "
					+ " where usr.user_id = :userId "
					+ " and usr.application_id = app.application_id";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("userId",userId);
	        			
			applicationList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Application.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return applicationList;		
	
	}

	public List<Role> getRoles(int userId, int applicationId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<Role> roleList = new ArrayList<>(1);
		try {
			String sql = "select * from applicationuser_role appuser, role "
					+ " where appuser.application_id = :applicationId "
					+ " and appuser.user_id = :userId "
					+ " and appuser.role_id = role.role_id;";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("userId",userId);
	        param.addValue("applicationId",applicationId);
	        			
	        roleList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Role.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return roleList;		
	
	}

	public Application getApplicationById(int applicationId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		Application application = null;
		
		try {
			String sql = "select * from application where application_id = :applicationId;";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("applicationId",applicationId);
	        			
	        List<Application> applicationList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Application.class));
	        if(applicationList != null && applicationList.size() > 0)
	        {
	        	application = applicationList.get(0);
	        }
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return application;		
	
	}
	
	public Application getApplicationByAcronymn(String appAcronymn) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		Application application = null;
		
		try {
			String sql = "select * from application where application_acronym = :appAcronymn;";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("appAcronymn",appAcronymn);
	        			
	        List<Application> applicationList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Application.class));
	        if(applicationList != null && applicationList.size() > 0)
	        {
	        	application = applicationList.get(0);
	        }
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return application;		
	
	}

	public Application addApplication(Application app) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "insert into application(application_acronym, application_name, working_hours) values (:applicationAcronym, :applicationName, :workingHours)";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource param = new BeanPropertySqlParameterSource(app);
	        			
			
			int insertCnt = getNamedParameterJdbcTemplate().update(sql, param, keyHolder);
			
	        if(insertCnt > 0)
	        {
	        	app.setApplicationId(keyHolder.getKey().intValue());
	        }
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while creating Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return app;		
	
	}

	public Application updateApplication(Application app) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			String sql = "update application "
					+ " set application_acronym=:applicationAcronym, "
					+ " application_name=:applicationName, "
					+ " working_hours=:workingHours"
					+ " where application_id=:applicationId";
			
			SqlParameterSource param = new BeanPropertySqlParameterSource(app);
			int updateCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
			logger.debug("Update cnt: " + updateCnt);
	        
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while updating Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return app;		
	
	}
	
	public List<User> getApplicationUsers(int applicationId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<User> userList = new ArrayList<User>(1);
		
		try {
			String sql = "select usr.* from application_user appuser, user usr "
					+ " where usr.user_id = appuser.user_id "
					+ " and appuser.application_id = :applicationId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("applicationId",applicationId);
	        			
	        userList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(User.class));
	       
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Application users");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return userList;		
	
	}

	public List<User> getUsers(Set<String> attUidSet) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<User> userList = new ArrayList<User>(1);
		
		try {
			String sql = "select * from user where username in (:attUids)";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("attUids",attUidSet);
	        			
	        userList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(User.class));
	       
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Users");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return userList;		
	
	}
	
	public List<Role> getAllRoles() throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Role> roleList = new ArrayList<Role>(1);
		
		try {
			String sql = "select * from role";			
						
	        roleList = getNamedParameterJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Role.class));	       
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Roles");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return roleList;		
	
	}
	
	public int addApplicationUser(int applicationId, int userId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int insertCnt = 0;
		try {
			String sql = "insert into application_user(application_id, user_id) "
					+ " values (:applicationId, :userId)";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("applicationId",applicationId);
	        param.addValue("userId",userId);
	        			
			
			insertCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while Assigning Users for Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return insertCnt;		
	
	}
	
	public int addApplicationUserRole(int applicationId, int userId, int roleId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int insertCnt = 0;
		try {
			String sql = "insert into applicationuser_role(application_id, user_id, role_id) "
					+ " values (:applicationId, :userId, :roleId)";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("applicationId",applicationId);
	        param.addValue("userId",userId);
	        param.addValue("roleId",roleId);
	        			
			
			insertCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while Assigning Users Role for Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return insertCnt;		
	
	}

	public int deleteApplicationUser(int applicationId, int userId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int deleteCnt = 0;
		try {
			String sql = "delete from application_user "
					+ " where application_id = :applicationId "
					+ " and user_id = :userId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("applicationId",applicationId);
	        param.addValue("userId",userId);
	        			
			deleteCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while unAssigning Users for Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return deleteCnt;		
	
	}
	
	public int deleteApplicationUserAllRole(int applicationId, int userId) throws TrackerException {


		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int deleteCnt = 0;
		try {
			String sql = "delete from applicationuser_role "
					+ " where application_id = :applicationId "
					+ " and user_id = :userId";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("applicationId",applicationId);
	        param.addValue("userId",userId);
	        			
			deleteCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while unAssigning All roles for applicaiton User");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return deleteCnt;		
	
	
	}

	public int deleteApplicationUserRole(int applicationId, int userId, int roleId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		int deleteCnt = 0;
		try {
			String sql = "delete from applicationuser_role "
					+ " where application_id = :applicationId"
					+ " and user_id = :userId "
					+ " and role_id = :roleId ";
								
			MapSqlParameterSource param = new MapSqlParameterSource();
	        param.addValue("applicationId",applicationId);
	        param.addValue("userId",userId);
	        param.addValue("roleId",roleId);
	        			
			deleteCnt = getNamedParameterJdbcTemplate().update(sql, param);
			
	
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while Assigning Users Role for Application");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return deleteCnt;		
	
	}

	public List<Role> getRolesForSelectedApplicationAndUser(int userId, int applicationId) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Role> roleList = new ArrayList<Role>(1);
		
		try {
			String sql = "select role.* from applicationuser_role approle, role "
					+ " where approle.application_id = :applicationId "
					+ " and approle.user_id  = :userId "
					+ " and approle.role_id = role.role_id";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("userId", userId);
			param.addValue("applicationId", applicationId);
						
	        roleList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(Role.class));	       
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching Roles");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return roleList;		
	
	}
}

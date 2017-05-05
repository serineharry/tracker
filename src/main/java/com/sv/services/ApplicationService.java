package com.sv.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerServiceFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.repositories.ApplicationDao;
import com.sv.security.TokenBasedAuthentication;
import com.sv.vo.Application;
import com.sv.vo.RequestPk;
import com.sv.vo.Role;
import com.sv.vo.User;

@Service
@Transactional
public class ApplicationService extends TrackerServiceFactory {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationDao appDao;
	
	public List<Application> getApplications(int userId) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Application> apps = null;
		
		try {
		
			apps = appDao.getApplications(userId);
			
			if(apps != null)
			{
				for(Application app : apps)
				{
					List<Role> roles = appDao.getRoles(userId, app.getApplicationId());
					app.setRoles(roles);
				}
			}
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return apps;
				
	}

	public Application getApplicationByAcronymn(String appAcronymn) throws TrackerException {
		

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
	
		Application app = null;
		try {
			app = appDao.getApplicationByAcronymn(appAcronymn);	
		} finally {

			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		}		
		return app;
	}

	public Application addApplication(Application app) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			app = appDao.addApplication(app);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return app;
	}

	public Application updateApplication(Application app) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			app = appDao.updateApplication(app);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return app;
	}
	
	public List<User> getApplicationUsers(int applicationId) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<User> users = null;
		
		try {
		
			users = appDao.getApplicationUsers(applicationId);
			
			if(users != null)
			{
				for(User app : users)
				{
					List<Role> roles = appDao.getRoles(app.getUserId(), applicationId);
					app.setRoles(roles);
				}
			}
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return users;
				
	}

	public List<User> getUsers(String attUids) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<User> users = null;
		
		try {
			
			if(attUids != null)
			{
				String attUid[] = attUids.split(",");
				
				if(attUid != null)
				{
					Arrays.parallelSetAll(attUid, (i) -> attUid[i].trim());
					Set<String> attUidSet = new HashSet<String>(Arrays.asList(attUid));
					
					users = appDao.getUsers(attUidSet);					
				}
			}
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return users;			
	
	}
	
	public List<Role> getAllRoles() throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Role> roles = null;
		
		try {
			
			roles = appDao.getAllRoles();					
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return roles;			
	
	}
	
	public void assignUserAndRoles(Application app) throws TrackerException
	{

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		
		try {
			
			int appId = app.getApplicationId();
			for (User user : app.getUsers())
			{
				if(user != null)
				{
					int userId = user.getUserId();
					int insCnt = appDao.addApplicationUser(appId, userId);
					
					if(insCnt > 0)
					{
						if(user.getRoles() != null)
						{
							for(Role role : user.getRoles())
							{
								if(role != null)
								{
									int roleId = role.getRoleId();
									appDao.addApplicationUserRole(appId, userId, roleId);
								}
								
							}
						}
						
					}
				}
			}
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
				
	}

	public void unAssignUserAndRoles(Application app) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
				
		try {
			
			int appId = app.getApplicationId();
			for (User user : app.getUsers())
			{
				if(user != null)
				{
					int userId = user.getUserId();
					int insCnt = appDao.deleteApplicationUser(appId, userId);
					if(insCnt > 0)
					{
						appDao.deleteApplicationUserAllRole(appId, userId);
					}
				}
			}
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
				
	}

	public void unAssignRole(Application app) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
				
		try {
			
			int appId = app.getApplicationId();
			for (User user : app.getUsers())
			{
				if(user != null)
				{
					int userId = user.getUserId();

					for(Role role : user.getRoles())
					{
						int roleId = role.getRoleId();
						appDao.deleteApplicationUserRole(appId, userId, roleId);
					}
				
				}
			}
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
				
	}

	public void assignRole(Application app) throws TrackerException {

		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
				
		try {
			
			int appId = app.getApplicationId();
			for (User user : app.getUsers())
			{
				if(user != null)
				{
					int userId = user.getUserId();

					for(Role role : user.getRoles())
					{
						int roleId = role.getRoleId();
						appDao.addApplicationUserRole(appId, userId, roleId);
					}
				
				}
			}
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
				
	}

	public List<Role> getRolesForSelectedApplicationAndUser(RequestPk reqPk) throws TrackerException {


		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		List<Role> roles = null;	
		try {
			
			
			roles = appDao.getRolesForSelectedApplicationAndUser(reqPk.getUserId(), reqPk.getApplicationId());
			
			// Add Company Roles to server security context
			Authentication currAuth = SecurityContextHolder.getContext().getAuthentication();
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(currAuth.getAuthorities());
			authorities.add(new SimpleGrantedAuthority("DEFAULT"));
			
			for(Role role : roles)
			{
				authorities.add(new SimpleGrantedAuthority(role.getRole()));
			}
			Authentication newAuth = new TokenBasedAuthentication((UserDetails)currAuth.getPrincipal(), (String)currAuth.getCredentials(), authorities);
            newAuth.setAuthenticated( true );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return roles;
				
	
	}
}

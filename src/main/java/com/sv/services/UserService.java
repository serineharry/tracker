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
import com.sv.repositories.UserDao;
import com.sv.security.CryptUtil;
import com.sv.vo.User;
import com.sv.vo.SearchCriteria;

@Service
@Transactional
public class UserService extends TrackerServiceFactory{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;

	public List<User> searchUser(List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<User> userList = null;
		
		try {		
			userList = userDao.searchUser(searchCriteriaList);					
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return userList;
				
	}

	public User getUser(int userId) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		User user = null;
		
		try {		
			user = userDao.getUser(userId);			
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return user;
				
	}
	
	public User getUserByName(String userName) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		User user = null;
		
		try {		
			user = userDao.findByUsername(userName);
			
			if(user != null)
			{
				user.setPassword(null);
			}
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return user;
				
	}
	
	public User addUser(User user) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			user.setPassword(CryptUtil.encrypt(user.getPassword()));
			user = userDao.addUser(user);	
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return user;
	}

	public User updateUser(User user) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			
			if(user.getPassword() != null)
			{
				User dbUser = userDao.findByUsername(user.getUsername());
				
				if(dbUser == null)
				{
					throw new TrackerException("1000", "Unable to find Username, Hacker ?!!!");
				}else if (!CryptUtil.decrypt(dbUser.getPassword()).equals(user.getPassword()))
				{
					throw new TrackerException("1000", "Please enter valid current password");
				}
				
				user.setPassword(CryptUtil.encrypt(user.getNewpassword()));				
			}			
			
			user = userDao.updateUser(user);
			
			user.setPassword(null);
			user.setNewpassword(null);
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return user;
	}

}

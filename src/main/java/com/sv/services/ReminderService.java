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
import com.sv.repositories.ReminderDao;
import com.sv.vo.Reminder;
import com.sv.vo.SearchCriteria;
import com.sv.vo.User;

@Service
@Transactional
public class ReminderService extends TrackerServiceFactory{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ReminderDao reminderDao;

	public List<Reminder> searchReminder(List<SearchCriteria> searchCriteriaList) throws TrackerException
	{
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		List<Reminder> reminderList = null;
		
		try {		
			reminderList = reminderDao.searchReminder(searchCriteriaList);
			
			for(Reminder reminder : reminderList)
			{
				if(reminder != null)
				{
					if("Week".equalsIgnoreCase(reminder.getReminderType()))
					{
						reminder.setWeekdays(reminderDao.getReminderWeekdays(reminder.getReminderId()));
						
					}else if ("Month".equalsIgnoreCase(reminder.getReminderType())){
						reminder.setMonthdays(reminderDao.getReminderMonthdays(reminder.getReminderId()));
					}
					
					reminder.setUsers(reminderDao.getReminderUserId(reminder.getReminderId()));
					
				}
			}
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminderList;
				
	}

	public Reminder getReminder(int reminderId) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		Reminder reminder = null;
		
		try {		
			reminder = reminderDao.getReminder(reminderId);
			
			if(reminder != null)
			{
				if("Week".equalsIgnoreCase(reminder.getReminderType()))
				{
					reminder.setWeekdays(reminderDao.getReminderWeekdays(reminderId));
					
				}else if ("Month".equalsIgnoreCase(reminder.getReminderType())){
					reminder.setMonthdays(reminderDao.getReminderMonthdays(reminderId));
				}
				
				reminder.setUsers(reminderDao.getReminderUserId(reminderId));
				
			}
						
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminder;
				
	}
	
	public Reminder addReminder(Reminder reminder) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			reminder = reminderDao.addReminder(reminder);
			
			if(reminder != null && reminder.getReminderId() > 0)
			{
				if("Week".equalsIgnoreCase(reminder.getReminderType()))
				{
					for(String weekday : reminder.getWeekdays())
					{
						reminderDao.addReminderWeekday(reminder.getReminderId(), weekday);	
					}					
				}else if("Month".equalsIgnoreCase(reminder.getReminderType()))
				{
					for(Integer monthday : reminder.getMonthdays())
					{
						reminderDao.addReminderMonthday(reminder.getReminderId(), monthday);	
					}
				}
				
				for(User user : reminder.getUsers())
				{
					if(user != null && user.getUserId() > 0)
					{
						reminderDao.addReminderUserId(reminder.getReminderId(), user.getUserId());
					}
				}
			}
			
			
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminder;
	}

	public Reminder updateReminder(Reminder reminder) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		
		try {
			reminder = reminderDao.updateReminder(reminder);
			
			// delete and recreate if needed
			reminderDao.deleteAllReminderWeekday(reminder.getReminderId());
			reminderDao.deleteAllReminderMonthday(reminder.getReminderId());
			reminderDao.deleteAllReminderUser(reminder.getReminderId());
			
			if("Week".equalsIgnoreCase(reminder.getReminderType()))
			{
				for(String weekday : reminder.getWeekdays())
				{
					reminderDao.addReminderWeekday(reminder.getReminderId(), weekday);	
				}					
			}else if("Month".equalsIgnoreCase(reminder.getReminderType()))
			{
				for(Integer monthday : reminder.getMonthdays())
				{
					reminderDao.addReminderMonthday(reminder.getReminderId(), monthday);	
				}
			}
			
			for(User user : reminder.getUsers())
			{
				if(user != null && user.getUserId() > 0)
				{
					reminderDao.addReminderUserId(reminder.getReminderId(), user.getUserId());
				}
			}
		
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return reminder;
	}
	
	public int deleteReminder(int reminderId) throws TrackerException {
		
		logger.info(ApplicationConstants.LOG_ENTRY_MESSAGE);
		int delCnt = 0;
		try {
			
			reminderDao.deleteAllReminderWeekday(reminderId);
			reminderDao.deleteAllReminderMonthday(reminderId);
			reminderDao.deleteAllReminderUser(reminderId);
			
			delCnt = reminderDao.deleteReminder(reminderId);
			
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}		
		return delCnt;
	}

}

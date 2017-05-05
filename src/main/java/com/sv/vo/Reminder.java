package com.sv.vo;

import java.util.List;

public class Reminder {
	
	private int reminderId;
	private int applicationId;
	private String reminderType;
	private Byte isBefore;
	private Byte isAfter;
	private Byte majorRelease;
	private Byte minorRelease;
	private Integer days;
	private List<String> weekdays;
	private List<Integer> monthdays;
	private String phase;
	private Byte notifyOwner;
	private List<User> users;
	private String content;
	
	public int getReminderId() {
		return reminderId;
	}
	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getReminderType() {
		return reminderType;
	}
	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}
	public Byte getIsBefore() {
		return isBefore;
	}
	public void setIsBefore(Byte isBefore) {
		this.isBefore = isBefore;
	}
	public Byte getIsAfter() {
		return isAfter;
	}
	public void setIsAfter(Byte isAfter) {
		this.isAfter = isAfter;
	}
	public Byte getMajorRelease() {
		return majorRelease;
	}
	public void setMajorRelease(Byte majorRelease) {
		this.majorRelease = majorRelease;
	}
	public Byte getMinorRelease() {
		return minorRelease;
	}
	public void setMinorRelease(Byte minorRelease) {
		this.minorRelease = minorRelease;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public List<String> getWeekdays() {
		return weekdays;
	}
	public void setWeekdays(List<String> weekdays) {
		this.weekdays = weekdays;
	}
	public List<Integer> getMonthdays() {
		return monthdays;
	}
	public void setMonthdays(List<Integer> monthdays) {
		this.monthdays = monthdays;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public Byte getNotifyOwner() {
		return notifyOwner;
	}
	public void setNotifyOwner(Byte notifyOwner) {
		this.notifyOwner = notifyOwner;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
		
}

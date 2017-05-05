package com.sv.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Task {

	private int taskId;
	private Integer userId;
	private int applicationId;
	private Integer scheduleId;
	
	private String taskName;
	private String taskDesc;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	private Double hoursOfWork;
	private String status;
	private Integer progress; 
	private Integer reviewProgress;
	private Integer otherProgress;
	
	private String comment;
	
	private String username;
	private String lastName;
	private String firstName;
	private String userstory;
	private String projectUid;
	private String release;
	
	
		
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getHoursOfWork() {
		return hoursOfWork;
	}
	public void setHoursOfWork(Double hoursOfWork) {
		this.hoursOfWork = hoursOfWork;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public Integer getReviewProgress() {
		return reviewProgress;
	}
	public void setReviewProgress(Integer reviewProgress) {
		this.reviewProgress = reviewProgress;
	}
	public Integer getOtherProgress() {
		return otherProgress;
	}
	public void setOtherProgress(Integer otherProgress) {
		this.otherProgress = otherProgress;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getUserstory() {
		return userstory;
	}
	public void setUserstory(String userstory) {
		this.userstory = userstory;
	}
	public String getProjectUid() {
		return projectUid;
	}
	public void setProjectUid(String projectUid) {
		this.projectUid = projectUid;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	


}
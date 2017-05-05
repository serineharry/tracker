package com.sv.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Userstory {
	
	private int userstoryId;
	private int projectId;
	private int applicationId;
	private String userstory;
	private String userstoryDesc;
	private String interfaces;
	private String dependencies;
	
	private List<Schedule> schedules;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date externalDate;
	private String comments;
	
	public int getUserstoryId() {
		return userstoryId;
	}
	public void setUserstoryId(int userstoryId) {
		this.userstoryId = userstoryId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getUserstory() {
		return userstory;
	}
	public void setUserstory(String userstory) {
		this.userstory = userstory;
	}
	public String getUserstoryDesc() {
		return userstoryDesc;
	}
	public void setUserstoryDesc(String userstoryDesc) {
		this.userstoryDesc = userstoryDesc;
	}
	public String getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(String interfaces) {
		this.interfaces = interfaces;
	}
	public String getDependencies() {
		return dependencies;
	}
	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}
	public Date getExternalDate() {
		return externalDate;
	}
	public void setExternalDate(Date externalDate) {
		this.externalDate = externalDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<Schedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	
		
}

package com.sv.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Project {

	private int projectId;
	private String projectUid;
	private String projectName;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	private String releaseInfo;

	private String projectType;
	
	private int applicationId;
	private String projectStatus;
	private Double effort;
	private String tier2;
	private String primarySe;
	private String primaryDeveloper;
	private String primaryTester;
	private String manager;
	private String leadManager;
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectUid() {
		return projectUid;
	}
	public void setProjectUid(String projectUid) {
		this.projectUid = projectUid;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public String getReleaseInfo() {
		return releaseInfo;
	}
	public void setReleaseInfo(String releaseInfo) {
		this.releaseInfo = releaseInfo;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public Double getEffort() {
		return effort;
	}
	public void setEffort(Double effort) {
		this.effort = effort;
	}
	public String getTier2() {
		return tier2;
	}
	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}
	public String getPrimarySe() {
		return primarySe;
	}
	public void setPrimarySe(String primarySe) {
		this.primarySe = primarySe;
	}
	public String getPrimaryDeveloper() {
		return primaryDeveloper;
	}
	public void setPrimaryDeveloper(String primaryDeveloper) {
		this.primaryDeveloper = primaryDeveloper;
	}
	public String getPrimaryTester() {
		return primaryTester;
	}
	public void setPrimaryTester(String primaryTester) {
		this.primaryTester = primaryTester;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getLeadManager() {
		return leadManager;
	}
	public void setLeadManager(String leadManager) {
		this.leadManager = leadManager;
	}
	
	// private ProjectType projectType;
	// private Release1 release1;

		
	
}

package com.sv.vo;

import java.io.Serializable;


public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;

	private int userId;
	private byte accountNonExpired;
	private byte enabled;
	private String firstName;

	private String lastName;
	private String password;
	private String username;
	
	private Double totalAvailableHrs;
	private Double perDayAvailableHrs;
	private Double hoursAssigned;
	
	private String mode;
	private Integer taskId;
	
	// used on userstory
	private Integer progress;
	private Integer reviewProgress;
	private Integer otherProgress;
	
		
	
	public Resource() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(byte accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public byte getEnabled() {
		return enabled;
	}

	public void setEnabled(byte enabled) {
		this.enabled = enabled;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getTotalAvailableHrs() {
		return totalAvailableHrs;
	}

	public void setTotalAvailableHrs(Double totalAvailableHrs) {
		this.totalAvailableHrs = totalAvailableHrs;
	}

	public Double getPerDayAvailableHrs() {
		return perDayAvailableHrs;
	}

	public void setPerDayAvailableHrs(Double perDayAvailableHrs) {
		this.perDayAvailableHrs = perDayAvailableHrs;
	}

	public Double getHoursAssigned() {
		return hoursAssigned;
	}

	public void setHoursAssigned(Double hoursAssigned) {
		this.hoursAssigned = hoursAssigned;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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
}
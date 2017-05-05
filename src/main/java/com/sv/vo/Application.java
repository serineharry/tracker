package com.sv.vo;

import java.io
.Serializable;
import java.util.List;


public class Application implements Serializable {

	private static final long serialVersionUID = 1L;

	private int applicationId;
	private String applicationAcronym;
	private String applicationName;
	private Double workingHours;

	private List<Role> roles;
	private List<User> users;

	public Application() {
	}

	public int getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationAcronym() {
		return this.applicationAcronym;
	}

	public void setApplicationAcronym(String applicationAcronym) {
		this.applicationAcronym = applicationAcronym;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Double getWorkingHours() {
		return this.workingHours;
	}

	public void setWorkingHours(Double workingHours) {
		this.workingHours = workingHours;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}



}
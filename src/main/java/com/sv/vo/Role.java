package com.sv.vo;

import java.io.Serializable;


public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int roleId;
	private String role;

	public Role() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
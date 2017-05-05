package com.sv.vo;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int userId;
	private byte accountNonExpired;
	private byte enabled;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String password;
	private String newpassword;
	private String username;
	private byte globalAdmin;
	private List<Role> roles;

	public User() {
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte getGlobalAdmin() {
		return globalAdmin;
	}

	public void setGlobalAdmin(byte globalAdmin) {
		this.globalAdmin = globalAdmin;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
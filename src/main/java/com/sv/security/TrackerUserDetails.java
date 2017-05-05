package com.sv.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.sv.vo.User;

public class TrackerUserDetails extends org.springframework.security.core.userdetails.User{

	private static final long serialVersionUID = 1L;

	private User user = null;
	
	public TrackerUserDetails(String login, String password, 
			Collection<? extends GrantedAuthority> authorities, User trackerUser) {
		super(login, password, authorities);
		this.user = trackerUser;
		this.user.setPassword(null);
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}

package com.sv.security;

import com.sv.vo.User;

/**
 * Created by fan.jin on 2016-10-17.
 */
public class TokenState {
    private String accessToken;
    private long expiresIn;
    private User user;

    public TokenState(String access_token, long expires_in, User user) {
        this.accessToken = access_token;
        this.expiresIn = expires_in;
        this.user = user;
    }

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
}
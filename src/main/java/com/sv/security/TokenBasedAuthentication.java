package com.sv.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Created by fan.jin on 2016-11-11.
 */
public class TokenBasedAuthentication extends AbstractAuthenticationToken {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
    private final UserDetails principle;

    /*public TokenBasedAuthentication( UserDetails principle ) {
        super( principle.getAuthorities() );
        this.principle = principle;
    }*/
    public TokenBasedAuthentication( UserDetails principle, String credential, Collection<? extends GrantedAuthority> authorities) {
        super( authorities );
        this.principle = principle;
        this.token = credential;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return principle;
    }

}

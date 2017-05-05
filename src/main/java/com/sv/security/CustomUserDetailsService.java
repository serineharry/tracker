package com.sv.security;

import java.
util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sv.repositories.UserDao;
import com.sv.vo.User;

@Service
//@Transactional
public class CustomUserDetailsService implements UserDetailsService 	{


	@Autowired
	UserDao userRepo;

	private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	
	@Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        User user = userRepo.findByUsername(login);
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        }/* else if (!user.getEnabled()) {
            throw new UserNotEnabledException("User " + login + " was not enabled");
        }*/

        /*Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Authority authority : user.getTableAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }*/
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("DEFAULT"));        

        return new TrackerUserDetails(login, CryptUtil.decrypt(user.getPassword()), grantedAuthorities, user);
    }
	
}

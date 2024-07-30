package com.hitices.storage.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

@Component
public class InMemoryUserDetailsService implements UserDetailsService {
	private Map<String, User> users = new HashMap<>();
	
	public InMemoryUserDetailsService() {
	}
	
	
	@PostConstruct
	private void init() {
		this.users = new HashMap<>();
		users.put("admin", new User("admin", new BCryptPasswordEncoder().encode("password"),new ArrayList< GrantedAuthority >(0)));
		users.put("pm1", new User("pm1", new BCryptPasswordEncoder().encode("password"), new ArrayList< GrantedAuthority >(0)));
		users.put("pm2", new User("pm2", new BCryptPasswordEncoder().encode("password"), new ArrayList< GrantedAuthority >(0)));
		users.put("dev1", new User("dev1", new BCryptPasswordEncoder().encode("password"), new ArrayList< GrantedAuthority >(0)));
		users.put("dev2", new User("dev2", new BCryptPasswordEncoder().encode("password"), new ArrayList< GrantedAuthority >(0)));
		users.put("test1", new User("test1", new BCryptPasswordEncoder().encode("password"), new ArrayList< GrantedAuthority >(0)));
		users.put("test2",new User("test2", new BCryptPasswordEncoder().encode("password"), new ArrayList< GrantedAuthority >(0)));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = users.get(username.toLowerCase());

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return user;
	}


}

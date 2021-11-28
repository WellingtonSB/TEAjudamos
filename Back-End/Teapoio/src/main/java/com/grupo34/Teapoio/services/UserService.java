package com.grupo34.Teapoio.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.grupo34.Teapoio.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
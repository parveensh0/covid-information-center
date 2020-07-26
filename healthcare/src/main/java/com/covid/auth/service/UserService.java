package com.covid.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.covid.auth.model.User;
import com.covid.auth.validator.UserForm;

/**
 * Extended support for user detail service.
 *
 */
public interface UserService extends UserDetailsService {

	User findByEmail(String email);

	User save(UserForm registration);
}

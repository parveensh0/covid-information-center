package com.covid.auth.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.covid.auth.model.Role;
import com.covid.auth.model.User;
import com.covid.auth.repository.UserRepository;
import com.covid.auth.validator.UserForm;

/**
 * Service class to manage operations on User.
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Verify user email exists or not and based on that load user configuration
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRoleToAuthorities(user.getRoles()));
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Persists a new user entity
	 *
	 * @param registerUser Validated UserForm object.
	 */
	@Override
	public User save(UserForm registerUser) {
		User user = new User();
		user.setFirstname(registerUser.getFirstname());
		user.setLastname(registerUser.getLastname());
		user.setEmail(registerUser.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
		user.setRoles(registerUser.getRoles());
		return userRepository.save(user);
	}

	private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
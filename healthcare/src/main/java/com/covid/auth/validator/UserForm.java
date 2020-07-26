package com.covid.auth.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.covid.auth.model.Role;

/**
 * This class will manage the user registration input fields and validates form
 * input.
 *
 */
public class UserForm {

	@NotEmpty
	private String firstname;

	@NotEmpty
	private String lastname;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Size(min = 8, max = 15, message = "Password must be between 8 to 15 characters")
	private String password;

	private List<Role> roles = Arrays.asList(new Role("ROLE_USER"));

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}

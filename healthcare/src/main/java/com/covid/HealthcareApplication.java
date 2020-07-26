package com.covid;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.covid.auth.model.Role;
import com.covid.auth.model.User;
import com.covid.auth.service.UserService;
import com.covid.auth.validator.UserForm;

@SpringBootApplication
public class HealthcareApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(HealthcareApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User userExists = userService.findByEmail("admin@covid.com");
		if (userExists == null) {
			UserForm user = new UserForm();
			user.setFirstname("admin");
			user.setLastname("admin");
			user.setEmail("admin@covid.com");
			user.setPassword("admin@admin");
			user.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
			userService.save(user);
		}
	}
}

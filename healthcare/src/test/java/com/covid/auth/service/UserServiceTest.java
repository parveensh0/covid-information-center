package com.covid.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.covid.auth.model.Role;
import com.covid.auth.model.User;
import com.covid.auth.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;

	@MockBean
	UserRepository userRepository;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private User user;

	@BeforeEach
	public void setUp() {
		user = new User();
		user.setFirstname("a");
		user.setLastname("a");
		user.setEmail("user@covidinformation.com");
		user.setPassword(bCryptPasswordEncoder().encode("sample"));
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));

		when(userRepository.save(user)).thenReturn(user);
	}

	@Test
	public void testNewUserIsCreatedSuccessfully() {

		User dbUser = userRepository.save(user);

		assertThat(dbUser).isNotNull();
		assertThat(user.getFirstname()).isEqualTo(dbUser.getFirstname());
		assertThat(user.getLastname()).isEqualTo(dbUser.getLastname());
		assertThat(user.getEmail()).isEqualTo(dbUser.getEmail());
		assertThat(user.getRoles()).isEqualTo(dbUser.getRoles());
	}

	@Test
	public void testExistingUserIsFetchedSuccessfulyByEmail() {

		when(userRepository.findByEmail("user@covidinformation.com")).thenReturn(user);

		userRepository.save(user);
		UserDetails useDetails = userService.loadUserByUsername("user@covidinformation.com");

		assertThat(useDetails).isNotNull();
		assertThat(user.getEmail()).isEqualTo(useDetails.getUsername());
		assertThat(useDetails.getAuthorities()).isNotEmpty();
	}

	@Test
	public void verifyExceptionIsThrownIfUserDoesNotExists() {
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername("superuser@covidinformation.com");
		});
	}
}
package com.covid.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.covid.auth.model.User;

/*
 * Extended class to support additional methods on User table
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}

package com.covid.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.covid.auth.model.Role;

/**
 * Presently not in use but will be used in future implementation.
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
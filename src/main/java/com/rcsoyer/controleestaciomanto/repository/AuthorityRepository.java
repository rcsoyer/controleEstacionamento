package com.rcsoyer.controleestaciomanto.repository;

import com.rcsoyer.controleestaciomanto.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

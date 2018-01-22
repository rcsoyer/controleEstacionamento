package com.rcsoyer.controleestaciomanto.repository;

import com.rcsoyer.controleestaciomanto.domain.Patio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Patio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatioRepository extends JpaRepository<Patio, Long> {

}

package com.rcsoyer.controleestaciomanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rcsoyer.controleestaciomanto.domain.Patio;


/**
 * Spring Data JPA repository for the Patio entity.
 */
@Repository
public interface PatioRepository extends JpaRepository<Patio, Long> {

}

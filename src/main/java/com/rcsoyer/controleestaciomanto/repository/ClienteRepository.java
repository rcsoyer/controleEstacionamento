package com.rcsoyer.controleestaciomanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rcsoyer.controleestaciomanto.domain.Cliente;


/**
 * Spring Data JPA repository for the Cliente entity.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}

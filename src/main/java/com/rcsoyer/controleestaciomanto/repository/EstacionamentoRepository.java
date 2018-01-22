package com.rcsoyer.controleestaciomanto.repository;

import com.rcsoyer.controleestaciomanto.domain.Estacionamento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Estacionamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {

}

package com.rcsoyer.controleestaciomanto.repository;

import com.rcsoyer.controleestaciomanto.domain.Veiculo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Veiculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}

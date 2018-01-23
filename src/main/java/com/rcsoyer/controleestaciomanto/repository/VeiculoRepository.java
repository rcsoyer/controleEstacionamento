package com.rcsoyer.controleestaciomanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rcsoyer.controleestaciomanto.domain.Veiculo;


/**
 * Spring Data JPA repository for the Veiculo entity.
 */
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}

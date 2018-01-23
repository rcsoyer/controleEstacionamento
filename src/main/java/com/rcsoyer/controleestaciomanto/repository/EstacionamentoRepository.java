package com.rcsoyer.controleestaciomanto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.rcsoyer.controleestaciomanto.domain.Estacionamento;


/**
 * Spring Data JPA repository for the Estacionamento entity.
 */
@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {

  @Query(value = "SELECT * FROM estacionamento WHERE patio_id IN (?) AND vlr_pagamento IS NULL",
      nativeQuery = true)
  List<Estacionamento> getVagasDisponiveis(List<Long> idsPatios);
}

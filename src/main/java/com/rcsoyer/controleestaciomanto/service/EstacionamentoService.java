package com.rcsoyer.controleestaciomanto.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rcsoyer.controleestaciomanto.service.dto.EstacionamentoDTO;

/**
 * Service Interface for managing Estacionamento.
 */
public interface EstacionamentoService {

  /**
   * Save a estacionamento.
   *
   * @param estacionamentoDTO the entity to save
   * @return the persisted entity
   */
  EstacionamentoDTO save(EstacionamentoDTO estacionamentoDTO);

  /**
   * Get all the estacionamentos.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<EstacionamentoDTO> findAll(Pageable pageable);

  /**
   * Get the "id" estacionamento.
   *
   * @param id the id of the entity
   * @return the entity
   */
  EstacionamentoDTO findOne(Long id);

  /**
   * Delete the "id" estacionamento.
   *
   * @param id the id of the entity
   */
  void delete(Long id);

  EstacionamentoDTO getPgCalculado(Long id);

  void pagarEstacionamento(EstacionamentoDTO dto);
}

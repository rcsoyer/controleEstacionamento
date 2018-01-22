package com.rcsoyer.controleestaciomanto.service;

import com.rcsoyer.controleestaciomanto.service.dto.VeiculoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Veiculo.
 */
public interface VeiculoService {

    /**
     * Save a veiculo.
     *
     * @param veiculoDTO the entity to save
     * @return the persisted entity
     */
    VeiculoDTO save(VeiculoDTO veiculoDTO);

    /**
     * Get all the veiculos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VeiculoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" veiculo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VeiculoDTO findOne(Long id);

    /**
     * Delete the "id" veiculo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

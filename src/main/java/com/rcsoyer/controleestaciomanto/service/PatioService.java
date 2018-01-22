package com.rcsoyer.controleestaciomanto.service;

import com.rcsoyer.controleestaciomanto.service.dto.PatioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Patio.
 */
public interface PatioService {

    /**
     * Save a patio.
     *
     * @param patioDTO the entity to save
     * @return the persisted entity
     */
    PatioDTO save(PatioDTO patioDTO);

    /**
     * Get all the patios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PatioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" patio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PatioDTO findOne(Long id);

    /**
     * Delete the "id" patio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

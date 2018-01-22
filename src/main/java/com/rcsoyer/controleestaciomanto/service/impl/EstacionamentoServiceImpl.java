package com.rcsoyer.controleestaciomanto.service.impl;

import com.rcsoyer.controleestaciomanto.service.EstacionamentoService;
import com.rcsoyer.controleestaciomanto.domain.Estacionamento;
import com.rcsoyer.controleestaciomanto.repository.EstacionamentoRepository;
import com.rcsoyer.controleestaciomanto.service.dto.EstacionamentoDTO;
import com.rcsoyer.controleestaciomanto.service.mapper.EstacionamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Estacionamento.
 */
@Service
@Transactional
public class EstacionamentoServiceImpl implements EstacionamentoService {

    private final Logger log = LoggerFactory.getLogger(EstacionamentoServiceImpl.class);

    private final EstacionamentoRepository estacionamentoRepository;

    private final EstacionamentoMapper estacionamentoMapper;

    public EstacionamentoServiceImpl(EstacionamentoRepository estacionamentoRepository, EstacionamentoMapper estacionamentoMapper) {
        this.estacionamentoRepository = estacionamentoRepository;
        this.estacionamentoMapper = estacionamentoMapper;
    }

    /**
     * Save a estacionamento.
     *
     * @param estacionamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EstacionamentoDTO save(EstacionamentoDTO estacionamentoDTO) {
        log.debug("Request to save Estacionamento : {}", estacionamentoDTO);
        Estacionamento estacionamento = estacionamentoMapper.toEntity(estacionamentoDTO);
        estacionamento = estacionamentoRepository.save(estacionamento);
        return estacionamentoMapper.toDto(estacionamento);
    }

    /**
     * Get all the estacionamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstacionamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Estacionamentos");
        return estacionamentoRepository.findAll(pageable)
            .map(estacionamentoMapper::toDto);
    }

    /**
     * Get one estacionamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EstacionamentoDTO findOne(Long id) {
        log.debug("Request to get Estacionamento : {}", id);
        Estacionamento estacionamento = estacionamentoRepository.findOne(id);
        return estacionamentoMapper.toDto(estacionamento);
    }

    /**
     * Delete the estacionamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estacionamento : {}", id);
        estacionamentoRepository.delete(id);
    }
}

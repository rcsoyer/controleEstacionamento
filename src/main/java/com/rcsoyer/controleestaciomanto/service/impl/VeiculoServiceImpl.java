package com.rcsoyer.controleestaciomanto.service.impl;

import com.rcsoyer.controleestaciomanto.service.VeiculoService;
import com.rcsoyer.controleestaciomanto.domain.Veiculo;
import com.rcsoyer.controleestaciomanto.repository.VeiculoRepository;
import com.rcsoyer.controleestaciomanto.service.dto.VeiculoDTO;
import com.rcsoyer.controleestaciomanto.service.mapper.VeiculoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Veiculo.
 */
@Service
@Transactional
public class VeiculoServiceImpl implements VeiculoService {

    private final Logger log = LoggerFactory.getLogger(VeiculoServiceImpl.class);

    private final VeiculoRepository veiculoRepository;

    private final VeiculoMapper veiculoMapper;

    public VeiculoServiceImpl(VeiculoRepository veiculoRepository, VeiculoMapper veiculoMapper) {
        this.veiculoRepository = veiculoRepository;
        this.veiculoMapper = veiculoMapper;
    }

    /**
     * Save a veiculo.
     *
     * @param veiculoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VeiculoDTO save(VeiculoDTO veiculoDTO) {
        log.debug("Request to save Veiculo : {}", veiculoDTO);
        Veiculo veiculo = veiculoMapper.toEntity(veiculoDTO);
        veiculo = veiculoRepository.save(veiculo);
        return veiculoMapper.toDto(veiculo);
    }

    /**
     * Get all the veiculos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VeiculoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Veiculos");
        return veiculoRepository.findAll(pageable)
            .map(veiculoMapper::toDto);
    }

    /**
     * Get one veiculo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VeiculoDTO findOne(Long id) {
        log.debug("Request to get Veiculo : {}", id);
        Veiculo veiculo = veiculoRepository.findOne(id);
        return veiculoMapper.toDto(veiculo);
    }

    /**
     * Delete the veiculo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Veiculo : {}", id);
        veiculoRepository.delete(id);
    }
}

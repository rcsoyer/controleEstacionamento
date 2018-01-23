package com.rcsoyer.controleestaciomanto.service.impl;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rcsoyer.controleestaciomanto.domain.Estacionamento;
import com.rcsoyer.controleestaciomanto.repository.EstacionamentoRepository;
import com.rcsoyer.controleestaciomanto.service.EstacionamentoService;
import com.rcsoyer.controleestaciomanto.service.PatioService;
import com.rcsoyer.controleestaciomanto.service.dto.EstacionamentoDTO;
import com.rcsoyer.controleestaciomanto.service.dto.PatioDTO;
import com.rcsoyer.controleestaciomanto.service.mapper.EstacionamentoMapper;


/**
 * Service Implementation for managing Estacionamento.
 */
@Service
@Transactional
public class EstacionamentoServiceImpl implements EstacionamentoService {

  private final Logger log = LoggerFactory.getLogger(EstacionamentoServiceImpl.class);

  private final PatioService patioService;

  private final EstacionamentoMapper mapper;

  private final EstacionamentoRepository repository;


  public EstacionamentoServiceImpl(EstacionamentoRepository estacionamentoRepository,
      EstacionamentoMapper estacionamentoMapper, PatioService patioService) {
    this.patioService = patioService;
    this.mapper = estacionamentoMapper;
    this.repository = estacionamentoRepository;
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
    Estacionamento estacionamento = mapper.toEntity(estacionamentoDTO);
    estacionamento = repository.save(estacionamento);
    return mapper.toDto(estacionamento);
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
    return repository.findAll(pageable).map(mapper::toDto);
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
    Estacionamento estacionamento = consultar(id);
    return mapper.toDto(estacionamento);
  }

  private Estacionamento consultar(Long id) {
    return repository.findOne(id);
  }

  /**
   * Delete the estacionamento by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Estacionamento : {}", id);
    repository.delete(id);
  }

  @Override
  public EstacionamentoDTO getPgCalculado(Long id) {
    Function<Long, Estacionamento> consultar = this::consultar;
    return consultar.andThen(mapper::toDto)
                    .andThen(calcularHoras())
                    .andThen(calcularPagamento())
                    .apply(id);
  }

  private UnaryOperator<EstacionamentoDTO> calcularHoras() {
    return dto -> {
      ZonedDateTime entrada = dto.getEntrada();
      ZonedDateTime saida = ZonedDateTime.now();
      long tempoPermanencia = entrada.until(saida, ChronoUnit.HOURS);
      dto.setSaida(saida);
      dto.setTempoPermanencia(tempoPermanencia);
      return dto;
    };
  }

  private UnaryOperator<EstacionamentoDTO> calcularPagamento() {
    return dto -> {
      long tempoPermanencia = dto.getTempoPermanencia();
      PatioDTO patio = patioService.findOne(dto.getPatioId());
      double vlrPagamento = tempoPermanencia * patio.getTaxaHora();
      dto.setVlrPagamento(vlrPagamento);
      return dto;
    };
  }

  @Override
  public void pagarEstacionamento(final EstacionamentoDTO dto) {
    Estacionamento entitade = mapper.toEntity(dto);
    entitade.setEmUso(Boolean.FALSE);
    repository.save(entitade);
  }
}

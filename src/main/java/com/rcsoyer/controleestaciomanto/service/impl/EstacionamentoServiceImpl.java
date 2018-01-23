package com.rcsoyer.controleestaciomanto.service.impl;

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

  private final EstacionamentoRepository estacionamentoRepository;


  public EstacionamentoServiceImpl(EstacionamentoRepository estacionamentoRepository,
      EstacionamentoMapper estacionamentoMapper, PatioService patioService) {
    this.patioService = patioService;
    this.mapper = estacionamentoMapper;
    this.estacionamentoRepository = estacionamentoRepository;
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
    estacionamento = estacionamentoRepository.save(estacionamento);
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
    return estacionamentoRepository.findAll(pageable).map(mapper::toDto);
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
    return estacionamentoRepository.findOne(id);
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

  @Override
  public EstacionamentoDTO getPgCalculado(Long id) {
    return null;
  }
  /*
   * @Override public EstacionamentoDTO getPgCalculado(Long id) { return Optional.of(consultar(id))
   * .map(calcular()) .get(); }
   * 
   * private Function<Estacionamento, EstacionamentoDTO> calcular() { Function<Estacionamento,
   * EstacionamentoDTO> toDto = mapper::toDto; return toDto.andThen(calcularHoras())
   * .andThen(calcularPagamento()); }
   * 
   * private UnaryOperator<EstacionamentoDTO> calcularHoras() { return dto -> { ZonedDateTime
   * entrada = dto.getEntrada(); ZonedDateTime saida = ZonedDateTime.now(); long tempoPermanencia =
   * entrada.until(saida, ChronoUnit.HOURS); dto.setSaida(saida);
   * dto.setTempoPermanencia(tempoPermanencia); return dto; }; }
   * 
   * private UnaryOperator<EstacionamentoDTO> calcularPagamento() { return dto -> { long
   * horasEstacionado = dto.getTempoPermanencia(); PatioDTO patio =
   * patioService.findOne(dto.getPatioId()); double vlrPagamento = horasEstacionado *
   * patio.getTaxaHora(); dto.setVlrPagamento(vlrPagamento); return dto; }; }
   */
}

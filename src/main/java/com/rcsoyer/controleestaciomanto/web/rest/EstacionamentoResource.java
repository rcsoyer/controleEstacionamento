package com.rcsoyer.controleestaciomanto.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.controleestaciomanto.service.EstacionamentoService;
import com.rcsoyer.controleestaciomanto.service.dto.EstacionamentoDTO;
import com.rcsoyer.controleestaciomanto.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.controleestaciomanto.web.rest.util.HeaderUtil;
import com.rcsoyer.controleestaciomanto.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Estacionamento.
 */
@RestController
@RequestMapping("/api")
public class EstacionamentoResource {

  private final Logger log = LoggerFactory.getLogger(EstacionamentoResource.class);

  private static final String ENTITY_NAME = "estacionamento";

  private final EstacionamentoService estacionamentoService;

  public EstacionamentoResource(EstacionamentoService estacionamentoService) {
    this.estacionamentoService = estacionamentoService;
  }

  /**
   * POST /estacionamentos : Create a new estacionamento.
   *
   * @param estacionamentoDTO the estacionamentoDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new estacionamentoDTO,
   *         or with status 400 (Bad Request) if the estacionamento has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/estacionamentos")
  @Timed
  public ResponseEntity<EstacionamentoDTO> createEstacionamento(
      @Valid @RequestBody EstacionamentoDTO estacionamentoDTO) throws URISyntaxException {
    log.debug("REST request to save Estacionamento : {}", estacionamentoDTO);
    if (estacionamentoDTO.getId() != null) {
      throw new BadRequestAlertException("A new estacionamento cannot already have an ID",
          ENTITY_NAME, "idexists");
    }
    EstacionamentoDTO result = estacionamentoService.save(estacionamentoDTO);
    return ResponseEntity.created(new URI("/api/estacionamentos/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * PUT /estacionamentos : Updates an existing estacionamento.
   *
   * @param estacionamentoDTO the estacionamentoDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated estacionamentoDTO, or
   *         with status 400 (Bad Request) if the estacionamentoDTO is not valid, or with status 500
   *         (Internal Server Error) if the estacionamentoDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/estacionamentos")
  @Timed
  public ResponseEntity<EstacionamentoDTO> updateEstacionamento(
      @Valid @RequestBody EstacionamentoDTO estacionamentoDTO) throws URISyntaxException {
    log.debug("REST request to update Estacionamento : {}", estacionamentoDTO);
    if (estacionamentoDTO.getId() == null) {
      return createEstacionamento(estacionamentoDTO);
    }
    EstacionamentoDTO result = estacionamentoService.save(estacionamentoDTO);
    return ResponseEntity.ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estacionamentoDTO.getId().toString()))
        .body(result);
  }

  /**
   * GET /estacionamentos : get all the estacionamentos.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of estacionamentos in body
   */
  @GetMapping("/estacionamentos")
  @Timed
  public ResponseEntity<List<EstacionamentoDTO>> getAllEstacionamentos(Pageable pageable) {
    log.debug("REST request to get a page of Estacionamentos");
    Page<EstacionamentoDTO> page = estacionamentoService.findAll(pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, "/api/estacionamentos");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /estacionamentos/:id : get the "id" estacionamento.
   *
   * @param id the id of the estacionamentoDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the estacionamentoDTO, or with
   *         status 404 (Not Found)
   */
  @GetMapping("/estacionamentos/{id}")
  @Timed
  public ResponseEntity<EstacionamentoDTO> getEstacionamento(@PathVariable Long id) {
    log.debug("REST request to get Estacionamento : {}", id);
    EstacionamentoDTO estacionamentoDTO = estacionamentoService.findOne(id);
    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(estacionamentoDTO));
  }

  /**
   * DELETE /estacionamentos/:id : delete the "id" estacionamento.
   *
   * @param id the id of the estacionamentoDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/estacionamentos/{id}")
  @Timed
  public ResponseEntity<Void> deleteEstacionamento(@PathVariable Long id) {
    log.debug("REST request to delete Estacionamento : {}", id);
    estacionamentoService.delete(id);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }

  @Timed
  @GetMapping("/estacionamentos/getPgCalculado/{id}")
  public ResponseEntity<EstacionamentoDTO> calcularPagamento(@PathVariable Long id) {
    log.debug("GET request para consultar entidade Estacionamento com pagamento calculado");
    EstacionamentoDTO dto = estacionamentoService.getPgCalculado(id);
    return ResponseUtil.wrapOrNotFound(Optional.of(dto));
  }

  @Timed
  @PutMapping("/estacionamentos/pagar")
  public ResponseEntity<EstacionamentoDTO> pagarEstacionamento(
      @Valid @RequestBody EstacionamentoDTO estacionamentoDTO) throws URISyntaxException {
    log.debug("REST request para pagar Estacionamento : {}", estacionamentoDTO);
    EstacionamentoDTO result = estacionamentoService.save(estacionamentoDTO);
    return ResponseEntity.ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estacionamentoDTO.getId().toString()))
        .body(result);
  }
}

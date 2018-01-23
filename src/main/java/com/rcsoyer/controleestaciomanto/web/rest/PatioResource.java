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
import com.rcsoyer.controleestaciomanto.service.PatioService;
import com.rcsoyer.controleestaciomanto.service.dto.PatioDTO;
import com.rcsoyer.controleestaciomanto.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.controleestaciomanto.web.rest.util.HeaderUtil;
import com.rcsoyer.controleestaciomanto.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Patio.
 */
@RestController
@RequestMapping("/api")
public class PatioResource {

  private final Logger log = LoggerFactory.getLogger(PatioResource.class);

  private static final String ENTITY_NAME = "patio";

  private final PatioService patioService;

  public PatioResource(PatioService patioService) {
    this.patioService = patioService;
  }

  /**
   * POST /patios : Create a new patio.
   *
   * @param patioDTO the patioDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new patioDTO, or with
   *         status 400 (Bad Request) if the patio has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/patios")
  @Timed
  public ResponseEntity<PatioDTO> createPatio(@Valid @RequestBody PatioDTO patioDTO)
      throws URISyntaxException {
    log.debug("REST request to save Patio : {}", patioDTO);
    if (patioDTO.getId() != null) {
      throw new BadRequestAlertException("A new patio cannot already have an ID", ENTITY_NAME,
          "idexists");
    }
    PatioDTO result = patioService.save(patioDTO);
    return ResponseEntity.created(new URI("/api/patios/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * PUT /patios : Updates an existing patio.
   *
   * @param patioDTO the patioDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated patioDTO, or with
   *         status 400 (Bad Request) if the patioDTO is not valid, or with status 500 (Internal
   *         Server Error) if the patioDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/patios")
  @Timed
  public ResponseEntity<PatioDTO> updatePatio(@Valid @RequestBody PatioDTO patioDTO)
      throws URISyntaxException {
    log.debug("REST request to update Patio : {}", patioDTO);
    if (patioDTO.getId() == null) {
      return createPatio(patioDTO);
    }
    PatioDTO result = patioService.save(patioDTO);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patioDTO.getId().toString()))
        .body(result);
  }

  /**
   * GET /patios : get all the patios.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of patios in body
   */
  @GetMapping("/patios")
  @Timed
  public ResponseEntity<List<PatioDTO>> getAllPatios(Pageable pageable) {
    log.debug("REST request to get a page of Patios");
    Page<PatioDTO> page = patioService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/patios");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /patios/:id : get the "id" patio.
   *
   * @param id the id of the patioDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the patioDTO, or with status 404
   *         (Not Found)
   */
  @GetMapping("/patios/{id}")
  @Timed
  public ResponseEntity<PatioDTO> getPatio(@PathVariable Long id) {
    log.debug("REST request to get Patio : {}", id);
    PatioDTO patioDTO = patioService.findOne(id);
    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(patioDTO));
  }

  /**
   * DELETE /patios/:id : delete the "id" patio.
   *
   * @param id the id of the patioDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/patios/{id}")
  @Timed
  public ResponseEntity<Void> deletePatio(@PathVariable Long id) {
    log.debug("REST request to delete Patio : {}", id);
    patioService.delete(id);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }
}

package com.rcsoyer.controleestaciomanto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rcsoyer.controleestaciomanto.service.VeiculoService;
import com.rcsoyer.controleestaciomanto.web.rest.errors.BadRequestAlertException;
import com.rcsoyer.controleestaciomanto.web.rest.util.HeaderUtil;
import com.rcsoyer.controleestaciomanto.web.rest.util.PaginationUtil;
import com.rcsoyer.controleestaciomanto.service.dto.VeiculoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Veiculo.
 */
@RestController
@RequestMapping("/api")
public class VeiculoResource {

    private final Logger log = LoggerFactory.getLogger(VeiculoResource.class);

    private static final String ENTITY_NAME = "veiculo";

    private final VeiculoService veiculoService;

    public VeiculoResource(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    /**
     * POST  /veiculos : Create a new veiculo.
     *
     * @param veiculoDTO the veiculoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new veiculoDTO, or with status 400 (Bad Request) if the veiculo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/veiculos")
    @Timed
    public ResponseEntity<VeiculoDTO> createVeiculo(@Valid @RequestBody VeiculoDTO veiculoDTO) throws URISyntaxException {
        log.debug("REST request to save Veiculo : {}", veiculoDTO);
        if (veiculoDTO.getId() != null) {
            throw new BadRequestAlertException("A new veiculo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VeiculoDTO result = veiculoService.save(veiculoDTO);
        return ResponseEntity.created(new URI("/api/veiculos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /veiculos : Updates an existing veiculo.
     *
     * @param veiculoDTO the veiculoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated veiculoDTO,
     * or with status 400 (Bad Request) if the veiculoDTO is not valid,
     * or with status 500 (Internal Server Error) if the veiculoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/veiculos")
    @Timed
    public ResponseEntity<VeiculoDTO> updateVeiculo(@Valid @RequestBody VeiculoDTO veiculoDTO) throws URISyntaxException {
        log.debug("REST request to update Veiculo : {}", veiculoDTO);
        if (veiculoDTO.getId() == null) {
            return createVeiculo(veiculoDTO);
        }
        VeiculoDTO result = veiculoService.save(veiculoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, veiculoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /veiculos : get all the veiculos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of veiculos in body
     */
    @GetMapping("/veiculos")
    @Timed
    public ResponseEntity<List<VeiculoDTO>> getAllVeiculos(Pageable pageable) {
        log.debug("REST request to get a page of Veiculos");
        Page<VeiculoDTO> page = veiculoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/veiculos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /veiculos/:id : get the "id" veiculo.
     *
     * @param id the id of the veiculoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the veiculoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/veiculos/{id}")
    @Timed
    public ResponseEntity<VeiculoDTO> getVeiculo(@PathVariable Long id) {
        log.debug("REST request to get Veiculo : {}", id);
        VeiculoDTO veiculoDTO = veiculoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(veiculoDTO));
    }

    /**
     * DELETE  /veiculos/:id : delete the "id" veiculo.
     *
     * @param id the id of the veiculoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/veiculos/{id}")
    @Timed
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Long id) {
        log.debug("REST request to delete Veiculo : {}", id);
        veiculoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

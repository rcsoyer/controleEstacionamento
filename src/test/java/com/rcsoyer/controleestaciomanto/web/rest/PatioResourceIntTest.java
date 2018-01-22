package com.rcsoyer.controleestaciomanto.web.rest;

import com.rcsoyer.controleestaciomanto.ControleEstacionamentoApp;

import com.rcsoyer.controleestaciomanto.domain.Patio;
import com.rcsoyer.controleestaciomanto.repository.PatioRepository;
import com.rcsoyer.controleestaciomanto.service.PatioService;
import com.rcsoyer.controleestaciomanto.service.dto.PatioDTO;
import com.rcsoyer.controleestaciomanto.service.mapper.PatioMapper;
import com.rcsoyer.controleestaciomanto.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.rcsoyer.controleestaciomanto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PatioResource REST controller.
 *
 * @see PatioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControleEstacionamentoApp.class)
public class PatioResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTD_VAGAS = 1;
    private static final Integer UPDATED_QTD_VAGAS = 2;

    private static final Double DEFAULT_TAXA_HORA = 1D;
    private static final Double UPDATED_TAXA_HORA = 2D;

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private PatioMapper patioMapper;

    @Autowired
    private PatioService patioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatioMockMvc;

    private Patio patio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatioResource patioResource = new PatioResource(patioService);
        this.restPatioMockMvc = MockMvcBuilders.standaloneSetup(patioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patio createEntity(EntityManager em) {
        Patio patio = new Patio()
            .descricao(DEFAULT_DESCRICAO)
            .qtdVagas(DEFAULT_QTD_VAGAS)
            .taxaHora(DEFAULT_TAXA_HORA);
        return patio;
    }

    @Before
    public void initTest() {
        patio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatio() throws Exception {
        int databaseSizeBeforeCreate = patioRepository.findAll().size();

        // Create the Patio
        PatioDTO patioDTO = patioMapper.toDto(patio);
        restPatioMockMvc.perform(post("/api/patios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patioDTO)))
            .andExpect(status().isCreated());

        // Validate the Patio in the database
        List<Patio> patioList = patioRepository.findAll();
        assertThat(patioList).hasSize(databaseSizeBeforeCreate + 1);
        Patio testPatio = patioList.get(patioList.size() - 1);
        assertThat(testPatio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPatio.getQtdVagas()).isEqualTo(DEFAULT_QTD_VAGAS);
        assertThat(testPatio.getTaxaHora()).isEqualTo(DEFAULT_TAXA_HORA);
    }

    @Test
    @Transactional
    public void createPatioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patioRepository.findAll().size();

        // Create the Patio with an existing ID
        patio.setId(1L);
        PatioDTO patioDTO = patioMapper.toDto(patio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatioMockMvc.perform(post("/api/patios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patio in the database
        List<Patio> patioList = patioRepository.findAll();
        assertThat(patioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = patioRepository.findAll().size();
        // set the field null
        patio.setDescricao(null);

        // Create the Patio, which fails.
        PatioDTO patioDTO = patioMapper.toDto(patio);

        restPatioMockMvc.perform(post("/api/patios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patioDTO)))
            .andExpect(status().isBadRequest());

        List<Patio> patioList = patioRepository.findAll();
        assertThat(patioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtdVagasIsRequired() throws Exception {
        int databaseSizeBeforeTest = patioRepository.findAll().size();
        // set the field null
        patio.setQtdVagas(null);

        // Create the Patio, which fails.
        PatioDTO patioDTO = patioMapper.toDto(patio);

        restPatioMockMvc.perform(post("/api/patios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patioDTO)))
            .andExpect(status().isBadRequest());

        List<Patio> patioList = patioRepository.findAll();
        assertThat(patioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxaHoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = patioRepository.findAll().size();
        // set the field null
        patio.setTaxaHora(null);

        // Create the Patio, which fails.
        PatioDTO patioDTO = patioMapper.toDto(patio);

        restPatioMockMvc.perform(post("/api/patios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patioDTO)))
            .andExpect(status().isBadRequest());

        List<Patio> patioList = patioRepository.findAll();
        assertThat(patioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatios() throws Exception {
        // Initialize the database
        patioRepository.saveAndFlush(patio);

        // Get all the patioList
        restPatioMockMvc.perform(get("/api/patios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patio.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].qtdVagas").value(hasItem(DEFAULT_QTD_VAGAS)))
            .andExpect(jsonPath("$.[*].taxaHora").value(hasItem(DEFAULT_TAXA_HORA.doubleValue())));
    }

    @Test
    @Transactional
    public void getPatio() throws Exception {
        // Initialize the database
        patioRepository.saveAndFlush(patio);

        // Get the patio
        restPatioMockMvc.perform(get("/api/patios/{id}", patio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patio.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.qtdVagas").value(DEFAULT_QTD_VAGAS))
            .andExpect(jsonPath("$.taxaHora").value(DEFAULT_TAXA_HORA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPatio() throws Exception {
        // Get the patio
        restPatioMockMvc.perform(get("/api/patios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatio() throws Exception {
        // Initialize the database
        patioRepository.saveAndFlush(patio);
        int databaseSizeBeforeUpdate = patioRepository.findAll().size();

        // Update the patio
        Patio updatedPatio = patioRepository.findOne(patio.getId());
        // Disconnect from session so that the updates on updatedPatio are not directly saved in db
        em.detach(updatedPatio);
        updatedPatio
            .descricao(UPDATED_DESCRICAO)
            .qtdVagas(UPDATED_QTD_VAGAS)
            .taxaHora(UPDATED_TAXA_HORA);
        PatioDTO patioDTO = patioMapper.toDto(updatedPatio);

        restPatioMockMvc.perform(put("/api/patios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patioDTO)))
            .andExpect(status().isOk());

        // Validate the Patio in the database
        List<Patio> patioList = patioRepository.findAll();
        assertThat(patioList).hasSize(databaseSizeBeforeUpdate);
        Patio testPatio = patioList.get(patioList.size() - 1);
        assertThat(testPatio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPatio.getQtdVagas()).isEqualTo(UPDATED_QTD_VAGAS);
        assertThat(testPatio.getTaxaHora()).isEqualTo(UPDATED_TAXA_HORA);
    }

    @Test
    @Transactional
    public void updateNonExistingPatio() throws Exception {
        int databaseSizeBeforeUpdate = patioRepository.findAll().size();

        // Create the Patio
        PatioDTO patioDTO = patioMapper.toDto(patio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPatioMockMvc.perform(put("/api/patios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patioDTO)))
            .andExpect(status().isCreated());

        // Validate the Patio in the database
        List<Patio> patioList = patioRepository.findAll();
        assertThat(patioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePatio() throws Exception {
        // Initialize the database
        patioRepository.saveAndFlush(patio);
        int databaseSizeBeforeDelete = patioRepository.findAll().size();

        // Get the patio
        restPatioMockMvc.perform(delete("/api/patios/{id}", patio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patio> patioList = patioRepository.findAll();
        assertThat(patioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patio.class);
        Patio patio1 = new Patio();
        patio1.setId(1L);
        Patio patio2 = new Patio();
        patio2.setId(patio1.getId());
        assertThat(patio1).isEqualTo(patio2);
        patio2.setId(2L);
        assertThat(patio1).isNotEqualTo(patio2);
        patio1.setId(null);
        assertThat(patio1).isNotEqualTo(patio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatioDTO.class);
        PatioDTO patioDTO1 = new PatioDTO();
        patioDTO1.setId(1L);
        PatioDTO patioDTO2 = new PatioDTO();
        assertThat(patioDTO1).isNotEqualTo(patioDTO2);
        patioDTO2.setId(patioDTO1.getId());
        assertThat(patioDTO1).isEqualTo(patioDTO2);
        patioDTO2.setId(2L);
        assertThat(patioDTO1).isNotEqualTo(patioDTO2);
        patioDTO1.setId(null);
        assertThat(patioDTO1).isNotEqualTo(patioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(patioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(patioMapper.fromId(null)).isNull();
    }
}

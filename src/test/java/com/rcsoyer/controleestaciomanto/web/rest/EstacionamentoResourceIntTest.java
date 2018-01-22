package com.rcsoyer.controleestaciomanto.web.rest;

import com.rcsoyer.controleestaciomanto.ControleEstacionamentoApp;

import com.rcsoyer.controleestaciomanto.domain.Estacionamento;
import com.rcsoyer.controleestaciomanto.domain.Veiculo;
import com.rcsoyer.controleestaciomanto.domain.Patio;
import com.rcsoyer.controleestaciomanto.repository.EstacionamentoRepository;
import com.rcsoyer.controleestaciomanto.service.EstacionamentoService;
import com.rcsoyer.controleestaciomanto.service.dto.EstacionamentoDTO;
import com.rcsoyer.controleestaciomanto.service.mapper.EstacionamentoMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.rcsoyer.controleestaciomanto.web.rest.TestUtil.sameInstant;
import static com.rcsoyer.controleestaciomanto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EstacionamentoResource REST controller.
 *
 * @see EstacionamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControleEstacionamentoApp.class)
public class EstacionamentoResourceIntTest {

    private static final ZonedDateTime DEFAULT_ENTRADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENTRADA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_SAIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SAIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_VAGA = 1;
    private static final Integer UPDATED_VAGA = 2;

    private static final Boolean DEFAULT_EM_USO = false;
    private static final Boolean UPDATED_EM_USO = true;

    private static final Double DEFAULT_VLR_PAGAMENTO = 1D;
    private static final Double UPDATED_VLR_PAGAMENTO = 2D;

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    @Autowired
    private EstacionamentoMapper estacionamentoMapper;

    @Autowired
    private EstacionamentoService estacionamentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstacionamentoMockMvc;

    private Estacionamento estacionamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstacionamentoResource estacionamentoResource = new EstacionamentoResource(estacionamentoService);
        this.restEstacionamentoMockMvc = MockMvcBuilders.standaloneSetup(estacionamentoResource)
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
    public static Estacionamento createEntity(EntityManager em) {
        Estacionamento estacionamento = new Estacionamento()
            .entrada(DEFAULT_ENTRADA)
            .saida(DEFAULT_SAIDA)
            .vaga(DEFAULT_VAGA)
            .emUso(DEFAULT_EM_USO)
            .vlrPagamento(DEFAULT_VLR_PAGAMENTO);
        // Add required entity
        Veiculo veiculo = VeiculoResourceIntTest.createEntity(em);
        em.persist(veiculo);
        em.flush();
        estacionamento.setVeiculo(veiculo);
        // Add required entity
        Patio patio = PatioResourceIntTest.createEntity(em);
        em.persist(patio);
        em.flush();
        estacionamento.setPatio(patio);
        return estacionamento;
    }

    @Before
    public void initTest() {
        estacionamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstacionamento() throws Exception {
        int databaseSizeBeforeCreate = estacionamentoRepository.findAll().size();

        // Create the Estacionamento
        EstacionamentoDTO estacionamentoDTO = estacionamentoMapper.toDto(estacionamento);
        restEstacionamentoMockMvc.perform(post("/api/estacionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estacionamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Estacionamento in the database
        List<Estacionamento> estacionamentoList = estacionamentoRepository.findAll();
        assertThat(estacionamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Estacionamento testEstacionamento = estacionamentoList.get(estacionamentoList.size() - 1);
        assertThat(testEstacionamento.getEntrada()).isEqualTo(DEFAULT_ENTRADA);
        assertThat(testEstacionamento.getSaida()).isEqualTo(DEFAULT_SAIDA);
        assertThat(testEstacionamento.getVaga()).isEqualTo(DEFAULT_VAGA);
        assertThat(testEstacionamento.isEmUso()).isEqualTo(DEFAULT_EM_USO);
        assertThat(testEstacionamento.getVlrPagamento()).isEqualTo(DEFAULT_VLR_PAGAMENTO);
    }

    @Test
    @Transactional
    public void createEstacionamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estacionamentoRepository.findAll().size();

        // Create the Estacionamento with an existing ID
        estacionamento.setId(1L);
        EstacionamentoDTO estacionamentoDTO = estacionamentoMapper.toDto(estacionamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstacionamentoMockMvc.perform(post("/api/estacionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estacionamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estacionamento in the database
        List<Estacionamento> estacionamentoList = estacionamentoRepository.findAll();
        assertThat(estacionamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = estacionamentoRepository.findAll().size();
        // set the field null
        estacionamento.setEntrada(null);

        // Create the Estacionamento, which fails.
        EstacionamentoDTO estacionamentoDTO = estacionamentoMapper.toDto(estacionamento);

        restEstacionamentoMockMvc.perform(post("/api/estacionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estacionamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Estacionamento> estacionamentoList = estacionamentoRepository.findAll();
        assertThat(estacionamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVagaIsRequired() throws Exception {
        int databaseSizeBeforeTest = estacionamentoRepository.findAll().size();
        // set the field null
        estacionamento.setVaga(null);

        // Create the Estacionamento, which fails.
        EstacionamentoDTO estacionamentoDTO = estacionamentoMapper.toDto(estacionamento);

        restEstacionamentoMockMvc.perform(post("/api/estacionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estacionamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Estacionamento> estacionamentoList = estacionamentoRepository.findAll();
        assertThat(estacionamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmUsoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estacionamentoRepository.findAll().size();
        // set the field null
        estacionamento.setEmUso(null);

        // Create the Estacionamento, which fails.
        EstacionamentoDTO estacionamentoDTO = estacionamentoMapper.toDto(estacionamento);

        restEstacionamentoMockMvc.perform(post("/api/estacionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estacionamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Estacionamento> estacionamentoList = estacionamentoRepository.findAll();
        assertThat(estacionamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstacionamentos() throws Exception {
        // Initialize the database
        estacionamentoRepository.saveAndFlush(estacionamento);

        // Get all the estacionamentoList
        restEstacionamentoMockMvc.perform(get("/api/estacionamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estacionamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].entrada").value(hasItem(sameInstant(DEFAULT_ENTRADA))))
            .andExpect(jsonPath("$.[*].saida").value(hasItem(sameInstant(DEFAULT_SAIDA))))
            .andExpect(jsonPath("$.[*].vaga").value(hasItem(DEFAULT_VAGA)))
            .andExpect(jsonPath("$.[*].emUso").value(hasItem(DEFAULT_EM_USO.booleanValue())))
            .andExpect(jsonPath("$.[*].vlrPagamento").value(hasItem(DEFAULT_VLR_PAGAMENTO.doubleValue())));
    }

    @Test
    @Transactional
    public void getEstacionamento() throws Exception {
        // Initialize the database
        estacionamentoRepository.saveAndFlush(estacionamento);

        // Get the estacionamento
        restEstacionamentoMockMvc.perform(get("/api/estacionamentos/{id}", estacionamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estacionamento.getId().intValue()))
            .andExpect(jsonPath("$.entrada").value(sameInstant(DEFAULT_ENTRADA)))
            .andExpect(jsonPath("$.saida").value(sameInstant(DEFAULT_SAIDA)))
            .andExpect(jsonPath("$.vaga").value(DEFAULT_VAGA))
            .andExpect(jsonPath("$.emUso").value(DEFAULT_EM_USO.booleanValue()))
            .andExpect(jsonPath("$.vlrPagamento").value(DEFAULT_VLR_PAGAMENTO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEstacionamento() throws Exception {
        // Get the estacionamento
        restEstacionamentoMockMvc.perform(get("/api/estacionamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstacionamento() throws Exception {
        // Initialize the database
        estacionamentoRepository.saveAndFlush(estacionamento);
        int databaseSizeBeforeUpdate = estacionamentoRepository.findAll().size();

        // Update the estacionamento
        Estacionamento updatedEstacionamento = estacionamentoRepository.findOne(estacionamento.getId());
        // Disconnect from session so that the updates on updatedEstacionamento are not directly saved in db
        em.detach(updatedEstacionamento);
        updatedEstacionamento
            .entrada(UPDATED_ENTRADA)
            .saida(UPDATED_SAIDA)
            .vaga(UPDATED_VAGA)
            .emUso(UPDATED_EM_USO)
            .vlrPagamento(UPDATED_VLR_PAGAMENTO);
        EstacionamentoDTO estacionamentoDTO = estacionamentoMapper.toDto(updatedEstacionamento);

        restEstacionamentoMockMvc.perform(put("/api/estacionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estacionamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Estacionamento in the database
        List<Estacionamento> estacionamentoList = estacionamentoRepository.findAll();
        assertThat(estacionamentoList).hasSize(databaseSizeBeforeUpdate);
        Estacionamento testEstacionamento = estacionamentoList.get(estacionamentoList.size() - 1);
        assertThat(testEstacionamento.getEntrada()).isEqualTo(UPDATED_ENTRADA);
        assertThat(testEstacionamento.getSaida()).isEqualTo(UPDATED_SAIDA);
        assertThat(testEstacionamento.getVaga()).isEqualTo(UPDATED_VAGA);
        assertThat(testEstacionamento.isEmUso()).isEqualTo(UPDATED_EM_USO);
        assertThat(testEstacionamento.getVlrPagamento()).isEqualTo(UPDATED_VLR_PAGAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstacionamento() throws Exception {
        int databaseSizeBeforeUpdate = estacionamentoRepository.findAll().size();

        // Create the Estacionamento
        EstacionamentoDTO estacionamentoDTO = estacionamentoMapper.toDto(estacionamento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstacionamentoMockMvc.perform(put("/api/estacionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estacionamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Estacionamento in the database
        List<Estacionamento> estacionamentoList = estacionamentoRepository.findAll();
        assertThat(estacionamentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEstacionamento() throws Exception {
        // Initialize the database
        estacionamentoRepository.saveAndFlush(estacionamento);
        int databaseSizeBeforeDelete = estacionamentoRepository.findAll().size();

        // Get the estacionamento
        restEstacionamentoMockMvc.perform(delete("/api/estacionamentos/{id}", estacionamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estacionamento> estacionamentoList = estacionamentoRepository.findAll();
        assertThat(estacionamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estacionamento.class);
        Estacionamento estacionamento1 = new Estacionamento();
        estacionamento1.setId(1L);
        Estacionamento estacionamento2 = new Estacionamento();
        estacionamento2.setId(estacionamento1.getId());
        assertThat(estacionamento1).isEqualTo(estacionamento2);
        estacionamento2.setId(2L);
        assertThat(estacionamento1).isNotEqualTo(estacionamento2);
        estacionamento1.setId(null);
        assertThat(estacionamento1).isNotEqualTo(estacionamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstacionamentoDTO.class);
        EstacionamentoDTO estacionamentoDTO1 = new EstacionamentoDTO();
        estacionamentoDTO1.setId(1L);
        EstacionamentoDTO estacionamentoDTO2 = new EstacionamentoDTO();
        assertThat(estacionamentoDTO1).isNotEqualTo(estacionamentoDTO2);
        estacionamentoDTO2.setId(estacionamentoDTO1.getId());
        assertThat(estacionamentoDTO1).isEqualTo(estacionamentoDTO2);
        estacionamentoDTO2.setId(2L);
        assertThat(estacionamentoDTO1).isNotEqualTo(estacionamentoDTO2);
        estacionamentoDTO1.setId(null);
        assertThat(estacionamentoDTO1).isNotEqualTo(estacionamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estacionamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estacionamentoMapper.fromId(null)).isNull();
    }
}

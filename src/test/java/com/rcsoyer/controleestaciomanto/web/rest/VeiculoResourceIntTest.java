package com.rcsoyer.controleestaciomanto.web.rest;

import com.rcsoyer.controleestaciomanto.ControleEstacionamentoApp;

import com.rcsoyer.controleestaciomanto.domain.Veiculo;
import com.rcsoyer.controleestaciomanto.domain.Cliente;
import com.rcsoyer.controleestaciomanto.repository.VeiculoRepository;
import com.rcsoyer.controleestaciomanto.service.VeiculoService;
import com.rcsoyer.controleestaciomanto.service.dto.VeiculoDTO;
import com.rcsoyer.controleestaciomanto.service.mapper.VeiculoMapper;
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
 * Test class for the VeiculoResource REST controller.
 *
 * @see VeiculoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControleEstacionamentoApp.class)
public class VeiculoResourceIntTest {

    private static final String DEFAULT_PLACA = "AAAAAAA";
    private static final String UPDATED_PLACA = "BBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoMapper veiculoMapper;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVeiculoMockMvc;

    private Veiculo veiculo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VeiculoResource veiculoResource = new VeiculoResource(veiculoService);
        this.restVeiculoMockMvc = MockMvcBuilders.standaloneSetup(veiculoResource)
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
    public static Veiculo createEntity(EntityManager em) {
        Veiculo veiculo = new Veiculo()
            .placa(DEFAULT_PLACA)
            .modelo(DEFAULT_MODELO)
            .cor(DEFAULT_COR);
        // Add required entity
        Cliente cliente = ClienteResourceIntTest.createEntity(em);
        em.persist(cliente);
        em.flush();
        veiculo.setCliente(cliente);
        return veiculo;
    }

    @Before
    public void initTest() {
        veiculo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVeiculo() throws Exception {
        int databaseSizeBeforeCreate = veiculoRepository.findAll().size();

        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);
        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isCreated());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeCreate + 1);
        Veiculo testVeiculo = veiculoList.get(veiculoList.size() - 1);
        assertThat(testVeiculo.getPlaca()).isEqualTo(DEFAULT_PLACA);
        assertThat(testVeiculo.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testVeiculo.getCor()).isEqualTo(DEFAULT_COR);
    }

    @Test
    @Transactional
    public void createVeiculoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = veiculoRepository.findAll().size();

        // Create the Veiculo with an existing ID
        veiculo.setId(1L);
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPlacaIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setPlaca(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setModelo(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setCor(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVeiculos() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        // Get all the veiculoList
        restVeiculoMockMvc.perform(get("/api/veiculos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(veiculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].placa").value(hasItem(DEFAULT_PLACA.toString())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }

    @Test
    @Transactional
    public void getVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        // Get the veiculo
        restVeiculoMockMvc.perform(get("/api/veiculos/{id}", veiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(veiculo.getId().intValue()))
            .andExpect(jsonPath("$.placa").value(DEFAULT_PLACA.toString()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVeiculo() throws Exception {
        // Get the veiculo
        restVeiculoMockMvc.perform(get("/api/veiculos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();

        // Update the veiculo
        Veiculo updatedVeiculo = veiculoRepository.findOne(veiculo.getId());
        // Disconnect from session so that the updates on updatedVeiculo are not directly saved in db
        em.detach(updatedVeiculo);
        updatedVeiculo
            .placa(UPDATED_PLACA)
            .modelo(UPDATED_MODELO)
            .cor(UPDATED_COR);
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(updatedVeiculo);

        restVeiculoMockMvc.perform(put("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isOk());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
        Veiculo testVeiculo = veiculoList.get(veiculoList.size() - 1);
        assertThat(testVeiculo.getPlaca()).isEqualTo(UPDATED_PLACA);
        assertThat(testVeiculo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVeiculo.getCor()).isEqualTo(UPDATED_COR);
    }

    @Test
    @Transactional
    public void updateNonExistingVeiculo() throws Exception {
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();

        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVeiculoMockMvc.perform(put("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isCreated());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);
        int databaseSizeBeforeDelete = veiculoRepository.findAll().size();

        // Get the veiculo
        restVeiculoMockMvc.perform(delete("/api/veiculos/{id}", veiculo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Veiculo.class);
        Veiculo veiculo1 = new Veiculo();
        veiculo1.setId(1L);
        Veiculo veiculo2 = new Veiculo();
        veiculo2.setId(veiculo1.getId());
        assertThat(veiculo1).isEqualTo(veiculo2);
        veiculo2.setId(2L);
        assertThat(veiculo1).isNotEqualTo(veiculo2);
        veiculo1.setId(null);
        assertThat(veiculo1).isNotEqualTo(veiculo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VeiculoDTO.class);
        VeiculoDTO veiculoDTO1 = new VeiculoDTO();
        veiculoDTO1.setId(1L);
        VeiculoDTO veiculoDTO2 = new VeiculoDTO();
        assertThat(veiculoDTO1).isNotEqualTo(veiculoDTO2);
        veiculoDTO2.setId(veiculoDTO1.getId());
        assertThat(veiculoDTO1).isEqualTo(veiculoDTO2);
        veiculoDTO2.setId(2L);
        assertThat(veiculoDTO1).isNotEqualTo(veiculoDTO2);
        veiculoDTO1.setId(null);
        assertThat(veiculoDTO1).isNotEqualTo(veiculoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(veiculoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(veiculoMapper.fromId(null)).isNull();
    }
}

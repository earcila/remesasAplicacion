package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.RemesasApp;

import io.github.jhipster.application.domain.Remesas;
import io.github.jhipster.application.repository.RemesasRepository;
import io.github.jhipster.application.service.RemesasService;
import io.github.jhipster.application.service.dto.RemesasDTO;
import io.github.jhipster.application.service.mapper.RemesasMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.RemesasCriteria;
import io.github.jhipster.application.service.RemesasQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RemesasResource REST controller.
 *
 * @see RemesasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RemesasApp.class)
public class RemesasResourceIntTest {

    private static final String DEFAULT_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_TASA = "AAAAAAAAAA";
    private static final String UPDATED_TASA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ACT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ACT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RemesasRepository remesasRepository;

    @Autowired
    private RemesasMapper remesasMapper;

    @Autowired
    private RemesasService remesasService;

    @Autowired
    private RemesasQueryService remesasQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRemesasMockMvc;

    private Remesas remesas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RemesasResource remesasResource = new RemesasResource(remesasService, remesasQueryService);
        this.restRemesasMockMvc = MockMvcBuilders.standaloneSetup(remesasResource)
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
    public static Remesas createEntity(EntityManager em) {
        Remesas remesas = new Remesas()
            .empresa(DEFAULT_EMPRESA)
            .tasa(DEFAULT_TASA)
            .fechaAct(DEFAULT_FECHA_ACT);
        return remesas;
    }

    @Before
    public void initTest() {
        remesas = createEntity(em);
    }

    @Test
    @Transactional
    public void createRemesas() throws Exception {
        int databaseSizeBeforeCreate = remesasRepository.findAll().size();

        // Create the Remesas
        RemesasDTO remesasDTO = remesasMapper.toDto(remesas);
        restRemesasMockMvc.perform(post("/api/remesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remesasDTO)))
            .andExpect(status().isCreated());

        // Validate the Remesas in the database
        List<Remesas> remesasList = remesasRepository.findAll();
        assertThat(remesasList).hasSize(databaseSizeBeforeCreate + 1);
        Remesas testRemesas = remesasList.get(remesasList.size() - 1);
        assertThat(testRemesas.getEmpresa()).isEqualTo(DEFAULT_EMPRESA);
        assertThat(testRemesas.getTasa()).isEqualTo(DEFAULT_TASA);
        assertThat(testRemesas.getFechaAct()).isEqualTo(DEFAULT_FECHA_ACT);
    }

    @Test
    @Transactional
    public void createRemesasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = remesasRepository.findAll().size();

        // Create the Remesas with an existing ID
        remesas.setId(1L);
        RemesasDTO remesasDTO = remesasMapper.toDto(remesas);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemesasMockMvc.perform(post("/api/remesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remesasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Remesas in the database
        List<Remesas> remesasList = remesasRepository.findAll();
        assertThat(remesasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = remesasRepository.findAll().size();
        // set the field null
        remesas.setEmpresa(null);

        // Create the Remesas, which fails.
        RemesasDTO remesasDTO = remesasMapper.toDto(remesas);

        restRemesasMockMvc.perform(post("/api/remesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remesasDTO)))
            .andExpect(status().isBadRequest());

        List<Remesas> remesasList = remesasRepository.findAll();
        assertThat(remesasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTasaIsRequired() throws Exception {
        int databaseSizeBeforeTest = remesasRepository.findAll().size();
        // set the field null
        remesas.setTasa(null);

        // Create the Remesas, which fails.
        RemesasDTO remesasDTO = remesasMapper.toDto(remesas);

        restRemesasMockMvc.perform(post("/api/remesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remesasDTO)))
            .andExpect(status().isBadRequest());

        List<Remesas> remesasList = remesasRepository.findAll();
        assertThat(remesasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRemesas() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList
        restRemesasMockMvc.perform(get("/api/remesas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remesas.getId().intValue())))
            .andExpect(jsonPath("$.[*].empresa").value(hasItem(DEFAULT_EMPRESA.toString())))
            .andExpect(jsonPath("$.[*].tasa").value(hasItem(DEFAULT_TASA.toString())))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));
    }
    
    @Test
    @Transactional
    public void getRemesas() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get the remesas
        restRemesasMockMvc.perform(get("/api/remesas/{id}", remesas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(remesas.getId().intValue()))
            .andExpect(jsonPath("$.empresa").value(DEFAULT_EMPRESA.toString()))
            .andExpect(jsonPath("$.tasa").value(DEFAULT_TASA.toString()))
            .andExpect(jsonPath("$.fechaAct").value(DEFAULT_FECHA_ACT.toString()));
    }

    @Test
    @Transactional
    public void getAllRemesasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where empresa equals to DEFAULT_EMPRESA
        defaultRemesasShouldBeFound("empresa.equals=" + DEFAULT_EMPRESA);

        // Get all the remesasList where empresa equals to UPDATED_EMPRESA
        defaultRemesasShouldNotBeFound("empresa.equals=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllRemesasByEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where empresa in DEFAULT_EMPRESA or UPDATED_EMPRESA
        defaultRemesasShouldBeFound("empresa.in=" + DEFAULT_EMPRESA + "," + UPDATED_EMPRESA);

        // Get all the remesasList where empresa equals to UPDATED_EMPRESA
        defaultRemesasShouldNotBeFound("empresa.in=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    public void getAllRemesasByEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where empresa is not null
        defaultRemesasShouldBeFound("empresa.specified=true");

        // Get all the remesasList where empresa is null
        defaultRemesasShouldNotBeFound("empresa.specified=false");
    }

    @Test
    @Transactional
    public void getAllRemesasByTasaIsEqualToSomething() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where tasa equals to DEFAULT_TASA
        defaultRemesasShouldBeFound("tasa.equals=" + DEFAULT_TASA);

        // Get all the remesasList where tasa equals to UPDATED_TASA
        defaultRemesasShouldNotBeFound("tasa.equals=" + UPDATED_TASA);
    }

    @Test
    @Transactional
    public void getAllRemesasByTasaIsInShouldWork() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where tasa in DEFAULT_TASA or UPDATED_TASA
        defaultRemesasShouldBeFound("tasa.in=" + DEFAULT_TASA + "," + UPDATED_TASA);

        // Get all the remesasList where tasa equals to UPDATED_TASA
        defaultRemesasShouldNotBeFound("tasa.in=" + UPDATED_TASA);
    }

    @Test
    @Transactional
    public void getAllRemesasByTasaIsNullOrNotNull() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where tasa is not null
        defaultRemesasShouldBeFound("tasa.specified=true");

        // Get all the remesasList where tasa is null
        defaultRemesasShouldNotBeFound("tasa.specified=false");
    }

    @Test
    @Transactional
    public void getAllRemesasByFechaActIsEqualToSomething() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where fechaAct equals to DEFAULT_FECHA_ACT
        defaultRemesasShouldBeFound("fechaAct.equals=" + DEFAULT_FECHA_ACT);

        // Get all the remesasList where fechaAct equals to UPDATED_FECHA_ACT
        defaultRemesasShouldNotBeFound("fechaAct.equals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllRemesasByFechaActIsInShouldWork() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where fechaAct in DEFAULT_FECHA_ACT or UPDATED_FECHA_ACT
        defaultRemesasShouldBeFound("fechaAct.in=" + DEFAULT_FECHA_ACT + "," + UPDATED_FECHA_ACT);

        // Get all the remesasList where fechaAct equals to UPDATED_FECHA_ACT
        defaultRemesasShouldNotBeFound("fechaAct.in=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllRemesasByFechaActIsNullOrNotNull() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where fechaAct is not null
        defaultRemesasShouldBeFound("fechaAct.specified=true");

        // Get all the remesasList where fechaAct is null
        defaultRemesasShouldNotBeFound("fechaAct.specified=false");
    }

    @Test
    @Transactional
    public void getAllRemesasByFechaActIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where fechaAct greater than or equals to DEFAULT_FECHA_ACT
        defaultRemesasShouldBeFound("fechaAct.greaterOrEqualThan=" + DEFAULT_FECHA_ACT);

        // Get all the remesasList where fechaAct greater than or equals to UPDATED_FECHA_ACT
        defaultRemesasShouldNotBeFound("fechaAct.greaterOrEqualThan=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllRemesasByFechaActIsLessThanSomething() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        // Get all the remesasList where fechaAct less than or equals to DEFAULT_FECHA_ACT
        defaultRemesasShouldNotBeFound("fechaAct.lessThan=" + DEFAULT_FECHA_ACT);

        // Get all the remesasList where fechaAct less than or equals to UPDATED_FECHA_ACT
        defaultRemesasShouldBeFound("fechaAct.lessThan=" + UPDATED_FECHA_ACT);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRemesasShouldBeFound(String filter) throws Exception {
        restRemesasMockMvc.perform(get("/api/remesas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remesas.getId().intValue())))
            .andExpect(jsonPath("$.[*].empresa").value(hasItem(DEFAULT_EMPRESA.toString())))
            .andExpect(jsonPath("$.[*].tasa").value(hasItem(DEFAULT_TASA.toString())))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));

        // Check, that the count call also returns 1
        restRemesasMockMvc.perform(get("/api/remesas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRemesasShouldNotBeFound(String filter) throws Exception {
        restRemesasMockMvc.perform(get("/api/remesas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRemesasMockMvc.perform(get("/api/remesas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRemesas() throws Exception {
        // Get the remesas
        restRemesasMockMvc.perform(get("/api/remesas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemesas() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        int databaseSizeBeforeUpdate = remesasRepository.findAll().size();

        // Update the remesas
        Remesas updatedRemesas = remesasRepository.findById(remesas.getId()).get();
        // Disconnect from session so that the updates on updatedRemesas are not directly saved in db
        em.detach(updatedRemesas);
        updatedRemesas
            .empresa(UPDATED_EMPRESA)
            .tasa(UPDATED_TASA)
            .fechaAct(UPDATED_FECHA_ACT);
        RemesasDTO remesasDTO = remesasMapper.toDto(updatedRemesas);

        restRemesasMockMvc.perform(put("/api/remesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remesasDTO)))
            .andExpect(status().isOk());

        // Validate the Remesas in the database
        List<Remesas> remesasList = remesasRepository.findAll();
        assertThat(remesasList).hasSize(databaseSizeBeforeUpdate);
        Remesas testRemesas = remesasList.get(remesasList.size() - 1);
        assertThat(testRemesas.getEmpresa()).isEqualTo(UPDATED_EMPRESA);
        assertThat(testRemesas.getTasa()).isEqualTo(UPDATED_TASA);
        assertThat(testRemesas.getFechaAct()).isEqualTo(UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void updateNonExistingRemesas() throws Exception {
        int databaseSizeBeforeUpdate = remesasRepository.findAll().size();

        // Create the Remesas
        RemesasDTO remesasDTO = remesasMapper.toDto(remesas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemesasMockMvc.perform(put("/api/remesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remesasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Remesas in the database
        List<Remesas> remesasList = remesasRepository.findAll();
        assertThat(remesasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRemesas() throws Exception {
        // Initialize the database
        remesasRepository.saveAndFlush(remesas);

        int databaseSizeBeforeDelete = remesasRepository.findAll().size();

        // Get the remesas
        restRemesasMockMvc.perform(delete("/api/remesas/{id}", remesas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Remesas> remesasList = remesasRepository.findAll();
        assertThat(remesasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remesas.class);
        Remesas remesas1 = new Remesas();
        remesas1.setId(1L);
        Remesas remesas2 = new Remesas();
        remesas2.setId(remesas1.getId());
        assertThat(remesas1).isEqualTo(remesas2);
        remesas2.setId(2L);
        assertThat(remesas1).isNotEqualTo(remesas2);
        remesas1.setId(null);
        assertThat(remesas1).isNotEqualTo(remesas2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemesasDTO.class);
        RemesasDTO remesasDTO1 = new RemesasDTO();
        remesasDTO1.setId(1L);
        RemesasDTO remesasDTO2 = new RemesasDTO();
        assertThat(remesasDTO1).isNotEqualTo(remesasDTO2);
        remesasDTO2.setId(remesasDTO1.getId());
        assertThat(remesasDTO1).isEqualTo(remesasDTO2);
        remesasDTO2.setId(2L);
        assertThat(remesasDTO1).isNotEqualTo(remesasDTO2);
        remesasDTO1.setId(null);
        assertThat(remesasDTO1).isNotEqualTo(remesasDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(remesasMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(remesasMapper.fromId(null)).isNull();
    }
}

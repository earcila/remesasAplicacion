package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.RemesasService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.RemesasDTO;
import io.github.jhipster.application.service.dto.RemesasCriteria;
import io.github.jhipster.application.service.RemesasQueryService;
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
 * REST controller for managing Remesas.
 */
@RestController
@RequestMapping("/api")
public class RemesasResource {

    private final Logger log = LoggerFactory.getLogger(RemesasResource.class);

    private static final String ENTITY_NAME = "remesas";

    private final RemesasService remesasService;

    private final RemesasQueryService remesasQueryService;

    public RemesasResource(RemesasService remesasService, RemesasQueryService remesasQueryService) {
        this.remesasService = remesasService;
        this.remesasQueryService = remesasQueryService;
    }

    /**
     * POST  /remesas : Create a new remesas.
     *
     * @param remesasDTO the remesasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new remesasDTO, or with status 400 (Bad Request) if the remesas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/remesas")
    @Timed
    public ResponseEntity<RemesasDTO> createRemesas(@Valid @RequestBody RemesasDTO remesasDTO) throws URISyntaxException {
        log.debug("REST request to save Remesas : {}", remesasDTO);
        if (remesasDTO.getId() != null) {
            throw new BadRequestAlertException("A new remesas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RemesasDTO result = remesasService.save(remesasDTO);
        return ResponseEntity.created(new URI("/api/remesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /remesas : Updates an existing remesas.
     *
     * @param remesasDTO the remesasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated remesasDTO,
     * or with status 400 (Bad Request) if the remesasDTO is not valid,
     * or with status 500 (Internal Server Error) if the remesasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/remesas")
    @Timed
    public ResponseEntity<RemesasDTO> updateRemesas(@Valid @RequestBody RemesasDTO remesasDTO) throws URISyntaxException {
        log.debug("REST request to update Remesas : {}", remesasDTO);
        if (remesasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RemesasDTO result = remesasService.save(remesasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, remesasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /remesas : get all the remesas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of remesas in body
     */
    @GetMapping("/remesas")
    @Timed
    public ResponseEntity<List<RemesasDTO>> getAllRemesas(RemesasCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Remesas by criteria: {}", criteria);
        Page<RemesasDTO> page = remesasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/remesas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /remesas/count : count all the remesas.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/remesas/count")
    @Timed
    public ResponseEntity<Long> countRemesas(RemesasCriteria criteria) {
        log.debug("REST request to count Remesas by criteria: {}", criteria);
        return ResponseEntity.ok().body(remesasQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /remesas/:id : get the "id" remesas.
     *
     * @param id the id of the remesasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the remesasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/remesas/{id}")
    @Timed
    public ResponseEntity<RemesasDTO> getRemesas(@PathVariable Long id) {
        log.debug("REST request to get Remesas : {}", id);
        Optional<RemesasDTO> remesasDTO = remesasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remesasDTO);
    }

    /**
     * DELETE  /remesas/:id : delete the "id" remesas.
     *
     * @param id the id of the remesasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/remesas/{id}")
    @Timed
    public ResponseEntity<Void> deleteRemesas(@PathVariable Long id) {
        log.debug("REST request to delete Remesas : {}", id);
        remesasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

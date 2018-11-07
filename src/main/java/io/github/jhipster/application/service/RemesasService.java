package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.RemesasDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Remesas.
 */
public interface RemesasService {

    /**
     * Save a remesas.
     *
     * @param remesasDTO the entity to save
     * @return the persisted entity
     */
    RemesasDTO save(RemesasDTO remesasDTO);

    /**
     * Get all the remesas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RemesasDTO> findAll(Pageable pageable);


    /**
     * Get the "id" remesas.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RemesasDTO> findOne(Long id);

    /**
     * Delete the "id" remesas.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RemesasService;
import io.github.jhipster.application.domain.Remesas;
import io.github.jhipster.application.repository.RemesasRepository;
import io.github.jhipster.application.service.dto.RemesasDTO;
import io.github.jhipster.application.service.mapper.RemesasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Remesas.
 */
@Service
@Transactional
public class RemesasServiceImpl implements RemesasService {

    private final Logger log = LoggerFactory.getLogger(RemesasServiceImpl.class);

    private final RemesasRepository remesasRepository;

    private final RemesasMapper remesasMapper;

    public RemesasServiceImpl(RemesasRepository remesasRepository, RemesasMapper remesasMapper) {
        this.remesasRepository = remesasRepository;
        this.remesasMapper = remesasMapper;
    }

    /**
     * Save a remesas.
     *
     * @param remesasDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RemesasDTO save(RemesasDTO remesasDTO) {
        log.debug("Request to save Remesas : {}", remesasDTO);

        Remesas remesas = remesasMapper.toEntity(remesasDTO);
        remesas = remesasRepository.save(remesas);
        return remesasMapper.toDto(remesas);
    }

    /**
     * Get all the remesas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RemesasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Remesas");
        return remesasRepository.findAll(pageable)
            .map(remesasMapper::toDto);
    }


    /**
     * Get one remesas by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RemesasDTO> findOne(Long id) {
        log.debug("Request to get Remesas : {}", id);
        return remesasRepository.findById(id)
            .map(remesasMapper::toDto);
    }

    /**
     * Delete the remesas by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Remesas : {}", id);
        remesasRepository.deleteById(id);
    }
}

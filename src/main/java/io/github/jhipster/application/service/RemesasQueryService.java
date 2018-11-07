package io.github.jhipster.application.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.jhipster.application.domain.Remesas;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.RemesasRepository;
import io.github.jhipster.application.service.dto.RemesasCriteria;
import io.github.jhipster.application.service.dto.RemesasDTO;
import io.github.jhipster.application.service.mapper.RemesasMapper;

/**
 * Service for executing complex queries for Remesas entities in the database.
 * The main input is a {@link RemesasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RemesasDTO} or a {@link Page} of {@link RemesasDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RemesasQueryService extends QueryService<Remesas> {

    private final Logger log = LoggerFactory.getLogger(RemesasQueryService.class);

    private final RemesasRepository remesasRepository;

    private final RemesasMapper remesasMapper;

    public RemesasQueryService(RemesasRepository remesasRepository, RemesasMapper remesasMapper) {
        this.remesasRepository = remesasRepository;
        this.remesasMapper = remesasMapper;
    }

    /**
     * Return a {@link List} of {@link RemesasDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RemesasDTO> findByCriteria(RemesasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Remesas> specification = createSpecification(criteria);
        return remesasMapper.toDto(remesasRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RemesasDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RemesasDTO> findByCriteria(RemesasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Remesas> specification = createSpecification(criteria);
        return remesasRepository.findAll(specification, page)
            .map(remesasMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RemesasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Remesas> specification = createSpecification(criteria);
        return remesasRepository.count(specification);
    }

    /**
     * Function to convert RemesasCriteria to a {@link Specification}
     */
    private Specification<Remesas> createSpecification(RemesasCriteria criteria) {
        Specification<Remesas> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Remesas_.id));
            }
            if (criteria.getEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresa(), Remesas_.empresa));
            }
            if (criteria.getTasa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTasa(), Remesas_.tasa));
            }
            if (criteria.getFechaAct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAct(), Remesas_.fechaAct));
            }
        }
        return specification;
    }
}

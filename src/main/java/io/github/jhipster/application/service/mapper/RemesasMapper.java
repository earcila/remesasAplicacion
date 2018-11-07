package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.RemesasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Remesas and its DTO RemesasDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RemesasMapper extends EntityMapper<RemesasDTO, Remesas> {



    default Remesas fromId(Long id) {
        if (id == null) {
            return null;
        }
        Remesas remesas = new Remesas();
        remesas.setId(id);
        return remesas;
    }
}

package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Remesas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Remesas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemesasRepository extends JpaRepository<Remesas, Long>, JpaSpecificationExecutor<Remesas> {

}

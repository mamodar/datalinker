package de.rki.mamodar;

import java.util.ArrayList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * The value repository extending a JPA Repository.
 *
 * @author Kyanoush Yahosseini
 */
public interface ValueRepository extends JpaRepository<Value, Long> {

  ArrayList<Value> getByProjectRdmoId(@Param("projectRdmoId") Long projectRdmoId);

  ArrayList<Value> getByProjectRdmoId(@Param("projectRdmoId") Long projectRdmoId, Sort projectName);

}

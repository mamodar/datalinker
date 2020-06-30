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

  /**
   * Gets a list of all values for a project by its rdmo id.
   *
   * @param projectRdmoId the project rdmo id
   * @return the by project rdmo id
   */
  ArrayList<Value> getByProjectRdmoId(@Param("projectRdmoId") Long projectRdmoId);

  /**
   * Gets a list of all values for a project by its rdmo id sorted by projectName.
   *
   * @param projectRdmoId the project rdmo id
   * @param projectName   the project name
   * @return the by project rdmo id
   */
  ArrayList<Value> getByProjectRdmoId(@Param("projectRdmoId") Long projectRdmoId, Sort projectName);

}

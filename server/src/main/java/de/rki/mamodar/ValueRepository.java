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
   * Get values by their {@link Project}.
   *
   * @param project     the search project
   * @param projectName
   * @return a list of values
   */
  ArrayList<Value> getByProject(@Param("project") Project project, Sort projectName);

}

package de.rki.mamodar;

import java.util.List;
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
   * @param project the search project
   * @return a list of values
   */
  List<Value> getByProject(@Param("project") Project project);

}

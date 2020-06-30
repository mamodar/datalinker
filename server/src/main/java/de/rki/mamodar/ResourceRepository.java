package de.rki.mamodar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * The resource repository extending a JPA Repository.
 *
 * @author Kyanoush Yahosseini
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {

  /**
   * Find by project list.
   *
   * @param project the  search project
   * @return The list of found resources
   */
  List<Resource> getByProject(@Param("project") Project project);

  /**
   * Gets a list of resources by the rdmo id of their project.
   *
   * @param projectRdmoId the project rdmo id
   * @return the by project rdmo id
   */
  List<Resource> getByProjectRdmoId(@Param("rdmo_id") Long projectRdmoId);
}

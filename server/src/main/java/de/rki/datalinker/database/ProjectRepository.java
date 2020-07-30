package de.rki.datalinker.database;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The project repository extending a JPA Repository.
 *
 * @author Kyanoush Yahosseini
 */
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

  /**
   * Search a project by rdmo id.
   *
   * @param rdmoId the rdmo id
   * @return An Optional Project.
   */
  @Query(value = "SELECT p FROM Project p LEFT JOIN FETCH p.owner WHERE p.rdmoId = :rdmo_id")
  Optional<Project> findByRdmoId(@Param("rdmo_id") long rdmoId);

  /**
   * Search projects by a query parameter.
   *
   * @param query the query
   * @return An Optional List of Projects.
   */
  @Query(
      value = "SELECT * FROM project p WHERE p.id IN (SELECT id FROM search_view, plainto_tsquery(:query) AS q WHERE (tsv @@ q));",
      nativeQuery = true)
  Optional<List<Project>> searchFTS(@Param("query") String query);

}

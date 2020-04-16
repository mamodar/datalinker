package de.rki.mamodar;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  Optional<Project> findByRdmoId(@Param("rdmo_id") long rdmoId);
  @Query(
      value="SELECT * FROM project p WHERE p.id IN (SELECT id FROM search_view, plainto_tsquery(:query) AS q WHERE (tsv @@ q));",
      nativeQuery = true)
    Optional<ArrayList<Project>> searchFTS(@Param("query") String query);

}

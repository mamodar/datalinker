package de.rki.datalinker.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * The haystack repository extending a JPA Repository.
 */
public interface HaystackRepository extends JpaRepository<Haystack, Long> {

  /**
   * Find a project haystack by its rdmo id.
   *
   * @param rdmoId a rdmo id
   * @return the haystack for a project
   */
  Haystack findByRdmoId(@Param("rdmo_id") Long rdmoId);
}

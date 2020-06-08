package de.rki.mamodar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * The resource repository extending a JPA Repository.
 * @author Kyanoush Yahosseini
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {

  List<Resource> findByProject(@Param("project") Project project);
}

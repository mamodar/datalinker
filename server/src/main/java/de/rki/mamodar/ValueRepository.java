package de.rki.mamodar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ValueRepository extends JpaRepository<Value, Long> {

  List<Value> getByProjectId(@Param("project_id") long projectId);

}

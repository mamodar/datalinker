package de.rki.mamodar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  List<Project> findByProjectNameContainingIgnoreCase(@Param("projectName") String projectName);

  List<Project> findByProjectName(@Param("projectName") String projectName);

}

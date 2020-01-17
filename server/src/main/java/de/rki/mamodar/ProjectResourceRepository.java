package de.rki.mamodar;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProjectResourceRepository extends JpaRepository<ProjectResource, Long> {

  Collection<ProjectResource> findProjectResourcesByProject_Id(@Param("project_id") long projectId);

  Collection<ProjectResource> findProjectResourcesByResource_Id(
      @Param("resource_id") long resourceId);

}

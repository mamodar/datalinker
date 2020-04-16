package de.rki.mamodar;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

  Collection<Relationship> findRelationshipsByProject_Id(@Param("project_id") long projectId);

  Collection<Relationship> findRelationshipsByResource_Id(@Param("resource_id") long resourceId);

  Collection<Relationship> findRelationshipsByResource_IdAndProject_Id(@Param("resource_id") long resourceId,@Param("project_id") long projectId);
}

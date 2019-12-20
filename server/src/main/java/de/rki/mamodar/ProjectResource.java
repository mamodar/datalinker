package de.rki.mamodar;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ProjectResource {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  Long id;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date creationTimestamp;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "project_id")
  Project project;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resource_id")
  Resource resource;

  ProjectResource() {

  }

  public Long getId() {
    return id;
  }

  public Date getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }


}

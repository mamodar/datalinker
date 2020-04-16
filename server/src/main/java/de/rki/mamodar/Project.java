package de.rki.mamodar;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "rdmo_id",nullable = true,updatable = false)
  private Long rdmoId;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date creationTimestamp;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "description")
  private String description;

  @Column(name = "owner")
  private String owner;

  public Project() {
  }

  public Project(String projectName) {
  }

  public Long getId() {
    return id;
  }

  public Long getRdmoId() {
    return rdmoId;
  }

  public void setRdmoId(Long rdmoId) {
    this.rdmoId = rdmoId;
  }

  public void setCreationTimestamp(Date date) {
    this.creationTimestamp = date;
  }

  public Date getCreationTimestamp() {
    return creationTimestamp;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getDescription() {
    return description;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }
}


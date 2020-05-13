package de.rki.mamodar;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date updatedTimestamp;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "description")
  private String description;

  @Column(name = "owner")
  private String owner;

  @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
  private List<Resource> resources;

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

  public Date getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  public void setUpdatedTimestamp(Date updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
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

  public List<Resource> getResources() {
    return resources;
  }

  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }
}


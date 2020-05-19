package de.rki.mamodar;

import java.lang.reflect.Array;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * This entity corresponds to the representation of a project in the database.
 * A project is a RDMO project imported by {@link de.rki.mamodar.RdmoRestConsumer}.
 * @author Kyanoush Yahosseini
 */
@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "rdmo_id",nullable = true,updatable = false)
  private Long rdmoId;

  /**
   * The timestamp when the project was first created/imported from RDMO.
   */
  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date creationTimestamp;

  /**
   * The timestamp of the last change to the project entity.
   */
  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date updatedTimestamp;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "description")
  private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  @Column(name = "owner")
  private List<User> owner;

  @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
  private List<Resource> resources;

  /**
   * Instantiates a new empty project.
   */
  public Project() {
  }


  /**
   * Gets the auto generated id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the imported RDMO id.
   *
   * @return the RDMO id
   */
  public Long getRdmoId() {
    return rdmoId;
  }

  /**
   * Sets the imported RDMO id.
   *
   * @param rdmoId the RDMO id
   */
  public void setRdmoId(Long rdmoId) {
    this.rdmoId = rdmoId;
  }

  /**
   * Sets the creation timestamp.
   *
   * @param date the date
   */
  public void setCreationTimestamp(Date date) {
    this.creationTimestamp = date;
  }

  /**
   * Gets the creation timestamp.
   *
   * @return the creation timestamp
   */
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }

  /**
   * Gets the last time updated timestamp.
   *
   * @return the updated timestamp
   */
  public Date getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  /**
   * Sets the last time updated timestamp.
   *
   * @param updatedTimestamp the updated timestamp
   */
  public void setUpdatedTimestamp(Date updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
  }

  /**
   * Gets project name.
   *
   * @return the project name
   */
  public String getProjectName() {
    return projectName;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets project name.
   *
   * @param projectName the project name
   */
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets owner.
   *
   * @return the owner
   */
  public List<User> getOwner() {
    return owner;
  }

  /**
   * Sets owner.
   *
   * @param owner the owner
   */
  public void setOwner(List<User> owner) {
    if (this.owner == null) {
      this.owner = new ArrayList<>();
    }
    this.owner.clear();
    this.owner.addAll(owner);
  }

  /**
   * Appends the owner list.
   *
   * @param owner the owner
   */
  public void addOwner(User owner) {
    this.owner.add(owner);
  }

  /**
   * Gets resources.
   *
   * @return the resources
   */
  public List<Resource> getResources() {
    return resources;
  }

  /**
   * Sets resources.
   *
   * @param resources the resources
   */
  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }

  public ArrayList<User> findDuplicatesByUsername(User deleteOwner) {
    ArrayList<User> duplicates = new ArrayList<>();
    for(User user : this.owner){
      if(user.getUsername().equals(deleteOwner.getUsername())) {
        duplicates.add(user);
      }
    }
    return duplicates;
  }
}


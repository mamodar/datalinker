package de.rki.mamodar;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This entity corresponds to the representation of a resource in the database.
 * Resources are manipulated by calls to {@link de.rki.mamodar.ResourceController}.
 * @author Kyanoush Yahosseini
 */
@Entity
@Table(name = "resource")
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  /**
   * The timestamp when the resource was created.
   */
  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date creationTimestamp;

  /**
   * The timestamp when the resource was last updated.
   */
  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date updatedTimestamp;

  @Column(name = "path",nullable = false)
  private String path;

  @Column(name="location",nullable = false)
  @Enumerated(EnumType.STRING)
  private ResourceType resourceType;

  @Column(name="size")
  private Float size;

  @Column(name = "personal")
  private Boolean isPersonal = true;

  @Column(name = "archived")
  private Boolean isArchived = false;

  @Column(name = "third_party")
  private Boolean isThirdParty = false;


  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "created_by_user_id",nullable = false)
  private User createdByUser;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "updated_by_user_id",nullable = false)
  private User updatedByUser;

  @ManyToOne(targetEntity = Project.class)
  private Project project;

  /**
   * Instantiate a new empty Resource.
   */
  public Resource() {
  }

  /**
   * Instantiate a new Resource from a {@link ResourceDTO}.
   *
   * @param resourceDTO the resource send dto
   */
  public Resource(ResourceDTO resourceDTO) {

    this.path = resourceDTO.getPath();
    this.resourceType = ResourceType.valueOf(resourceDTO.getLocation());
    this.description = resourceDTO.getDescription();
    this.isArchived = resourceDTO.getArchived();
    this.isPersonal = resourceDTO.getPersonal();
    this.isThirdParty = resourceDTO.getThirdParty();
    this.size = resourceDTO.getSize();

  }

  /**
   * Update a new Resource from a {@link ResourceDTO}.
   *
   * @param resourceDTO the resource send dto
   */
  public void update(ResourceDTO resourceDTO) {
    Objects.requireNonNull(resourceDTO.getPath());
    Objects.requireNonNull(resourceDTO.getLocation());

    this.path = resourceDTO.getPath();
    this.resourceType = ResourceType.valueOf(resourceDTO.getLocation());
    this.description = resourceDTO.getDescription() != null ? resourceDTO.getDescription() : this.description;
    this.isArchived = resourceDTO.getArchived() != null ? resourceDTO.getArchived() : this.isArchived;
    this.isPersonal = resourceDTO.getPersonal() != null ? resourceDTO.getPersonal() : this.isPersonal;
    this.isThirdParty = resourceDTO.getThirdParty() != null ? resourceDTO.getThirdParty() : this.isThirdParty;
    this.size = resourceDTO.getSize() != null ? resourceDTO.getSize() : this.size;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }


  /**
   * Gets creation timestamp.
   *
   * @return the creation timestamp
   */
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }

  /**
   * Sets creation timestamp.
   *
   * @param creationTimestamp the creation timestamp
   */
  public void setCreationTimestamp(Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  /**
   * Gets updated timestamp.
   *
   * @return the updated timestamp
   */
  public Date getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  /**
   * Sets updated timestamp.
   *
   * @param updatedTimestamp the updated timestamp
   */
  public void setUpdatedTimestamp(Date updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
  }

  /**
   * Gets updated by user.
   *
   * @return the updated by user
   */
  public User getUpdatedByUser() {
    return updatedByUser;
  }

  /**
   * Sets updated by user.
   *
   * @param updatedByUser the updated by user
   */
  public void setUpdatedByUser(User updatedByUser) {
    this.updatedByUser = updatedByUser;
  }

  /**
   * Gets created by user.
   *
   * @return the created by user
   */
  public User getCreatedByUser() {
    return createdByUser;
  }

  /**
   * Sets created by user.
   *
   * @param user the user
   */
  public void setCreatedByUser(User user) {
    this.createdByUser = user;
  }

  /**
   * Gets path.
   *
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets path.
   *
   * @param path the path
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Gets resource type.
   *
   * @return the resource type
   */
  public ResourceType getResourceType() {
    return resourceType;
  }

  /**
   * Sets resource type.
   *
   * @param resourceType the resource type
   */
  public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
  }

  /**
   * Gets size.
   *
   * @return the size
   */
  public Float getSize() {
    return size;
  }

  /**
   * Sets size.
   *
   * @param size the size
   */
  public void setSize(Float size) {
    this.size = size;
  }

  /**
   * Gets personal.
   *
   * @return the personal
   */
  public Boolean getPersonal() {
    return isPersonal;
  }

  /**
   * Sets personal.
   *
   * @param personal the personal
   */
  public void setPersonal(Boolean personal) {
    isPersonal = personal;
  }

  /**
   * Gets archived.
   *
   * @return the archived
   */
  public Boolean getArchived() {
    return isArchived;
  }

  /**
   * Sets archived.
   *
   * @param archived the archived
   */
  public void setArchived(Boolean archived) {
    isArchived = archived;
  }

  /**
   * Gets third party.
   *
   * @return the third party
   */
  public Boolean getThirdParty() {
    return isThirdParty;
  }

  /**
   * Sets third party.
   *
   * @param thirdParty the third party
   */
  public void setThirdParty(Boolean thirdParty) {
    isThirdParty = thirdParty;
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
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets project.
   *
   * @return the project
   */
  public Project getProject() {
    return project;
  }

  /**
   * Sets project.
   *
   * @param project the project
   */
  public void setProject(Project project) {
    this.project = project;
  }


}

package de.rki.mamodar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class provides a DTO for sending {@link de.rki.mamodar.Resource}s as part of {@link de.rki.mamodar.ProjectController} or {@link de.rki.mamodar.ResourceController}.
 *
 * @author Kyanoush Yahosseini
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceSendDTO {
  private Long id;
  private String createdBy;
  private String updatedBy;
  private String path;
  private String location;
  private String createdTimestamp;
  private String updatedTimestamp;
  private Float size;
  private Boolean isPersonal;
  private Boolean isArchived = false;
  private Boolean isThirdParty = false;
  private String description;
  private Long projectId;


  /**
   * Instantiates a new Resource send dto.
   */
  public ResourceSendDTO(){}

  /**
   * Instantiates a new Resource send dto.
   *
   * @param resource the resource
   */
  public ResourceSendDTO(Resource resource){
  this.id = resource.getId();
  this.location = resource.getResourceType().name();
  this.path = resource.getPath();
  this.isArchived = resource.getArchived();
  this.isThirdParty = resource.getThirdParty();
  this.isPersonal = resource.getPersonal();
  this.size = resource.getSize();
  this.createdTimestamp = resource.getCreationTimestamp().toString();
  this.updatedTimestamp = resource.getUpdatedTimestamp().toString();
  this.projectId = resource.getProject().getId();
  this.createdBy = resource.getCreatedByUser().getUsername();
  this.updatedBy = resource.getUpdatedByUser().getUsername();
  this.description = resource.getDescription();
}

  /**
   * Gets created timestamp.
   *
   * @return the created timestamp
   */
  public String getCreatedTimestamp() {
    return createdTimestamp;
  }

  /**
   * Sets created timestamp.
   *
   * @param createdTimestamp the created timestamp
   */
  public void setCreatedTimestamp(String createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
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
   * Gets created by.
   *
   * @return the created by
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets created by.
   *
   * @param createdBy the created by
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Gets updated by.
   *
   * @return the updated by
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * Sets updated by.
   *
   * @param updatedBy the updated by
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * Gets updated timestamp.
   *
   * @return the updated timestamp
   */
  public String getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  /**
   * Sets updated timestamp.
   *
   * @param updatedTimestamp the updated timestamp
   */
  public void setUpdatedTimestamp(String updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
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
   * Gets location.
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets location.
   *
   * @param location the location
   */
  public void setLocation(String location) {
    this.location = location;
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
   * Gets project id.
   *
   * @return the project id
   */
  public Long getProjectId() {
    return projectId;
  }

  /**
   * Sets project id.
   *
   * @param projectId the project id
   */
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
}

package de.rki.datalinker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rki.datalinker.api.ProjectController;
import de.rki.datalinker.api.ResourceController;
import de.rki.datalinker.database.Resource;

/**
 * This class provides a DTO for sending {@link Resource}s as part of {@link ProjectController} or {@link
 * ResourceController}**.
 *
 * @author Kyanoush Yahosseini
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDTO {

  private Long id;
  private String createdBy;
  private String updatedBy;
  private String path;
  private String location;
  private String license;
  private String type;
  private String createdTimestamp;
  private String updatedTimestamp;
  private Float size;
  private Boolean isPersonal = true;
  private Boolean isArchived = false;
  private String description;
  private Long projectId;


  /**
   * Instantiates a new empty Resource dto.
   */
  public ResourceDTO() {
  }

  /**
   * Instantiates a new Resource dto from a resource DAO.
   *
   * @param resource the resource
   */
  public ResourceDTO(Resource resource) {
    this.id = resource.getId();
    this.location = resource.getResourceType();
    this.path = resource.getPath();
    this.isArchived = resource.getArchived();
    this.isPersonal = resource.getPersonal();
    this.size = resource.getSize();
    this.createdTimestamp = resource.getCreationTimestamp().toString();
    this.updatedTimestamp = resource.getUpdatedTimestamp().toString();
    this.projectId = resource.getProject().getId();
    this.createdBy = resource.getCreatedByUser().getUsername();
    this.updatedBy = resource.getUpdatedByUser().getUsername();
    this.description = resource.getDescription();
    this.license = resource.getLicense();
    this.type = resource.getType();
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

  /**
   * Gets license.
   *
   * @return the license
   */
  public String getLicense() {
    return license;
  }

  /**
   * Sets license.
   *
   * @param license the license
   */
  public void setLicense(String license) {
    this.license = license;
  }

  /**
   * Gets the type of a resource.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type of a resource.
   *
   * @param type the type
   */
  public void setType(String type) {
    this.type = type;
  }
}

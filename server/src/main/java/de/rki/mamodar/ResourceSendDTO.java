package de.rki.mamodar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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


public ResourceSendDTO(){}

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

  public String getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(String createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public Long getId() {
    return id;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  public void setUpdatedTimestamp(String updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Float getSize() {
    return size;
  }

  public void setSize(Float size) {
    this.size = size;
  }

  public Boolean getPersonal() {
    return isPersonal;
  }

  public void setPersonal(Boolean personal) {
    isPersonal = personal;
  }

  public Boolean getArchived() {
    return isArchived;
  }

  public void setArchived(Boolean archived) {
    isArchived = archived;
  }

  public Boolean getThirdParty() {
    return isThirdParty;
  }

  public void setThirdParty(Boolean thirdParty) {
    isThirdParty = thirdParty;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
}

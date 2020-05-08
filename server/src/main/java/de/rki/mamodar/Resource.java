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

@Entity
@Table(name = "resource")
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date creationTimestamp;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  Date updatedTimestamp;

  @Column(name = "path",nullable = false)
  private String path;

  @Column(name="location",nullable = false)
  @Enumerated(EnumType.STRING)
  private Location location;

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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "project_id",nullable = false)
  private Project project;

  public Resource() {
  }

  public Resource(ResourceSendDTO resourceSendDTO) {

    this.path = resourceSendDTO.getPath();
    this.location = Location.valueOf(resourceSendDTO.getLocation());
    this.description =  resourceSendDTO.getDescription();
    this.isArchived = resourceSendDTO.getArchived();
    this.isPersonal = resourceSendDTO.getPersonal();
    this.isThirdParty = resourceSendDTO.getThirdParty();
    this.size = resourceSendDTO.getSize();

  }

  public void update(ResourceSendDTO resourceSendDTO) {
    Objects.requireNonNull(resourceSendDTO.getPath());
    Objects.requireNonNull(resourceSendDTO.getLocation());

    this.path = resourceSendDTO.getPath();
    this.location = Location.valueOf(resourceSendDTO.getLocation());
    this.description =  resourceSendDTO.getDescription()!=null?resourceSendDTO.getDescription():this.description;
    this.isArchived = resourceSendDTO.getArchived() != null?resourceSendDTO.getArchived():this.isArchived;
    this.isPersonal = resourceSendDTO.getPersonal()!= null?resourceSendDTO.getPersonal():this.isPersonal;
    this.isThirdParty = resourceSendDTO.getThirdParty()!= null?resourceSendDTO.getThirdParty():this.isThirdParty;
    this.size = resourceSendDTO.getSize()!=null?resourceSendDTO.getSize():this.size;
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

  public Date getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  public void setUpdatedTimestamp(Date updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
  }

  public User getUpdatedByUser() {
    return updatedByUser;
  }

  public void setUpdatedByUser(User updatedByUser) {
    this.updatedByUser = updatedByUser;
  }

  public User getCreatedByUser() {
    return createdByUser;
  }

  public void setCreatedByUser(User user) {
    this.createdByUser = user;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
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

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }


}

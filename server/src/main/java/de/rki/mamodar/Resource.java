package de.rki.mamodar;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "path")
  private String path;

  @Column
  @Enumerated(EnumType.STRING)
  private Location location;

  @Column
  private Float size;

  @Column(name = "personal")
  private Boolean isPersonal;

  @Column(name = "archived")
  private Boolean isArchived = false;

  @Column(name = "third_party")
  private Boolean isThirdParty = false;


  @Column
  private String description;


  public Resource() {
  }

  public Long getId() {
    return id;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreationTimestamp() {
    return creationTimestamp;
  }

  public Long getUserId() {
    return userId;
  }

  public String getPath() {
    return path;
  }

  public Location getLocation() {
    return location;
  }

  public String getDescription() {
    return description;
  }


  public void setCreationTimestamp(Date date) {
    this.creationTimestamp = date;
  }

  public void setSize(Float size) {
    this.size = size;
  }

  public void setPersonal(Boolean personal) {
    isPersonal = personal;
  }

  public void setArchived(Boolean archived) {
    isArchived = archived;
  }

  public void setThirdParty(Boolean thirdParty) {
    isThirdParty = thirdParty;
  }

  public Float getSize() {
    return size;
  }

  public Boolean getPersonal() {
    return isPersonal;
  }

  public Boolean getArchived() {
    return isArchived;
  }

  public Boolean getThirdParty() {
    return isThirdParty;
  }
}

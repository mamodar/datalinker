package de.rki.mamodar;

import de.rki.mamodar.rdmo.RdmoApiConsumer;
import de.rki.mamodar.rdmo.RdmoProjectDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * This DAO entity corresponding to the representation of a project in the database. A project is a RDMO project
 * imported by {@link RdmoApiConsumer}.
 *
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

  //set fetch to eager as we always want all users for a project
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @Column(name = "owner")
  private List<User> owner;

  @OneToMany(fetch = FetchType.LAZY)
  @Column(name = "value")
  private List<Value> value;

  @OneToMany(fetch = FetchType.LAZY)
  @Column(name = "resource")
  private List<Resource> resource;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "search_id")
  private Haystack haystack;

  /**
   * Instantiates a new empty project.
   */
  public Project() {
  }

  /**
   * Instantiates a  new project from a rdmo project dto.
   *
   * @param projectDTO         the project dto
   * @param valueRepository    the value repository
   * @param resourceRepository the resource repository
   * @param haystackRepository the haystack repository
   */
  public Project(RdmoProjectDTO projectDTO,
      ValueRepository valueRepository,
      ResourceRepository resourceRepository,
      HaystackRepository haystackRepository) {
    this.rdmoId = projectDTO.getId();
    this.projectName = projectDTO.getTitle();
    this.description = projectDTO.getDescription();
    this.creationTimestamp = new Date();
    this.updatedTimestamp = this.creationTimestamp;
    this.owner = new ArrayList<>();
    projectDTO.getOwners().forEach(owner -> this.owner.add(new User(owner)));
    ArrayList<Value> value = valueRepository.getByProjectRdmoId(this.rdmoId);
    this.value = new ArrayList<>(value);
    List<Resource> resource = resourceRepository.getByProjectRdmoId(this.rdmoId);
    this.resource = new ArrayList<>(resource);
    this.haystack = haystackRepository.findByRdmoId(this.rdmoId);

  }

  /**
   * Updates a project from a rdmo project dto
   *
   * @param project the project
   * @return the udpated project
   */
  public Project update(Project project) {
    this.projectName = project.getProjectName();
    this.description = project.getDescription();
    return this;
  }

  /**
   * Gets resource.
   *
   * @return the resource
   */
  public List<Resource> getResource() {
    return resource;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public List<Value> getValue() {
    return value;
  }

  /**
   * Sets value.
   *
   * @param value the value
   */
  public void setValue(List<Value> value) {
    this.value = value;
  }

  /**
   * Find duplicates by username array list.
   *
   * @param deleteOwner the delete owner
   * @return the array list
   */
  public ArrayList<User> findDuplicatesByUsername(User deleteOwner) {
    ArrayList<User> duplicates = new ArrayList<>();
    for (User user : this.owner) {
      if (user.getUsername().equals(deleteOwner.getUsername())) {
        duplicates.add(user);
      }
    }
    return duplicates;
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
   * Gets haystack. A haystack is a concatination of all string fields of a project.
   *
   * @return the haystack
   */
  public Haystack getHaystack() {
    return haystack;
  }

  /**
   * Sets haystack.  A haystack is a concatination of all string fields of a project.
   *
   * @param haystack the haystack
   */
  public void setHaystack(Haystack haystack) {
    this.haystack = haystack;
  }
}


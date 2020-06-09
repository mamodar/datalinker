package de.rki.mamodar;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This class provides a DTO for sending {@link de.rki.mamodar.Project}s as part of the {@link
 * de.rki.mamodar.ProjectController}*
 *
 * @author Kyanoush Yahosseini
 */
public class ProjectDTO {


  private Long id;
  private String creationTimestamp;
  private String projectName;
  private String description;
  private List<String> owner;

  /**
   * Instantiates a new empty Project DTO.
   */
  public ProjectDTO() {

  }

  /**
   * Instantiates a new Project DTO from a {@link de.rki.mamodar.Project} DAO.
   *
   * @param project the project
   */
  public ProjectDTO(Project project) {
    this.id = project.getId();
    this.creationTimestamp = project.creationTimestamp.toString();
    this.projectName = project.getProjectName();
    this.description = project.getDescription();
    this.owner = project.getOwner().stream().map(user -> user.getUsername()).collect(Collectors.toList());
  }

  /**
   * Gets owner.
   *
   * @return the owner
   */
  public List<String> getOwner() {
    return owner;
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
  public String getCreationTimestamp() {
    return creationTimestamp;
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

}

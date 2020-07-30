package de.rki.datalinker.dto;

import de.rki.datalinker.api.ProjectController;
import de.rki.datalinker.database.Project;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class provides a DTO for sending {@link Project}s as part of the {@link ProjectController}*
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
   * Instantiates a new Project DTO from a {@link Project} DAO.
   *
   * @param project the project
   */
  public ProjectDTO(Project project) {
    this.id = project.getId();
    this.creationTimestamp = project.getCreationTimestamp().toString();
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

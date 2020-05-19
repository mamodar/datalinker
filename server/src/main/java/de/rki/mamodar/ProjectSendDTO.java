package de.rki.mamodar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class provides a DTO for sending {@link de.rki.mamodar.Project}s as part of the {@link
 * de.rki.mamodar.ProjectController}
 *
 * @author Kyanoush Yahosseini
 */
public class ProjectSendDTO {
  private Long id;
  private String creationTimestamp;
  private String projectName;
  private String description;
  private List<String> owner;
  private ArrayList<ResourceSendDTO> resources ;

  /**
   * Instantiates a new Project DTO.
   */
  public ProjectSendDTO(){

  }

  /**
   * Instantiates a new Project DTO from a {@link de.rki.mamodar.Project} without connected {@link
   * de.rki.mamodar.Resource}s.
   *
   * @param project the project
   */
  public ProjectSendDTO(Project project){
    this.id = project.getId();
    this.creationTimestamp = project.creationTimestamp.toString();
    this.projectName = project.getProjectName();
    this.description = project.getDescription();
    this.owner = project.getOwner().stream().map(user -> user.getUsername()).collect(Collectors.toList());
  }

  /**
   * Instantiates a new Project DTO from a {@link de.rki.mamodar.Project} without connected {@link
   * de.rki.mamodar.Resource}s.
   *
   * @param project   a project
   * @param resources a list of connected resources
   */
  public ProjectSendDTO(Project project, List<Resource> resources){
    this.id = project.getId();
    this.creationTimestamp = project.creationTimestamp.toString();
    this.projectName = project.getProjectName();
    this.description = project.getDescription();
    this.owner = project.getOwner().stream().map(user -> user.getUsername()).collect(Collectors.toList());
    this.resources = new ArrayList<>();
    resources.sort(Comparator.comparing(Resource::getUpdatedTimestamp));
    Collections.reverse(resources);
    resources.forEach(resource -> this.resources.add(new ResourceSendDTO(resource)));

    //
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

  /**
   * Gets resources.
   *
   * @return the resources
   */
  public ArrayList<ResourceSendDTO> getResources() {
    return resources;
  }
}

package de.rki.mamodar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ProjectSendDTO {
  private Long id;
  private String creationTimestamp;
  private String projectName;
  private String description;
  private String owner;
  private ArrayList<ResourceSendDTO> resources ;

  public ProjectSendDTO(){

  }

  public ProjectSendDTO(Project project){
    this.id = project.getId();
    this.creationTimestamp = project.creationTimestamp.toString();
    this.projectName = project.getProjectName();
    this.description = project.getDescription();
    this.owner = project.getOwner();
  }

  public ProjectSendDTO(Project project, List<Resource> resources){
    this.id = project.getId();
    this.creationTimestamp = project.creationTimestamp.toString();
    this.projectName = project.getProjectName();
    this.description = project.getDescription();
    this.owner = project.getOwner();
    this.resources = new ArrayList<>();
    resources.sort(Comparator.comparing(Resource::getUpdatedTimestamp));
    Collections.reverse(resources);
    resources.forEach(resource -> this.resources.add(new ResourceSendDTO(resource)));

    //
  }

  public Long getId() {
    return id;
  }


  public String getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(String creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public ArrayList<ResourceSendDTO> getResources() {
    return resources;
  }

  public void setResources(ArrayList<ResourceSendDTO> resources) {
    this.resources = resources;
  }
}

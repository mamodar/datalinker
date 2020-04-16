package de.rki.mamodar;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RdmoProject {
private Long id;
private String title;
private String description;
private boolean read_only;
private RdmoOwner[] owners;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isRead_only() {
    return read_only;
  }

  public void setRead_only(boolean read_only) {
    this.read_only = read_only;
  }

  public RdmoOwner[] getOwners() {
    return owners;
  }

  public void setOwners(RdmoOwner[] owners) {
    this.owners = owners;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Project toProject(){

    Project project = new Project();
    project.setRdmoId(this.id);
    project.setProjectName(this.title);
    project.setDescription(this.description);
    project.setCreationTimestamp(new Date());
    //TODO: owners should be an array
    project.setOwner(this.owners[0].getUsername());
    return project;
  }
}

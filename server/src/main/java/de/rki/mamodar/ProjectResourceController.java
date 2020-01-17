package de.rki.mamodar;

import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ProjectResourceController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ProjectResourceRepository repository;
  private final ResourceRepository resourceRepository;
  private final ProjectRepository projectRepository;

  public ProjectResourceController(ProjectResourceRepository repository,
      ProjectRepository projectRepository, ResourceRepository resourceRepository) {
    this.repository = repository;
    this.resourceRepository = resourceRepository;
    this.projectRepository = projectRepository;
  }

  @GetMapping("/relationships/")
  Collection<ProjectResource> all() {
    log.info("GET: /relationships/");
    return repository.findAll();
  }

  @GetMapping("/relationships/projects/{id}")
  Collection<ProjectResource> allInProject(@PathVariable Long id) {
    log.info("GET: /relationships/projects/id");
    return repository.findProjectResourcesByProject_Id(id);
  }

  @GetMapping("/relationships/resources/{id}")
  Collection<ProjectResource> allInResource(@PathVariable Long id) {
    log.info("GET: /relationships/resources/id");
    return repository.findProjectResourcesByResource_Id(id);
  }



  @PostMapping("/relationships/")
  ProjectResource createEmptyProjectResource(){
    log.info("POST: /relationships/");
    ProjectResource newProjectResource = new ProjectResource();
    newProjectResource.setCreationTimestamp(new Date());
    repository.save(newProjectResource);
    log.info(String.valueOf(newProjectResource.creationTimestamp));
    return(newProjectResource);
  }

  @PutMapping("/relationships/{id}")
  ProjectResource addLink(@PathVariable Long id, @RequestBody @NotNull ProjectResource updatedProjectResource) {
    log.info("PUT: /relationships/");
    ProjectResource projectResource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("relationship", id));
    projectResource.setProject(updatedProjectResource.getProject());
    projectResource.setResource(updatedProjectResource.getResource());
    projectResource.setCreationTimestamp(new Date());
    repository.save(projectResource);
    return projectResource;
  }


  @DeleteMapping("/relationships/{id}")
  void removeLink(@PathVariable Long id) {
    log.info("DELETE: /relationships/id");
    ProjectResource projectResource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("relationship", id));
    repository.delete(projectResource);
  }

}

package de.rki.mamodar;

import java.util.Collection;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
    return repository.findAll();
  }

  @GetMapping("/relationships/projects/{id}")
  Collection<ProjectResource> allInProject(@PathVariable Long id) {
    return repository.findProjectResourcesByProject_Id(id);
  }

  @GetMapping("/relationships/resources/{id}")
  Collection<ProjectResource> allInResource(@PathVariable Long id) {
    return repository.findProjectResourcesByResource_Id(id);
  }

  @PutMapping("/relationships/")
  ProjectResource addLink(@RequestBody ProjectResource newProjectResource) {
    Project project = projectRepository.findById(newProjectResource.project.getId())
        .orElseThrow(
            () -> new ObjectNotFoundException("project", newProjectResource.project.getId()));
    Resource resource = resourceRepository.findById(newProjectResource.resource.getId())
        .orElseThrow(
            () -> new ObjectNotFoundException("resource", newProjectResource.resource.getId()));
    if (repository
        .existsProjectResourcesByProject_IdAndResource_Id(newProjectResource.project.getId(),
            newProjectResource.resource.getId())) {
      ProjectResource projectResource =
          repository
              .findProjectResourcesByProject_IdAndResource_Id(newProjectResource.project.getId(),
                  newProjectResource.resource.getId());
      projectResource.setResource(resource);
      projectResource.setProject(project);
      projectResource.setCreationTimestamp(new Date());
      return projectResource;
    } else {
      newProjectResource.setResource(resource);
      newProjectResource.setProject(project);
      newProjectResource.setCreationTimestamp(new Date());
      repository.save(newProjectResource);
      return newProjectResource;
    }
  }


  @DeleteMapping("/relationships/{id}")
  void removeLink(@PathVariable Long id) {
    log.info("DELETE: /relationships/:id");
    ProjectResource projectResource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("relationship", id));
    repository.delete(projectResource);
  }

}

package de.rki.mamodar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ResourceController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ResourceRepository repository;
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;
  @Autowired
  private AuthenticationFacade authenticationFacade;

  public ResourceController(ResourceRepository repository, ProjectRepository projectRepository,
      UserRepository userRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
  }

  @GetMapping("/resources/")
  List<ResourceSendDTO> all() {
    ArrayList<ResourceSendDTO> resources = new ArrayList<>();
    repository.findAll().forEach(resource -> resources.add(new ResourceSendDTO(resource) ));
    return resources;
  }

  @GetMapping("/resources/{id}")
  ResourceSendDTO one(@PathVariable Long id) {
    log.info("GET: /resources/id");
    Resource resource = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("resource", id));
    return new ResourceSendDTO(resource);

  }

  //creates an empty resource
  @PostMapping("/resources")
  ResourceSendDTO addResource(@RequestBody @NotNull ResourceSendDTO resourceDTO) {
    log.info("POST: /resources");
    Resource newResource = new Resource(resourceDTO);
    newResource.setUser(userRepository.getByDn(authenticationFacade.getLdapUser().getDn())) ;
    newResource.setCreationTimestamp(new Date());
    Project correspondingProject = projectRepository.findById(resourceDTO.getProjectId()).
        orElseThrow(() -> new ObjectNotFoundException("project", resourceDTO.getProjectId()));
    newResource.setProject(correspondingProject);
    repository.save(newResource);
    return new ResourceSendDTO(newResource);
  }

  @PutMapping("/resources/{id}")
  ResourceSendDTO updateOne(@PathVariable Long id, @RequestBody @NotNull ResourceSendDTO updatedResource) {
    log.info("PUT: /resources/id");
    Resource resource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("resource", id));
    resource.update(updatedResource);
    resource.setUser(userRepository.getByDn(authenticationFacade.getLdapUser().getDn())) ;
    resource.setCreationTimestamp(new Date());
    repository.save(resource);
    return new ResourceSendDTO(resource);

  }


  @DeleteMapping("/resources/{id}")
  void removeLink(@PathVariable Long id) {
    log.info("DELETE: /resources/id");
    Resource resource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("resource", id));

    Project project = projectRepository.findById(resource.getProject().getId())
        .orElseThrow(() -> new ObjectNotFoundException("project", resource.getProject().getId()));
    project.getResources().remove(resource);
    projectRepository.save(project);
    repository.delete(resource);
  }
}

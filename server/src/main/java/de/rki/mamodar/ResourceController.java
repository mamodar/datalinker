package de.rki.mamodar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller provides a REST API to create, read, update and delete access to resources.
 *
 * @author Kyanoush Yahosseini
 */
@RestController
@CrossOrigin(origins = "*")
public class ResourceController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ResourceRepository repository;
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;
  @Autowired
  private AuthenticationFacade authenticationFacade;

  /**
   * Instantiates a new Resource controller.
   *
   * @param repository        the resource repository
   * @param projectRepository the project repository
   * @param userRepository    the user repository
   */
  public ResourceController(ResourceRepository repository, ProjectRepository projectRepository,
      UserRepository userRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
  }

  /**
   * Gets a list of all resources and converts them to {@link de.rki.mamodar.ResourceSendDTO} **
   * @return a list of resource DTOs
   */
  @GetMapping("/resources/")
  List<ResourceSendDTO> all() {
    ArrayList<ResourceSendDTO> resources = new ArrayList<>();
    repository.findAll(Sort.by(Sort.Direction.ASC, "updatedTimestamp")).forEach(resource -> resources.add(new ResourceSendDTO(resource) ));
    return resources;
  }

  /**
   * Gets a resource by its id and converts it to {@link de.rki.mamodar.ResourceSendDTO}
   *
   * @param id the id
   * @return a resource DTO
   * @throws ObjectNotFoundException if resource is not found
   */
  @GetMapping("/resources/{id}")
  ResourceSendDTO one(@PathVariable Long id) {
    log.info("GET: /resources/id");
    Resource resource = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("resource", id));
    return new ResourceSendDTO(resource);

  }

  /**
   * Creates a new resource from a received resource DTO. The resource is automatically connected to a {@link de.rki.mamodar.Project} identified by id.
   *
   * @param resourceDTO the resource DTO
   * @return the created resource as an DTO
   * @throws ObjectNotFoundException if project id is not found
   */
  @PostMapping("/resources")
  ResourceSendDTO addResource(@RequestBody @NotNull ResourceSendDTO resourceDTO) {
    log.info("POST: /resources");

    Project correspondingProject = projectRepository.findById(resourceDTO.getProjectId()).
        orElseThrow(() -> new ObjectNotFoundException("project", resourceDTO.getProjectId()));

    Resource newResource = new Resource(resourceDTO);

    newResource.setCreatedByUser(userRepository.getByDn(authenticationFacade.getLdapUser().getDn()));
    newResource.setUpdatedByUser(newResource.getCreatedByUser());

    newResource.setCreationTimestamp(new Date());
    newResource.setUpdatedTimestamp(newResource.getCreationTimestamp());

    newResource.setProject(correspondingProject);
    repository.save(newResource);
    return new ResourceSendDTO(newResource);
  }

  /**
   * Updates a resource identified by id. The resource is automatically connected to a {@link de.rki.mamodar.Project} identified by id.
   *
   * @param id the resource id
   * @return the updated resource as an DTO
   * @throws ObjectNotFoundException if project id is not found or if resource id is not found
   */
  @PutMapping("/resources/{id}")
  ResourceSendDTO updateOne(@PathVariable Long id, @RequestBody @NotNull ResourceSendDTO updatedResource) {
    log.info("PUT: /resources/id");
    Resource resource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("resource", id));
    resource.update(updatedResource);
    resource.setUpdatedByUser(userRepository.getByDn(authenticationFacade.getLdapUser().getDn())); ;
    resource.setUpdatedTimestamp(new Date());
    repository.save(resource);
    return new ResourceSendDTO(resource);

  }


  /**
   * Deletes a resource identified by id.
   *
   * @param id the resource id
   * @throws ObjectNotFoundException if the resource is not found
   */
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

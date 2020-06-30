package de.rki.mamodar;

import de.rki.mamodar.rdmo.RdmoConverter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller provides a REST API to allow read access to projects and their corresponding resources. Projects are
 * read from an RDMO instance, hence create, update, or delete are not provided.
 *
 * @author Kyanoush Yahosseini
 */
@RestController
@CrossOrigin(origins = "*")
public class ProjectController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final ValueRepository valueRepository;
  private final ResourceRepository resourceRepository;


  /**
   * The autowired  {@link de.rki.mamodar.rdmo.RdmoConverter}.
   */
  @Autowired
  RdmoConverter rdmoConverter;

  /**
   * The autowired  {@link de.rki.mamodar.AuthenticationFacade} to validate the authenticity of the calling user.
   */
  @Autowired
  AuthenticationFacade authenticationFacade;

  /**
   * Instantiates a new Project controller.
   *
   * @param projectRepository  the project repository
   * @param userRepository     the user repository
   * @param resourceRepository the resource repository
   * @param valueRepository    the value repository
   */
  public ProjectController(
      ProjectRepository projectRepository,
      UserRepository userRepository,
      ResourceRepository resourceRepository,
      ValueRepository valueRepository) {
    this.projectRepository = projectRepository;
    this.userRepository = userRepository;
    this.resourceRepository = resourceRepository;
    this.valueRepository = valueRepository;
  }


  /**
   * Gets a list of the projects and converts them to {@link ProjectDTO} for a {@link de.rki.mamodar.User}.
   *
   * @return a list of projects as DTOs
   */
  @GetMapping("/projects")
  List<ProjectDTO> getAllProjects() {
    log.info("GET: /projects/rdmo " + authenticationFacade.getLdapUser().getDn());
    ArrayList<ProjectDTO> allProjects = new ArrayList<>();
    projectRepository.findAll(Sort.by(Direction.ASC, "projectName")).
        forEach(project -> allProjects.add(new ProjectDTO(project)));
    allProjects.removeIf(project -> !project.getOwner().contains(authenticationFacade.getLdapUser().getUsername()));
    return allProjects;

  }

  /**
   * Searches in all projects for projects matching the  search parameter and converts them to {@link ProjectDTO}.
   *
   * @param search the search parameter
   * @return a list of projects as DTOs
   */
  @GetMapping("/projects/search")
  List<ProjectDTO> searchAllProjects(@RequestParam(name = "search", required = false) String search,
      @RequestParam(name = "filter", required = false) String filter) {
    log.info("GET: /projects?search " + ((UserDetails) authenticationFacade.getAuthentication().getPrincipal())
        .getUsername());
    ArrayList<Project> filterProjects = new ArrayList<>();
    ArrayList<ProjectDTO> foundProjectsDTO = new ArrayList<>();
    ArrayList<Project> foundProjects;
    if (search != null) {
      foundProjects = new ArrayList<>(projectRepository.searchFTS(search).
          orElse(new ArrayList<>()));
    } else {
      foundProjects = new ArrayList<>(projectRepository.findAll());
    }
    if (filter != null) {
      ProjectSpecificationsBuilder builder = new ProjectSpecificationsBuilder();
      builder.parse(filter);
      Specification<Project> spec = builder.build();
      filterProjects.addAll(projectRepository.findAll(spec));
      foundProjects.retainAll(filterProjects);
    }
    foundProjects.forEach(project -> foundProjectsDTO.add(new ProjectDTO(project)));
    return foundProjectsDTO;
  }

  /**
   * Find a project by its id and converts it to {@link ProjectDTO}.
   *
   * @param id the project id
   * @return a projects as DTO
   * @throws ObjectNotFoundException
   */
  @GetMapping("/projects/{id}")
  ProjectDTO getOneProject(@PathVariable Long id) {
    log.info("GET: /projects/{id}");
    Project project = projectRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
    return new ProjectDTO(project);
  }

  /**
   * Gets the {@link de.rki.mamodar.Resource}s for one project and converts them to {@link
   * de.rki.mamodar.ResourceDTO}s.
   *
   * @param id the id
   * @return the resources for one project
   */
  @GetMapping("/projects/{id}/resources")
  List<ResourceDTO> getResourcesForOneProject(@PathVariable Long id) {
    log.info("GET: /projects/{id}/resources");
    Project project = projectRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
    List<ResourceDTO> resourceDTOs = new ArrayList<>();
    resourceRepository.getByProject(project).forEach(resource -> resourceDTOs.add(new ResourceDTO(resource)));
    return resourceDTOs;
  }

  /**
   * Gets the {@link de.rki.mamodar.Value}s for one project and converts them to {@link de.rki.mamodar.ValueDTO}s.
   *
   * @param id the id
   * @return the values for one project
   */
  @GetMapping("/projects/{id}/values")
  List<ValueDTO> getValuesForOneProject(@PathVariable Long id) {
    log.info("GET: /projects/{id}/values");
    Project project = projectRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
    ArrayList<ValueDTO> valueDTOs = new ArrayList<>();
    valueRepository.getByProjectRdmoId(project.getRdmoId(), Sort.by(Direction.ASC, "attribute")).forEach(value -> {
      if (value != null) {
        valueDTOs.add(new ValueDTO(value));
      }
    });
    valueDTOs.removeIf(valueDTO -> valueDTO.getAnswerText().isBlank());
    return valueDTOs;

  }
}




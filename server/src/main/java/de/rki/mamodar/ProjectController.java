package de.rki.mamodar;

import de.rki.mamodar.rdmo.RdmoApiConsumer;
import de.rki.mamodar.rdmo.RdmoConverter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
   * @param projectRepository the project repository
   * @param userRepository    the user repository
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
   * Gets a list of the projects and converts them to {@link de.rki.mamodar.ProjectSendDTO} for a {@link
   * de.rki.mamodar.User}**. Tries to update all projects by calling {@link RdmoApiConsumer} first.
   *
   * @return a list of projects as DTOs
   */
  @GetMapping("/projects")
  List<ProjectSendDTO> allProjects() {
    log.info("GET: /projects/rdmo " + authenticationFacade.getLdapUser().getDn());
    ArrayList<ProjectSendDTO> allProjects = new ArrayList<>();
    projectRepository.findAll(Sort.by(Direction.ASC, "projectName")).
        forEach(project -> allProjects.add(new ProjectSendDTO(project)));
    allProjects.removeIf(project -> !project.getOwner().contains(authenticationFacade.getLdapUser().getUsername()));
    return allProjects;

  }

  /**
   * Searches in all projects for projects matching the  search parameter and converts them to {@link
   * de.rki.mamodar.ProjectSendDTO}**
   *
   * @param search the search parameter
   * @return a list of projects as DTOs
   */
  @GetMapping("/projects/search")
  List<ProjectSendDTO> searchProjects(@RequestParam(name = "search") String search) {
    log.info("GET: /projects?search " + ((UserDetails) authenticationFacade.getAuthentication().getPrincipal())
        .getUsername());
    ArrayList<Project> foundProjects = (projectRepository.searchFTS(search).orElse(new ArrayList<>()));
    ArrayList<ProjectSendDTO> foundProjectsDTO = new ArrayList<>();
    foundProjects.forEach(project -> foundProjectsDTO.add(new ProjectSendDTO(project)));

    return foundProjectsDTO;
  }

  /**
   * Find a project by its id and converts it to {@link de.rki.mamodar.ProjectSendDTO}.
   *
   * @param id the project id
   * @return a projects as DTO
   * @throws ObjectNotFoundException
   */
  @GetMapping("/projects/{id}")
  ProjectSendDTO findProjectById(@PathVariable Long id) {
    log.info("GET: /projects/{id}");
    Project project = projectRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
    return new ProjectSendDTO(project);
  }

  @GetMapping("/projects/{id}/resources")
  List<ResourceSendDTO> findResourcesForProject(@PathVariable Long id) {
    log.info("GET: /projects/{id}/resources");
    Project project = projectRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
    List<ResourceSendDTO> resourceSendDTOs = new ArrayList<>();
    resourceRepository.findByProject(project).forEach(resource -> resourceSendDTOs.add(new ResourceSendDTO(resource)));
    return resourceSendDTOs;
  }

  @GetMapping("/projects/{id}/values")
  List<ValueSendDTO> findInformationForProject(@PathVariable Long id) {
    log.info("GET: /projects/{id}/values");
    Project project = projectRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
    List<ValueSendDTO> valueSendDTOs = new ArrayList<>();
    valueRepository.getByProjectId(project.getId()).forEach(value -> valueSendDTOs.add(new ValueSendDTO(value)));
    return valueSendDTOs;

  }
}




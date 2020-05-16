package de.rki.mamodar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
 * This controller provides a REST API to allow read access to projects and their corresponding resources.
 * Projects are read from an RDMO instance, hence create, update, or delete are not provided.
 * @author Kyanoush Yahosseini
 */
@RestController
@CrossOrigin(origins = "*")
public class ProjectController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ProjectRepository repository;
  /**
   * The autowired {@link de.rki.mamodar.RdmoRestConsumer} for accessing all RDMO projects.
   */
  @Autowired
   RdmoRestConsumer rdmoRestConsumer;
  /**
   * The autowired  {@link de.rki.mamodar.AuthenticationFacade} to validate the authenticity of the calling user.
   */
  @Autowired
  AuthenticationFacade authenticationFacade;

  /**
   * Instantiates a new Project controller.
   *
   * @param repository the project repository
   */
  public ProjectController(ProjectRepository repository) {
    this.repository = repository;
  }


  /**
   * Gets a list of the projects and converts them to {@link de.rki.mamodar.ProjectSendDTO} for a {@link
   * de.rki.mamodar.User}**. Tries to update all projects by calling {@link de.rki.mamodar.RdmoRestConsumer} first.
   *
   * @return a list of projects as DTOs
   */
  @GetMapping("/projects")
  List<ProjectSendDTO> allRdmo() {
    ArrayList<ProjectSendDTO> allProjects = new ArrayList<>();
    try {
      log.info("GET: /projects/rdmo " + authenticationFacade.getLdapUser().getDn());
      ArrayList<Project> rdmoResponse = rdmoRestConsumer.getProjectsFromRdmo();
      updateProjects(rdmoResponse);
    } finally {
      repository.findAll(Sort.by(Direction.ASC, "projectName")).
          forEach(project -> allProjects.add(new ProjectSendDTO(project)));
      allProjects.removeIf(project -> !project.getOwner().equals(authenticationFacade.getLdapUser().getUsername()));

      return allProjects;
    }

  }

  /**
   * Searches in all projects for projects matching the  search parameter and converts them to {@link
   * de.rki.mamodar.ProjectSendDTO}**
   *
   * @param search the search parameter
   * @return a list of projects as DTOs
   */
  @GetMapping("/projects/search")
  List<ProjectSendDTO> searchProject(@RequestParam(name = "search") String search){

    ArrayList<Project> foundProjects = (repository.searchFTS(search).orElse(new ArrayList<>()));
    ArrayList<ProjectSendDTO> foundProjectsDTO = new ArrayList<>();
    foundProjects.forEach(project -> foundProjectsDTO.add(new ProjectSendDTO(project)));
    log.info("GET: /projects?search " + ((UserDetails)authenticationFacade.getAuthentication().getPrincipal()).getUsername());
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
  ProjectSendDTO findId(@PathVariable Long id) {
    log.info("GET: /projects/{id}");
    Project project =  repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
    return new ProjectSendDTO(project,project.getResources());
  }


  private void updateProjects(ArrayList<Project> projects) {
    projects.forEach(p->updateProject(p));
  }

  private void updateProject(Project updatedProject){
    Optional<Project> project = repository.findByRdmoId(updatedProject.getRdmoId());
    if(project.isPresent()){
      project.get().setProjectName(updatedProject.getProjectName());
      project.get().setDescription(updatedProject.getDescription());
      project.get().setOwner(updatedProject.getOwner());
      repository.save(project.get());
    }else{
      repository.save(updatedProject);
    }
  }

}

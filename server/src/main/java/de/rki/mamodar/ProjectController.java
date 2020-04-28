package de.rki.mamodar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ProjectController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ProjectRepository repository;

  @Autowired
   RdmoRestConsumer rdmoRestConsumer;

  public ProjectController(ProjectRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/projects/")
  List<Project> all() {
    log.info("GET: /projects/");
    return repository.findAll();
  }

@GetMapping("/projects")
List<Project> searchProject(@RequestParam(name = "search") String search){
return (repository.searchFTS(search).orElse(new ArrayList<>()));
}
  @GetMapping("/projects/rdmo")
  List<Project> allRdmo(Authentication authentication) {
    log.info(authentication.getPrincipal().toString());
    ArrayList<Project> rdmoResponse = rdmoRestConsumer.getProjectsFromRdmo();
    updateProjects(rdmoResponse);
    return repository.findAll();
  }

  @GetMapping("/projects/{id}")
  Project findId(@PathVariable Long id) {
    log.info("GET: /projects/{id}");
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
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

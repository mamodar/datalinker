package de.rki.mamodar;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ProjectRepository repository;

  public ProjectController(ProjectRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/projects/")
  List<Project> all() {
    log.info("GET: /projects/");
    return repository.findAll();
  }

  @GetMapping("/projects/{id}")
  Project findId(@PathVariable Long id) {
    log.info("GET: /projects/{id}");
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("project", id));
  }

  @GetMapping("/projects/search")
  List<Project> title(@RequestParam(value = "title", required = true) String title) {
    log.info("GET: /projects/search");
    return repository.findByProjectNameContainingIgnoreCase(title);
  }


  @PostMapping("/projects/")
  Project addProject(@RequestBody Project project) {
    log.info("POST: /projects/");
    project.setCreationTimestamp(new Date());
    repository.save(project);
    return project;
  }

}

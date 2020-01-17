package de.rki.mamodar;

import java.util.Date;
import java.util.List;
import org.aspectj.asm.internal.Relationship;
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
@CrossOrigin(origins = "*")
public class ProjectController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ProjectRepository repository;
  private final ProjectResourceRepository projectResourceRepository;

  public ProjectController(ProjectRepository repository, ProjectResourceRepository projectResourceRepository) {
    this.repository = repository;
    this.projectResourceRepository = projectResourceRepository;
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
  @PostMapping("/projects/")
  Project addProject(@RequestBody Project project) {
    log.info("POST: /projects/");
    project.setCreationTimestamp(new Date());
    repository.save(project);
    return project;
  }

}
